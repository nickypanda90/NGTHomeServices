$(document).ready(function () {
    $('.alert-danger').hide();
    $('.alert-success').hide();

    var $login = $('#login');
    var $user = $("#user");
    // var isAuthenticated = $.cookie('username');
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
        $("#user").hide();
    }

    // Get Logged in user details
    //getUserDetails();
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
      let table = $('#workOrderTbl');
      let tbody = table.find('tbody');
      response.forEach((val, index)=> {
        let statusId = val.status + val.service_id;
        let actionID= "action" + val.service_id;
        let row = $('<tr>').attr('id', val.service_id).html("<td>" + val.service_id + "</td><td>"+ (val.service_description ? val.service_description : "") + "</td><td>" 
                      + (val.service_date_time ? moment(val.service_date_time).format("MM-DD-YYYY HH:mm:ss")  : "" ) + 
                    "</td><td>" + val.service_category + "</td><td id= "+statusId+">"+ (val.status ? val.status : "") +
                    "</td><td id="+actionID+" >" + ( val.status == "Pending"  ? "<a href='' onclick='action(this)' title='Approve Service' data-val='"+ JSON.stringify(val)+ "' ><i class='fa fa-check success' aria-hidden='true'></i></a> <a href'' data-val='"+ JSON.stringify(val)+ "' onclick='action(this)' title='Deny Service'><i class='fa fa-times danger' aria-hidden='true'></i></a>" :  "" )  + "</td>");
        tbody.append(row);
      });
  });
  }
  
  function action(self){
    event.preventDefault();
    let data = JSON.parse($(self).attr('data-val'));
    if($(self).find('i').hasClass('success')) {
      data.status = "Approved";
    } else {
      data.status = "Denied";
    }
    $.ajax({
      url: "/service/update",
      type: "POST",
      data: JSON.stringify(data),
      crossDomain: true,
      contentType: "application/json;",
      dataType: "json",
      cache: false,
      processData: false
    }).done((response) =>{
      if(response) {
        $("#Pending"+response.service_id).html(response.status);
        $("#action"+response.service_id).html("");
      }   
    });
  }