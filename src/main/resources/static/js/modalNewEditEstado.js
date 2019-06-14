$(function(){

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoEstado, #tblListaEstado .btnEditarEstado').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');
        // alert(nome);

        if (nome === "edit") {
            $('.modal-title').text('Editar') ;
            $.get(href, function (estado, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(estado.id);
                $('#formNewEdit #nome').val(estado.nome);
                $('#formNewEdit #sigla').val(estado.sigla);
            });
            $('#modalNewEditEstado').modal();
        } else if (nome === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo') ;
            $('#formNewEdit #id').val('');
            $('#formNewEdit #nome').val('');
            $('#formNewEdit #sigla').val('');
            $('#modalNewEditEstado').modal();
        }
    })
})
