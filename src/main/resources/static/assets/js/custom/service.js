$(document).ready(function () {
    $('.alert-danger').hide();
    $('.alert-success').hide();

    var $login = $('#login');
    var $user = $("#user");
    var isAuthenticated = localStorage.getItem('username');
    if (isAuthenticated) {
      // update user name
      $login.hide();
      $user.show();
      $user.find('span').text(isAuthenticated);
      $user.find('a').attr("data-original-title", isAuthenticated);
    } else {
      // not authenticated
      $login.show();
      $user.hide();
    }

    if ($('.datetimepicker').length != 0) {
      //init DateTimePickers
      materialKit.initFormExtendedDatetimepickers();
    }

    let amountMap = {
      carpetCleaning: "$300",
      homeCleaning: "$400",
      painting: "$600",
      plumbing: "$250",
      gutterCleaning: "$300",
      masonry: "$3000",
      roofing: "$2500",
      windows: "$525",
      fencing: "$900",
      handymen: "$150",
      landscaping: "$425",
      pestControl: "$360"
    };

    function getAmountValueFromService() {
      let serviceType = getUrlVars()["serviceType"];
      return amountMap[serviceType];
    }

    $('#serviceAmt').val(getAmountValueFromService());

    let customerURL = "/business/api/category/" + getUrlVars()["serviceType"];
    
    $.ajax({
        url : customerURL,
        type: "GET",
        crossDomain: true,
        contentType: "application/json;",
        dataType: "json",
        cache: false,
        processData:false
        }).done(function(response){ 
          if(response){
            response.forEach((val, index) => {
                $('#contractor').append(`<option value="${val.customer_Id}"> 
                ${val.name} 
                </option>`); 
            })
            
          } 
      });
  });

function getUrlVars() {
    var vars = {};
    let parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
        vars[key] = value;
    });
    return vars;
}

function process(input){
    let value = input.value;
    let numbers = value.replace(/[^0-9]/g, "");
    input.value = numbers;
}

function validateCreditCard(){
    let credit_card = $("#credit_card").val();
    let confirm_credit_card = $("#confirm_credit_card").val();
    
    if(credit_card == "" || confirm_credit_card == "") {
        return false;
    } else {
        if(credit_card.length === 16 && confirm_credit_card.length === 16 && credit_card === confirm_credit_card){
            return true;
        }else {
            return false;
        }
    }
}

function validateCVV(){
    let cvv = $("#cvv").val();
    if(cvv){
        return true;
    } else {
        return false;
    }
}

function validateMonthYear(){
    let year = $( ".expYear option:selected" ).val();
    let month = $( ".expMonth option:selected" ).val();

    if(year && year != "" && month && month != "") {
        return true;
    } else {
        return false;
    }

}

function confirmBooking(){
    if(validateCreditCard() && validateCVV() && validateMonthYear()){
        $('.alert-danger').hide();
        $('.alert-success').hide();
        var $form = $("#billing_form");
        var data = getFormDataBilling($form);
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
            if(response){
                $("#billing_form")[0].reset();
                $("#service_form")[0].reset();
                $('#booking').modal('hide');
                displayConfirmationMsg('Service Request has been successfully submitted');
            } else {
                displayBillingErrorMsg('Some error occured while billing');
            }
        });
    } else {

        displayBillingErrorMsg("Please correct credit card details.");
    }
}

function displayBillingErrorMsg(msg){
    var $form = $("#billing_form");
    $form.find('#error_msg').html(msg);
    $form.find('.alert-danger').show();
}

function getExpiryMonth(){
    return $( ".expMonth option:selected" ).val();
}

function getExpiryYear(){
    return $( ".expYear option:selected" ).val();
}

function getFormDataBilling($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    if(getExpiryMonth()) {
        indexed_array["card_expiry_month"] = getExpiryMonth();
    }
    if(getExpiryYear()) {
        indexed_array["card_expiry_year"] = getExpiryYear();
    }
    var serviceId = $("#serviceId").val();
    if(serviceId){
        indexed_array["service_id"] = serviceId;
    }

    return indexed_array;
}

function getFormData($form){
    let unindexed_array = $form.serializeArray();
    let indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    let serviceType = getUrlVars()["serviceType"];
    if(serviceType) {
        indexed_array["service_category"] = serviceType;
    }

    if (getSelectedCategory() != "") {
        indexed_array["customer_id"] = getSelectedCategory();
    }
    
    return indexed_array;
}

function getSelectedCategory() {
    return $(".categories option:selected").val();
}

// Service Request
function submitRequest(){
    $('.alert-danger').hide();
    $('.alert-success').hide();
    var $form = $("#service_form");
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
        if(response){
          // open my modal on success
          $("#serviceId").val(response.service_id);
          $("#booking").modal();
        } else {
            displayErrorMsg('Some error occured while billing');
        }
    });
}

function displayErrorMsg(msg){
    $("#error_service").html(msg);
    $("#error_service").show();
}

function displayConfirmationMsg(msg){
    var $form = $("#service_form");
    $form.find('#success_msg').html(msg);
    $form.find('.alert-success').show();
}
  

