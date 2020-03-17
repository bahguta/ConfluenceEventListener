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