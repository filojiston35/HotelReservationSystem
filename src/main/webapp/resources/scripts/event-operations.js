$(document).ready(function () {
    dataTableAutoScroll();
    dataTableAutoScroll2();

});
function deneme(eventMessage) {
    var viewEvent = document.getElementsByClassName("viewEvent");
    var viewEventPanel = document.getElementById("viewEventForm:viewEventPanel");
    $(viewEvent).empty();
    $(viewEvent).append(eventMessage);
}
function dataTableAutoScroll() {
    loadingSecreen();
    var dataTable = document.getElementById("myEventListForm:eventList");
    if ($(dataTable).height() > 600) {
        var scroll = document.getElementById("myEventListForm:myEventDataTableScroll");
        $(scroll).css("height", "600px");
    } else if ($(dataTable).height() < 600) {
        var scroll = document.getElementById("myEventListForm:myEventDataTableScroll");
        $(scroll).css("height", "");
    }
}

function dataTableAutoScroll2() {
    var dataTable = document.getElementById("otherEventListForm:otherEventList");
    if ($(dataTable).height() > 600) {
        var scroll = document.getElementById("otherEventListForm:otherEventDataTableScroll");
        $(scroll).css("height", "600px");
    } else if ($(dataTable).height() < 600) {
        var scroll = document.getElementById("otherEventListForm:otherEventDataTableScroll");
        $(scroll).css("height", "");
    }
}



function loadingSecreen() {
    var editor = document.getElementById("addEventForm:ptexteditor_input");
    var messageTitle = document.getElementById("addEventForm:eventTitle");

    if ($(messageTitle).val().length > 0 & $(editor).val().length > 0) {
         PF('loadingScreen').show();
    }

}
