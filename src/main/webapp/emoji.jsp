<style>
  *{box-sizing: border-box;}
  .dropdown-menu{
    width:45%;
    height: 130px;
    overflow-y: scroll;
    background-color: #80808040;
  }
  .row{
    display: flex;
    padding: .5px;
    width: auto;
    margin: 0 auto;
    min-height: 50px;
  }
  .colEmoji{
    min-height: 30px;
    margin: 0 .3%;
    display: flex;
    width: 30px;
    text-align: center;
    font-size: 28px
  }  
  #btn-emoji{
    background-color: transparent;
    padding: 5px 5px;
    border-color: transparent;
  }
  #start-emoji{
    font-size:24px; 
    text-decoration:none;
  }
</style>

<script>        
  $(document).ready(function(){
    // Forces the cursor to stay inside the textArea
    focusRight("input", ""); 
    // scroll the chat div to the bottom
    function scrollEmoji() {
      var emojiDiv = document.getElementsByClassName('dropdown-menu');
      emojiDiv.scrollTop = emojiDiv.scrollHeight;
      emojiDiv.scrollLeft = emojiDiv.scrollLeft;
    };
    $("div.colEmoji").on({
      click: function(){
          var emoji= $(this);
          var emojiMenu = $(document.getElementById("emojieMenu"));
          emoji.css("background-color", "#06b1e896");
          // Properly writes to the textArea
          focusRight("input",(emoji.text()));
          // After 1s, it removes the background-color
          setTimeout(function(){emoji.css("background-color", "transparent");}, 1500);
      }, 
       mouseenter: function(){
        $(this).css("cursor", "default"); 
      }
    });
    $("#emojieMenu").on({
      mouseenter: function(){
        var emojiMenu = $(document.getElementById("emojieMenu"));
        // Forces the cursor to stay inside the textArea
        focusRight("input", '');
      },
      mouseleave: function(){
        var emojiMenu = $(document.getElementById("emojieMenu"));
        // Forces the cursor to stay inside the textArea
        focusRight("input", '');
        emojiMenu.fadeOut('slow');
      }
    });
    $("#btn-emoji").on({
      click: function(){
          var emojiMenu = $(document.getElementById("emojieMenu"));
          if(emojiMenu.css("display") == "block"){
            emojiMenu.fadeOut('slow');
            return;
          }
          emojiMenu.fadeIn();
          emojiMenu.css("display", "block")
      } 
    });
  });

  function focusRight(areaId,text) {
    var txtarea = document.getElementById(areaId);
    var scrollPos = txtarea.scrollTop;
    var strPos = 0;
    var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ? 
        "ff" : (document.selection ? "ie" : false ) );
    if (br == "ie") { 
        txtarea.focus();
        var range = document.selection.createRange();
        range.moveStart ('character', -txtarea.value.length);
        strPos = range.text.length;
    }
    else if (br == "ff") strPos = txtarea.selectionStart;

    var front = (txtarea.value).substring(0,strPos);  
    var back = (txtarea.value).substring(strPos,txtarea.value.length); 
    txtarea.value=front+text+back;
    strPos = strPos + text.length;
    if (br == "ie") { 
        txtarea.focus();
        var range = document.selection.createRange();
        range.moveStart ('character', -txtarea.value.length);
        range.moveStart ('character', strPos);
        range.moveEnd ('character', 0);
        range.select();
    }
    else if (br == "ff") {
        txtarea.selectionStart = strPos;
        txtarea.selectionEnd = strPos;
        txtarea.focus();
    }
    txtarea.scrollTop = scrollPos;
  }                       
</script>
<div class="dropup">
  <button style=" margin-bottom:5px;" id="btn-emoji" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown"><a id="start-emoji" href="#">&#x1F600;</a></button>
  <div id = "emojieMenu" class="dropdown-menu">
    <div class="row">
	<div class="colEmoji">&#x1F600;</div>
	<div class="colEmoji">&#x1F601;</div>
	<div class="colEmoji">&#x1F602;</div>
	<div class="colEmoji">&#x1F603;</div>
	<div class="colEmoji">&#x1F604;</div>
	<div class="colEmoji">&#x1F605;</div>
	<div class="colEmoji">&#x1F606;</div>
	<div class="colEmoji">&#x1F607;</div>
	<div class="colEmoji">&#x1F608;</div>
	<div class="colEmoji">&#x1F609;</div>
	<div class="colEmoji">&#x1F60A;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F60B;</div>
	<div class="colEmoji">&#x1F60C;</div>
	<div class="colEmoji">&#x1F60D;</div>
	<div class="colEmoji">&#x1F60E;</div>
	<div class="colEmoji">&#x1F60F;</div>
	<div class="colEmoji">&#x1F610;</div>
	<div class="colEmoji">&#x1F611;</div>
	<div class="colEmoji">&#x1F612;</div>
	<div class="colEmoji">&#x1F613;</div>
	<div class="colEmoji">&#x1F614;</div>
	<div class="colEmoji">&#x1F615;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F616;</div>
	<div class="colEmoji">&#x1F617;</div>
	<div class="colEmoji">&#x1F618;</div>
	<div class="colEmoji">&#x1F619;</div>
	<div class="colEmoji">&#x1F61A;</div>
	<div class="colEmoji">&#x1F61B;</div>
	<div class="colEmoji">&#x1F61C;</div>
	<div class="colEmoji">&#x1F61D;</div>
	<div class="colEmoji">&#x1F61E;</div>
	<div class="colEmoji">&#x1F61F;</div>
	<div class="colEmoji">&#x1F620;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F621;</div>
	<div class="colEmoji">&#x1F622;</div>
	<div class="colEmoji">&#x1F623;</div>
	<div class="colEmoji">&#x1F624;</div>
	<div class="colEmoji">&#x1F625;</div>
	<div class="colEmoji">&#x1F626;</div>
	<div class="colEmoji">&#x1F627;</div>
	<div class="colEmoji">&#x1F628;</div>
	<div class="colEmoji">&#x1F629;</div>
	<div class="colEmoji">&#x1F62A;</div>
	<div class="colEmoji">&#x1F62B;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F62C;</div>
	<div class="colEmoji">&#x1F62D;</div>
	<div class="colEmoji">&#x1F62E;</div>
	<div class="colEmoji">&#x1F62F;</div>
	<div class="colEmoji">&#x1F630;</div>
	<div class="colEmoji">&#x1F631;</div>
	<div class="colEmoji">&#x1F632;</div>
	<div class="colEmoji">&#x1F633;</div>
	<div class="colEmoji">&#x1F634;</div>
	<div class="colEmoji">&#x1F635;</div>
	<div class="colEmoji">&#x1F636;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F637;</div>
	<div class="colEmoji">&#x1F638;</div>
	<div class="colEmoji">&#x1F639;</div>
	<div class="colEmoji">&#x1F63A;</div>
	<div class="colEmoji">&#x1F63B;</div>
	<div class="colEmoji">&#x1F63C;</div>
	<div class="colEmoji">&#x1F63D;</div>
	<div class="colEmoji">&#x1F63E;</div>
	<div class="colEmoji">&#x1F63F;</div>
	<div class="colEmoji">&#x1F640;</div>
	<div class="colEmoji">&#x1F641;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F642;</div>
	<div class="colEmoji">&#x1F643;</div>
	<div class="colEmoji">&#x1F644;</div>
	<div class="colEmoji">&#x1F910;</div>
	<div class="colEmoji">&#x1F911;</div>
	<div class="colEmoji">&#x1F912;</div>
	<div class="colEmoji">&#x1F913;</div>
	<div class="colEmoji">&#x1F914;</div>
	<div class="colEmoji">&#x1F915;</div>
	<div class="colEmoji">&#x1F916;</div>
	<div class="colEmoji">&#x1F917;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F918;</div>
	<div class="colEmoji">&#x1F919;</div>
	<div class="colEmoji">&#x1F91A;</div>
	<div class="colEmoji">&#x1F91B;</div>
	<div class="colEmoji">&#x1F91C;</div>
	<div class="colEmoji">&#x1F91D;</div>
	<div class="colEmoji">&#x1F91E;</div>
	<div class="colEmoji">&#x1F91F;</div>
	<div class="colEmoji">&#x1F920;</div>
	<div class="colEmoji">&#x1F921;</div>
	<div class="colEmoji">&#x1F922;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F923;</div>
	<div class="colEmoji">&#x1F924;</div>
	<div class="colEmoji">&#x1F925;</div>
	<div class="colEmoji">&#x1F926;</div>
	<div class="colEmoji">&#x1F927;</div>
	<div class="colEmoji">&#x1F928;</div>
	<div class="colEmoji">&#x1F929;</div>
	<div class="colEmoji">&#x1F92A;</div>
	<div class="colEmoji">&#x1F92B;</div>
	<div class="colEmoji">&#x1F92C;</div>
	<div class="colEmoji">&#x1F92D;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F92E;</div>
	<div class="colEmoji">&#x1F92F;</div>
	<div class="colEmoji">&#x1F9D0;</div>
	<div class="colEmoji">&#x1F9D1;</div>
	<div class="colEmoji">&#x1F9D2;</div>
	<div class="colEmoji">&#x1F9D3;</div>
	<div class="colEmoji">&#x1F9D4;</div>
	<div class="colEmoji">&#x1F9D5;</div>
	<div class="colEmoji">&#x1F9D6;</div>
	<div class="colEmoji">&#x1F9D7;</div>
	<div class="colEmoji">&#x1F9D8;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F9D9;</div>
	<div class="colEmoji">&#x1F9DA;</div>
	<div class="colEmoji">&#x1F9DB;</div>
	<div class="colEmoji">&#x1F9DC;</div>
	<div class="colEmoji">&#x1F9DD;</div>
	<div class="colEmoji">&#x1F9DE;</div>
	<div class="colEmoji">&#x1F9DF;</div>
	<div class="colEmoji">&#x1F9E0;</div>
	<div class="colEmoji">&#x1F9E1;</div>
	<div class="colEmoji">&#x1F9E2;</div>
	<div class="colEmoji">&#x1F9E3;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F9E4;</div>
	<div class="colEmoji">&#x1F9E5;</div>
	<div class="colEmoji">&#x1F9E6;</div>
	<div class="colEmoji">&#x1F4AA;</div>
	<div class="colEmoji">&#x1F918;</div>
	<div class="colEmoji">&#x1F919;</div>
	<div class="colEmoji">&#x1F91A;</div>
	<div class="colEmoji">&#x1F91B;</div>
	<div class="colEmoji">&#x1F91C;</div>
	<div class="colEmoji">&#x1F91E;</div>
	<div class="colEmoji">&#x1F91F;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F590;</div>
	<div class="colEmoji">&#x1F595;</div>
	<div class="colEmoji">&#x1F596;</div>
	<div class="colEmoji">&#x1F440;</div>
	<div class="colEmoji">&#x1F442;</div>
	<div class="colEmoji">&#x1F443;</div>
	<div class="colEmoji">&#x1F444;</div>
	<div class="colEmoji">&#x1F445;</div>
	<div class="colEmoji">&#x1F446;</div>
	<div class="colEmoji">&#x1F447;</div>
	<div class="colEmoji">&#x1F448;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F449;</div>
	<div class="colEmoji">&#x1F44A;</div>
	<div class="colEmoji">&#x1F44B;</div>
	<div class="colEmoji">&#x1F44C;</div>
	<div class="colEmoji">&#x1F44D;</div>
	<div class="colEmoji">&#x1F44E;</div>
	<div class="colEmoji">&#x1F44F;</div>
	<div class="colEmoji">&#x1F450;</div>
	<div class="colEmoji">&#x1F451;</div>
	<div class="colEmoji">&#x1F452;</div>
	<div class="colEmoji">&#x1F453;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F454;</div>
	<div class="colEmoji">&#x1F455;</div>
	<div class="colEmoji">&#x1F456;</div>
	<div class="colEmoji">&#x1F457;</div>
	<div class="colEmoji">&#x1F458;</div>
	<div class="colEmoji">&#x1F459;</div>
	<div class="colEmoji">&#x1F45A;</div>
	<div class="colEmoji">&#x1F45B;</div>
	<div class="colEmoji">&#x1F45C;</div>
	<div class="colEmoji">&#x1F45D;</div>
	<div class="colEmoji">&#x1F45E;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F45F;</div>
	<div class="colEmoji">&#x1F460;</div>
	<div class="colEmoji">&#x1F461;</div>
	<div class="colEmoji">&#x1F462;</div>
	<div class="colEmoji">&#x1F463;</div>
	<div class="colEmoji">&#x1F464;</div>
	<div class="colEmoji">&#x1F465;</div>
	<div class="colEmoji">&#x1F466;</div>
	<div class="colEmoji">&#x1F467;</div>
	<div class="colEmoji">&#x1F468;</div>
	<div class="colEmoji">&#x1F469;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F46A;</div>
	<div class="colEmoji">&#x1F46B;</div>
	<div class="colEmoji">&#x1F46C;</div>
	<div class="colEmoji">&#x1F46D;</div>
	<div class="colEmoji">&#x1F46E;</div>
	<div class="colEmoji">&#x1F46F;</div>
	<div class="colEmoji">&#x1F470;</div>
	<div class="colEmoji">&#x1F471;</div>
	<div class="colEmoji">&#x1F472;</div>
	<div class="colEmoji">&#x1F473;</div>
	<div class="colEmoji">&#x1F474;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F475;</div>
	<div class="colEmoji">&#x1F476;</div>
	<div class="colEmoji">&#x1F477;</div>
	<div class="colEmoji">&#x1F478;</div>
	<div class="colEmoji">&#x1F479;</div>
	<div class="colEmoji">&#x1F47A;</div>
	<div class="colEmoji">&#x1F47B;</div>
	<div class="colEmoji">&#x1F47C;</div>
	<div class="colEmoji">&#x1F47D;</div>
	<div class="colEmoji">&#x1F47E;</div>
	<div class="colEmoji">&#x1F47F;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F480;</div>
	<div class="colEmoji">&#x1F481;</div>
	<div class="colEmoji">&#x1F482;</div>
	<div class="colEmoji">&#x1F483;</div>
	<div class="colEmoji">&#x1F484;</div>
	<div class="colEmoji">&#x1F485;</div>
	<div class="colEmoji">&#x1F486;</div>
	<div class="colEmoji">&#x1F487;</div>
	<div class="colEmoji">&#x1F488;</div>
	<div class="colEmoji">&#x1F489;</div>
	<div class="colEmoji">&#x1F48A;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F48B;</div>
	<div class="colEmoji">&#x1F48C;</div>
	<div class="colEmoji">&#x1F48D;</div>
	<div class="colEmoji">&#x1F48E;</div>
	<div class="colEmoji">&#x1F48F;</div>
	<div class="colEmoji">&#x1F490;</div>
	<div class="colEmoji">&#x1F491;</div>
	<div class="colEmoji">&#x1F492;</div>
	<div class="colEmoji">&#x1F493;</div>
	<div class="colEmoji">&#x1F494;</div>
	<div class="colEmoji">&#x1F495;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F496;</div>
	<div class="colEmoji">&#x1F497;</div>
	<div class="colEmoji">&#x1F498;</div>
	<div class="colEmoji">&#x1F499;</div>
	<div class="colEmoji">&#x1F49A;</div>
	<div class="colEmoji">&#x1F49B;</div>
	<div class="colEmoji">&#x1F49C;</div>
	<div class="colEmoji">&#x1F49D;</div>
	<div class="colEmoji">&#x1F49E;</div>
	<div class="colEmoji">&#x1F49F;</div>
	<div class="colEmoji">&#x1F4A0;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4A1;</div>
	<div class="colEmoji">&#x1F4A2;</div>
	<div class="colEmoji">&#x1F4A3;</div>
	<div class="colEmoji">&#x1F4A4;</div>
	<div class="colEmoji">&#x1F4A5;</div>
	<div class="colEmoji">&#x1F4A6;</div>
	<div class="colEmoji">&#x1F4A7;</div>
	<div class="colEmoji">&#x1F4A8;</div>
	<div class="colEmoji">&#x1F4A9;</div>
	<div class="colEmoji">&#x1F4AA;</div>
	<div class="colEmoji">&#x1F4AB;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4AC;</div>
	<div class="colEmoji">&#x1F4AD;</div>
	<div class="colEmoji">&#x1F4AE;</div>
	<div class="colEmoji">&#x1F4AF;</div>
	<div class="colEmoji">&#x1F4B0;</div>
	<div class="colEmoji">&#x1F4B1;</div>
	<div class="colEmoji">&#x1F4B2;</div>
	<div class="colEmoji">&#x1F4B3;</div>
	<div class="colEmoji">&#x1F4B4;</div>
	<div class="colEmoji">&#x1F4B5;</div>
	<div class="colEmoji">&#x1F4B6;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4B7;</div>
	<div class="colEmoji">&#x1F4B8;</div>
	<div class="colEmoji">&#x1F4B9;</div>
	<div class="colEmoji">&#x1F4BA;</div>
	<div class="colEmoji">&#x1F4BB;</div>
	<div class="colEmoji">&#x1F4BC;</div>
	<div class="colEmoji">&#x1F4BD;</div>
	<div class="colEmoji">&#x1F4BE;</div>
	<div class="colEmoji">&#x1F4BF;</div>
	<div class="colEmoji">&#x1F4C0;</div>
	<div class="colEmoji">&#x1F4C1;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4C2;</div>
	<div class="colEmoji">&#x1F4C3;</div>
	<div class="colEmoji">&#x1F4C4;</div>
	<div class="colEmoji">&#x1F4C5;</div>
	<div class="colEmoji">&#x1F4C6;</div>
	<div class="colEmoji">&#x1F4C7;</div>
	<div class="colEmoji">&#x1F4C8;</div>
	<div class="colEmoji">&#x1F4C9;</div>
	<div class="colEmoji">&#x1F4CA;</div>
	<div class="colEmoji">&#x1F4CB;</div>
	<div class="colEmoji">&#x1F4CC;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4CD;</div>
	<div class="colEmoji">&#x1F4CE;</div>
	<div class="colEmoji">&#x1F4CF;</div>
	<div class="colEmoji">&#x1F4D0;</div>
	<div class="colEmoji">&#x1F4D1;</div>
	<div class="colEmoji">&#x1F4D2;</div>
	<div class="colEmoji">&#x1F4D3;</div>
	<div class="colEmoji">&#x1F4D4;</div>
	<div class="colEmoji">&#x1F4D5;</div>
	<div class="colEmoji">&#x1F4D6;</div>
	<div class="colEmoji">&#x1F4D7;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4D8;</div>
	<div class="colEmoji">&#x1F4D9;</div>
	<div class="colEmoji">&#x1F4DA;</div>
	<div class="colEmoji">&#x1F4DB;</div>
	<div class="colEmoji">&#x1F4DC;</div>
	<div class="colEmoji">&#x1F4DD;</div>
	<div class="colEmoji">&#x1F4DE;</div>
	<div class="colEmoji">&#x1F4DF;</div>
	<div class="colEmoji">&#x1F4E0;</div>
	<div class="colEmoji">&#x1F4E1;</div>
	<div class="colEmoji">&#x1F4E2;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4E3;</div>
	<div class="colEmoji">&#x1F4E4;</div>
	<div class="colEmoji">&#x1F4E5;</div>
	<div class="colEmoji">&#x1F4E6;</div>
	<div class="colEmoji">&#x1F4E7;</div>
	<div class="colEmoji">&#x1F4E8;</div>
	<div class="colEmoji">&#x1F4E9;</div>
	<div class="colEmoji">&#x1F4EA;</div>
	<div class="colEmoji">&#x1F4EB;</div>
	<div class="colEmoji">&#x1F4EC;</div>
	<div class="colEmoji">&#x1F4ED;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F4EE;</div>
	<div class="colEmoji">&#x1F4EF;</div>
	<div class="colEmoji">&#x1F4F0;</div>
	<div class="colEmoji">&#x1F4F1;</div>
	<div class="colEmoji">&#x1F4F2;</div>
	<div class="colEmoji">&#x1F4F3;</div>
	<div class="colEmoji">&#x1F4F4;</div>
	<div class="colEmoji">&#x1F4F5;</div>
	<div class="colEmoji">&#x1F4F6;</div>
	<div class="colEmoji">&#x1F4F7;</div>
	<div class="colEmoji">&#x1F385;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F3C2;</div>
	<div class="colEmoji">&#x1F3C3;</div>
	<div class="colEmoji">&#x1F3C4;</div>
	<div class="colEmoji">&#x1F3C7;</div>
	<div class="colEmoji">&#x1F3CA;</div>
	<div class="colEmoji">&#x1F3CB;</div>
	<div class="colEmoji">&#x1F3CC;</div>
	<div class="colEmoji">&#x1F442;</div>
	<div class="colEmoji">&#x1F443;</div>
	<div class="colEmoji">&#x1F446;</div>
	<div class="colEmoji">&#x1F447;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F448;</div>
	<div class="colEmoji">&#x1F449;</div>
	<div class="colEmoji">&#x1F44A;</div>
	<div class="colEmoji">&#x1F44B;</div>
	<div class="colEmoji">&#x1F44C;</div>
	<div class="colEmoji">&#x1F44D;</div>
	<div class="colEmoji">&#x1F44E;</div>
	<div class="colEmoji">&#x1F44F;</div>
	<div class="colEmoji">&#x1F450;</div>
	<div class="colEmoji">&#x1F466;</div>
	<div class="colEmoji">&#x1F467;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F468;</div>
	<div class="colEmoji">&#x1F469;</div>
	<div class="colEmoji">&#x1F46E;</div>
	<div class="colEmoji">&#x1F470;</div>
	<div class="colEmoji">&#x1F471;</div>
	<div class="colEmoji">&#x1F472;</div>
	<div class="colEmoji">&#x1F473;</div>
	<div class="colEmoji">&#x1F474;</div>
	<div class="colEmoji">&#x1F475;</div>
	<div class="colEmoji">&#x1F476;</div>
	<div class="colEmoji">&#x1F477;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F478;</div>
	<div class="colEmoji">&#x1F47C;</div>
	<div class="colEmoji">&#x1F481;</div>
	<div class="colEmoji">&#x1F482;</div>
	<div class="colEmoji">&#x1F483;</div>
	<div class="colEmoji">&#x1F485;</div>
	<div class="colEmoji">&#x1F486;</div>
	<div class="colEmoji">&#x1F487;</div>
	<div class="colEmoji">&#x1F933;</div>
	<div class="colEmoji">&#x1F934;</div>
	<div class="colEmoji">&#x1F935;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F936;</div>
	<div class="colEmoji">&#x1F937;</div>
	<div class="colEmoji">&#x1F938;</div>
	<div class="colEmoji">&#x1F939;</div>
	<div class="colEmoji">&#x1F645;</div>
	<div class="colEmoji">&#x1F646;</div>
	<div class="colEmoji">&#x1F647;</div>
	<div class="colEmoji">&#x1F64B;</div>
	<div class="colEmoji">&#x1F64C;</div>
	<div class="colEmoji">&#x1F64D;</div>
	<div class="colEmoji">&#x1F64E;</div>
</div>
<div class="row">
	<div class="colEmoji">&#x1F64F;</div>
	<div class="colEmoji">&#x1F93C;</div>
	<div class="colEmoji">&#x1F93D;</div>
	<div class="colEmoji">&#x1F93E;</div>
</div>
</div>