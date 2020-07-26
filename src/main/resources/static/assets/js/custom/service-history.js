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
                "<td>" + val.serviceId + "</td><td><input type='text' name='service_description' class='form-control unedit' rows='1'  id='description-"+ val.serviceId  + "' value ='" + val.serviceDescription + " '/> <span class='edit' id='spandesc-"+ val.serviceId  + "'>" 
                        + (val.serviceDescription ? val.serviceDescription : "") + " </span></td><td>" 
                      + "<input type='text' id='date-"+ val.serviceId  + "' value ='" + moment(val.serviceDateTime).format("MM-DD-YYYY HH:mm:ss")  + "' name='service_date_time' class='form-control datetimepicker unedit'> <span class='edit' id='spandate-"+ val.serviceId  + "'>" 
                      + (val.serviceDateTime ? moment(val.serviceDateTime).format("MM-DD-YYYY HH:mm:ss")  : "" ) + 
                    "</span></td><td>" + val.serviceCategory + "</td><td id= "+statusId+">"+ (val.status ? val.status : "") +
                    "</td><td id="+actionID+" >" + ( val.status == "Pending" || val.status == "Approved" ? "<a href class='unedit' onclick='action(this)' title='Approve' data-val='"+ JSON.stringify(val)+ "' ><i class='fa fa-check success' aria-hidden='true'></i></a> <a href class='edit' onclick='action(this)' title='Edit Service' data-val='"+ JSON.stringify(val)+ "' ><i class='fa fa-pencil pencil' aria-hidden='true'></i></a> <a href'' data-val='"+ JSON.stringify(val)+ "' onclick='action(this)' title='Cancel Service'><i class='fa fa-times danger' aria-hidden='true'></i></a>" :  "" )  + "</td>");
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
  

  function dataMapperFunction(data){
    const dataMapper = {
      "serviceId": "service_id",
      "customerId": "customer_id",
      "businessId": "business_id",
      "serviceDateTime": "service_date_time",
      "serviceDescription": "service_description",
      "status": "status",
      "serviceCategory": "service_category",
      "promoCode": "promo"
    }
    let newObj = {};
    for( let prop in data){
      newObj[dataMapper[prop]] = data[prop];
    }
    return newObj;
  }

  function checkDate(date){
    let myDate = moment(date);
    let today = moment();
    let diff = myDate.diff(today, 'days');

    return (diff > 1 ? true : false);
    
  }

  function action(self){
    event.preventDefault();
    let data = JSON.parse($(self).attr('data-val'));
    const rowid = data.serviceId;
    if($(self).find('i').hasClass('pencil')) {
      if(checkDate(data.serviceDateTime)){
        $('#' + data.status + rowid).html('Pending');
        data.status = "Pending";

        $('tr[id='+ rowid +']').find('.unedit').show();
        $('tr[id='+ rowid +']').find('.edit').hide();
      }
    } else if ($(self).find('i').hasClass('success')) {
      // data.status = "Updated";
      //input desc
      let descFieldId = '#description-' + rowid;
      let spandateField = '#spandesc-'+ rowid;
      data.serviceDescription = $(descFieldId).val();

      $(spandateField).html(data.serviceDescription);
      data.status = $('#' + data.status + rowid).html();
      //input date
      let dateFieldId =  '#date-' + rowid;
      data.serviceDateTime = $(dateFieldId).val();
      let spandescField = '#spandate-'+ rowid;
      $(spandescField).html(data.serviceDateTime);
     
      data = dataMapperFunction(data);
      updateStatus(data);
    } else {
      if(checkDate(data.serviceDateTime)){
        data.status = "Cancelled";
        data = dataMapperFunction(data);
        updateStatus(data);
      }
    }
    
  }

  function updateStatus(data){
    // update the records 
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
        $('tr[id='+ response.service_id +']').find('.edit').show();
        $('tr[id='+ response.service_id +']').find('.unedit').hide();
        $("#" + response.status +response.service_id).html(response.status);
        //$("#action"+response.service_id).html("");

      }   
    });
  }