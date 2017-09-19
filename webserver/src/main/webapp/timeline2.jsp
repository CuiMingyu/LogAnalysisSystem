<%--
  Created by IntelliJ IDEA.
  User: swz
  Date: 2017/9/11
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.model.TimeRateData" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/timeline.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
</head>

<body>
<div id="background" >
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
    <div>
        <body style="height:500px; top: 60px;">

        <div id="container" style="width: 100%;height:1000px"></div>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>

        <script type="text/javascript">
        <%
           ArrayList list=(ArrayList)request.getAttribute("trdlist");
           int[] pv=new int[8];
           int[] uv=new int[8];
           int num=0;
           System.out.println(list.size());
           for(int i=0;i<list.size();i++){
               TimeRateData timerate=(TimeRateData)list.get(i);
               pv[num]=timerate.getPV();
               uv[num]=timerate.getUV();
               num++;
               i+=4;
           }

        %>

            var dom = document.getElementById("container");
            var myChart = echarts.init(dom);
            option = {
                title: {
                    text: '一天内PU、UV变化',
                    subtext: 'Group5'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['PV','UV']
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
                    data: ['0：00-1:00','4:00-5:00','8:00-9:00','12:00-13:00','16:00-17:00','20:00-21:00','23:00-24:00']
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
                        data:[<%=pv[0]%>, <%=pv[1]%>, <%=pv[2]%>, <%=pv[3]%>,<%=pv[4]%>, <%=pv[5]%>, <%=pv[6]%>],
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
                    },
                    {
                        name:'UV',
                        type:'line',
                        data:[<%=uv[0]%>, <%=uv[1]%>, <%=uv[2]%>, <%=uv[3]%>,<%=uv[4]%>, <%=uv[5]%>, <%=uv[6]%>],
                        markPoint:{
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'},
                                [{
                                    symbol: 'none',
                                    x: '90%',
                                    yAxis: 'max'
                                }, {
                                    symbol: 'circle',
                                    label: {
                                        normal: {
                                            position: 'start',
                                            formatter: '最大值'
                                        }
                                    },
                                    type: 'max',
                                    name: '最高点'
                                }]
                            ]
                        }
                    }
                ]
            };








            myChart.setOption(option);


        </script>

        </body>
    </div>
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
        </ul>
    </div>
</div>
</div>
</body>
</html>
