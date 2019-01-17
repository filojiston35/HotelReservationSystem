$(".btn-primary").click(function(){
    $("#personnelInDepartment").css("opacity",0);
    $("#departmentTable tr:first-child td").css("border-right","none");
});

function personnelInDepartmentShow(){
    $("#personnelInDepartment").css("opacity",100);
    $("#departmentTable tr:first-child td").css("border-right","2px solid #ddd");
}
function personnelInDepartmentHide(){
      $("#personnelInDepartment").css("opacity",0);
    $("#departmentTable tr:first-child td").css("border-right","none");
}

$(document).ready(function () {
    dataTableAutoScroll();
    dataTableAutoScroll2();
});
function dataTableAutoScroll(){
    var dataTable=document.getElementById("departmentForm:departmentList");
    if($(dataTable).height()>600){
        var scroll=document.getElementById("departmentForm:departmentDataTableScroll");
        $(scroll).css("height","600px");
    }
    else if($(dataTable).height()<600){
        var scroll=document.getElementById("departmentForm:departmentDataTableScroll");
        $(scroll).css("height","");
    }
}
function dataTableAutoScroll2(){
    var dataTable=document.getElementById("personnelInDepartment:personnelList");
    if($(dataTable).height()>600){
        var scroll=document.getElementById("personnelInDepartment:personnelDataTableScroll");
        $(scroll).css("height","600px");
    }
    else if($(dataTable).height()<600){
        var scroll=document.getElementById("personnelInDepartment:personnelDataTableScroll");
        $(scroll).css("height","");
    }
}
