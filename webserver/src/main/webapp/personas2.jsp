<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="main.java.model.UserPreference" %>
<%@ page import="main.java.model.UserDev" %><%--
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
</div>
<div>
    <div style="position:relative; width: 100%; top: 80px;">

    <div id="container" style="width: 100%;height: 130%"></div>
    <a href="personas.jsp" style="display:block;margin:15px auto;
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
        var dom = document.getElementById("container");
        var myChart = echarts.init(dom);
        <%
           String phone=(String)request.getAttribute("phone");
           System.out.println(phone);
           String preference=(String) request.getAttribute("preference");
           Boolean message=(Boolean) request.getAttribute("message");
           ArrayList userdevicelist=(ArrayList)request.getAttribute("userdevicelist");
           UserDev device = null;
           if(request.getAttribute("device")!=null)
               device =(UserDev)request.getAttribute("device");
           ArrayList<UserPreference> plist=(ArrayList)request.getAttribute("preferencelist");
           ArrayList phonelist=(ArrayList)request.getAttribute("phonelist");
           String []userphone=new String[100];
           String []label=new String[100];
           int []type=new int[100];
           for(int i=0;i<plist.size();i++){
               UserPreference userdlist=(UserPreference)plist.get(i);
               userphone[i]=userdlist.getPhone();
               System.out.println(preference);
               label[i]=userdlist.getLabel();
               type[i]=userdlist.getType();
           }


         %>

        if(<%=phone==null||phone.equals("")%>)
        {
            if(<%=preference==null%>){
                //提示框输入内容
                alert("请输入内容");
                }
            else {
                <%System.out.println(phone);%>;
                option = {
                    backgroundColor: new echarts.graphic.RadialGradient(0, 0, 0, [{
                        offset: 0,
                        color: '#f7f8fa'
                    }, {
                        offset: 1,
                        color: '#cdd0d5'
                    }]),
                    title: {
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
                        bottom: 20,
                        data: (function () {
                            var res = [];
                            var len = 0;
                            <%
                            List<String> phoneString1 = new ArrayList<String>();
                            for(int i = 0;i < plist.size();i++)
                                {
                                    phoneString1.add(userphone[i].toString());
                                }
                            %>
                            var phoneString = [];
                            phoneString = <%=phoneString1%>;
                            while (len <<%=plist.size()%>) {
                                res.push({
                                    name: phoneString[len],
                                })
                                len++;
                            }
                            return res;
                        })()
                    }],
                    toolbox: {
                        show: true,
                        feature: {
                            dataView: {show: true, readOnly: true},
                            restore: {show: true},
                            saveAsImage: {show: true}
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

                        data: (function () {
                            var res = [];
                            var len = 0;
                            res.push({
                                name: '<%=preference%>',
                                value:<%=plist.size()%>,
                                symbolSize: 30,
                                draggable: 'true',
                                category: '<%=preference%>'
                            });
                            <%
                            List<String> phoneString = new ArrayList<String>();
                            List<Integer> typeInt = new ArrayList<Integer>();
                            List<Integer> sizeInt = new ArrayList<Integer>();
//                            List<String> freqString = new ArrayList<String>();
                            for(int i = 0;i < plist.size();i++)
                                {
                                    phoneString.add(userphone[i].toString());
                                    typeInt.add(new Integer(type[i]));
                                    if(type[i] == 0)
                                    {
//                                       freqString.add('"'+"低频"+'"');
                                       sizeInt.add(new Integer(12));
                                    }
                                    else if(type[i] == 1)
                                    {
//                                       freqString.add('"'+"中频"+'"');
                                       sizeInt.add(new Integer(18));
                                    }

                                    else
                                    {
//                                       freqString.add('"'+"高频"+'"');
                                       sizeInt.add(new Integer(24));
                                    }

                                }
                            %>
                            var phoneString = [];
                            phoneString = <%=phoneString%>;

                            var typeInt = [];
                            typeInt = <%=typeInt%>;
                            var sizeInt = [];
                            sizeInt = <%=sizeInt%>;
                            var freqString = [];
                            <%--freqString = <%=freqString%>--%>
                            while (len <<%=plist.size()%>) {
                                res.push({
                                    name: phoneString[len],
                                    value: typeInt[len],
                                    draggable: 'true',
                                    symbolSize: sizeInt[len],
                                    //category:phoneString[len]
                                })
                                len++;
                            }
                            return res;
                        })(),
                        <%--links: (function () {--%>
                        <%--var res = [];--%>
                        <%--var len = 0;--%>
                        <%--var phoneString = [];--%>
                        <%--phoneString = <%=phoneString%>;--%>
                        <%--while(len<<%=plist.size()%>)--%>
                        <%--{--%>
                        <%--res.push({--%>
                        <%--source:'<%=preference%>',--%>
                        <%--target:phoneString[len]--%>
                        <%--})--%>
                        <%--len++;--%>
                        <%--}--%>
                        <%--return res;--%>
                        <%--})(),--%>
                        categories: (function () {
                            var res = [];
                            var len = 0;
                            res.push({
                                name: '<%=preference%>',
                            });
                            var phoneString = [];
                            phoneString = <%=phoneString%>;
                            while (len <<%=plist.size()%>) {
                                res.push({
                                    name: phoneString[len],
                                })
                                len++;
                            }
                            return res;
                        })(),

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
        }
        else{
            <%System.out.println(1);%>
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
                    subtext: "用户喜好关系",
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
                    bottom: 20,

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
                    name: '具体用户画像',
                    type: 'graph',
                    layout: 'force',

                    force: {
                        repulsion: 50
                    },
                    data: (function () {
                        var res = [];
                        var len = 0;
                        res.push({
                            name: '<%=phone%>',
                            value:<%=plist.size()%>,
                            symbolSize: 30,
                            draggable: 'true'
                        });
                        res.push({
                            name:'<%=(device!=null?device.getDevice():"none")%>',
                            value:0,
                            symbolSize:25,
                            draggable:'true'
                        });
                        <%
                       List<String> phoneString0 = new ArrayList<String>();
//                       List<String> freqString0 = new ArrayList<String>();
                       List<Integer> typeInt0 = new ArrayList<Integer>();
                       List<Integer> sizeInt0 = new ArrayList<Integer>();
                       for(int i = 0;i < plist.size();i++)
                           {
                               phoneString0.add('"'+ plist.get(i).getLabel() + '"');
                               typeInt0.add(new Integer(type[i]));
                               if(type[i] == 0)
                                   {
//                                   freqString0.add('"'+"低频"+'"');
                                   sizeInt0.add(new Integer(12));
                                   }
                               else if(type[i] == 1)
                                   {
//                                    freqString0.add('"'+"中频"+'"');
                                    sizeInt0.add(new Integer(18));
                                   }

                               else
                                   {
//                                   freqString0.add('"'+"高频"+'"');
                                   sizeInt0.add(new Integer(24));
                                   }
                           }
                       %>
                        var phoneString = [];
                        phoneString = <%=phoneString0%>;
                        var typeInt = [];
                        typeInt = <%=typeInt0%>;
                        var sizeInt = [];
                        sizeInt = <%=sizeInt0%>;
                        var freqString = [];
                        <%--freqString = <%=freqString0%>;--%>
                        while (len <<%=plist.size()%>) {
                            res.push({
                                name: phoneString[len],
                                value: typeInt[len],
                                draggable: 'true',
                                symbolSize: sizeInt[len],
                                //category:phoneString[len]
                            });
                            len++;
                        }
                        return res;
                    })(),


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




        myChart.setOption(option);



    </script>

    </div>
</div>

</body>
</html>