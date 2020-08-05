$(document).ready(function(){
    $('.alert-danger').hide();
});

function submitLoginForm() {
    $('.alert-danger').hide();
    var $form = $("#login_form");
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
        if(response.authenticated ) {
            localStorage.setItem('username', response.name);
            localStorage.setItem("role", response.role_id);
            localStorage.setItem("id", response.customer_Id);
            let redirectUrl = "../../index.html";
            window.location.replace(redirectUrl);
        }else {
            localStorage.clear();
            displayErrorMsg('Incorrect Username/Password!!!');
        }
    });
}

function displayErrorMsg(msg){
    var $form = $("#login_form");
    $form.find('#error_msg').html(msg);
    $form.find('.alert-danger').show();
}

function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}