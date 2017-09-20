<%--
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
            <li><a href="predict.jsp">Traffic forecast</a></li>
        </ul>
    </div>
</div>

<div>
    <div id="querydiv">
        <div>
            <h1>Personas analysis system</h1>
            <hr>
            <form action="UserDPServlet.do" method="post">
                <scan style="color:#FFF;font-size:20px;">Phone:</scan>
                <input type="text" name="phone" id="phone">
                <scan style="color:#FFF;font-size:20px;">Behaviour preferences:</scan>
                <select name="preference" id="preference">
                    <option value="下载">下载</option>
                    <option value="火车">火车</option>
                    <option value="腾讯">腾讯</option>
                    <option value="404">404</option>
                    <option value="淘宝网">淘宝网</option>
                    <option value="开采">开采</option>
                    <option value="新闻">新闻</option>
                    <option value="首页">首页</option>
                    <option value="提示信息">提示信息</option>
                    <option value="百度">百度</option>
                    <option value="百家讲坛">百家讲坛</option>
                    <option value="快递">快递</option>
                    <option value="新浪">新浪</option>
                    <option value="爱">爱</option>
                    <option value="云币">云币</option>
                    <option value="PANDORA">PANDORA</option>
                    <option value="手抄报">手抄报</option>
                    <option value="解梦">解梦</option>
                    <option value="人才网">人才网</option>
                    <option value="加载">加载</option>
                </select>

                <input type="submit" value="query" id="querybutton">
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
