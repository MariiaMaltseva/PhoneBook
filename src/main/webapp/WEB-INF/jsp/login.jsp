<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Cache-Control" content="no-cache">
        <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

        <title>Login Page</title>

        <link href="/static/css/bootstrap.min.css" rel="stylesheet">
        <link href="/static/css/style.css" rel="stylesheet">

    </head>
    <body>

    <div role="navigation">
        <div class="navbar navbar-inverse">
            <a href="#" class="navbar-brand navbar-center">Phone Book</a>
        </div>
    </div>

    <c:choose>
        <c:when test="${mode == 'MODE_HOME'}">
            <div class="container text-center">
                        <h3>Login to Phone Book</h3>

            <form class="form-horizontal" name='loginForm' action="/user/login" method='POST'>
            <div class="form-group">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" align="center">${error}</div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-success" align="center">${message}</div>
                </c:if>
            </div>

                <hr>

                <div class="form-group">
                <label class="control-label col-md-3">Login</label>

                        <div class="col-md-7">
                            <input type="text" class="form-control" name="login" value="${user.login}" autofocus="true"/>
                        </div>
                    </div>

                <div class="form-group">
                        <label class="control-label col-md-3">Password</label>

                        <div class="col-md-7">
                            <input type="password" class="form-control" name="password"/>
                        </div>
                    </div>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" value="Login"/>
                    </div>
                </form>

                <div class="register">
                    <h5><a href="/user/register">Register for an account</a></h5>
                </div>
            </div>
        </c:when>
    </c:choose>

    <script scr="static/js/jquery-3.1.1.min.js"></script>
    <script scr="static/js/bootstrap.min.js"></script>

    </body>
</html>