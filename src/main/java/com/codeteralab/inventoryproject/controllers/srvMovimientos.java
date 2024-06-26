package com.codeteralab.inventoryproject.controllers;

import com.codeteralab.inventoryproject.models.MovimientosDAO;
import com.codeteralab.inventoryproject.models.movimientos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "srvMovimientosServlet", value = "/srvMovimientos-Servlet")
public class srvMovimientos extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "listar":
                        listar(request, response);
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

    // funcion para listar los movimientos
    private void listar(HttpServletRequest request, HttpServletResponse response) {
        MovimientosDAO dao = new MovimientosDAO();
        List<movimientos> movimientosList = null;
        try {
            String filtro = request.getParameter("filtro");
            movimientosList = dao.getAllMovimientos(filtro);
            request.setAttribute("movimientos", movimientosList);
        } catch (Exception e) {
            request.getSession().setAttribute("msje", "No se pudieron listar los movimientos: " + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp?page=history").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void destroy() {
    }
}