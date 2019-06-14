$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip({
        trigger: 'hover'
    });
});


$('.btnTabelaCli').on('click', function () {
    var href = $(this).attr('href');
    var tag = $(this).attr('target');
    $.get(href, function () {
        $('#myIframeCli').target(tag);
    });
    $('#tblParcelasCli').find('.modal-body').css({
        width: 'auto',
        height: 'auto'
    })
    $('#tblParcelasCli').modal();
})