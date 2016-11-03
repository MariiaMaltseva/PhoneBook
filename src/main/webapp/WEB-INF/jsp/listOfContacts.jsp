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
            <a href="/" class="navbar-brand">My Contacts</a>
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

    <form id="deleteForm" method="POST" action="/delete">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id = "deleteHiddenId" name="id" />
    </form>

    <script>
                function deleteSubmit(id) {
            document.getElementById("deleteHiddenId").value=id;
                    document.getElementById("deleteForm").submit();
        }

        function formSubmit() {
            document.getElementById("logoutForm").submit();
        }

                function confirmDelete() {
                    return confirm("Are you sure that you want delete this contact?");
                }

    </script>

    <div class="container text-center" id="userDiv">
        <div class="table-responsive">
            <div class="new contact text-right">
                <h5><a href="new">add new contact</a></h5>
            </div>
            <hr>

            <div class="form-group">
                <c:if test="${not empty message}">
                    <div class="alert alert-warning" align="center">${message}</div>
                    <hr>
                </c:if>
            </div>

            <form class="navbar-form pull-left" role="search" action="/search">
                <div class="input-group">
                    <input type="text" class="form-control" name="searchRequest" placeholder="Search">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>

            <table class="table table-striped table-bordered text-left" id="contactList">
                <thead>
                    <tr>
                        <th>Full Name</th>
                        <th>Cell phone</th>
                        <th>Home phone</th>
                        <th>email</th>
                        <th>Address</th>
                        <th colspan="2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="contact" items="${contact}">
                    <tr>
                        <td>${contact.lastName} ${contact.firstName} ${contact.middleName}</td>
                        <td>${contact.cellPhone}</td>
                        <td>${contact.homePhone}</td>
                        <td>${contact.email}</td>
                        <td>${contact.address}</td>
                        <td><a href="update?id=${contact.id}"><span class="glyphicon glyphicon-pencil"></span></a></td>
                        <td><a href="javascript:deleteSubmit(${contact.id})" onclick="return confirmDelete();"><span class="glyphicon glyphicon-trash"></span></a> </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="new contact text-right">
                <h5><a href="/new">add new contact</a></h5>
            </div>
        </div>
    </div>

<script scr="static/js/jquery-3.1.1.min.js"></script>
<script scr="static/js/bootstrap.min.js"></script>

</body>
</html>