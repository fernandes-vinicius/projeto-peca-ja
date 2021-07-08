$(document).on("click", "#confirme", function (e) {
    var link = $(this).attr("href"); // "get" the intended link in a var
    e.preventDefault();
    bootbox.confirm({
        message: "Confirmar operação?",
        buttons: {
            cancel: {
                label: 'Cancelar',
                className: 'bb-btn btn-default'
            },
            confirm: {
                label: 'Confirmar',
                className: 'bb-btn btn-info'
            }
        },
        callback: function (result) {
            if (result) {
                document.location.href = link;
            }
        }
    });
});