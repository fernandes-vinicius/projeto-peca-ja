function loadModal(url){

    $.get(url, function (data) {
        $(".modal").html(data);
        $("#modalDetalhes").modal("show");
    }, 'html');
}

