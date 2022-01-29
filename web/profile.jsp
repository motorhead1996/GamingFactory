<%@page import="java.util.HashMap"%>
<%@page import="model.Game"%>
<%@page import="model.Purchase"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Session"%>
<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>
<% if (!isOkuser) {
        response.sendError(404);
    } else {%>
<div class="profile">
    <div class="profilecontainer">
        <h1>My Profile</h1>
        <div class="profileform"> 
            <form action="Profile" method="POST">
                <p>Name</p>  
                <input type="text" id="name" name="name" readonly="true" value="<%=user.getName()%>">
                <p>Surname</p>
                <input type="text" id="sname" name="sname" readonly="true" value="<%=user.getSurname()%>" >
                <p>Email</p>
                <input type="email" id="email" name="email" readonly="true" value="<%=user.getEmail()%>">
                <p>City</p>
                <input type="text" id="city" name="city" readonly="true" value="<%=user.getCity()%>">
                <p>Address</p>
                <input type="text" id="address" name="address" readonly="true" value="<%=user.getAddress()%>">
                <p>Phone</p>
                <input type="text" id="phone" name="phone" readonly="true" value="<%=user.getPhone()%>">
            </form> 
        </div>
    </div>
    <%}
    %>



    <%@include file="footer.jsp" %>
    <%@include file="ending.jsp" %>
