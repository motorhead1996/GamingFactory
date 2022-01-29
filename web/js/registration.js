const name = document.getElementById("name");
const surname = document.getElementById("surname");
const email = document.getElementById("email");
const password1 = document.getElementById("password1");
const password2 = document.getElementById("password2");
const city = document.getElementById("city");
const address = document.getElementById("address");
const phone = document.getElementById("phone");

var nameRegex = /^(?!-)[a-zA-Z-]*[a-zA-Z]{1,32}$/;
var emailRegex = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,32}$/;
var cityRegex = /^[a-zA-Z',.\s-]{1,25}$/;
var addressRegex = /^[A-Za-z0-9 ]+$/;
var phoneRegex = /^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]{8,11}$/;

function validName(n) {
    if (n.match(nameRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validPassword(p) {
    if (p.match(passwordRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validEmail(e) {
    if (e.match(emailRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validCity(n) {
    if (n.match(cityRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validAddress(n) {
    if (n.match(addressRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validPhone(n) {
    if (n.match(phoneRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function regex() {
    var errors = [];
    if (!validName(name.value)) {
        errors.push("Bad Name");
    }
    if (!validName(surname.value)) {
        errors.push("Bad Surname");
    }
    if (!validPassword(password1.value)) {
        errors.push("Bad Password")
    }
    if (password1.value != password2.value) {
        errors.push("Passwords dont match");
    }
    if (!validEmail(email.value)) {
        errors.push("Bad Email");
    }
    if (!validCity(city.value)) {
        errors.push("Bad City name");
    }
    if (!validAddress(address.value)) {
        errors.push("Bad Address");
    }
    if (!validPhone(phone.value)) {
        errors.push("Bad Phone");
    }
    if (errors.length > 0) {
        var alerterrors = errors.join(", ");
        alert(alerterrors);
    } else if (errors.length == 0) {
        var req = new XMLHttpRequest();
        req.open("POST", "Singup", true);
        var formData = new FormData();
        formData.append("address",address.value);
        formData.append("city",city.value);
        formData.append("name",name.value);
        formData.append("surname",surname.value);
        formData.append("email",email.value);
        formData.append("password",password1.value);
        formData.append("phone",phone.value);
        req.send(formData);
        req.onreadystatechange = function () {
            if (req.readyState == 4) {
                var response = req.responseText;
                if (response == "Successful") {
                    alert(response);
                    location.assign("login.jsp");
                } else {
                    alert(response);
                }

            }
        }
    }
}