function imagepreview(input){
    if(input.files && input.files[0]){
        var filerd=new FileReader();
        filerd.onload=function(e){
            $('.imgpreview').attr('src',e.target.result);
        };
        filerd.readAsDataURL(input.files[0]);
    }
}

$('.imageDefaultButton').click(function () {
    alert("asdsad");
    $('.imgpreview').attr('src',"../resources/images/user-regular.svg");
});