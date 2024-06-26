<%@ page import="com.codeteralab.inventoryproject.models.usuarios" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    usuarios usr = (usuarios) session.getAttribute("usuario");
    String msje = (String) session.getAttribute("msje");
    if(request.getAttribute("productos") == null) {
%>
<script>
    window.onload = function () {
        window.location.href = "${pageContext.request.contextPath}/srvProductos-Servlet?accion=listar&page=subtract&filtro=Activo";
    }
</script>
<%
        return;
    }
%>
<h1 class="mb-4">Salida de inventario</h1>
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

<br><br>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Estatus</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${productos}">
            <tr>
                <th scope="row">${doc.idProducto}</th>
                <td>${doc.nombre}</td>
                <td>${doc.cantidad}</td>
                <td>$${doc.precio}</td>
                <td>${doc.estatus}</td>
                <td>
                    <button onclick="setInputsSalida(${doc.idProducto}, <%=usr.getIdUsuario()%>)" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#entradaModal">Salida</button>
                </td>
            </tr>
        </c:forEach>
        <!-- Agrega más filas según sea necesario -->
        </tbody>
    </table>
</div>
<!-- Modal Entrada -->
<div class="modal fade" id="entradaModal" tabindex="-1" aria-labelledby="entradaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="entradaModalLabel">Salida de inventario</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="entradaSalidaForm" action="${pageContext.request.contextPath}/srvProductos-Servlet?accion=salida">
                    <input type="text" class="form-control d-none" id="idProducto" name="idProducto" placeholder="idProducto" required>
                    <input type="text" class="form-control d-none" id="idUsuario" name="idUsuario" placeholder="idUsuario" required>
                    <input type="text" class="form-control d-none" id="page" name="page" placeholder="page" value="subtract" required>

                    <div class="mb-3">
                        <label for="cantidad" class="form-label">Cantidad</label>
                        <input type="number" class="form-control" id="cantidad" name="cantidad" placeholder="Cantidad" required>
                    </div>
                    <button type="submit" name="accion" value="salida" class="btn btn-primary w-100">Salida</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<script>
    function setInputsSalida(idProducto, idUsuario){
        document.getElementById("idProducto").value = idProducto;
        document.getElementById("idUsuario").value = idUsuario;
    }
</script>