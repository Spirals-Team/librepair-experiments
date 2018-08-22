<%--
Copyright (C) 2011-2013 B3Partners B.V.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/taglibs.jsp"%>

<stripes:layout-render name="/WEB-INF/jsp/templates/admin.jsp" pageTitle="Projectoverzicht" menuitem="project">
    <stripes:layout-component name="head">
        <title>Wait page</title>
    </stripes:layout-component>
    <stripes:layout-component name="content">
        percentage: <span id="percentage"></span> <br/>
        Voortgang:<span id="currentAction"></span><br/>
        <textarea rows="30" cols="50" id="logs"></textarea>
        
        <script type="text/javascript">
function update() {
    // Dit request wordt door @WaitPage server side gedelayed 
    $.ajax({
        url: window.location.href,
        data: {ajax: true},
        success: function(obj, opts) {
            //var obj = $.parseJSON(response.responseText);
           // progress.updateProgress(obj.progress / 100, obj.currentAction);
            var percentage = document.getElementById("percentage");
            percentage.innerHTML = obj.progress + "%";
            var s = document.getElementById("logs");
            if(obj.logs){
                for(var i = 0 ; i < obj.logs.length;i++){
                    s.value += obj.logs[i] + "\n";
                }
            }
            s.scrollTop = s.scrollHeight;
            
            var ca = document.getElementById("currentAction");
            ca.innerHTML = obj.currentAction;
            if(!obj.finished) {
                setTimeout(function() {
                    update();
                }, 0);
            } else {
                window.location.reload();
            }
        },
        error: function(response, opts) {
            console.log('server-side failure with status code ' + response.status);
        }
    });
}
setTimeout(function() {
    update();
}, 0);


        </script>

    </stripes:layout-component>
</stripes:layout-render>
