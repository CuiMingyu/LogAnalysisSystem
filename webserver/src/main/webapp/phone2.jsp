<%--
  Created by IntelliJ IDEA.
  User: swz
  Date: 2017/9/11
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.model.NewDeviceMachine" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/phone.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
    <script src = "jquery/jquery-3.2.1.min.js"></script>

    <%--<script>--%>
        <%--$(document).ready(function () {--%>
            <%--$.ajax(--%>
                <%--{--%>
                    <%--url:"NewDeviceMachineServlet.do",--%>
                    <%--type:"get",--%>
                    <%--dataType:"json",--%>
                    <%--success:function (data) {--%>
                        <%--var list = data.data;--%>
                        <%--var series = [];--%>
                        <%--for(var i = 0;i<list.length;i++)--%>
                        <%--{--%>
                            <%--series.push(list[i])--%>
                        <%--}--%>
                        <%--option.series = series;--%>
                        <%--myChart.setOption(option)--%>
                    <%--},--%>
                    <%--error:function(msg)--%>
                    <%--{--%>
                        <%--alert(msg);--%>
                    <%--}--%>
                <%--}--%>
            <%--)--%>
        <%--});--%>
    <%--</script>--%>
</head>

<body>
<div id="background2" >
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

<div>
    <div style="position:relative;top:60px;width:100%;">

        <div id="container" style="width:100%;height:1000px"></div>
        <a href="phone.jsp" style="display:block;margin:15px auto;
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
            ArrayList list=(ArrayList)request.getAttribute("ndmlist");

            int []num =new int[25] ;
            int []addnum=new int[25];
            int []result=new int[25];
            for(int i=0;i<list.size();i++){
                 NewDeviceMachine phonename=(NewDeviceMachine)list.get(i);

                 num[i]=phonename.getOldnum();
                 addnum[i]=phonename.getNewnum();
                 result[i]=phonename.getAllnum();
                System.out.println(num[0]);
                System.out.println(num[1]);
                System.out.println(num[2]);
            }
            %>

            var dom = document.getElementById("container");
            var myChart = echarts.init(dom);

            <%--ArrayList list=(ArrayList)request.getAttribute("ndmlist");--%>
            <%--<%int i = 0;%>--%>
            option=null;
            option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['初始数量','新增数量','总数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',

                        data : ['360手机','HTC','LG','Moto','OPPO','vivo','一加手机','三星','中兴','乐视','努比亚','华为','小米','格力','索尼','联想','苹果','荣耀','诺基亚','酷派','金立','魅族']
                    }
                ],

                yAxis : [
                    {
                        type : 'value',
                        data:[]
                    }
                ],
                series : [
                    {
                        <%--<%NewDeviceMachine phonename=(NewDeviceMachine)list.get(i);%>--%>
                        name:'初始数量',
                        type:'bar',
                        data:[<%=num[0]%>,<%=num[1]%>,<%=num[2]%>,<%=num[3]%>,<%=num[4]%>,<%=num[5]%>,<%=num[6]%>,<%=num[7]%>,<%=num[8]%>,<%=num[9]%>,<%=num[10]%>,
                            <%=num[11]%>,<%=num[12]%>,<%=num[13]%>,<%=num[14]%>,<%=num[15]%>,<%=num[16]%>,<%=num[16]%>,<%=num[17]%>,<%=num[18]%>,<%=num[19]%>,<%=num[20]%>
                            ]
                    },
                    {
                        name:'新增数量',
                        type:'bar',
                        stack: '广告',
                        data:[<%=addnum[0]%>,<%=addnum[1]%>,<%=addnum[2]%>,<%=addnum[3]%>,<%=addnum[4]%>,<%=addnum[5]%>,<%=addnum[6]%>,<%=addnum[7]%>,<%=addnum[8]%>,<%=addnum[9]%>,<%=addnum[10]%>,
                            <%=addnum[11]%>,<%=addnum[12]%>,<%=addnum[13]%>,<%=addnum[14]%>,<%=addnum[15]%>,<%=addnum[16]%>,<%=addnum[16]%>,<%=addnum[17]%>,<%=addnum[18]%>,<%=addnum[19]%>,<%=addnum[20]%>

                        ]
                    },


                    {
                        name:'总数量',
                        type:'bar',
                        data:[<%=result[0]%>,<%=result[1]%>,<%=result[2]%>,<%=result[3]%>,<%=result[4]%>,<%=result[5]%>,<%=result[6]%>,<%=result[7]%>,<%=result[8]%>,<%=result[9]%>,<%=result[10]%>,
                            <%=result[11]%>,<%=result[12]%>,<%=result[13]%>,<%=result[14]%>,<%=result[15]%>,<%=result[16]%>,<%=result[16]%>,<%=result[17]%>,<%=result[18]%>,<%=result[19]%>,<%=result[20]%>
                        ],
                        markLine : {
                            lineStyle: {
                                normal: {
                                    type: 'dashed'
                                }
                            },
                            data : [
                                [{type : 'min'}, {type : 'max'}]
                            ]
                        }
                    },

                ]
            };


            <%--option.series[0].data.push(<%phonename.getOldnum();%>);--%>
            <%--<%}}%>--%>
            <%--option.series[0].data=series--%>


            myChart.setOption(option);


    </script>

    </div>
</div>
</div>
</body>
</html>

