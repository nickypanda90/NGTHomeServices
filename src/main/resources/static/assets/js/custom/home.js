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
    if (role == "customer") {
      $('.customer').show();
      $('.contractor').hide();

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

// Typeahead for services

var substringMatcher = function (strs) {
  return function findMatches(q, cb) {
    var matches, substringRegex;

    // an array that will be populated with substring matches
    matches = [];

    // regex used to determine if a string contains the substring `q`
    substrRegex = new RegExp(q, 'i');

    // iterate through the pool of strings and for any string that
    // contains the substring `q`, add it to the `matches` array
    $.each(strs, function (i, str) {
      if (substrRegex.test(str)) {
        matches.push(str);
      }
    });

    cb(matches);
  };
};

var services = ["carpetCleaning", "homeCleaning", "painting",
  "plumbing", "gutterCleaning", "masonry",
  "roofing", "windows", "fencing", "handymen",
  "landscaping", "pestControl"
];

$('.typeahead').typeahead({
  hint: true,
  highlight: true,
  minLength: 1
},
  {
    name: 'services',
    source: substringMatcher(services)
  });


function submitService() {
  let find_service_val = $('.tt-input').val();
  let redirectUrl = "pages/customer/service-page.html?serviceType=" + find_service_val;
  window.location.replace(redirectUrl);
}