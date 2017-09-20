window.onload = function() {
    var menu = document.getElementById("newMenu");
    menu.style.display = 'none';
}

document.onclick = function() {
    var menu = document.getElementById("newMenu");
    menu.style.display = 'none';
}
function showMenu() {
    var newMenu = document.getElementById("newMenu");
    event.cancelBubble = true;
    newMenu.style.display = 'block';
    newMenu.style.position = 'fixed';
    newMenu.style.width = '20%';
    newMenu.style.background = '#2c3544';
    newMenu.style.top = '15%';
    newMenu.style.left = '40%';
    newMenu.style.padding = '0';
}