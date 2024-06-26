package com.codeteralab.inventoryproject.controllers;

import com.codeteralab.inventoryproject.models.ProductosDAO;
import com.codeteralab.inventoryproject.models.productos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "srvProductosServlet", value = "/srvProductos-Servlet")
public class srvProductos extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "agregar":
                        agregar(request, response);
                        break;
                    case "listar":
                        listar(request, response);
                        break;
                    case "entrada":
                        entrada(request, response);
                        break;
                    case "salida":
                        salida(request, response);
                        break;
                    case "estatusUpdate":
                        estatusUpdate(request, response);
                        break;
                }
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            try {
                request.getSession().setAttribute("msje", "Error interno: " + e.getMessage());
                this.getServletConfig().getServletContext().getRequestDispatcher("/mensaje.jsp").forward(request, response);

            } catch (Exception ex) {
                System.out.println("Error" + e.getMessage());
            }
        }
    }

    // actuliza el estado del producto activo/inactivo
    private void estatusUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProductosDAO dao = new ProductosDAO();
        String idProducto = request.getParameter("idProducto");
        if(idProducto != null){
            dao.toggleEstatusProducto(Integer.parseInt(idProducto));
        }
        request.getSession().setAttribute("msje", "Estado actualizado");
        this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=inventory").forward(request, response);
    }

    // lista los productos
    private void listar(HttpServletRequest request, HttpServletResponse response) {
        ProductosDAO dao = new ProductosDAO();
        List<productos> productosList = null;
        try {
            String filtro = request.getParameter("filtro");
            productosList = dao.getAllProductos(filtro != null ? filtro : "all");
            request.setAttribute("productos", productosList);
        } catch (Exception e) {
            request.getSession().setAttribute("msje", "No se pudieron listar los productos: " + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            String page = request.getParameter("page");
            if (page == null) {
                page = "inventory";
            }
            this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=" + page).forward(request, response);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void destroy() {
    }

    // agrega un producto
    private void agregar(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ProductosDAO dao;
        productos producto = new productos();
        producto.setNombre(request.getParameter("nombre"));
        producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
        dao = new ProductosDAO();
        dao.agregarProducto(producto);
        request.getSession().setAttribute("msje", "Agregado exitosamente");
        this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=inventory").forward(request, response);
    }

    // entrada de inventario
    private void entrada(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ProductosDAO dao;
        dao = new ProductosDAO();
        String idProducto = request.getParameter("idProducto");
        String cantidad = request.getParameter("cantidad");
        String idUsuario = request.getParameter("idUsuario");
        if(idProducto != null && cantidad != null && idUsuario != null) {
            int idProductoInt = Integer.parseInt(idProducto), cantidadInt = Integer.parseInt(cantidad), idUsuarioInt = Integer.parseInt(idUsuario);
            if(cantidadInt > 0) {
                dao.entradaProducto(idProductoInt, cantidadInt, idUsuarioInt);
            } else {
                request.getSession().setAttribute("msje", "No puedes disminuir la cantidad del producto");
            }
        } else {
            request.getSession().setAttribute("msje", "Faltan datos");
        }
        this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=inventory").forward(request, response);
    }

    // salida de inventario
    private void salida(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ProductosDAO dao;
        dao = new ProductosDAO();
        String idProducto = request.getParameter("idProducto");
        String cantidad = request.getParameter("cantidad");
        String idUsuario = request.getParameter("idUsuario");
        if(idProducto != null && cantidad != null && idUsuario != null) {
            int idProductoInt = Integer.parseInt(idProducto), cantidadInt = Integer.parseInt(cantidad), idUsuarioInt = Integer.parseInt(idUsuario);
            productos producto = dao.getProductoById(idProductoInt);
            if(cantidadInt > 0) {
                if(cantidadInt <= producto.getCantidad()) {
                    dao.salidaProducto(idProductoInt, cantidadInt, idUsuarioInt);
                } else {
                    request.getSession().setAttribute("msje", "No se puede sacar una cantidad mayor de un producto de la que está en inventario");
                }
            } else {
                request.getSession().setAttribute("msje", "No se permiten numero negativos");
            }
        } else {
            request.getSession().setAttribute("msje", "Faltan datos");
        }
        String page = request.getParameter("page");
        if (page == null) {
            page = "inventory";
        }
        this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=" + page).forward(request, response);
    }
}