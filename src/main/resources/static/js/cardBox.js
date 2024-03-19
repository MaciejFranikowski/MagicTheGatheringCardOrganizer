$(document).ready(function(){
    $("#deckTableSearch").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#deckTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});
$(document).ready(function(){
    $("#collectionTableSearch").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#collectionTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});
$(document).ready(function(){
    $("#loanTableSearch").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#loanTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});