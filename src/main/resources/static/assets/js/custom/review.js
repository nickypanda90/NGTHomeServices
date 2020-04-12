$(document).ready(function(){
    $('.alert-danger').hide();
    $('.alert-success').hide();
    
});

function submitReview() {
    $('.alert-danger').hide();
    $('.alert-success').hide();
    var $form = $("#feedback_form");
    let post_url = $form.attr("action");
    let request_method = $form.attr("method");
    let data = getFormData($form);
    console.log(data);
    if(validation()) {
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
                    $("#feedback_form")[0].reset();
                    displayConfirmationMsg('Successfully Submitted the review');
                }else {
                    displayErrorMsg('Some error occured');
                }
        });
    } else {
        displayErrorMsg('Select a category');
    }
}

function displayErrorMsg(msg){
    var $form = $("#feedback_form");
    $form.find('#error_msg').html(msg);
    $form.find('.alert-danger').show();
}

function displayConfirmationMsg(msg){
    var $form = $("#feedback_form");
    $form.find('#success_msg').html(msg);
    $form.find('.alert-success').show();
}
  
  
  function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};
  
    $.map(unindexed_array, function(n, i){
      indexed_array[n['name']] = n['value'];
    });

    if(getSelectedCategory()){
        indexed_array["review_catg"] =  getSelectedCategory();
    }
  
    return indexed_array;
  }

function getSelectedCategory(){
    return $( ".categories option:selected" ).val();  
}

function validation(){
    if($( ".categories option:selected" ).val() != "" ){
        return true;
    } else {
        return false;
    }
}