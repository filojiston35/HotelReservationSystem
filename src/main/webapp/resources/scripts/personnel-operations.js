$(document).ready(function () {
    dataTableAutoScroll();


});
function dataTableAutoScroll() {
    loadingSecreen();
    var dataTable = document.getElementById("personnelForm:personnelList");
    if ($(dataTable).height() > 600) {
        var scroll = document.getElementById("personnelForm:personnelDataTableScroll");
        $(scroll).css("height", "600px");
    } else if ($(dataTable).height() < 600) {
        var scroll = document.getElementById("personnelForm:personnelDataTableScroll");
        $(scroll).css("height", "");
    }
}

function loadingSecreen() {
    var name = document.getElementById("personnelInsertForm:personnel_name");
    var surname = document.getElementById("personnelInsertForm:personnel_surname");
    var department = document.getElementById("personnelInsertForm:personnel_department");
    var mail = document.getElementById("personnelInsertForm:personnel_mail");
    var personnelButton = document.getElementById("personnelInsertForm:personnelInsertButton");
    var specialCharacter=/^[a-zA-Z,ç,Ç,ğ,Ğ,ı,İ,ö,Ö,ş,Ş,ü,Ü,' ']+$/;
     var re = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
        if($(name).val().length>0 & specialCharacter.test($(name).val()) & $(surname).val().length>0 & specialCharacter.test($(surname).val()))
        {
            if($(mail).val().length > 0 & re.test($(mail).val()) & $(department).val()!=''){
                  PF('loadingScreen').show();
            }
        }
    
}