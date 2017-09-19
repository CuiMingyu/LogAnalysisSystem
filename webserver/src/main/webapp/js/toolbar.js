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
    newMenu.style.width = '400px';
    newMenu.style.background = '#2c3544';
    newMenu.style.top = '150px';
    newMenu.style.left = '700px';
    newMenu.style.padding = '0';
}