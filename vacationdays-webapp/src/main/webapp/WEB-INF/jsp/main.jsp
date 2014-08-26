<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Vacation Days</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Alex Zamyshlyaev">

    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
    <%--<link rel="stylesheet" href="css/flatly.min.css">--%>
    <link rel="stylesheet" href="css/main.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
            <a class="navbar-brand" href="#">Vacation Days</a>
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

        <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a
            mostly barebones HTML document.</p>
    </div>

    <section id="debug">
        <c:if test="${haveData}">
            Have data!
        </c:if>
        <c:if test="${not haveData}">
            No data
        </c:if>
    </section>

    <section id="initial">
        <div>Введи дату, на которую точно известно количество дней отпуска и это количество.</div>

        <form class="form-inline" onsubmit="return VDays.submitInitial()">
            <div class="form-group">
                <label for="initialDate">Дата:</label>
                <input type="date" id="initialDate" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="initialNum">Количество:</label>
                <input type="number" id="initialNum" class="form-control" min="0"/>
            </div>
            <button type="submit" class="btn btn-primary">Запомнить</button>
        </form>
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
                            <label for="initialDate">С:</label>
                            <input type="date" class="form-control inpFrom"/>
                        </div>
                        <div class="form-group">
                            <label for="initialDate">По:</label>
                            <input type="date" id="innpVacEnd" class="form-control inpTo"/>
                        </div>
                        <div class="form-group">
                            <label for="initialDate">Где были:</label>
                            <input type="text" id="inpVacComment" class="form-control inpComment"/>
                        </div>
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
            </div>

            <ul class="list-group">
            </ul>
        </div>
    </section>

    <section id="status" style="display: none">
        <p class="lead">К настоящему моменту накоплено <span id="spCurrentNum"></span></p>
    </section>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script src="js/vacdays.js"></script>
<script>
    VDays.haveData = ${haveData};
</script>
</body>
</html>
