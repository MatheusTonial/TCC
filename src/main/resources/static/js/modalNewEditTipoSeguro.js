$(function(){

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoTipoSeguro, #tblListaTipoSeguro .btnEditarTipoSeguro').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');
        // alert(nome);

        if (nome === "edit") {
            $('.modal-title').text('Editar') ;
            $.get(href, function (tipo_seguro, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(tipo_seguro.id);
                $('#formNewEdit #descricao').val(tipo_seguro.descricao);
            });
            $('#modalNewEditTipoSeguro').modal();
        } else if (nome === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo') ;
            $('#formNewEdit #id').val('');
            $('#formNewEdit #descricao').val('');
            $('#modalNewEditTipoSeguro').modal();
        }
    })


})
