$(function () {

    var tabela = document.getElementById('tblListaSeguro'), rIndex, cIndex;
    var today = new Date();

    var lp = document.getElementById('listagemParcela').innerText;
    lp = lp.split(',');


    $('#tipoSeguro').on('change', function () {
        var elem = document.getElementById("tipoSeguro");
        var strUser = elem.options[elem.selectedIndex].text;
        var divHid = document.getElementById("divVeiculo");
        if (strUser == "Veiculo") {
            divHid.style.visibility = 'visible';
        } else {
            divHid.style.visibility = 'hidden';
        }
    });


    for (var i = 1; i < tabela.rows.length; i++) {
        for (var j = 0; j < tabela.rows[i].cells.length; j++) {
            if (j == 4) {
                var d = (tabela.rows[i].cells[j]).innerText;
                d = d.split('/');
                var ds = new Date((d[2]), d[1] - 1, d[0]);
                ds.setFullYear(ds.getFullYear() + 1);
                if (today < ds) {
                    var res = Math.abs(ds - today) / 1000;
                    var dias = Math.floor(res / 86400);
                    var p1 = (tabela.rows[i].cells[7]).getElementsByClassName('prazo1')[0].innerText;
                    var p2 = (tabela.rows[i].cells[7]).getElementsByClassName('prazo2')[0].innerText;
                    var idS = tabela.rows[i].getElementsByClassName('idSeguro')[0].innerText;
                    var email = (tabela.rows[i].cells[7]).getElementsByClassName('btnNotificacao')[0];
                    // MUDAR IF PARA ==
                    if (dias <= p1 || dias <= p2) {
                        email.setAttribute('href', '/seguros/confirmar/' + idS + '/1');
                        email.style.visibility = 'visible';
                    }

                    for (var x = 0; x < lp.length; x++) {
                        var sta = lp[x].substring(11, 17);
                        var idSS = lp[x].substring(lp[x].length - 1, lp[x].length);
                        if (sta == "aberto" && idSS == idS) {
                            var diaP = lp[x].substring(0, 10);
                            var idData = "";
                            diaP = diaP.split('-');
                            idData = diaP[2] + diaP[1] + diaP[0];
                            var dataPa = new Date((diaP[0]), diaP[1] - 1, diaP[2]);
                            var comp = Math.abs(dataPa - today) / 1000;
                            var diasPa = Math.floor(comp / 86400);
                            // MUDAR IF PARA ==
                            if (diasPa <= p1 || diasPa <= p2) {
                                // var email = (tabela.rows[i].cells[7]).getElementsByClassName('btnNotificacao')[0];
                                var email2 = (tabela.rows[i].cells[7]).getElementsByClassName('btnNotificacao')[0];
                                email.setAttribute('href', '/seguros/confirmar/' + idS + '/' + idData);
                                email2.style.visibility = 'visible';
                            }
                        }
                    }
                }
            }
        }
    }

    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip({
            trigger: 'hover'
        });
    });


    $('.btnTabela').on('click', function () {
        var href = $(this).attr('href');
        var tag = $(this).attr('target');
        $.get(href, function () {
            $('#myIframe').target(tag);
        });
        $('#tblParcelas').find('.modal-body').css({
            width: 'auto',
            height: 'auto'
        })
        $('#tblParcelas').modal();
    })

    $('.esconderBotao').on('click', function () {
        var idV = document.getElementById('teste').getElementsByClassName('idEmail').innerText;
        // console.log(idV);
    })

    // $('.btnNovo, #tblLista .btnEditar').on('show.bs.modal', function (event) {
    $('.btnNovoSeguro, #tblListaSeguro .btnEditarSeguro, .btnNotificacao').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var nome = $(this).attr('name');
        // alert(nome);
        if (nome === "edit") {
            $('.modal-title').text('Editar');
            $.get(href, function (seguro, status) {
                // alert("Executou edit");
                $('#formNewEdit #id').val(seguro.id);
                $('#formNewEdit #valor').val(seguro.valor);
                $('#formNewEdit #nParcelas').val(seguro.nparcelas);
                var dia = seguro.dataSeg.dayOfMonth;
                var mes = seguro.dataSeg.monthValue;
                if (dia < 10) {
                    dia = "0" + dia;
                }
                if (mes < 10) {
                    mes = "0" + mes;
                }
                $('#formNewEdit #dataSeg').val(dia + '/' + mes + '/' + seguro.dataSeg.year);
                $('#formNewEdit #usuario').val(seguro.usuario.id);
                $('#formNewEdit #tipoSeguro').val(seguro.tipoSeguro.id);
                $('#formNewEdit #veiculo').val(seguro.veiculo.id);
                $('#formNewEdit #email').val(seguro.email.id);
            });
            $('#modalNewEditSeguro').modal();
        } else if (nome === "email") {
            $.get(href, function (confirmar) {
                $('#teste #endereco').val(confirmar[0]);
                $('#teste #assunto').val(confirmar[1]);
                $('#teste #texto').val(confirmar[2]);
                $('#teste #id').val(confirmar[3]);
            });
            $('#mandarEmail').modal();
        } else if (nome === "new") {
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();

            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }
            today = dd + '/' + mm + '/' + yyyy;
            // alert("Executou novo");
            $('.modal-title').text('Novo');
            $('#formNewEdit #id').val('');
            $('#formNewEdit #valor').val('');
            $('#formNewEdit #valor').focusin();
            $('#formNewEdit #nParcelas').val(1);
            $('#formNewEdit #dataSeg').val(today);
            $('#formNewEdit #usuario').val('');
            $('#formNewEdit #tipo_seguro').val('');
            $('#formNewEdit #veiculo').val(1);
            $('#formNewEdit #email').val('');
            $('#modalNewEditSeguro').modal();
        }

    })

    $('#desfazer').on('click', function () {
        var padrao1 = "Prezado CLIENTE, seu seguro de TIPO ira vencer em DATA"
        var padrao2 = "Prezado CLIENTE, a proximal  parcela de seu seguro de TIPO ira vencer em DATA"
        var titulo1 = "Vencimento Seguro"
        var titulo2 = "Vencimento Parcela Seguro"
        console.log(document.getElementById('desfazer').getAttribute('value'));
        if(document.getElementById('desfazer').getAttribute('value') == 1){
            document.getElementById('texto').value = padrao1;
            document.getElementById('titulo').value = titulo1;
        }else if(document.getElementById('desfazer').getAttribute('value') == 2){
            document.getElementById('texto').value = padrao2;
            document.getElementById('titulo').value = titulo2;
        }
    })

    $.validator.addMethod("dataValid", function (value, element) {
        var dataT = value.split('/');
        var hoje = new Date();
        hoje = hoje.getFullYear();
        var msgDia = "Data invalida!"
        if (dataT[1] > 0 && dataT[1] < 13 && dataT[0] > 0 && dataT[2] == hoje) {
            if (dataT[1] == 04 || dataT[1] == 06 || dataT[1] == 9 || dataT[1] == 11) {
                if (dataT[0] < 31) {
                    return true;
                } else {
                    msgDia;
                    return false;
                }
            } else if (dataT[1] == 2) {
                if (dataT[0] < 30) {
                    return true;
                } else {
                    msgDia;
                    return false;
                }
            } else {
                if (dataT[0] < 32 && dataT[1] != 2) {
                    return true;
                } else {
                    msgDia;
                    return false;
                }
            }
        } else {
            msgDia;
            return false;
        }
        return true
    }, msgDia);

    $(function () {
        $('#formNewEdit').validate({
            rules: {
                valor: {
                    maxlength: 45,
                    minlength: 4,
                    required: true
                },
                dataSeg: {
                    dataValid: true,
                    required: true
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    })
})


/***
 * var dd = today.getDate();
 var mm = today.getMonth()+1; //January is 0!
 var yyyy = today.getFullYear();

 if(dd<10) {
    dd = '0'+dd
}

 if(mm<10) {
    mm = '0'+mm
}

 today = mm + '/' + dd + '/' + yyyy;
 document.write(today);
 *
 *
 *
 * */