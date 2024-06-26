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
        window.location.href = "${pageContext.request.contextPath}/srvProductos-Servlet?accion=listar";
    }
</script>
<%
        return;
    }
%>
<h1 class="mb-4">Inventario</h1>
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

<%
    if(usr.getRol().getNombre().equalsIgnoreCase("Administrador")){
%>
<button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">Agregar</button>
<%
    }
%>
<button class="btn btn-danger btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Filtrar
</button>
<div class="dropdown">
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/srvProductos-Servlet?accion=listar&filtro=all">Todos</a></li>
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/srvProductos-Servlet?accion=listar&filtro=Activo">Activos</a></li>
        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/srvProductos-Servlet?accion=listar&filtro=Inactivo">Inactivos</a></li>
    </ul>
</div>

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
            <% if(usr.getRol().getNombre().equalsIgnoreCase("Administrador")) { %>
            <th>Acciones</th>
            <% } %>
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
                <%
                    if(usr.getRol().getNombre().equalsIgnoreCase("Administrador")){
                %>
                <td>
                    <button onclick="setInputsEntrada(${doc.idProducto}, <%=usr.getIdUsuario()%>)" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#entradaModal">Entrada</button>

                    <c:if test="${doc.estatus == 'Activo'}">
                        <a href="${pageContext.request.contextPath}/srvProductos-Servlet?accion=estatusUpdate&idProducto=${doc.idProducto}" class="btn btn-danger btn-sm">Baja</a>
                    </c:if>
                    <c:if test="${doc.estatus == 'Inactivo'}">
                        <a href="${pageContext.request.contextPath}/srvProductos-Servlet?accion=estatusUpdate&idProducto=${doc.idProducto}" class="btn btn-danger btn-sm">Alta</a>
                    </c:if>

                </td>
                <%
                    }
                %>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Agregar producto</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/srvProductos-Servlet?accion=agregar">
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                    </div>
                    <div class="mb-3">
                        <label for="precio" class="form-label">Precio</label>
                        <input type="number" class="form-control" id="precio" name="precio" placeholder="Precio" required>
                    </div>
                    <button type="submit" name="accion" value="agregar" class="btn btn-primary w-100">Agregar</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal Entrada -->
<div class="modal fade" id="entradaModal" tabindex="-1" aria-labelledby="entradaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="entradaModalLabel">Entrada de inventario</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="entradaSalidaForm" action="${pageContext.request.contextPath}/srvProductos-Servlet?accion=entrada">
                    <input type="text" class="form-control d-none" id="idProducto" name="idProducto" placeholder="idProducto" required>
                    <input type="text" class="form-control d-none" id="idUsuario" name="idUsuario" placeholder="idUsuario" required>

                    <div class="mb-3">
                        <label for="cantidad" class="form-label">Cantidad</label>
                        <input type="number" class="form-control" id="cantidad" name="cantidad" placeholder="Cantidad" required>
                    </div>
                    <button type="submit" name="accion" value="entrada" class="btn btn-primary w-100">Entrada</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<script>
    function setInputsEntrada(idProducto, idUsuario){
        document.getElementById("idProducto").value = idProducto;
        document.getElementById("idUsuario").value = idUsuario;
    }
</script>