window.onload = function() {
  var menu = document.getElementById("newMenu");
  menu.style.display = 'none';
}

document.onclick = function() {
  var menu = document.getElementById("newMenu");
  menu.style.display = 'none';
}

document.getElementById("newMenu").onclick = function() {
  var menu = document.getElementById("newMenu");
  menu.style.display = 'block';
}

// document.onclick = function()
// {
//   var newMenu = document.getElementsById("newMenu");
//   var event = window.event;
//   var src = event.target;
//   if(src.getAttribute("id")=="newMenu")
//     //document.onclick;
//   else{
//     newMenu.style.display = "none";
//     //document.onclick;
//   }
// }

function showMenu() {
  event.cancelBubble = true;
  newMenu.style.display = 'block';
  newMenu.style.position = 'fixed';
  newMenu.style.width = '400px';
  newMenu.style.background = '#2c3544';
  newMenu.style.top = '100px';
  newMenu.style.left = '650px';
  newMenu.style.padding = '0';
}
