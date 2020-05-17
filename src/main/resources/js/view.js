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

function isBusy (button) {

    return button.hasAttribute('aria-busy') && button.getAttribute('aria-busy') === 'true';

}


function setParticipa(){
    AJS.$(document).on('click', '#btnOK', function() {

        this.setAttribute('disabled', '');
        AJS.$('.button-spinner').spin();

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
    });
}

function GO(){
    window.location.href = "/confluence/display/CON/Confluencer";
}
