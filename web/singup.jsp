<%@include file="head.jsp" %>
<%@include file="navbar.jsp" %>

<%if (isOkuser) {
        response.sendError(404);
    } else {

%>
<div class="registration">
    <div class="registrationcontainer">
        <h1>REGISTRATION</h1>
        <div class="registrationform">

            <form id="register" method="post" action="Singup">
                <p>Name</p>
                <input id="name" type="text" name="name">
                <p class="error">*name cant contain spaces or numbers, max 32 characters</p>
                <p>Surname</p>
                <input id="surname" type="text" name="surname">
                <p class="error">*surname cant contain spaces or numbers, max 32 characters</p>
                <p>Email</p>
                <input id="email" type="text" name="email">
                <p>Password</p>
                <input id="password1" type="password" name="password">
                <p class="error">*minimum eight characters, at least one letter and one number, max 32 characters</p>
                <p>Repeat Password</p>
                <input id="password2" type="password" name="repeatpassword">
                <p>City</p>
                <input id="city" type="text" name="city">
                <p class="error">*city cant contain special characters and numbers</p>
                <p>Address</p>
                <input id="address" type="text" name="address">
                <p class="error">*address cant contain special characters</p>
                <p>Phone Number</p>
                <input id="phone" type="text" name="phone">
                <p class="error">*digits only min 6, max 11 digits</p>
            </form>
            <button id="submit" onclick="regex();">Submit</button>
        </div>
    </div>
</div>       
<% }%>    
<%@include file="footer.jsp" %>
<script src="js/registration.js"></script>
<%@include file="ending.jsp" %>
