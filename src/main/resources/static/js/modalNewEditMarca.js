$(function () {

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoMarca, #tblListaMarca .btnEditarMarca').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');
        // alert(nome);

        if (nome === "edit") {
            $('.modal-title').text('Editar');
            $.get(href, function (marca, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(marca.id);
                $('#formNewEdit #descricao').val(marca.descricao);
            });
            $('#modalNewEditMarca').modal();
            // $('#modalNewEdit').modal().find('.modal-body #descricao').html(marca.descricao);
        } else if (nome === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo');
            $('#formNewEdit #id').val('');
            $('#formNewEdit #descricao').val('');
            $('#modalNewEditMarca').modal();
        }
    })
    // $('#salvar').on('click', function () {
    //     $("#formNewEdit").validate({
    //         rules: {
    //             descricao: {
    //                 required: true,
    //                 minLength: 4
    //             }
    //         },
    //         messages: {
    //             descricao: {
    //                 required: "Campo em branco!",
    //                 minLength: "Campo precisa ter no minimo 4 caracteres"
    //             }
    //         }
    //     });
    // })
})

// modal.find('.modal-body span').html(
//     'Tem certeza que deseja remover a marca <strong>'
//     + marca + '</strong>?')


//primeiro parametro é a pagina com o conteudo
//primeiro parametro da função é o conteudo


// var marcas = {};
// marcas["id"] = $("#id").val(marcas.id);
// marcas["descricao"] = $("#descricao").val(marcas.descricao);
// $.ajax({
//     type: "POST",
//     contentType: "application/json",
//     url: "/api/login",
//     data: JSON.stringify(marcas),
//     dataType: 'json',
//     cache: false,
//     timeout: 600000,
//     success: function (data) {
//
//         var json = "<h4>Ajax Response</h4><pre>"
//             + JSON.stringify(data, null, 4) + "</pre>";
//         $('#feedback').html(json);
//
//         console.log("SUCCESS : ", data);
//         //$("#btn-login").prop("disabled", false);
//         $('#modalNewEdit').modal();
//     },
//     error: function (e) {
//
//         var json = "<h4>Ajax Response Error</h4><pre>"
//             + e.responseText + "</pre>";
//         $('#feedback').html(json);
//
//         console.log("ERROR : ", e);
//         // $("#btn-login").prop("disabled", false);
//
//     }
// });