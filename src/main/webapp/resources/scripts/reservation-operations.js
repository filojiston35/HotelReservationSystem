function hideExtraButtons() {
    $(".timeline-event.timeline-event-range").attr("onclick", "showExtraButtons();");
    $(".fixed").css("bottom", "0px");
    var extraButtons = document.getElementsByClassName("reservationExtraButtons");
    $(extraButtons).hide(500);
}
function showExtraButtons() {
    $(".fixed").css("bottom", "150px");
    var extraButtons = document.getElementsByClassName("reservationExtraButtons");
    $(extraButtons).show(500);
}

$(document).ready(function () {
    $(".timeline-event.timeline-event-range").attr("onclick", "showExtraButtons();");
    $("#content").attr("onclick", "contentFunction();");
    hideExtraButtons();
});
function contentFunction() {
    if ($(".timeline-event.timeline-event-range").hasClass("timeline-event-selected")) {

    } else {
        $(".timeline-event.timeline-event-range").removeClass("timeline-event-selected");
        $(".timeline-event.timeline-event-range").removeClass("ui-state-active");
        hideExtraButtons();

    }
}

function deneme(eventMessage) {
    var viewEvent = document.getElementsByClassName("viewEvent");
    $(viewEvent).empty();
    $(viewEvent).append(eventMessage);
}

function insertButton() {
    var insetButton = document.getElementsByClassName("reservationInsertButton");
    $(insetButton).click(function () {
       hideExtraButtons();
    });
}
 