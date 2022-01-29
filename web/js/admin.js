document.getElementById('uploadimage').addEventListener('click', openDialog);
function openDialog() {
    document.getElementById('file').click();
}
function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}
const form = document.getElementById("uploadgame");
const title = document.getElementById("title");
const studio = document.getElementById("studio");
const price = document.getElementById("price");
const image = document.getElementById("file");
const button = document.getElementById("uploadgamebutton");
const category = document.getElementById("category");
const genre = document.getElementById("genre");
const dlt = document.getElementById("deleteid");
const avl = document.getElementById("avaliableid");

var titlestudioRegex = /^[A-Za-z0-9 ]{2,32}$/;
var priceRegex = /^[0-9]{1,4}$/;


function validateTitle(n) {
    if (n.match(titlestudioRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validateStudio(n) {
    if (n.match(titlestudioRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validatePrice(n) {
    if (n.match(priceRegex) == null) {
        return false;
    } else {
        return "true";
    }
}
function validateImage() {
    var file = image.value.split('.').pop();
    if (file == "jpg" || file == "jpeg" || file == "png") {
        return true;
    } else {
        return false;
    }
}
function validateGenre() {
    var g = genre.value;
    if (g < 1 || g > 6) {
        return false;
    } else {
        return true;
    }
}
function validateCategory() {
    var c = category.value;
    if (c < 1 || c > 3) {
        return false;
    } else {
        return true;
    }
}
function upload() {
    var errors = [];
    if (!validateTitle(title.value)) {
        errors.push("Bad Title");
    }
    if (!validateStudio(studio.value)) {
        errors.push("Bad Studio");
    }
    if (!validatePrice(price.value)) {
        errors.push("Bad Price");
    }
    if (!validateImage()) {
        errors.push("No image");
    }
    if (!validateGenre()) {
        errors.push("Bad Genre");
    }
    if (!validateCategory()) {
        errors.push("Bad Category");
    }
    if (errors.length > 0) {
        var alerterrors = errors.join(", ");
        form.addEventListener('submit', function (event) {
            event.preventDefault();
        });
        alert(alerterrors);
    } else {
        var formData = new FormData();
        formData.append("title", title.value);
        formData.append("studio", studio.value);
        formData.append("price", price.value);
        formData.append("category", category.value);
        formData.append("genre", genre.value);
        formData.append("image", image.files[0]);

        var req = new XMLHttpRequest();
        req.open("POST", "UploadGame", true);
        req.send(formData);

        req.onreadystatechange = function () {
            if (req.readyState == 4) {
                alert(req.responseText);
            }
        };
    }
}
function del() {
    var req = new XMLHttpRequest();
    req.open("POST", "DeleteGame", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("title=" + dlt.value.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            builddelete();
            buildavaliable();
        }
    };
}

function builddelete() {
    var req = new XMLHttpRequest();
    req.open("GET", "DeleteGame", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            var response = req.responseText;
            var json = JSON.parse(req.responseText);
            if (response == "null") {
                removeAllChildNodes(dlt);
            } else {
                removeAllChildNodes(dlt);
                for (var i = 0; i < json.length; i++) {
                    var option = document.createElement("option");
                    option.value = json[i].id;
                    option.innerHTML = json[i].name + " : " + json[i].category.name;
                    dlt.appendChild(option);
                }
            }
        }
    };
}
function avaliable() {
    var req = new XMLHttpRequest();
    req.open("POST", "AvaliableGame", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("title=" + avl.value.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            builddelete();
            buildavaliable();
        }
    };
}
function buildavaliable() {
    var req = new XMLHttpRequest();
    req.open("GET", "AvaliableGame", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            var response = req.responseText;
            var json = JSON.parse(req.responseText);
            if (response == "null") {
                removeAllChildNodes(avl);
            } else {
                removeAllChildNodes(avl);
                for (var i = 0; i < json.length; i++) {
                    var option = document.createElement("option");
                    option.value = json[i].id;
                    option.innerHTML = json[i].name + " : " + json[i].category.name;
                    avl.appendChild(option);
                }
            }
        }
    };
}
window.onload = builddelete();
window.onload = buildavaliable();