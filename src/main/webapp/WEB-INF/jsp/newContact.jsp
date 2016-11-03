<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

    <title>Phone Book Page</title>

    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">

</head>
<body>

    <div role="navigation">
        <div class="navbar navbar-inverse">
            <a href="/" class="navbar-brand">Phone Book</a>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a>${userName}   |</a> </li>
                    <li><a href="javascript:formSubmit()">Logout</a> </li>
                </ul>
            </div>
        </div>
    </div>

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <script>
        function formSubmit() {
            document.getElementById("logoutForm").submit();
        }
    </script>

    <div class="container text-center">

        <div class="form-group">
            <c:if test="${not empty message}">
                <div class="alert alert-info" align="center">${message}</div>
            </c:if>
        </div>

        <hr>

        <form:form modelAttribute="contact" class="form-horizontal" method="POST" action="create">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${contact.id}">

            <div class="form-group">
                <label class="control-label col-md-3">Last Name*</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="lastName" value="${contact.lastName}" autofocus="true"/>
                    <form:errors path="lastName" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">First Name*</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="firstName" value="${contact.firstName}"/>
                    <form:errors path="firstName" cssClass="error" />
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Middle Name*</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="middleName" value="${contact.middleName}"/>
                    <form:errors path="middleName" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Cell Phone*</label>

                <div class="col-md-7">
                    <input type="text" id="phone" class="form-control" name="cellPhone" value="${contact.cellPhone}" placeholder="+380 XX XXX XXXX"/>
                    <form:errors path="cellPhone" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Home Phone</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="homePhone" value="${contact.homePhone}" placeholder="+380 44 XXX XXXX"/>
                    <form:errors path="homePhone" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Address</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="address" value="${contact.address}"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3">Email</label>

                <div class="col-md-7">
                    <input type="text" class="form-control" name="email" value="${contact.email}"/>
                    <form:errors path="email" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <input type="submit" class="btn btn-primary" value="Save"/>
            </div>
        </form:form>

        <div class="list">
            <h5><a href="/">Show contact's list</a></h5>
        </div>
    </div>

    <script scr="static/js/jquery-3.1.1.min.js"></script>
    <script scr="static/js/bootstrap.min.js"></script>

</body>
</html>