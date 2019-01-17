$(document).ready(function () {
    var mail = document.getElementById("forgotPasswordForm:mail");
    var button = document.getElementById("forgotPasswordForm:forgotPasswordButton");
    var re = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
    $(button).click(function () {
        if ($(mail).val().length > 0 & re.test($(mail).val())) {
            PF('loadingScreen').show();
        }

    });
});
