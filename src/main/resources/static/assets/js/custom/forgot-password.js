$(document).ready(function(){
    $('.alert-danger').hide();
});

function resetPassword() {
    if(validatePassword()){
        $('.alert-danger').hide();
        var $form = $("#reset-password");
        let post_url = $form.attr("action");
        let request_method = $form.attr("method");
        let data = getFormData($form);

        // append email_id to data before ajax
        if(getUrlVars()["email_id"]) {
            data["email_id"] = getUrlVars()["email_id"];
        }

        $.ajax({
            url : post_url,
            type: request_method,
            data : JSON.stringify(data),
            crossDomain: true,
            contentType: "application/json;",
            dataType: "json",
            cache: false,
            processData:false
            }).done(function(response){ 
                console.log(response);
                // if(response) {
                //     let redirectUrl = './forgot-password.html' + '?email_id=' + data.email_id;
                //     console.log(redirectUrl);
                //     window.location.replace(redirectUrl);
                // } else {
                //     // update error message                
                //     $form.find('.alert-danger').show();
                // }
                
        });
    } else {
        // Update and show error message
        var $form = $("#reset-password");
        $form.find('#error_msg').html("Password dosen't match");
        $form.find('.alert-danger').show();
    }
}


function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
  
    $.map(unindexed_array, function(n, i){
      indexed_array[n['name']] = n['value'];
    });
  
    return indexed_array;
}

function getUrlVars() {
    var vars = {};
    let parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
      vars[key] = value;
    });
    return vars;
}

function validatePassword(){
    let passwords = $("#reset-password").find('input[type="password"]');
    if(passwords.length === 2){
        let password = passwords[0].value;
        let confirm_password = passwords[1].value;
        if(password === confirm_password){
            return true;
        }else {
            return false;
        }
    }
}


