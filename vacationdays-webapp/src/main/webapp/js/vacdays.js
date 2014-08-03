var VDays = {
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

    _processUserData: function (data) {
        var me = this;

        $('#spCurrentNum').text(me._numToDaysStr(data['currentNum']));

        var ulEl = $('#vacations').find('ul');
        ulEl.find('li').remove();

        $.each(data['vacations'], function (i, v) {
            console.dir(v);
            var text = me._arrToDate(v.start);

            $('<li class="list-group-item">' + text + '</li>')
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