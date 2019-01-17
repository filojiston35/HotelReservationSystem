$(document).ready(function () {
    dataTableAutoScroll();
});
function dataTableAutoScroll(){
    var dataTable=document.getElementById("roomsForm:roomList");
    if($(dataTable).height()>600){
        var scroll=document.getElementById("roomsForm:roomDataTableScroll");
        $(scroll).css("height","600px");
    }
    else if($(dataTable).height()<600){
        var scroll=document.getElementById("roomsForm:roomDataTableScroll");
        $(scroll).css("height","");
    }
}