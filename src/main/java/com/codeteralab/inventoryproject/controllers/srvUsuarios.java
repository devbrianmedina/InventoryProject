package com.codeteralab.inventoryproject.controllers;

import com.codeteralab.inventoryproject.models.UsuariosDAO;
import com.codeteralab.inventoryproject.models.usuarios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "srvUsuariosServlet", value = "/srvUsuarios-Servlet")
public class srvUsuarios extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrarsession(request, response);
                    default:
                        response.sendRedirect("login.jsp");
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

    public void destroy() {
    }

    // inicio de sesion
    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession sesion;
        UsuariosDAO dao;
        usuarios usuario;
        usuario = this.obtenerUsuario(request);
        dao = new UsuariosDAO();
        usuario = dao.identificarUsuario(usuario);
        if (usuario != null) {
            sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            request.setAttribute("msje", "Bienvenido al sistema");
            this.getServletConfig().getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
        }else{
            request.setAttribute("msje", "Credenciales Incorrectas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    // cierre de sesion
    private void cerrarsession(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuario", null);
        sesion.invalidate();
        response.sendRedirect("login.jsp");
    }

    // obtiene un usuario
    private usuarios obtenerUsuario(HttpServletRequest request) {
        usuarios u = new usuarios();
        u.setCorreo(request.getParameter("email"));
        u.setContrasena(request.getParameter("password"));
        return u;
    }
}