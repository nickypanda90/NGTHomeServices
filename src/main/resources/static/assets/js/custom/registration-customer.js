 $(document).ready(function () {
    function customerRegistration(){
        var post_url = $(this).attr("action"); //get form action url
        var request_method = $(this).attr("method"); //get form GET/POST method
        // var form_data = new FormData(this); //Creates new FormData object
        var form_data = $("#customer_form").serializeObject();
        console.log(form_data);
        debugger;
        $.ajax({
          url : post_url,
          type: request_method,
          data : form_data,
          contentType: false,
          cache: false,
          processData:false
          }).done(function(response){ 
            // window.location.replace("./index.html");
        });
    }
});