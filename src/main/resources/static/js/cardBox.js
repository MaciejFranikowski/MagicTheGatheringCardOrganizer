$(document).ready(function(){
    $(".table-search").on("keyup", function() {
        const value = $(this).val().toLowerCase();
        const tableType = $(this).data('tr-name');
        const $tableTr = $('#'+tableType+'Table tr');
        $tableTr.filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

$(document).ready(function (){
    $('.card-search').on('input', function (){
        var query = $(this).val().trim();
        var dropdownId = $(this).data('dropdown');
        var $dropdown = $('#dropdownMenu' + dropdownId);
        if (query !== '') {
            $.ajax({
                url: '/api/cards/autocomplete/' + encodeURIComponent(query),
                method: 'GET',
                success: function(response) {
                    displayResults(response, $dropdown);
                },
                error: function(xhr, status, error) {
                    console.error('Error:', error);
                }
            });
        } else {
            $dropdown.empty().hide();
        }
    });
    function displayResults(results, $dropdown) {
        $dropdown.empty();
        if (results.length > 0) {
            $.each(results, function(index, result) {
                $dropdown.append('<a class="dropdown-item" href="#">' + result + '</a>');
            });
            $dropdown.show();
        } else {
            $dropdown.hide();
        }
    }

    $(document).on('click', '.dropdown-menu .dropdown-item', function() {
        var selectedValue = $(this).text();
        var $input = $(this).closest('.position-relative').find('.card-search');
        $input.val(selectedValue);
        $('.dropdown-menu').hide();
    });

    $('.card-info').on('click', function (){
        var cardName = $(this).find("td:first").text();
        window.location = "/card/"+cardName
    });

    $('.delete-card-button').on('click', function (event){
        event.stopPropagation();
    });
})
function deleteCard(id, type, boxId) {
    $.ajax({
        url: '/deleteCard/'+type+'/'+id,
        data:{
            boxId:boxId,
        },
        method: 'GET',
        async:false,
    });
    this.window.location="/cardBox/"+boxId;
}