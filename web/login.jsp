<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>
<%if (isOkuser) {
        response.sendError(404);
    } else {%>
<div class="login">
    <div class="logincontainer">
        <h1>LOG IN</h1>
        <div class="loginform">

            <form id="login" method="post" action="Login">
                <p>Email</p>
                <input type="text" name="email" id="loginemail">
                <p>Password</p>
                <input type="password" name="password" id="loginpassword">
            </form>
            <button id="submit" onclick="restartCart();login();">Submit</button>
        </div>
    </div>
</div>
<% } %>


<%@include file="footer.jsp" %>
<script src="js/login.js"></script>
<%@include file="ending.jsp" %>
