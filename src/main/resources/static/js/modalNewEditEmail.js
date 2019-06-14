$(function(){
    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnEmail, #tblListaEmail .btnEditarEmail, .btnEnviarEmail').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');

        if (nome === "notificacao") {
            $('.modal-title').text('Editar Notificação') ;
            $.get(href, function (email, status) {
                $('#formNewEditEmail #id').val(email.id);
                $('#formNewEditEmail #titulo').val(email.titulo);
                $('#formNewEditEmail #texto').val(email.texto);
                $('#formNewEditEmail #prazo1').val(email.prazo1);
                $('#formNewEditEmail #prazo2').val(email.prazo2);
                $('#formNewEditEmail #desfazer').val(email.id);
                $('#formNewEditEmail #desfazer').name = email.id;
            });
            $('#modalNewEditEmail').modal();
        }
        else if (nome === "email") {
            $('.modal-title').text('Confirmar Notificação') ;
            $.get(href, function (email, status) {
                $('#formNewEditEmail #id').val(email.id);
                $('#formNewEditEmail #titulo').val(email.titulo);
                $('#formNewEditEmail #texto').val(email.texto);
                $('#formNewEditEmail #prazo1').val(email.prazo1);
                $('#formNewEditEmail #prazo2').val(email.prazo2);
                console.log(email.id)
            });
            $('#mandarEmail').modal();
        }
    })
})