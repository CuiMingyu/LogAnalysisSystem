<%@ page import="java.util.ArrayList" %>
<%@ page import="main.java.model.ByteSeries" %><%--
  Created by IntelliJ IDEA.
  User: Mingyu
  Date: 2017/9/19
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/phone.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
</head>

<div>
    <div style="height:500px; top: 60px;">

    <div id="container" style="width: 100%;height:1000px"></div>
        <a href="predict.jsp" style="display:block;margin:15px auto;
          width:150px;line-height:50px;background:#000;color:#FFF;
          text-decoration:none;text-align:center;border-radius:15px;
          font-size:28px;">Back</a>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>

    <script type="text/javascript">

        <%
            ArrayList list=(ArrayList)request.getAttribute("bslist");

            String []data =new String[25] ;
            String []time=new String[25];

            for(int i=0;i<list.size();i++){
                 ByteSeries user=(ByteSeries) list.get(i);
                data[i]=user.getData();
                time[i]=user.getTime();

            }
            %>

        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);

        <%--ArrayList list=(ArrayList)request.getAttribute("ndmlist");--%>
        <%--<%int i = 0;%>--%>

        option = {
            title: {
                text: '三天内流量预测',
                subtext: 'Group5'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['data']
            },
            toolbox: {
                show: true,
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    dataView: {readOnly: false},
                    magicType: {type: ['line', 'bar']},
                    restore: {},
                    saveAsImage: {}
                }
            },
            xAxis:  {
                type: 'category',
                boundaryGap: false,
                data: ['<%=time[0]%>>','<%=time[1]%>','<%=time[2]%>']
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            series: [
                {
                    name:'PV',
                    type:'line',
                    data:[<%=data[0]%>, <%=data[1]%>, <%=data[2]%>],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }

            ]
        };


        <%--option.series[0].data.push(<%phonename.getOldnum();%>);--%>
        <%--<%}}%>--%>
        <%--option.series[0].data=series--%>


        myChart.setOption(option);


    </script>

    </div>
</div>
<body>
<div id="background2">
    <header>
        <nav>
            <ul>
                <li class="logo"><a href="index.jsp">Home page</a></li>

                <li><a href="javascript:void(0)" id="menu" onclick="showMenu()">Menu</a></li>
                <li><a href="">Contact</a></li>
                <li><a href="">Top</a></li>
            </ul>
        </nav>
    </header>

    <div id="newMenu">
        <div>
            <h2>Menu</h2>
            <hr>
            <ul>
                <li><a href="activity.jsp">Activity analysis</a></li>
                <li><a href="phone.jsp">Phone analysis</a></li>
                <li><a href="timeline.jsp">Timeline analysis</a></li>
                <li><a href="personas.jsp">Personas analysis</a></li>
                <li><a href="website.jsp">Hot20 websites</a></li>
                <li><a href="predict.jsp">Traffic forecast</a></li>
            </ul>
        </div>
    </div>


</div>
</body>
</html>
