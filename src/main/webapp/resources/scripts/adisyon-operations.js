$(document).ready(function () {
    $(".adisyonContainer").hide();
    dataTableAutoScroll();
});

$(".showAdisyonDetail").click(function () {
    $(".adisyonContainer").show(500);
});

function dataTableAutoScroll(){
    var dataTable=document.getElementById("roomsForm:roomList");
    if($(dataTable).height()>400){
        var scroll=document.getElementById("roomsForm:roomDataTableScroll");
        $(scroll).css("height","400px");
    }
    else if($(dataTable).height()<400){
        var scroll=document.getElementById("roomsForm:roomDataTableScroll");
        $(scroll).css("height","");
    }
}