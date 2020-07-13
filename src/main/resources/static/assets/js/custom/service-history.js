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

    const id = localStorage.getItem("id");
    console.log(id);
    getOrderHistory(id);
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
  
  function getOrderHistory(id){
      console.log("method ",id);
    $.ajax({
      url: "/customer/api/servicehistory/" + id,
      type: "GET",
      crossDomain: true,
      contentType: "application/json;",
      dataType: "json",
      cache: false,
      processData: false
  }).done(function (response) {
      let table = $('#serviceOrderTbl');
      let tbody = table.find('tbody');
      response.forEach((val, index)=> {
        let statusId = val.status + val.serviceId;
        let actionID= "action" + val.serviceId;
        let row = $('<tr>').attr('id', val.serviceId).html(
                "<td>" + val.serviceId + "</td><td><textarea type='text' name='service_description' class='form-control unedit' rows='1' id='serviceDescription'></textarea> <span class='edit' >" 
                        + (val.serviceDescription ? val.serviceDescription : "") + " </span></td><td>" 
                      + "<input type='text' name='service_date_time' class='form-control datetimepicker unedit'> <span class='edit'>" 
                      + (val.serviceDateTime ? moment(val.serviceDateTime).format("MM-DD-YYYY HH:mm:ss")  : "" ) + 
                    "</span></td><td>" + val.serviceCategory + "</td><td id= "+statusId+">"+ (val.status ? val.status : "") +
                    "</td><td id="+actionID+" >" + ( val.status == "Pending"  ? "<a href onclick='action(this)' title='Edit Service' data-val='"+ JSON.stringify(val)+ "' ><i class='fa fa-pencil pencil' aria-hidden='true'></i></a> <a href'' data-val='"+ JSON.stringify(val)+ "' onclick='action(this)' title='Deny Service'><i class='fa fa-times danger' aria-hidden='true'></i></a>" :  "" )  + "</td>");
        tbody.append(row);

        $('.unedit').hide();
        $('.edit').show();
        if ($('.datetimepicker').length != 0) {
            //init DateTimePickers
            materialKit.initFormExtendedDatetimepickers();
        }
      });
  });
  }
  
  function action(self){
    event.preventDefault();
    let data = JSON.parse($(self).attr('data-val'));
    if($(self).find('i').hasClass('pencil')) {
      data.status = "Pending";
      const rowid = data.serviceId;
      $('tr[id='+ rowid +']').find('.unedit').show();
      $('tr[id='+ rowid +']').find('.edit').hide();
    } else {
      data.status = "Cancelled";
    }

    // update the records 
    // $.ajax({
    //   url: "/service/update",
    //   type: "POST",
    //   data: JSON.stringify(data),
    //   crossDomain: true,
    //   contentType: "application/json;",
    //   dataType: "json",
    //   cache: false,
    //   processData: false
    // }).done((response) =>{
    //   if(response) {
    //     $("#Pending"+response.service_id).html(response.status);
    //     $("#action"+response.service_id).html("");
    //   }   
    // });
  }