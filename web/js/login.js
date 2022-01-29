function login() {
    var email = document.getElementById("loginemail");
    var password = document.getElementById("loginpassword");
    var req = new XMLHttpRequest();
    req.open("POST", "Login", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("email="+email.value+"&password="+password.value);
    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            if(req.responseText=="Successful"){
            location.assign("index.jsp");    
            }
        alert(req.responseText);
        
        }
    }
}