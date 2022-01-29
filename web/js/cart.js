function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function addGame(id) {

    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("addGame=" + id.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            build();

        }
    }


}
function deleteGame(id) {

    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("deleteGame=" + id.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            build();

        }
    }
}
function removeGame(id) {
    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("removeGame=" + id.toString());

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            build();

        }
    }
}
function buyGame() {
    var req = new XMLHttpRequest();
    req.open("POST", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send("buyGames=1");

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            alert(req.responseText);
            location.assign("index.jsp");

        }
    }

}
function build() {
    var req = new XMLHttpRequest();
    req.open("GET", "Cart", true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.send();

    req.onreadystatechange = function () {
        if (req.readyState == 4) {
            var response = req.responseText;
            var json = JSON.parse(req.responseText);
            if (response == "null") {
                var cart = document.getElementById("cart");
                removeAllChildNodes(cart);
                var cartcontainer = document.createElement("div");
                cartcontainer.setAttribute("class", "cartcontainer");
                var h1 = document.createElement("h1");
                h1.innerHTML = "My Cart";
                var p = document.createElement("p");
                p.innerHTML="No items in cart"
                cartcontainer.appendChild(h1);
                cartcontainer.appendChild(p);
                cart.appendChild(cartcontainer);
            } else {
                var cart = document.getElementById("cart");
                removeAllChildNodes(cart);
                var cartcontainer = document.createElement("div");
                cartcontainer.setAttribute("class", "cartcontainer");
                var h1 = document.createElement("h1");
                h1.innerHTML = "My Cart";
                cartcontainer.appendChild(h1);
                for (var i = 0; i < json.length; i++) {
                    var item = document.createElement("div");
                    item.setAttribute("class", "item");
                    var img = document.createElement("img");
                    img.src = json[i].imgurl;
                    var info = document.createElement("div");
                    info.setAttribute("class", "info");
                    var title = document.createElement("p");
                    title.innerHTML = "Title: " + json[i].name;
                    var studio = document.createElement("p");
                    studio.innerHTML = "Studio: " + json[i].Studio;
                    var genre = document.createElement("p");
                    genre.innerHTML = "Genre: " + json[i].genre.name;
                    var platform = document.createElement("p");
                    platform.innerHTML = "Platform: " + json[i].category.name;
                    var price = document.createElement("p");
                    price.innerHTML = "Price: " + json[i].price + "$";
                    var quantity = document.createElement("p");
                    quantity.innerHTML = "Quantity: " + json[i].quantity;
                    var buttonremove = document.createElement("div");
                    buttonremove.setAttribute("class", "buttonremove");
                    var dlt = document.createElement("button");
                    dlt.setAttribute("class", "button");
                    dlt.innerHTML = "Delete";
                    dlt.value = json[i].id;
                    dlt.onclick = function () {
                        deleteGame(this.value);
                    };
                    var add = document.createElement("button");
                    add.setAttribute("class", "button");
                    add.innerHTML = "Add";
                    add.value = json[i].id;
                    add.onclick = function () {
                        addGame(this.value);
                    };
                    var remove = document.createElement("button");
                    remove.setAttribute("class", "button");
                    remove.innerHTML = "Remove";
                    remove.value = json[i].id;
                    remove.onclick = function () {
                        removeGame(this.value);
                    };

                    info.appendChild(title);
                    info.appendChild(studio);
                    info.appendChild(genre);
                    info.appendChild(platform);
                    info.appendChild(price);
                    info.appendChild(quantity);

                    item.appendChild(img);
                    item.appendChild(info);
                    item.appendChild(buttonremove);
                    buttonremove.appendChild(dlt);
                    buttonremove.appendChild(add);
                    buttonremove.appendChild(remove);

                    cartcontainer.appendChild(item);


                }
                var button = document.createElement("button");
                button.id = "checkout";
                button.innerHTML = "Buy";
                button.onclick = function () {
                    buyGame();

                };
                var total = document.createElement("p");
                var sum = 0;
                for (var i = 0; i < json.length; i++) {
                    var price = json[i].quantity * json[i].price;
                    sum = sum + price;
                }
                total.innerHTML = "TOTAL:" + sum + "$";
                cartcontainer.appendChild(total);
                cartcontainer.appendChild(button);
                cart.appendChild(cartcontainer);
            }

        }
    };

}
window.onload = build();
