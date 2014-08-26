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
        $('#secAddVacButton').hide();
        $('#secAddVacForm').show();

        var me = this;
        form = $(form);

        var data = {
            from: form.find('.inpFrom').val(),
            to: form.find('.inpTo').val(),
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

    _processUserData: function (data) {
        var me = this;

        $('#spCurrentNum').text(me._numToDaysStr(data['currentNum']));

        var ulEl = $('#vacations').find('ul');
        ulEl.find('li').remove();

        $.each(data['vacations'], function (i, v) {
            console.dir(v);
            var start = me._arrToDate(v.start);
            var end = me._arrToDate(v.end);
            var text = start.toLocaleDateString() + ' - ' + end.toLocaleDateString() + ": " + v.comment + " (" +
                me._numToDaysStr(v.duration) + ")";

            $('<li class="list-group-item">' + text + '</li>')
                .data('id', v.id)
                .appendTo(ulEl);
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
});