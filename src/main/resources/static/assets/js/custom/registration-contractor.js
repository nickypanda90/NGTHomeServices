
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
        if(agreeTerms()) {
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
                    if(response["name"] != undefined) {
                        window.location.replace("../index.html");
                    } else {
                        // update error message                
                        displayErrorMsg('Some error occured while registration');
                    }
                    
            });
        } else {
            displayErrorMsg("Please Agree to our terms and condtion");
        }
    } else {
        displayErrorMsg("Password dosen't match");
    }
}

function displayErrorMsg(msg){
    var $form = $("#contractor-form");
    $form.find('#error_msg').html(msg);
    $form.find('.alert-danger').show();
}
function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
  
    $.map(unindexed_array, function(n, i){
      indexed_array[n['name']] = n['value'];
    });
  
    
    if(getSelectedCategory()){
        indexed_array["category"] =  getSelectedCategory();
    }
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

function agreeTerms() {
    return $('input[type="checkbox"]').is(':checked');
}


function getSelectedCategory(){
    return $( ".categories option:selected" ).val();
}