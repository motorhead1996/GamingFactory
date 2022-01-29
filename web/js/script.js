function restartCart() {

    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("restartCart=1");
    req.onreadystatechange = function () {
        if (req.readyState == 4) {

        }
    }
}