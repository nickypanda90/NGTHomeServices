
$(document).ready(function(){
    $('.alert-danger').hide();
});

function process(input){
    let value = input.value;
    let letters = value.replace(/[^0-9]/g, "");
    input.value = letters;
}

function getCategory(){

}

function signup() {
    if(validatePassword()){
        $('.alert-danger').hide();
        var $form = $("#contractor-form");
        let post_url = $form.attr("action");
        let request_method = $form.attr("method");
        let data = getFormData($form);

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
        var $form = $("#contractor-form");
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

function validatePassword(){
    let passwords = $("#contractor-form").find('input[type="password"]');
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
