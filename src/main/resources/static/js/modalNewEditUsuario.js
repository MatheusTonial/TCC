$(function(){

    $(document).ready(function () {
        $('[data-toggle="popover"]').popover();
    });

    document.getElementById("estado").onchange = function () {
        var estd = this.value;
        document.getElementById("tblCidade").disabled = true;
        // document.getElementById("tblCidade").style.visibility = 'hidden';
        // document.getElementById("lblCidade").style.visibility = 'hidden';
        document.getElementById('tblCidade').innerHTML = ""; //clear s2 to avoid conflicts between options values
        var listCidade = document.getElementById("cidade");
        console.log(estd);
        var teste = 0;
        for(var i = 0; i <= listCidade.length; i++){
            if(listCidade[i].getAttribute("name") == estd){
                console.log(listCidade[i].text +" - "+ listCidade[i].value);
                // listCidade[i];
                var ele = document.createElement('option');
                ele.textContent = listCidade[i].text;
                ele.value = listCidade[i].value;
                document.getElementById("tblCidade").appendChild(ele);
                document.getElementById("tblCidade").disabled = false;
                // document.getElementById("lblCidade").innerText = "Teste";
                // document.getElementById("lblCidade");
            }
    }




    }


    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoUsuario, #tblListaUsuario .btnEditarUsuario').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nomeModal = $(this).attr('name');
        // alert(nomeModalModal);

        if (nomeModal === "edit") {
            $('.modal-title').text('Editar') ;
            $.get(href, function (usuario, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(usuario.id);
                $('#formNewEdit #nome').val(usuario.nome);
                $('#formNewEdit #email').val(usuario.email);
                $('#formNewEdit #senha').val(usuario.senha);
                $('#formNewEdit #rua').val(usuario.rua);
                $('#formNewEdit #numero').val(usuario.numero);
                $('#formNewEdit #bairro').val(usuario.bairro);
                $('#formNewEdit #telefone').val(usuario.telefone);
                $('#formNewEdit #cpf').val(usuario.cpf);
                $('#formNewEdit #rg').val(usuario.rg);
                $('#formNewEdit #tipo').val(usuario.tipo);
                $('#formNewEdit #cidade').val(usuario.cidade.id);
            });
            $('#modalNewEditUsuario').modal();
        } else if (nomeModal === "new") {
            // alert("Executou novo");
            $('.modal-title').text('Novo') ;
            $('#formNewEdit #id').val('');
            $('#formNewEdit #nome').val('');
            $('#formNewEdit #email').val('');
            $('#formNewEdit #senha').val('');
            $('#formNewEdit #rua').val('');
            $('#formNewEdit #numero').val('');
            $('#formNewEdit #bairro').val('');
            $('#formNewEdit #telefone').val('');
            $('#formNewEdit #cpf').val('');
            $('#formNewEdit #rg').val('');
            $('#formNewEdit #tipo').val('');
            $('#formNewEdit #cidade').val('');
            $('#modalNewEditUsuario').modal();
        }
    })
})
