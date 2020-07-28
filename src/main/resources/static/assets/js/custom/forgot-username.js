
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

function submitUserName(){
    var $form = $("#form-forgot-username");
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
    });
}