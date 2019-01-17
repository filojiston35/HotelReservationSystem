$(document).ready(function () {
  //  $("fieldset").hide();
});
function deneme(eventMessage) {
    var viewEvent = document.getElementsByClassName("viewEvent");
    $(viewEvent).empty();
    $(viewEvent).append(eventMessage);
}