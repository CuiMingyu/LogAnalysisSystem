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
                <scan style="color:#FFF;font-size:20px;">Behaviour preferences:<region/scan>
                <select name="preference" id="preference">
                    <option value="下载">下载</option>
                    <option value="丰田">丰田</option>
                </select>

                <input type="submit" value="query" id="querybutton">
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
