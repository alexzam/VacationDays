var VDays = {
    haveData: false,

    submitInitial: function () {
        var me = this;
        var data = {
            dt: me._dateToArr($('#initialDate').datepicker('getDate')),
            n: $('#initialNum').val()
        };
        $.ajax({
            url: "/json/setInitial",
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                me._processUserData(data);

                $('#editInitial').slideUp();
                $('#status, #vacations, #initial').slideDown();
            }
        });

        return false;
    },

    addVacation: function () {
        $('#secAddVacButton').hide();
        $('#secAddVacForm').show();
    },

    submitVacation: function (form) {
        $('#secAddVacButton').show();
        $('#secAddVacForm').hide();

        var me = this;
        form = $(form);

        var data = {
            start: me._dateToArr(form.find('.inpFrom').datepicker('getDate')),
            end: me._dateToArr(form.find('.inpTo').datepicker('getDate')),
            comment: form.find('.inpComment').val(),
            id: form.data('id')
        };

        $.ajax({
            url: "/json/setVacation",
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                me._processUserData(data);
            }
        });

        return false;
    },

    startEditVac: function (vacEl) {
        var me = this;
        var vac = vacEl.data('vac');

        var form = $('#vacEditFormTemplate').find('a').clone();
        form.find('#inpEdVacStart').val(me._arrToDate(vac.start).toLocaleDateString());
        form.find('#inpEdVacEnd').val(me._arrToDate(vac.end).toLocaleDateString());
        form.find('#inpEdVacComment').val(vac.comment);
        form.find('.datepicker').datepicker();

        form.insertAfter(vacEl).show();
        form.find('.datepicker').datepicker();
        form.find('form').data('id', vac.id);

        vacEl.hide();
    },

    onCancelEdit: function () {
        VDays._closeEdit($(this).closest('.list-group-item'));
    },

    onDeleteVacation: function () {
        var formItem = $(this).closest('form');

        $.ajax({
            url: "/json/deleteVacation",
            data: {
                id: formItem.data('id')
            },
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                VDays._processUserData(data);
            }
        });
    },

    googleAuth: function (auth) {
        var at = auth['access_token'];
        var it = auth['id_token'];

//        $.post();  // TODO Continue here 8)
    },

    _closeEdit: function (el) {
        el.prev().show();
        el.remove();
    },

    _processUserData: function (data) {
        var me = this;

        $('#spCurrentNum').text(me._numToDaysStr(data['currentNum']));

        var vacList = $('#vacations').find('.list-group');
        vacList.children().remove();

        var initial = "На " + me._arrToDate(data['lastKnownDate']).toLocaleDateString() + " оставалось " +
            me._numToDaysStr(data['lastKnownValue']);

        $('#initMessage').text(initial);

        $.each(data['vacations'], function (i, v) {
            var start = me._arrToDate(v.start);
            var end = me._arrToDate(v.end);
            var btext = me._numToDaysStr(v.duration) + ": ";
            var text = start.toLocaleDateString() + ' - ' + end.toLocaleDateString() + ": " + v.comment;

            $('<a class="list-group-item vacation-item" href="#"><strong>' + btext + '</strong>' + text + '</a>')
                .data('id', v.id)
                .data('vac', v)
                .appendTo(vacList);
        });
    },

    _numToDaysStr: function (d) {
        var sd = '' + d;

        if (/1.$/.test(sd)) return d + ' дней';
        if (/1$/.test(sd)) return d + ' день';
        if (/[2-4]$/.test(sd)) return d + ' дня';
        return d + ' дней';
    },

    _arrToDate: function (arr) {
        return new Date(arr[0], arr[1], arr[2]);
    },

    _dateToArr: function (date) {
        return [
            date.getFullYear(),
            date.getMonth(),
            date.getDate()
        ];
    }
};

$(function () {
    if (VDays.haveData) {
        $.ajax({
            url: "/json/getData",
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                VDays._processUserData(data);

                $('#initial').hide();
                $('#status, #vacations, #editInitial')
                    .show();
            }
        });
    }

    var $vacations = $('#vacations');

    $vacations
        .find('.list-group')
        .on("click", '.vacation-item', function () {
            VDays.startEditVac($(this));
            return false;
        });

    $vacations.on('click', '.formEditVac .btnCancel', VDays.onCancelEdit);
    $vacations.on('click', '.formEditVac .btnDelete', VDays.onDeleteVacation);

    $.datepicker.setDefaults($.datepicker.regional[ "ru" ]);

    $('section .datepicker')
        .datepicker();
});