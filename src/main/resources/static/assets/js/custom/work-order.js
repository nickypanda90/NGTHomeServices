$(document).ready(function () {
  var $login = $('#login');
  var $user = $("#user");
  var isAuthenticated = localStorage.getItem('username');
  var role = localStorage.getItem("role");
  var id = localStorage.getItem("id");
  if (isAuthenticated && role && id) {
    // update user name
    $login.hide();
    $user.show();
    $user.find('span').text(isAuthenticated);
    $user.find('a').attr("data-original-title", isAuthenticated);
    if(role == "customer"){
      $('.customer').show();
    } else {
      $('.contractor').show();
      $('.customer').hide();
    }


  getWorkOrder(id);

  //Initialize datatable
  // $('#workOrderTbl').DataTable(
  //   {
  //     "searching": false,
  //     // "pagingType": "numbers",
  //     // "pageLength": 10,
  //     "paging": false,
  //     "bInfo" : false,
  //     "bLengthChange": false,
  //     "language": {
  //       "emptyTable": "No data available in table"
  //     },
  //     "ajax": {
  //       url: "/service/contractor/"+ id
  //     },
  //     "columns": [
  //       { 
  //         // "title": "Service ID",
  //         "data": "service_id" 
  //       },
  //       { 
  //         // "title": "Customer ID",
  //         "data": "customer_id" 
  //       },
  //       { 
  //         // "title": "Business ID", 
  //         "data": "business_id" 
  //       },
  //       { 
  //         // "title": "Service Date time",
  //         "data": "service_date_time" 
  //       },
  //       {  
  //         // "title": "Description",
  //         "data": "service_description" 
  //       },
  //       { 
  //         // "title": "Category",
  //         "data": "service_category" 
  //       }
  //   ]
  // }
  // );

  } else {
    // not authenticated
    $login.show();
    $("#user").hide();
    $('.customer').hide();
    $('.contractor').hide();
    window.location.replace("../../index.html"); 
  }
});

function scrollToDownload() {
  if ($('.section-download').length != 0) {
    $("html, body").animate({
      scrollTop: $('.section-download').offset().top
    }, 1000);
  }
}

function getUrlVars() {
  var vars = {};
  let parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
    vars[key] = value;
  });
  return vars;
}

function getWorkOrder(id){
  $.ajax({
    url: "/service/contractor/" + id,
    type: "GET",
    crossDomain: true,
    contentType: "application/json;",
    dataType: "json",
    cache: false,
    processData: false
}).done(function (response) {
    var table = $('#workOrderTbl');
    var tbody = table.find('tbody');
    response.forEach((val, index)=> {
      // var row = $('<tr>').html("<td>" + val.service_id + "</td><td>" + 
      //             val.customer_id + "</td><td>" + val.business_id + 
      //             "</td><td>" +val.customer_id + "</td><td>"+ moment(val.service_date_time).format("MM-DD-YYYY HH:mm:ss") + 
      //             "</td><td>" +val.service_description + "</td><td>" +  
      //             val.service_category + "</td>");
    var row = $('<tr>').html("<td>" + val.service_id + "</td><td>"+val.service_description + "</td><td>" 
                    + moment(val.service_date_time).format("MM-DD-YYYY HH:mm:ss") + 
                  "</td><td>" +val.service_description + "</td><td>" +  
                  val.service_category + "</td>");
      tbody.append(row);
    });
});
}