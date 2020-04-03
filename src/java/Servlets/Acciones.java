/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Conexion.GestionSQL;
import Negocio.Notificacion;
import automatizartareas.ProgramacionCerrarSolicitudes;
import automatizartareas.ProgramacionSolicitudesAVencerse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author jorge.correa
 */
public class Acciones extends HttpServlet {

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
                         
            String strAccion = request.getParameter("txtAccion");  
            String strSQL, strMensaje, strTareaActiva;
            Vector arrCodigosS = new Vector();
            Vector arrNombresS = new Vector(); 
            String[] strTemp = null;
             Notificacion n = new Notificacion();
            
            //Obtener la fecha actual.
                
            Calendar c = Calendar.getInstance();
            String strDia, strMes, strAnio, strFechaActual;

            strDia = Integer.toString(c.get(Calendar.DATE));
            strMes = Integer.toString(c.get(Calendar.MONTH)+1);
            strAnio = Integer.toString(c.get(Calendar.YEAR));                

            if (Integer.parseInt(strMes) < 10){
                strMes = "0" + strMes;
            }                    

            strFechaActual = strAnio + "-" + strMes + "-" + strDia;                       
                        
            if (strAccion.equals("Reclasificar")){
                String strIdTipoSolNuevo = request.getParameter("txtTipoSolNuevo");
                String strRadicado = request.getParameter("txtRadicado");
                strMensaje = null;
                
                strSQL = "select r.txtReqRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strIdTipoSolNuevo + "'";
                String[] strReqRpta = GestionSQL.getFila(strSQL,"Buzon");
                
                if (strReqRpta != null){
                    strSQL = "UPDATE buzon.buzon_pqrs SET txtTipoPQRS = '" + strIdTipoSolNuevo + "', txtReqRpta = '" + strReqRpta[0] + "' WHERE txtRadicado = '" + strRadicado + "'";                
                    strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                    
                    if (strMensaje == null){
                        out.println("<script type='text/javascript'>");
                        out.println("alert('La solicitud fue reclasificada correctamente!. Ciérrela en este momento para ver reflejado el cambio realizado.');");
                        out.println("window.close();");
                        out.println("</script>");
                    }else{
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Se generó un error al intentar actualizar el tipo de solicitud!, Póngase en contacto con el Administrador del Sistema.');");          
                        out.println("</script>");
                    }
                }                
            }
            
            if (strAccion.equals("TipoPQRS")){
                String strIdTipoPQRS = request.getParameter("txtIdTipoQRS");
                
                strSQL = "select r.txtReqRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strIdTipoPQRS + "'";
                String[] strReqRpta = GestionSQL.getFila(strSQL,"Buzon");
                
                if (strReqRpta != null){
                    out.println("<html>");
                    out.println("<head>");       
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<input type='hidden' id='txtReqRpta' value='" + strReqRpta[0] + "'>");
                    out.println("</body>");
                    out.println("</html>");
                }else{
                    out.println("<html>");
                    out.println("<head>");       
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<input type='text' id='txtReqRpta value='No se pudo recuperar la información.'>");
                    out.println("</body>");
                    out.println("</html>");
                }                
            }
                        
            if (strAccion.equals("Servicio")){
                String strCedula = request.getParameter("txtServicio");         
                if(! (strCedula.equals("-1"))){
                    strSQL = "select count(*) from users_servicios_x_responsable s where s.txtServicio = '" + strCedula + "'";                
                    String[] strTotalResp = GestionSQL.getFila(strSQL,"Users");
                                        
                    if (strTotalResp[0].equals("1")){
                        strSQL = "select pe.txtIdentificacion, pe.txtNombreCompleto, c.txtNombre from users_personas pe, users_cargos c, users_servicios_x_responsable s where (pe.txtCargo = c.txtIdentificacion) and (s.txtResponsable = pe.txtIdentificacion) and s.txtServicio = '" + strCedula + "'";                
                        String[] strResponsable = GestionSQL.getFila(strSQL,"Users");
                        
                        out.println("<html>");
                        out.println("<head>");       
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<input type='text' id='txtNombreCargo' class='CAMPOFORM' value='" + strResponsable[0] + "' style='width: 250px;display:none;'>");
                        out.println("<input type='text' id='txtNombreCargoC' class='CAMPOFORMREAD' value='" + strResponsable[1] + " - " + strResponsable[2] +"' style='width: 383px;' readonly>");              
                        out.println(" &nbsp;<img src='Images/error.png' id='imgNombreCargo' alt='Campo obligatorio' class='IMGERROR' style='display:none;'>");
                        out.println("</body>");
                        out.println("</html>");
                        
                    }else{
                        strSQL = "select pe.txtIdentificacion, pe.txtNombreCompleto, c.txtNombre from users_personas pe, users_cargos c, users_servicios s, users_procesos p where (pe.txtCargo = c.txtIdentificacion) and (s.txtProceso = p.txtCodigo) and (p.txtResponsable = pe.txtIdentificacion) and s.txtCodigo =  '" + strCedula + "'";                
                        String[] strCoordinador = GestionSQL.getFila(strSQL,"Users");
                        
                        out.println("<html>");
                        out.println("<head>");       
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<input type='text' id='txtNombreCargo' class='CAMPOFORM' value='" + strCoordinador[0] + "' style='width: 250px;display:none;'>");
                        out.println("<input type='text' id='txtNombreCargoC' class='CAMPOFORMREAD' value='" + strCoordinador[1] + " - " + strCoordinador[2] +"' style='width: 383px;' readonly>");              
                        out.println(" &nbsp;<img src='Images/error.png' id='imgNombreCargo' alt='Campo obligatorio' class='IMGERROR' style='display:none;'>");
                        out.println("</body>");
                        out.println("</html>");                        
                    }                       
                }
        }
            
             if (strAccion.equals("Responsable")){
                  String strResponsable= request.getParameter("txtResponsable");       
                  String strRadicado = request.getParameter("txtRadicado");                 
                  String strResponsableIni = request.getParameter("txtResponsableIni");      
                  String strUsuarioActual = request.getParameter("txtUsuarioActual");  
                  
                  // Actualización del nuevo responsable.
                  
                  strSQL = "update buzon_pqrs set txtNomCargo = '" + strResponsable + "' where txtRadicado = '" + strRadicado +"'";                
                  strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                  
                  // Creación de la traza del cambio de responsable.
                                    
                  strSQL = "insert into buzon_log_cambio_responsable values('" + strRadicado + "', '" + strUsuarioActual + "','" + strResponsableIni + "','" + strResponsable + "','" + strFechaActual + "')";
                  strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");                
                  
                  // Envío de notificación al nuevo responsable.
                                   
                  n.NotificacionPQRS(strRadicado);
                  n.notificarResponsableRE();

             }
                                       
             if (strAccion.equals("Obs")){
                 String strId = request.getParameter("txtId");
                 String strRadicado = request.getParameter("txtRadicado");
                 String strRpta = request.getParameter("txtRptaNew");
                 String strTipoRpta = request.getParameter("txtRpta");
                 
                 strSQL="update buzon.buzon_obs_x_sol set txtAtendida = 'S', dtFechaCierre='" + strFechaActual + "', txtRpta='" + strRpta + "' where txtIdObs = '" + strId + "' and txtRadicado = '" + strRadicado + "'";    
                 strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                
                if (strMensaje == null){
                    //Inserción exitosa                     
                    out.println("<html>");
                    out.println("<head>");
                    out.println("</head>");
                    out.println("<body>");                    
                    out.println("<div class='TEXTOEXITO'>");                                                     
                    out.println("La observación fue actualizada correctamente!.");                  
                    out.println("</div>");                   
                    out.println("<script>");
                    out.println("opener.frmActualizar.btnActualizar.click();");
                    out.println("$('#btnResponder').hide();");
                    out.println("$( '#rdSI' ).attr('disabled','disabled');");
                    out.println("$( '#rdNO' ).attr('disabled','disabled');");     
                    
                    if (strTipoRpta.equals("N")){
                        out.println("$( '#dRptaNew' ).attr('contenteditable','false');");
                    }
                    
                    n.responderObs(strRadicado, strId);
                    
                    out.println("</script>");
                    out.println("</body>");
                    out.println("</html>");                                                            
                }else{
                    //Inserción fallida                   
                    out.println("<html>");
                    out.println("<head>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class='TEXTOFALLO'>");
                    out.println("<br>Se produjo el siguiente error al actualizar el registro: " + strMensaje);
                    out.println("</div>");                 
                    out.println("</body>");
                    out.println("</html>");
                }            
             }
             
             HttpSession session = request.getSession(true);
                                       
             if ( (strAccion.equals("LSolVencerse")) || (strAccion.equals("LCerrarSols"))){                 
                 strTareaActiva = "N";
                 if (strAccion.equals("LSolVencerse")){
                 
                        //Iniciar tarea de las solicitudes a vencerse.
                    ProgramacionSolicitudesAVencerse psav = new ProgramacionSolicitudesAVencerse();
                    psav.crearProgramacion();
                    strTareaActiva = "S";
                }

                if (strAccion.equals("LCerrarSols")){                

                    //Iniciar tarea para cerrar las solicitudes.
                    ProgramacionCerrarSolicitudes pcs = new ProgramacionCerrarSolicitudes();
                    pcs.crearProgramacion();                 
                    strTareaActiva = "S";
                }
                session.setAttribute("txtTareaActiva", strTareaActiva);
             }             
             
              
            if (strAccion.equals("PararTareas")){
                if (session.getAttribute("txtTareaActiva") != null){
                    strTareaActiva = session.getAttribute("txtTareaActiva").toString();
                }else{
                    strTareaActiva = "N";
                }
                
                try{
                    if (strTareaActiva.equals("S")){
                        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                        scheduler.shutdown(false);
                         strTareaActiva = "N";
                         session.setAttribute("txtTareaActiva", strTareaActiva);
                        System.out.println("Las tareas fueron detenidas correctamente.");
                    }                    
                }catch(SchedulerException se){
                      out.println(se.getMessage());
                }catch(Exception e){
                     out.println(e.getMessage());
                }
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
