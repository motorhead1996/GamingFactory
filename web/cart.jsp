<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>
<% if (!isOkuser) {
        response.sendRedirect("login.jsp");
    } else {%>

<div class="cart" id="cart">

</div>    

<% }%>


<%@include file="footer.jsp" %>
<script src="js/cart.js"></script>
<%@include file="ending.jsp" %>
