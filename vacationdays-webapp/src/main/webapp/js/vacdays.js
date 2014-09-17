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

                $('#initial').slideUp();
                $('#status').add('#vacations').slideDown();
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
        vacEl.hide();
    },

    _processUserData: function (data) {
        var me = this;

        $('#spCurrentNum').text(me._numToDaysStr(data['currentNum']));

        var vacList = $('#vacations').find('.list-group');
        vacList.children().remove();

        $.each(data['vacations'], function (i, v) {
            var start = me._arrToDate(v.start);
            var end = me._arrToDate(v.end);
            var text = start.toLocaleDateString() + ' - ' + end.toLocaleDateString() + ": " + v.comment + " (" +
                me._numToDaysStr(v.duration) + ")";

            $('<a class="list-group-item vacation-item" href="#">' + text + '</a>')
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
                $('#status').add('#vacations').show();
            }
        });
    }

    $('#vacations')
        .find('.list-group')
        .on("click", '.vacation-item', function (e) {
            VDays.startEditVac($(this));
            return false;
        });

    $('.datepicker').datepicker();
});