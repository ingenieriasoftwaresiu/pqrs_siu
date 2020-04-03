/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Conexion.GestionSQL;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jorge.correa
 */
public class Ingreso extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String strUsuario = request.getParameter("txtUsuario");
           String strPwd = request.getParameter("txtPwd");
           
           String[] strDatos = GestionSQL.getFila("select p.txtIdentificacion, p.txtNombreCompleto, p.txtAdministrador from users_personas p where p.txtUsuario = '" + strUsuario + "' and p.txtPassword = '" + strPwd +"'","Users");
           
           if (strDatos == null){    
               out.println("<html>");
               out.println("<head>");              
               out.println("</head>");
               out.println("<body>");
               out.println("El usuario y/o la contraseña ingresado(s) es(son) incorrecto(s).");
               out.println("</body>");
               out.println("</html>");
           }else{ 
               String strCedula = strDatos[0];
               String strNombre = strDatos[1];
               String strAdministrador = strDatos[2];
               
               HttpSession session = request.getSession(true);                                 
               out.println("txtCedula="+strCedula);
           } 
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
