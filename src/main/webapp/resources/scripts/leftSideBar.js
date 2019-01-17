$(".leftSidebarButton").click(function () {
    var leftsize = $("#leftSidebar").css("margin-left");
    if (leftsize == '0px')
    {
        $(".jqplot-table-legend").css("transition", "0.4s");
        $("#leftSidebar").addClass("leftSideBarEffect");
        $(".jqplot-table-legend").css("left", "100%");
        $(".leftSidebarButton").css("background-color", "#c03926");
        $("#content").addClass("contentShowEffect");
    } else if (leftsize == '220px')
    {
        $("#leftSidebar").removeClass("leftSideBarEffect");
        $(".jqplot-table-legend").css("transition", "0.4s");
        $(".jqplot-table-legend").css("left", "90%")
        $(".leftSidebarButton").css("background-color", "");
        $("#content").removeClass("contentShowEffect");
    }

});
$(document).ready(function () {
    $(".jqplot-table-legend").css("transition", "0.4s");
    var element1 = $(document.getElementById("leftSidebarForm:managment-dropdown"));
    $(element1).click(function () {
        $("#managment-options").slideToggle("slow");
    });

    var element2 = $(document.getElementById("leftSidebarForm:personal-dropdown"));
    $(element2).click(function () {
        $("#personal-settings").slideToggle("slow");
    });
});




