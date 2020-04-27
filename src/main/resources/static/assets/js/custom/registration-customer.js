
$(document).ready(function () {
  $('.alert-danger').hide();
  $('.alert-success').hide();
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
  if (validateEmail()){
    if( validatePasswordField()){
      if(validatePassword()){
        if(agreeTerms()) {
          $('.alert-danger').hide();
          $('.alert-success').hide();
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
            }).done((response) => { 
              if(response.customer_Id){
                displaySuccessMsg("Registration Succesfull");
                setTimeout(() => { 
                  window.location.replace("../../index.html");
                }, 5000);              }
              else {
                displayErrorMsg('Some error occured while registration');
              }
          });
        } else {
          displayErrorMsg("Please Agree to our terms and condtion");
        }
      } else {
        displayErrorMsg("Password doesn't match");
      }
    } else {
      displayErrorMsg("Password is required");
    }
  } else {
    displayErrorMsg("Email is required");
  }
}

function validateEmail(){
  let emailId = $("#customer_form").find('input[type="email"]');
  if(emailId.val() == ""){
    return false;
  } else {
    return true;
  }
}

function validatePasswordField(){
  let password = $("#customer_form").find('#password');
  if(password.val() == ""){
    return false;
  } else {
    return true;
  }
}

function validatePassword(){
  let passwords = $("#customer_form").find('input[type="password"]');
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

function displayErrorMsg(msg){
  var $form = $("#customer_form");
  $form.find('#error_msg').html(msg);
  $form.find('.alert-danger').show();
}

function displaySuccessMsg(msg){
  var $form = $("#customer_form");
  $form.find('#success_msg').html(msg);
  $form.find('.alert-success').show();
}

function process(input){
  let value = input.value;
  let numbers = value.replace(/[^0-9]/g, "");
  input.value = numbers;
}