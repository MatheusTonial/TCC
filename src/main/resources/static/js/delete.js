
$('#confirmRemove').on('show.bs.modal', function(event) {

        var button = $(event.relatedTarget);

        var id = button.data('id');
        var tipo = button.data('name');

        var modal = $(this);
        var form = modal.find('form');
        var action = form.data('url-base');

        if (!action.endsWith('/')) {
            action += '/';
        }

        form.attr('action', action + id);

        modal.find('.modal-body span').html(
            'Tem certeza que deseja remover  <strong>'
            + tipo + '</strong>?')
    });
