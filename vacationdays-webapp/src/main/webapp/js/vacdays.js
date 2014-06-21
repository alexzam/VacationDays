var VDays = {
    submitInitial: function () {
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
                console.dir(data);
            }
        });

        return false;
    }
};