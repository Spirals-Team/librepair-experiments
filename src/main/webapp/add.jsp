<%@ page import="codeu.model.data.User" %>
<%@ page import="java.util.List" %>
<style>
.dropbtn {
  background-color: #46b9e2;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: ;
  cursor: pointer;
}

#myInput {
  border-box: box-sizing;
  background-position: 14px 12px;
  background-repeat: no-repeat;
  font-size: 16px;
  padding: 14px 20px 12px 45px;
  border: none;
  border-bottom: 1px solid #ddd;
}

#myInput:focus {outline: 3px solid #ddd;}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  height: 200px;
  display: none;
  position: absolute;
  background-color: #f6f6f6;
  min-width: 230px;
  overflow-y: scroll;
  border: 1px solid #ddd;
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}
.dropdown a:hover {background-color: #ddd;}
  
</style>

<script>
  $(document).ready(function(){
    $(".user").on({
      click: function(){
        var name = $(this).text(); 
        if(confirm("Are you sure you want to add " + name + " to this chat?")){
           $.post("", {nameToBeAdded: name });
        }
        $(this).fadeOut("fast");
        var menu = $(document.getElementById("myDropdown"));
        menu.fadeOut('slow');
      }
    });
    $(".dropbtn").on({
      click: function(){
        var search = document.getElementById("myInput");
        var menu = $(document.getElementById("myDropdown"));
        if(menu.css("display") == "block"){
            menu.fadeOut('fast');
            return;
        }
        menu.css("display", "block")
        search.focus(); 
      }, 
      mouseover: function(){
        $(this).css("background-color", "#0c90bf")
      }, 
      mouseleave: function(){
        $(this).css("background-color", "#46b9e2")
      }
    });
     
    $(".dropdown").on({
      mouseleave: function(){
        var menu = $(document.getElementById("myDropdown"));
        // After 1s, it removes the menu
        setTimeout(function(){menu.fadeOut('slow');}, 1000);
      }
    });  
     $("#myDropdown").on({
       mouseenter: function(){
       $(this).css("display", "block")
      }
    });  
  });

  function filterFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    div = document.getElementById("myDropdown");
    a = div.getElementsByTagName("a");
    for (i = 0; i < a.length; i++) {
        if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
  }
</script>

<div class="dropdown" style="margin-bottom: 5px;">
<button class="dropbtn">Add User</button>
  <div id="myDropdown" class="dropdown-content">
    <input type="text" placeholder="&#x1f50d;Search.." id="myInput" onkeyup="filterFunction()">
    <% List<User> excludedUsers = (List<User>) request.getAttribute("excludedUsers"); %>
    <% for (User user: excludedUsers){
        String name = user.getName();
    %>
		    <a class="user"><%=name%></a>
	  <% } %>
  </div>
</div>