<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String msje = (String) session.getAttribute("msje");
    if(request.getAttribute("movimientos") == null) {
%>
<script>
    window.onload = function () {
        window.location.href = "${pageContext.request.contextPath}/srvMovimientos-Servlet?accion=listar";
    }
</script>
<%
        return;
    }
%>
<h1 class="mb-4">Historial de movimientos</h1>
<%
    if(msje != null){
%>
<div class="alert alert-warning" role="alert">
    ${msje}
</div>
<%
    }
    session.setAttribute("msje", null);
%>

<button class="btn btn-danger btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Filtrar
</button>
<div class="dropdown">
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/srvMovimientos-Servlet?accion=listar&filtro=Entrada">Entrada</a></li>
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/srvMovimientos-Servlet?accion=listar&filtro=Salida">Salida</a></li>
    </ul>
</div>

<br><br>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>#</th>
            <th>Producto</th>
            <th>Tipo</th>
            <th>Cantidad</th>
            <th>Usuario</th>
            <th>Fecha y hora</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${movimientos}">
            <tr>
                <th scope="row">${doc.idMovimiento}</th>
                <td>${doc.producto.nombre}</td>
                <td>${doc.tipo}</td>
                <td>${doc.cantidad}</td>
                <td>${doc.usuario.nombre}</td>
                <td>${doc.fechaHora}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>