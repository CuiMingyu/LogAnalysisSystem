<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/website.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
</head>

<body>
<div id="background">
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

    <div id="content">
        <div id="contenttitle">
            <h1>Hot100 Websites</h1>
            <hr>
        </div>

        <div id="container1"></div>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
        <script type="text/javascript">
            var dom = document.getElementById("container1");
            var myChart = echarts.init(dom);
            var app = {};
            option = null;
            option = {
                title: {
                    text: 'Top20PV网站'
                },
                tooltip: {},
                legend: {
                    data:['TopPV']
                },
                xAxis: {
                    data: ["aitingwang.com","dianping.com","info.b2b168.com","baike.pcbaby.com.cn","qlrc.com",
                        "iask.sina.com.cn","college.zjut.cc","tulaoshi.com","www.maigoo.com","chinapp.com",
                        "pingshu8.com","bbs.yingjiesheng.com","blog.sina.com.cn","ks5u.com","game.ali213.net",
                        "atobo.com.cn","jiancai365.cn","vvjob.com","cncrk.com","jobs.zhaopin.com"],
                    show:false
                },
                yAxis: {},
                series: [{
                    name: 'TopPV',
                    type: 'bar',
                    data: [1089, 512, 501, 472, 466,466,462,444,425,388,380,373,349,346,338,335,322,316,309,306]
                }]
            };;
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        </script>

        <div id="container2"></div>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
        <script type="text/javascript">
            var dom = document.getElementById("container2");
            var myChart = echarts.init(dom);
            var app = {};
            option = null;
            option = {
                title: {
                    text: 'Top20UV网站'
                },
                tooltip: {},
                legend: {
                    data:['TopUV']
                },
                xAxis: {
                    data: ["aitingwang.com","dianping.com","info.b2b168.com","baike.pcbaby.com.cn","qlrc.com",
                        "iask.sina.com.cn","college.zjut.cc","tulaoshi.com","www.maigoo.com","chinapp.com",
                        "pingshu8.com","bbs.yingjiesheng.com","blog.sina.com.cn","ks5u.com","game.ali213.net",
                        "atobo.com.cn","jiancai365.cn","vvjob.com","cncrk.com","jobs.zhaopin.com"],
                    show:false
                },
                yAxis: {},
                series: [{
                    name: 'TopUV',
                    type: 'bar',
                    data: [1089, 512, 501, 472, 466,466,462,444,425,388,380,373,349,346,338,335,322,316,309,306],
                    itemStyle:{
                        normal:{
                            color:function(params){
                                var colorList=
                                    ['rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)',
                                        'rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)',
                                        'rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)',
                                        'rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)','rgb(164,205,238)'];
                                return colorList[params.dataIndex];
                            }
                        }
                    }
                }]
            };;
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        </script>

        <div id="container3"></div>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
        <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
        <script type="text/javascript">
            var dom = document.getElementById("container3");
            var myChart = echarts.init(dom);
            var app = {};
            option = null;
            option = {
                title: {
                    text: 'Top20IP网站'
                },
                tooltip: {},
                legend: {
                    data:['TopIP']
                },
                xAxis: {
                    data: ["aitingwang.com","info.b2b168.com","iask.sina.com.cn","qlrc.com","dianping.com",
                        "college.zjut.cc","baike.pcbaby.com.cn","www.maigoo.com","atobo.com.cn","ks5u.com",
                        "jiancai365.cn","tulaoshi.com","jobs.zhaopin.com","pingshu8.com","chinapp.com",
                        "bbs.yingjiesheng.com","game.ali213.net","blog.sina.com.cn","bbs.3dmgame.com","shilladfs.com"],
                    show:false
                },
                yAxis: {},
                series: [{
                    name: 'TopIP',
                    type: 'bar',
                    data: [222, 153, 152, 149, 145,143,140,137,136,135,135,135,133,131,126,126,125,125,124,123],
                    itemStyle:{
                        normal:{
                            color:function(params){
                                var colorList=
                                    ['rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)',
                                        'rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)',
                                        'rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)',
                                        'rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)','rgb(160,100,238)'];
                                return colorList[params.dataIndex];
                            }
                        }
                    }
                }]
            };;
            if (option && typeof option === "object") {
                myChart.setOption(option, true);
            }
        </script>
    </div>
</div>
</body>
</html>
