$(document).ready(function () {
    var $login = $('#login');
    var $user = $("#user");
    var isAuthenticated = localStorage.getItem('username');
    var role = localStorage.getItem("role");
    if (isAuthenticated && role) {
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
    } else {
      // not authenticated
      $login.show();
      $("#user").hide();
      $('.customer').hide();
      $('.contractor').hide();
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