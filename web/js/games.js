
function addGame(id) {

    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("addGame=" + id.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);

        }
    }

}




