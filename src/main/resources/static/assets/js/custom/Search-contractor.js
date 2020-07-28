function toCamelCase(str){
    return str.split(' ').map(function(word,index){
        // If it is the first word make sure to lowercase all the chars.
        if(index == 0){
            return word.toLowerCase();
        }
        // If it is not the first word only upper case the first char and lowercase the rest.
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    }).join('');
}

$(document).ready(function () {

    var $login = $('#login');
    var $user = $("#user");
    var isAuthenticated = localStorage.getItem('username');
    var role = localStorage.getItem("role");
    var id = localStorage.getItem("id");
    var table = $('#serviceOrderTbl1').DataTable({
        "sAjaxSource": "/customer/api/getcontractorlist",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "columns": [
            {
                "data": 'name',
                render: function(data, type,row, meta) {
                    return type === 'display' ?
                        '<a href="./service-page.html?serviceType='+ toCamelCase(row.review_catg) +'&selectedContractor='+data+'">' + data + '</a>' :
                        data;
                }}
            ,
            { "data": "review_catg" },
            { "data": "rating" },

            {
                "data": 'count',
                render: function(data, type,row, meta) {
                    return type === 'display' ?
                        '<progress value="' + data + '" max="20"></progress>' :
                        data;
                }
            },

        ]
    })


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

        // getWorkOrder(id);

    } else {
        // not authenticated
        $login.show();
        $("#user").hide();
        $('.customer').hide();
        $('.contractor').hide();
        window.location.replace("../../index.html");
    }
});


//     function getWorkOrder(id){
//         $.ajax({
//             url: "/customer/api/getcontractorlist",
//             type: "GET",
//             crossDomain: true,
//             contentType: "application/json;",
//             dataType: "json",
//             cache: false,
//             processData: false
//         }).done(function (response) {
//             let table = $('#serviceOrderTbl1');
//
//             let tbody = table.find('tbody');
//
//             response.forEach((val, index)=> {
//                 let row = $('<tr>').attr('id', val.name).html("<td>" + val.name +
//                     "</td><td>"+ (val.review_catg ) +
//                     "</td><td>" + val.rating);
//                 tbody.append(row);
//             });
//         });
//
//
//
//
//
// }