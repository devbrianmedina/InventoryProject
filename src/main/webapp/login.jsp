<%@ page import="com.codeteralab.inventoryproject.models.usuarios" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    usuarios usr = (usuarios) session.getAttribute("usuario");
    if(usr != null) {
%>
<script>
    window.location.href = "${pageContext.request.contextPath}/views/index.jsp";
</script>
<% } %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inicio de sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
    </style>
    <link rel="stylesheet" type="text/css" href="static/css/login.css">
</head>
<body>
<div class="card">
    <div class="card-header">
        Inicio de sesión
    </div>
    <div class="card-body">
        <form action="srvUsuarios-Servlet?accion=verificar">
            <div class="mb-3">
                <label for="email" class="form-label">Correo electrónico</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
            </div>
            <button type="submit" name="accion" value="verificar" class="btn btn-primary w-100">Acceder</button>
        </form>
    </div>
    <% if(request.getAttribute("msje") != null){ %>
    <div class="card-footer text-center">
        <small class="text-muted">Mensaje: ${msje}</small>
    </div>
    <% } %>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>