<%--
  Created by IntelliJ IDEA.
  User: swz
  Date: 2017/9/11
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/activity.css">
    <link rel="stylesheet" href="css/toolbar.css">
    <script src="js/toolbar.js"></script>
	<%--<script src="jquery/jquery-3.2.1.min.js"></script>--%>
    <%--<script>--%>
            <%--$("#form").submit(function showData() {--%>
                <%--var date = $("#date").value;--%>
                <%--alert(0);--%>
                <%--$.ajax({--%>
                    <%--url: "CityRateDataDateServlet.do",--%>
                    <%--data: {date: date},--%>
                    <%--dataType: "json",--%>
                    <%--success: function (data) {--%>
                        <%--alert(data.toString());--%>
                    <%--}--%>
                <%--})--%>
            <%--})--%>
  <%--</script>--%>
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

<div id="querydiv">
    <div>
        <h1>Activity analysis system</h1>
        <hr>
        <p>
            Please input the date you want to query.
        </p>
        <form id="form" action="CityRateDataDateServlet.do" method="post">
            <input type="text" name="date" value="2017-01-01" id="date">
            <input type="submit" value="query" id="querybutton">
        </form>
    </div>
</div>
</div>
</body>
</html>
