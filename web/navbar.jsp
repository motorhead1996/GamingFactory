<%@page import="model.User"%>
<%  User user = null;
    boolean isOkuser = false;
    try {
        user = (User) request.getSession().getAttribute("user");
        if (!user.equals(null)) {
            isOkuser = true;
        }
    } catch (NullPointerException ex) {
        isOkuser = false;
    }
%>
<nav class="navbar">
    <div class="navbarcontainer">
        <a href="index.jsp" id="navbarlogo">GAMING FACTORY</a>
        <ul class="navbarmenu">
            <li class="navbaritem">
                <a href="pc.jsp" class="navbarlinks">PC</a>
            </li>
            <li class="navbaritem">
                <a href="playstation.jsp" class="navbarlinks">PlayStation</a>
            </li>
            <li class="navbaritem">
                <a href="xbox.jsp" class="navbarlinks">XBox</a>
            </li>
            <% if (isOkuser) { %>
            <% if (user.isLvl()) { %>
            <li class="navbarbtn"><a href="admin.jsp" class="button">Admin</a></li>
                <% } %>
            <li class="navbarbtn"><a href="cart.jsp" class="button">Cart</a></li>
            <li class="navbarbtn"><a href="profile.jsp" class="button">My Profile</a></li>
            <li class="navbarbtn"><a href="Logout" class="button" onclick="restartCart();" >Log Out</a></li>
                <%  } else {  %>
            <li class="navbarbtn"><a href="cart.jsp" class="button">Cart</a></li>
            <li class="navbarbtn"><a href="login.jsp" class="button">Log In</a></li>
            <li class="navbarbtn"><a href="singup.jsp" class="button">Sign Up</a></li>
                <% }%>
        </ul>
    </div>
</nav>
