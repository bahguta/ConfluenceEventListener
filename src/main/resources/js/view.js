function getParticipa(){
    new AJS.$.ajax({
        url: "/confluence/rest/Confluencer/1.0/participa?name=" + AJS.params.remoteUser,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        async: "false",
        success: function (){

            alert("SUCCESS !!! " + AJS.params.remoteUser);
        },
        error: function(){
            alert("ERROR !!!");
        }
    });
}

function cancelarParticipa(){
    new AJS.$.ajax({ // urlBase
        url: "/confluence/rest/Confluencer/1.0/name/cancelar",
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        async: "false",
        success: function (){
            window.location.href = "/confluence";
        },
        error: function(){
            AJS.messages.error("#a-custom-context", {
                title: 'Error',
                body: '<p> You can`t cancel at this moment !!!  </p>'
            });
        }
    });
}

function setParticipa(){
    new AJS.$.ajax({ // urlBase
        url: "/confluence/rest/Confluencer/1.0/name/participa",
        type: "PUT",
        dataType: "json",
        contentType: "application/json",
        async: "false",
        success: function (){
            window.location.href = "/confluence/display/CON/Confluencer";
        },
        error: function(){
            AJS.messages.error("#a-custom-context", {
                title: 'Error',
                body: '<p> You can`t participate at this moment !!!  </p>'
            });
        }
    });
}

//function setParticipa(){
//    new AJS.$.ajax({ // urlBase
//        url: "/confluence/rest/Confluencer/1.0/{name}/participa?name=" + AJS.params.remoteUser,
//        type: "PUT",
//        dataType: "json",
//        contentType: "application/json",
//        async: "false",
//        success: function (){
//            window.location.href = "/confluence/display/CON/Confluencer";
//        },
//        error: function(){
//            AJS.messages.error("#a-custom-context", {
//                title: 'Error',
//                body: '<p> You can`t participate at this moment !!!  </p>'
//            });
//        }
//    });
//}

function GO(){
    window.location.href = "/confluence/display/CON/Confluencer";
}


    //    autoFocus: true,
    //    el: jQuery("#project-config-versions-table"),
    //    allowReorder: true,
    //    resources: {//http://localhost:1990/confluence/rest/Confluencer/1.0/participa
    //        //all: "rest/project/HSP/versions?expand=operations",
    //        all: "rest/Confluencer/1.0/participa",
    //        self: "rest/version"
    //    }

    //var toggle = document.getElementById('toggle');
   // alert("name : " + AJS.params.remoteUser );
     //alert("toggle :: " + toggle.checked);
        //alert("checkbox :: " + AJS.$("$user").toString());
    //if (toggle.checked == true){
       // var user = "$user";
        //user.setParticipate(toggle.checked);
        //eventSeekerManager.userParticipate(user);
        //alert("toggle :: " + toggle.checked);

        //window.location.href = href="/confluence/display/CON/Confluencer+Home";
    //}



//alert("User:" + getCurrentUserData()+ " Group:"+ getCurrentUserGroups());


//})();





//
//             function participa(){
//                 var toggle = document.getElementById('checkBoxOne');
//                 if (toggle.checked == true){
//                    // var user = "$user";
//                     alert(toggle.checked);
//                     $eventSeekerManager.userParticipate($user);
//
//                     window.location.href = href="/confluence/display/CON/Confluencer+Home";
//                    return toggle.checked;
//                 }
//
//             }

//AJS.$(document).ready(function(){
//    AJS.$("#btnOK").click(function(){
//        $eventSeekerManager.userParticipate($user);
//    }
// }




//function participa(){
//   user.setParticipa();
//}

//var form = document.getElementById('toggle-form');
//form.addEventListener('click', function(e) {
//    var toggle = document.getElementById('toggle');
//    var isChecked = toggle.checked;     // new value of the toggle
//    if (isChecked === false) { // do not call server if unchecked
//        console.log("--------------------------------toggle is unchecked");
//        return;
//    }
//    toggle.busy = true;
//    $.post('$participate', { value: isChecked })
//        .done(function () {
//            console.log('---------------------------success');
//        })
//        .fail(function () {
//            toggle.checked = !isChecked;
//            console.error('------------------------------display an error message');
//        })
//        .always(function () {
//            toggle.busy = false;
//        });
//});






//AJS.$(document).on('click', '#btnOK', function() {
//    $user.setParticipate();
//
//});






//#requireResource("com.cis.confluence.plugins.confluencer-module:eventService-resources")
//
//function participa() {
//    $participate = true
//}

//var imageTrashBin = AJS.params.contextPath  + "/download/batch/com.dynatrace.jira.plugin.sla:slaresource/com.dynatrace.jira.plugin.sla:trashbin.png"

//AJS.$('div.foo');
//var toggle = document.getElementById('btn');
//toggle.checked = true;
//${participate} = true;
//function participa(){
//   // ${text} = ${pageProperties};
//    //var toggle = document.getElementById('participa');
//    ${setParticipate(true)};
//};
//

//var toggle = document.getElementById('wifi-toggle');
//toggle.addEventListener('change', function(e) {
//    var isChecked = tog
//    gle.checked;     // new value of the toggle
//    var participate = $participate;
//
//    $.post(participate, { value: isChecked })
//        .done(function () {
//            participate = true;
//        })
//        .fail(function () {
//            toggle.checked = !isChecked;
//            console.error('display an error message');
//        })
//        .always(function () {
//            toggle.busy = false;
//        });
//});
//
//
//// create fake server response
//var url = ${participate};
//var server = sinon.fakeServer.create();
//server.autoRespond = true;
//server.autoRespondAfter = 2000;
//
//server.respondWith('POST', url, [
//    200,
//    { 'Content-Type': 'application/json' },
//    ''
//]);