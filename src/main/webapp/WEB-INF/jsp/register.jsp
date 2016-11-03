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

        <title>Register Page</title>

        <link href="/static/css/bootstrap.min.css" rel="stylesheet">
        <link href="/static/css/style.css" rel="stylesheet">
    </head>

    <body>

        <div role="navigation">
            <div class="navbar navbar-inverse">
                <a href="#" class="navbar-brand navbar-center">Registration</a>
            </div>
        </div>

        <div class="container text-center">
            <c:choose>
                <c:when test="${mode == 'MODE_REGISTER'}">
                    <h3>Create an account</h3>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger" align="center">
                        <strong>Error!</strong> User with such login is already exist. Please try again.
                    </div>
                </c:otherwise>
            </c:choose>

            <hr>
            <form:form modelAttribute="user" class="form-horizontal" method="POST" action="/user/add">

                <div class="form-group">
                    <label class="control-label col-md-3">Full Name</label>

                    <div class="col-md-7">
                        <input type="text" class="form-control" name="fullName" value="${user.fullName}" autofocus="true"/>
                        <form:errors path="fullName" cssClass="error"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3">Login</label>

                    <div class="col-md-7">
                        <input type="text" class="form-control" name="login" value="${user.login}"/>
                        <form:errors path="login" cssClass="error text-left" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3">Password</label>

                    <div class="col-md-7">
                        <input type="password" class="form-control" name="password" value="${user.password}"/>
                        <form:errors path="password" cssClass="error"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3">Confirm Password</label>

                    <div class="col-md-7">
                        <input type="password" class="form-control" name="confirmPassword" value="${user.confirmPassword}"/>
                        <form:errors element="div" cssClass="error"/>
                    </div>
                </div>

                <div class="form-group">
                    <input type="submit" class="btn btn-primary" value="Register"/>
                </div>
            </form:form>
            <div class="register">
                <h5><a href="/user/login">Already have an account? Login</a></h5>
            </div>
        </div>

    <script scr="static/js/jquery-3.1.1.min.js"></script>
    <script scr="static/js/bootstrap.min.js"></script>

    </body>
</html>