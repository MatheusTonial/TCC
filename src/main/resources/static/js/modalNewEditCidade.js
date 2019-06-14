$(function(){

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoCidade, #tblListarCidade .btnEditarCidade').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');

        if (nome === "edit") {
            $('.modal-title').text('Editar') ;
            $.get(href, function (cidade, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(cidade.id);
                $('#formNewEdit #nome').val(cidade.nome);
                $('#formNewEdit #estado').val(cidade.estado.id);
            });
            $('#modalNewEditCidade').modal();
        } else if (nome === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo') ;
            $('#formNewEdit #id').val('');
            $('#formNewEdit #nome').val('');
            $('#formNewEdit #estado').val('');
            $('#modalNewEditCidade').modal();
        }
    })
})