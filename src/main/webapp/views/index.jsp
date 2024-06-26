<%@ page import="com.codeteralab.inventoryproject.models.usuarios" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if(session.getAttribute("usuario") != null) {
        usuarios usr = (usuarios) session.getAttribute("usuario");
        String strPage = request.getParameter("page");
        if (strPage == null) {
            strPage = "home";
        }
%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row flex-nowrap">
        <jsp:include page="/html/sidebar.jsp" />
        <div class="col py-3 bg-light">
            <% if(strPage.equalsIgnoreCase("home")){ %>
            <jsp:include page="home.jsp" />
            <% } else if(strPage.equalsIgnoreCase("inventory")){ %>
            <jsp:include page="inventory.jsp" />
            <% } else if(strPage.equalsIgnoreCase("subtract")){ %>
            <jsp:include page="subtract.jsp" />
            <% } else if(strPage.equalsIgnoreCase("history")){ %>
            <jsp:include page="history.jsp" />
            <% } else { %>
            <jsp:include page="home.jsp" />
            <% } %>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
<%
    } else {
%>
<script>
    window.location.href = "${pageContext.request.contextPath}/login.jsp";
</script>
<%
    }
%>