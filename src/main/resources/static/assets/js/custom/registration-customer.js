   $(document).ready(function () {
    
});

   function getFormData($form){
     var unindexed_array = $form.serializeArray();
     var indexed_array = {};

     $.map(unindexed_array, function(n, i){
       indexed_array[n['name']] = n['value'];
     });

     return indexed_array;
   }

function customerRegistration(){
  var $form = $("#customer_form");
  var data = getFormData($form);
  console.log(data);
  let customer_form = $("#customer_form");
  let post_url = customer_form.attr("action"); //get form action url
  let request_method = customer_form.attr("method"); //get form GET/POST method
  console.log(request_method);
  let form_data = customer_form.serialize();
  console.log(form_data);
  var object = {};

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
      //window.location.replace("../index.html");
  });
}