<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
</head>

<body>
<div id="background" >


    <header>
        <!-- 顶栏开始 -->
        <nav>
            <ul>
                <li class="logo"><a href="index.jsp">Home page</a></li>

                <li><a href="javascript:void(0)" id="menu" onclick="showMenu()">Menu</a></li>
                <li><a href="">Top</a></li>
            </ul>
        </nav>
        <!-- 顶栏结束 -->
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
        <!-- 欢迎模块开始 -->
        <div id="banner">
            <div class="inner">
                <a class="a1">Welcome to Log Analyse System!</a>
                <hr>
                <a class="a2">
                    Welcome to Log Analse System!
                    The early bird catches worm.
                    God helps those who help themselves.
                    We can both of God and the devil.
                </a>
            </div>
        </div>
        <!-- 欢迎模块结束 -->


    </header>

    <!-- 主页内容模块开始 -->
    <div id="content">
        <!-- 地区活跃度介绍模块开始 -->
        <div class="content-title">
            <p>Our Features</p>
        </div>

        <div id="content1">
            <img src="css/images/1.JPEG">
            <a href="activity.jsp" class="a1">用户活跃度</a>
            <a href="activity.jsp" class="a2">
                根据用户的UV和PV来计算某一地区某一天的用户活跃度
            </a>
        </div>

        <div id="content2">
            <img src="css/images/2.JPEG">
            <a href="phone.jsp" class="a1">用户机型</a>
            <a href="phone.jsp" class="a2">
                根据起止日期来查询该日期段内用户手机品牌的数量变化
            </a>
        </div>

        <div id="content3">
            <img src="css/images/3.JPEG">
            <a href="timeline.jsp" class="a1">上网时段</a>
            <a href="timeline.jsp" class="a2">
                通过查询某一天的日期，来得到该天的PV和UV
            </a>
        </div>

        <div id="content4">
            <img src="css/images/4.JPEG">
            <a href="personas.jsp" class="a1">用户画像</a>
            <a href="personas.jsp" class="a2">
                根据用户手机号或者用户的喜好来查询出该用户的所有喜好或拥有该喜好的所有用户
            </a>
        </div>

        <div id="content5">
            <img src="css/images/5.JPEG">
            <a href="website.jsp" class="a1">热门网站分析top20</a>
            <a href="website.jsp" class="a2">
                根据所存信息来分析活跃度、PV、UV最多的前20个网站
            </a>
        </div>

        <div id="content6">
            <img src="css/images/6.JPEG">
            <a href="predict.jsp" class="a1">用户流量预测</a>
            <a href="predict.jsp" class="a2">
                根据用户手机号来预测未来三天该用户可能会使用的流量值
            </a>
        </div>
        <!-- 地区活跃度介绍模块开始 -->
    </div>
    <!-- 主页内容模块结束 -->
</div>
</body>
</html>