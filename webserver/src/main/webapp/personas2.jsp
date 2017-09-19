<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.model.UserPreference" %><%--
  Created by IntelliJ IDEA.
  User: swz
  Date: 2017/9/11
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/personas.css">
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
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        <%
           String phone=(String)request.getAttribute("phone");
           String preference=(String) request.getAttribute("preference");
           Boolean message=(Boolean) request.getAttribute("message");
           ArrayList userdevicelist=(ArrayList)request.getAttribute("userdevicelist");
           ArrayList plist=(ArrayList)request.getAttribute("preferencelist");
           String []userphone=new String[100];
           String []label=new String[100];
           int []type=new int[100];
           for(int i=0;i<plist.size();i++){
               UserPreference userdlist=(UserPreference)plist.get(i);
               userphone[i]=userdlist.getPhone();
               label[i]=userdlist.getLabel();
               type[i]=userdlist.getType();
           }
           ArrayList device=(ArrayList)request.getAttribute("device");

           ArrayList list6=(ArrayList)request.getAttribute("phonelist");

         %>

        if(<%=phone==null||phone.equals("")%>){

                option = {
                    backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
                        offset: 0,
                        color: '#f7f8fa'
                    }, {
                        offset: 1,
                        color: '#cdd0d5'
                    }]),
                    title:{
                        text: "用户画像",
                        subtext: "用户喜好程度",
                        top: "top",
                        left: "center"
                    },
                    tooltip: {},
                    legend: [{
                        formatter: function (name) {
                            return echarts.format.truncateText(name, 40, '14px Microsoft Yahei', '…');
                        },

                        tooltip: {
                            show: true
                        },
                        selectedMode: 'false',
                        bottom: 20

                    }],
                    toolbox: {
                        show : true,
                        feature : {
                            dataView : {show: true, readOnly: true},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    animationDuration: 3000,
                    animationEasingUpdate: 'quinticInOut',
                    series: [{
                        name: '用户画像',
                        type: 'graph',
                        layout: 'force',

                        force: {
                            repulsion: 50
                        },
                        data:(function (){
                            var res=[];
                            var item={"name": "<%=label[0]%>",
                                "value": 0,
                                "symbolSize":24,
                                "category":"<%=label[0]%>" ,
                                "draggable": "true"};
                            res.push(item);

                           // alert(res);

                            <% int temp=0;%>
                            for(var i=0;i<<%=plist.size()%>;i++){
                                var temp={
                                    "name": "<%=userphone[temp]%>",
                                    "value": <%=type[temp]%>,
                                    "symbolSize":18,
                                    "category":"<%=label[0]%>" ,
                                    "draggable": "true"
                                }
                                res.push(temp);
                                <%temp++;%>
                            }

//            <th>res</th>
                            return res;
                        })(),

                        links: [{
                            "source": "广州大学",
                            "target": "计算机科学与教育软件学院"
                        }],
                        categories: [{
                            'name': '丰田'
                        }],
                        focusNodeAdjacency: true,
                        roam: true,
                        label: {
                            normal: {

                                show: true,
                                position: 'top',

                            }
                        },
                        lineStyle: {
                            normal: {
                                color: 'source',
                                curveness: 0,
                                type: "solid"
                            }
                        }
                    }]
                };

        }



//        option = {
//            backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
//                offset: 0,
//                color: '#f7f8fa'
//            }, {
//                offset: 1,
//                color: '#cdd0d5'
//            }]),
//            title:{
//                text: "用户画像",
//                subtext: "用户喜好关系",
//                top: "top",
//                left: "center"
//            },
//            tooltip: {},
//            legend: [{
//                formatter: function (name) {
//                    return echarts.format.truncateText(name, 40, '14px Microsoft Yahei', '…');
//                },
//                tooltip: {
//                    show: true
//                },
//                selectedMode: 'false',
//                bottom: 20,
//                data: ['下载', '신라인터넷면세점', '丰田', '维修', 'Sina', '阅文', '新浪网', '人才网', '人民政府', '山东', '服务平台', '秤', 'CA002', '未来', '小游戏', '404', '风行', '昆山', '百科', '旅游网']
//            }],
//            toolbox: {
//                show : true,
//                feature : {
//                    dataView : {show: true, readOnly: true},
//                    restore : {show: true},
//                    saveAsImage : {show: true}
//                }
//            },
//            animationDuration: 3000,
//            animationEasingUpdate: 'quinticInOut',
//            series: [{
//                name: '用户画像',
//                type: 'graph',
//                layout: 'force',
//
//                force: {
//                    repulsion: 50
//                },
//                data: [{
//                    "name": "丰田",
//                    "value": 3,
//                    "symbolSize":24,
//                    "category": "丰田",
//                    "draggable": "true"
//                },{
//                    "name": "下载",
//                    "value": 6,
//                    "symbolSize": 24,
//                    "category": "下载",
//                    "draggable": "true"
//                }, {
//                    "name": "신라인터넷면세점",
//                    "value": 5,
//                    "symbolSize": 24,
//                    "category": "신라인터넷면세점",
//                    "draggable": "true"
//                },{
//                    "name": "维修",
//                    "value": 6,
//                    "symbolSize": 24,
//                    "category": "维修",
//                    "draggable": "true"
//                }, {
//                    "name": "Sina",
//                    "value": 8,
//                    "symbolSize": 24,
//                    "category": "Sina",
//                    "draggable": "true"
//                }, {
//                    "name": "阅文",
//                    "value": 5,
//                    "symbolSize": 24,
//                    "category": "阅文",
//                    "draggable": "true"
//                }, {
//                    "name": "新浪网",
//                    "value": 6,
//                    "symbolSize": 24,
//                    "category": "新浪网",
//                    "draggable": "true"
//                },  {
//                    "name": "人才网",
//                    "value": 10,
//                    "symbolSize": 24,
//                    "category": "人才网",
//                    "draggable": "true"
//                },{
//                    "name": "人民政府",
//                    "value": 6,
//                    "symbolSize": 24,
//                    "category": "人民政府",
//                    "draggable": "true"
//                }, {
//                    "name": "山东",
//                    "value": 6,
//                    "symbolSize": 24,
//                    "category": "山东",
//                    "draggable": "true"
//                },{
//                    "name": "服务平台",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "服务平台",
//                    "draggable": "true"
//                },{
//                    "name": "秤",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "秤",
//                    "draggable": "true"
//                },{
//                    "name": "CA002",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "CA002",
//                    "draggable": "true"
//                },{
//                    "name": "未来",
//                    "value": 2,
//                    "symbolSize":24,
//                    "category": "未来",
//                    "draggable": "true"
//                },{
//                    "name": "小游戏",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "小游戏",
//                    "draggable": "true"
//                },{
//                    "name": "404",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "404",
//                    "draggable": "true"
//                },{
//                    "name": "旅游网",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "旅游网 ",
//                    "draggable": "true"
//                },{
//                    "name": "风行",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "风行",
//                    "draggable": "true"
//                },{
//                    "name": "昆山",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "昆山",
//                    "draggable": "true"
//                },{
//                    "name": "百科",
//                    "value": 2,
//                    "symbolSize": 24,
//                    "category": "百科",
//                    "draggable": "true"
//                }],
//                links: [{
//                    "source": "广州大学",
//                    "target": "计算机科学与教育软件学院"
//                },],
//                categories: [{
//                    'name': '下载'
//                }, {
//                    'name': '신라인터넷면세점'
//                }, {
//                    'name': '维修'
//                }, {
//                    'name': 'Sina'
//                }, {
//                    'name': '阅文'
//                }, {
//                    'name': '新浪网'
//                }, {
//                    'name': '人才网'
//                }, {
//                    'name': '人民政府'
//                }, {
//                    'name': '山东'
//                }, {
//                    'name': '服务平台'
//                }, {
//                    'name': '秤'
//                }, {
//                    'name': 'CA002'
//                }, {
//                    'name': '未来'
//                }, {
//                    'name': '小游戏'
//                }, {
//                    'name': '404'
//                }, {
//                    'name': '风行'
//                }, {
//                    'name': '昆山'
//                }, {
//                    'name': '百科'
//                }, {
//                    'name': '旅游网'
//                },{
//                    'name': '丰田'
//                }],
//                focusNodeAdjacency: true,
//                roam: true,
//                label: {
//                    normal: {
//
//                        show: true,
//                        position: 'top',
//
//                    }
//                },
//                lineStyle: {
//                    normal: {
//                        color: 'source',
//                        curveness: 0,
//                        type: "solid"
//                    }
//                }
//            }]
//        };
        myChart.setOption(option);



    </script>

    </body>
</div>

</body>
</html>
