var VDays = {
    haveData: false,

    submitInitial: function () {
        var me = this;
        var data = {
            dt: $('#initialDate').val(),
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
            start: form.find('.inpFrom').val(),
            end: form.find('.inpTo').val(),
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
        var id = vacEl.data('id');

        $('#vacEditFormTemplate').find('a').clone().insertAfter(vacEl).show();
        vacEl.hide();
    },

    _processUserData: function (data) {
        var me = this;

        $('#spCurrentNum').text(me._numToDaysStr(data['currentNum']));

        var vacList = $('#vacations').find('.list-group');
        vacList.children().remove();

        $.each(data['vacations'], function (i, v) {
            console.dir(v);
            var start = me._arrToDate(v.start);
            var end = me._arrToDate(v.end);
            var text = start.toLocaleDateString() + ' - ' + end.toLocaleDateString() + ": " + v.comment + " (" +
                me._numToDaysStr(v.duration) + ")";

            $('<a class="list-group-item" href="#">' + text + '</a>')
                .data('id', v.id)
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
        .on("click", 'a', function (e) {
            VDays.startEditVac($(this));
            return false;
        });
});