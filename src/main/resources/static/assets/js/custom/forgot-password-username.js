$(document).ready(function(){
    $('.alert-danger').hide();
    $('.alert-success').hide();
});


function goToPassword() {
    var $form = $("#forgot-username");
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
            if(response) {
                displaySuccessMsg('Please check your email to reset your password');
                $("#forgot-username")[0].reset();
            } else {
                // update error message                
                $form.find('.alert-danger').show();
            }
            
    });
}

function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
  
    $.map(unindexed_array, function(n, i){
      indexed_array[n['name']] = n['value'];
    });
  
    return indexed_array;
}

function displaySuccessMsg(msg) {
    var $form = $("#forgot-username");
    $form.find('#success_msg').html(msg);
    $form.find('.alert-success').show();
}