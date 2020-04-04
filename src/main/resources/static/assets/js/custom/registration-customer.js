 $(document).ready(function () {
    
});

function customerRegistration(){
  let customer_form = $("#customer_form");
  let post_url = customer_form.attr("action"); //get form action url
  let request_method = customer_form.attr("method"); //get form GET/POST method
  console.log(request_method);
  let form_data = customer_form.serialize();
  console.log(form_data);
  $.ajax({
    url : post_url,
    type: request_method,
    data : form_data,
    crossDomain: true,
    contentType: "application/json;",
    dataType: "json",
    cache: false,
    processData:false
    }).done(function(response){ 
      console.log(response);
      window.location.replace("./index.html");
  });
}