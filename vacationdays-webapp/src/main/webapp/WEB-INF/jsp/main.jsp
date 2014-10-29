<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>СколькоОтпуска</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Alex Zamyshlyaev">

    <link rel="stylesheet" href="css/jquery-ui.min.css">
    <link rel="stylesheet" href="css/jquery-ui.theme.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
    <%--<link rel="stylesheet" href="css/flatly.min.css">--%>
    <link rel="stylesheet" href="css/main.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script>
        var token = "${token}";

        (function () {
            var po = document.createElement('script');
            po.type = 'text/javascript';
            po.async = true;
            po.src = 'https://plus.google.com/js/client:plusone.js?onload=start';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(po, s);
        })();
    </script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">СколькоОтпуска</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <h1>Посчитай свой отпуск</h1>

        <p class="lead">Здесь можно быстро посчитать, сколько дней отпуска у вас осталось, если три месяца назад Леночка
            из отдела кадров говорила, что осталась неделя.</p>
    </div>

    <section id="auth">
        <div id="signinButton">
          <span class="g-signin"
                data-scope="https://www.googleapis.com/auth/plus.login"
                data-clientid="${googleAuthClientId}"
                data-redirecturi="postmessage"
                data-accesstype="offline"
                data-cookiepolicy="single_host_origin"
                data-callback="signInCallback">
          </span>
        </div>
        <div id="result"></div>
    </section>

    <section id="editInitial">
        <div class="panel panel-info">
        <div>
                Для того, чтобы посчитать отпуск, нам надо с чего-то начать. Если вы помните все свои отпуска с момента
                приёма на работу, введите ниже дату приёма на работу и ноль. Если нет, узнайте в отделе кадров, сколько
                дней остаётся сейчас и введите дату, когда узнавали и это число. А мы посчитаем остальное.
            </div>

            <form class="form-inline" onsubmit="return VDays.submitInitial()">
                <div class="form-group">
                    <label for="initialDate">Дата, про которую известно:</label>
                    <input type="text" id="initialDate" class="form-control datepicker"/>
                </div>
                <div class="form-group">
                    <label for="initialNum">Сколько дней тогда оставалось:</label>
                    <input type="number" id="initialNum" class="form-control" min="0" value="0"/>
                </div>
                <button type="submit" class="btn btn-primary">Запомнить</button>
            </form>
        </div>
    </section>

    <section id="initial" style="display: none">
        <div class="alert alert-info" role="alert">
            <span id="initMessage"></span>
            <button class="btn btn-default btn-sm" onclick="VDays.changeInitial()">
                <span class="glyphicon glyphicon-pencil"></span>
            </button>
        </div>
    </section>

    <section id="vacations" style="display: none">
        <div class="panel panel-info">
            <p class="panel-heading">Отпуска</p>

            <div class="panel-body">
                <p id="secAddVacButton">
                    <button class="btn" onclick="VDays.addVacation()">Добавить отпуск</button>
                </p>

                <div id="secAddVacForm" style="display: none">
                    <form id="vacFormAdd" class="form-inline" data-id="0" onsubmit="return VDays.submitVacation(this)">
                        <div class="form-group">
                            <label for="innpVacStart">С:</label>
                            <input type="text" id="innpVacStart" class="form-control inpFrom datepicker"/>
                        </div>
                        <div class="form-group">
                            <label for="innpVacEnd">По:</label>
                            <input type="text" id="innpVacEnd" class="form-control inpTo datepicker"/>
                        </div>
                        <div class="form-group">
                            <label for="inpVacComment">Где были:</label>
                            <input type="text" id="inpVacComment" class="form-control inpComment"/>
                        </div>
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
            </div>

            <div class="list-group">
            </div>
        </div>
    </section>

    <section id="status" style="display: none">
        <p class="lead">К настоящему моменту накоплено <span id="spCurrentNum"></span></p>
    </section>

    <div id="vacEditFormTemplate" style="display: none">
        <a class="list-group-item disabled">
            <form class="form-inline formEditVac" onsubmit="return VDays.submitVacation(this)">
                <div class="form-group">
                    <label for="inpEdVacStart">С:</label>
                    <input type="text" id="inpEdVacStart" class="form-control inpFrom datepicker"/>
                </div>
                <div class="form-group">
                    <label for="inpEdVacEnd">По:</label>
                    <input type="text" id="inpEdVacEnd" class="form-control inpTo datepicker"/>
                </div>
                <div class="form-group">
                    <label for="inpEdVacComment">Где были:</label>
                    <input type="text" id="inpEdVacComment" class="form-control inpComment"/>
                </div>
                <button type="submit" class="btn btn-primary">Изменить</button>
                <button type="button" class="btn btn-danger btnDelete">Удалить</button>
                <button type="button" class="btn btn-default flRight btnCancel">Отмена</button>
            </form>
        </a>
    </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/datepicker-ru.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="js/vacdays.js"></script>
<script>
    VDays.haveData = ${haveData};
</script>
</body>
</html>
