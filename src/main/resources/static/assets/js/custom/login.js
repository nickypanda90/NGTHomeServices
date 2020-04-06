function submitLoginForm() {
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
          // window.location.replace("../index.html");
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