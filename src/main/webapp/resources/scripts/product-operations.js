$(document).ready(function () {
    dataTableAutoScroll();
    dataTableAutoScroll2();
});
function dataTableAutoScroll() {
    var dataTable = document.getElementById("productCategoriesForm:productCategoriesList");
    if ($(dataTable).height() > 600) {
        var scroll = document.getElementById("productCategoriesForm:productCategoriesDataTableScroll");
        $(scroll).css("height", "600px");
    } else if ($(dataTable).height() < 600) {
        var scroll = document.getElementById("productCategoriesForm:productCategoriesDataTableScroll");
        $(scroll).css("height", "");
    }
}

function dataTableAutoScroll2() {
    var dataTable = document.getElementById("productsForm:productsList");
    if ($(dataTable).height() > 600) {
        var scroll = document.getElementById("productsForm:productDataTableScroll");
        $(scroll).css("height", "600px");
    } else if ($(dataTable).height() < 600) {
        var scroll = document.getElementById("productsForm:productDataTableScroll");
        $(scroll).css("height", "");
    }
}