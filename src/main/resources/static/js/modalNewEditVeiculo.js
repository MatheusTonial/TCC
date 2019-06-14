$(function(){

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoVeiculo, #tblListaVeiculo .btnEditarVeiculo').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');
        // alert(nome);

        if (nome === "edit") {
            $('.modal-title').text('Editar') ;
            $.get(href, function (veiculo, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(veiculo.id);
                $('#formNewEdit #placa').val(veiculo.placa);
                $('#formNewEdit #modelo').val(veiculo.modelo);
                $('#formNewEdit #ano').val(veiculo.ano);
                $('#formNewEdit #marca').val(veiculo.marca.id);
            });
            $('#modalNewEditVeiculo').modal();
        } else if (nome === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo') ;
            $('#formNewEdit #id').val('');
            $('#formNewEdit #placa').val('');
            $('#formNewEdit #modelo').val('');
            $('#formNewEdit #ano').val('');
            $('#formNewEdit #marca').val('');
            $('#modalNewEditVeiculo').modal();
        }
    })
})
