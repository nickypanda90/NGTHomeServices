
$(document).ready(function () {

});

function getFormData($form){
  let unindexed_array = $form.serializeArray();
  let indexed_array = {};

  $.map(unindexed_array, function(n, i){
    indexed_array[n['name']] = n['value'];
  });

  return indexed_array;
}

function customerRegistration(){
  if(validatePassword()){
    var $form = $("#customer_form");
    var data = getFormData($form);
    let post_url = $form.attr("action"); //get form action url
    let request_method = $form.attr("method"); //get form GET/POST method

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
        window.location.replace("../index.html");
    });
  } else {
    // Update and show error message
    var $form = $("#reset-password");
    $form.find('#error_msg').html("Password dosen't match");
    $form.find('.alert-danger').show();
  }
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