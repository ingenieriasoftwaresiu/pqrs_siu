/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Conexion.GestionSQL;
import Negocio.EnviarEmail;
import Negocio.Notificacion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge.correa
 */
public class Registro extends HttpServlet {

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
            
           String strForm = request.getParameter("txtForm");  
           String strAccion = request.getParameter("txtAccion");                        
           String strIdEstado = "";
           
           String strSQL = "";
           String strMensaje = "";
           String strClaveEmail = "";
           String strResult = null;
            Notificacion n = new Notificacion();
           
           // Gestión de Estados.
           
           if (strForm.equals("frmEstado")){

               String strCodigo = request.getParameter("txtCodigo");
               String strNombre = request.getParameter("txtNombre");               
               
               //Creación
               
               if (strAccion.equals("C")){   
                    //Validar duplicidad.
                    strResult = validarRegistro(strCodigo,strForm);           
                  
                    if (strResult != null){
                        out.println(strResult);
                    }else{                
                        strSQL = "INSERT INTO buzon_estados (txtCodigo,txtNombre) VALUES('" + strCodigo + "','" + strNombre + "');";
                    }
               }
               
               if (strAccion.equals("V")){                             
                        strSQL = "update buzon_estados set txtNombre = '" + strNombre + "' where txtCodigo = '" + strCodigo + "'";                                         
               }
          }
           
           // Gestión de Retroalimentación.
           
           if (strForm.equals("frmRetroalimentacion")){

               String strCodigo = request.getParameter("txtCodigo");
               String strNombre = request.getParameter("txtNombre");    
               String strTiempoRpta = request.getParameter("txtTiempoRpta");       
               String strDescripcion = request.getParameter("txtDescripcion");  
               String strReqRpta = request.getParameter("txtReqRpta");
               
               //Creación
               
               if (strAccion.equals("C")){   
                    //Validar duplicidad.
                    strResult = validarRegistro(strCodigo,strForm);           
                  
                    if (strResult != null){
                        out.println(strResult);
                    }else{                
                        strSQL = "INSERT INTO buzon_retroalimentacion (txtCodigo,txtNombre,txtTiempoRpta,txtDescripcion,txtReqRpta) VALUES('" + strCodigo + "','" + strNombre + "','" + strTiempoRpta + "','" + strDescripcion +"','" + strReqRpta + "');";
                    }
               }
               
               if (strAccion.equals("V")){                             
                        strSQL = "update buzon_retroalimentacion set txtNombre = '" + strNombre + "', txtTiempoRpta = '" + strTiempoRpta + "', txtDescripcion = '" + strDescripcion + "', txtReqRpta = '" + strReqRpta + "' where txtCodigo = '" + strCodigo + "'";                                         
               }
          }
           
            // Gestión de Observaciones.
           
           if (strForm.equals("frmObs")){
               String strRadicado = request.getParameter("txtRadicado");
               String strObs = request.getParameter("txtObs");
               String strFecha = request.getParameter("txtFecha");  
               String strSatisfaccion = request.getParameter("txtSatisfaccion");           
               String strAtendida = request.getParameter("txtAtendida");           
               
                if (strAccion.equals("C")){   
                    
                    if (strSatisfaccion.equals("N")){
                         strSQL = "INSERT INTO buzon_obs_x_sol (txtRadicado,dtFecha,txtObs,txtAtendida) VALUES('" + strRadicado + "','" + strFecha + "','" + strObs + "','" + strAtendida + "')";
                         n.notificarObs(strRadicado, strObs);
                    }else{
                        strSQL = "update buzon_pqrs set txtIdEstado = 'CPU' where txtRadicado = '" + strRadicado +"'";
                    }                     
                }
                              
           }
           
           // Gestión de Rol.
           
           if (strForm.equals("frmRol")){

               String strCodigo = request.getParameter("txtCodigo");
               String strNombre = request.getParameter("txtNombre");               
               
                //Creación
               
               if (strAccion.equals("C")){   
                    //Validar duplicidad.
                    strResult = validarRegistro(strCodigo,strForm);           
                  
                    if (strResult != null){
                        out.println(strResult);
                    }else{                
                        strSQL = "INSERT INTO buzon_roles (txtCodigo, txtNombre) VALUES('" + strCodigo + "','" + strNombre + "');";
                    }
               }
               
               if (strAccion.equals("V")){                             
                        strSQL = "update buzon_roles set txtNombre = '" + strNombre + "' where txtCodigo = '" + strCodigo + "'";                                         
               }
           }                 
                       
            // Gestión de Rol por usuario.
           
           if (strForm.equals("frmRolXUsuario")){

               String strIdRol = request.getParameter("txtIdRol");
               String stridUsuario = request.getParameter("txtIdUsuario");               
               
               //Creación
               
               if (strAccion.equals("C")){   
                    //Validar duplicidad.
                    strResult = validarRegistro(strIdRol + "," + stridUsuario,strForm);           
                  
                    if (strResult != null){
                        out.println(strResult);
                    }else{                
                        strSQL = "INSERT INTO buzon_rolesXusuario (txtRol,txtUsuario) VALUES('" + strIdRol + "','" + stridUsuario + "');";
                    }
               }               
          }
           
           // Gestión de PQRS.
           
           if (strForm.equals("frmPQRS")){
               
               String strFechaCreacion = request.getParameter("txtFechaCreacion");
               String strTipoPQRS = request.getParameter("txtTipoPQRS");
               String strTipoEntidad = request.getParameter("txtTipoEntidad");
               String strNombreCargo = request.getParameter("txtNombreCargo");
               String strNomEntidad = request.getParameter("txtNomEntidad");
               String strNomGrupo = request.getParameter("txtNombreGrupo");
               String strNombreUser = request.getParameter("txtNombreUser");
               String strTelefono = request.getParameter("txtTelefono");
               String strEmail = request.getParameter("txtEmail");
               String strServicio = request.getParameter("txtServicio");
               String strDescripcion = request.getParameter("txtDescripcion");       
               strIdEstado = request.getParameter("txtIdEstado");               
               String strRptaIni = request.getParameter("txtRptaIni");
               String strRpta = request.getParameter("txtRpta");
               String strFechaRpta = request.getParameter("txtFechaRpta");
               String strAnonimo = request.getParameter("txtAnonimo");
               String strReqRpta = request.getParameter("txtReqRpta");
               String strNumId = request.getParameter("txtNumId");
               String strRadicado="";
               String strGrupo_Entidad = "";               
                              
                if (strAccion.equals("C")){                       
        
                    if (strNomEntidad.equals("")){
                        strGrupo_Entidad = strNomGrupo;
                    }else{
                        strGrupo_Entidad = strNomEntidad;
                    }                                             
                    
                    if (!strIdEstado.equals("AT")){
                        strFechaRpta = null;
                    }       
                    
                    // Cálculo del consecutivo de la solicitud.
                    
                    strSQL = "select g.txtConsecutivo from buzon_generales g where g.txtCodigo = 'frmGeneral'";
                    String[] strRadicados = GestionSQL.getFila(strSQL,"Buzon");        

                    if (strRadicados != null){           
                        strRadicado = Integer.toString(Integer.parseInt(strRadicados[0]) + 1);            
                    }else{          
                        strRadicado = "1";
                    }        

                    if (!strRadicado.equals("")){           
                        strSQL = "update buzon_generales set txtConsecutivo = '" + strRadicado + "' where txtCodigo = 'frmGeneral'";         
                        GestionSQL.ejecuta(strSQL,"Buzon");
                    }
                    
                    strSQL = "INSERT INTO buzon_pqrs (txtRadicado,dtFechaCreacion,txtTipoPQRS,txtNomCargo,txtTipoEntidad,txtGrupo_Entidad,txtNombreUser,txtTelefono,txtEmail,txtServicio,txtDescripcion,txtIdEstado,txtAnonimo,txtReqRpta, txtNumId) values('" + strRadicado + "','" + 
                                  strFechaCreacion + "','" + strTipoPQRS + "','" + strNombreCargo+ "','" + strTipoEntidad  + "','" + strGrupo_Entidad + "','" + strNombreUser + "','" + strTelefono +"','" + strEmail + "','" + strServicio + "','" + strDescripcion +"','" + strIdEstado + "','" + strAnonimo + "','" + strReqRpta + "','" + strNumId + "');";
                    
                   String strCadena = "Su solicitud se ha asociado con el consecutivo #"+strRadicado+".";
                    out.println("<script type='text/javascript'>");
                    out.println("alert('" + strCadena +"');");
                    out.println("</script>");                
                } 
                
                if (strAccion.equals("V")){  
                    strRadicado = request.getParameter("txtRadicado");
                    strSQL = "update buzon_pqrs set txtIdEstado='" + strIdEstado +  "', txtRpta='" + strRpta + "', dtFechaRpta='" + strFechaRpta + "' where txtRadicado='" + strRadicado + "'";                                                            
                }
                strClaveEmail = strRadicado;
           }
           
           // Gestión de Parámetros Generales.
           
           if (strForm.equals("frmGeneral")){
               String strConsecutivo = request.getParameter("txtConsecutivo");
               String strNroPaginas = request.getParameter("txtNroPaginas");
               String strNomServidor = request.getParameter("txtNombreServidor");
               String strNumPuerto = request.getParameter("txtNumeroPuerto");
               String strUsuario = request.getParameter("txtUsuario");
               String strPassword = request.getParameter("txtPassword");
               String strRutaSegPQRS = request.getParameter("txtRutaSegPQRS");
               String strAsistente = request.getParameter("txtAsistente");
               String strNroDiasCierre = request.getParameter("txtNroDiasCierre");
               String strRutaArchivos = request.getParameter("txtRutaArchivos");
               String strNroDiasAlerta = request.getParameter("txtNroDiasAlerta");
               
               if (strAccion.equals("C")){                                                                         
                    strSQL = "INSERT INTO buzon_generales values('" + strForm + "','" + strConsecutivo + "','" + strNroPaginas + "','" + strNomServidor + "','" + strNumPuerto+ "','" + strUsuario  + "','" + strPassword + "','" + strRutaSegPQRS + "','" + strAsistente +"','" + strNroDiasCierre + "','" + strRutaArchivos + "','" + strNroDiasAlerta + "');";
               } 
               
               if (strAccion.equals("V")){    
                    strSQL = "update buzon_generales set txtConsecutivo='" + strConsecutivo + "', txtNroPaginas='" + strNroPaginas + "', txtNomServidor='" + strNomServidor + "', txtNumPuerto='" + strNumPuerto + 
                                  "', txtUsuario='" + strUsuario +"', txtPassword='" + strPassword + "', txtRutaSegPRQS='" + strRutaSegPQRS + "', txtAsistente='" + strAsistente + "', txtNroDiasCierre='" + strNroDiasCierre +"', txtRutaArchivos='" + strRutaArchivos +"', txtNroDiasAlerta='" + strNroDiasAlerta + "' where txtCodigo='" + strForm + "'";                    
               }
           }
               
          if (!strSQL.equals("")){
                strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                
                if (strMensaje == null){
                    //Inserción exitosa                     
                    out.println("<html>");
                    out.println("<head>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class='TEXTOEXITO'>");                                                     
                    out.println("El registro fue ingresado correctamente!.");                  
                    out.println("</div>");                   
                    out.println("</body>");
                    out.println("</html>");      
                                          
                     if (strForm.equals("frmPQRS")){                                         
                         n.NotificacionPQRS(strClaveEmail);
                         
                        if (strIdEstado.equals("PR")){
                            n.notificarClienteRE();
                            n.notificarResponsableRE();
                            n.notificarAsistenteRE();
                        }

                        if (strIdEstado.equals("AT")){
                            n.notificarClienteAT();
                            n.notificarResponsableAT();
                            n.notificarAsistenteAT();
                        }
                     }          
                                         
                }else{
                    //Inserción fallida                   
                    out.println("<html>");
                    out.println("<head>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class='TEXTOFALLO'>");
                    out.println("Se produjo el siguiente error al insertar el registro: " + strMensaje);
                    out.println("</div>");                  
                    out.println("</body>");
                    out.println("</html>");
                }
            }
           
        } finally {            
            out.close();
        }
    }
   
    /*
    private void enviarNotificacion(String strClaveEmail){
        String strMensaje = "";
        String strDestino = "";
        String strAsunto = "";
        String strIdResponsable = "";
        String strTipoPQRS = "";
        String strSQL = "";
        String[] strDatosIniciales = null;
        String strIdEstadoActual = "";
        String[] strDatosEstadoActual = null;
        String[] strDatosGenerales = null;
        String[] strDatosAsistente = null;
        String[] strDatosResponsable = null;
        String[] strDatosTipoPQRS = null;
        String[] strURLApp = null;
        String[] strRpta = null;
        
        strSQL = "select a.txtUrlAcceso from users_apps a where a.txtCodigo = 'PQRS'";
        strURLApp = GestionSQL.getFila(strSQL,"Users");   ;
                
        strSQL = "SELECT p.txtIdEstado, p.txtNombreUser, p.txtEmail, p.txtNomCargo, p.txtTipoPQRS, p.txtRptaIni, p.txtRpta from buzon_pqrs p where p.txtRadicado = '" + strClaveEmail + "'";       
        strDatosIniciales = GestionSQL.getFila(strSQL,"Buzon");   
        
        strIdEstadoActual = strDatosIniciales[0];
        
        if (strIdEstadoActual != null){                          
            EnviarEmail mail = new EnviarEmail();
            
            strSQL = "SELECT e.txtNombre FROM buzon_estados e where e.txtCodigo = '" + strIdEstadoActual + "'";
            strDatosEstadoActual = GestionSQL.getFila(strSQL,"Buzon");
            
            // Calcular el responsable de la solicitud.
            
            strIdResponsable = strDatosIniciales[3];
            strSQL = "select p.txtNombreCompleto, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strIdResponsable + "'";       
            strDatosResponsable = GestionSQL.getFila(strSQL,"Users");   
            
            // Calcular el tiempo de respuesta y nombre del tipo de solicitud.
            
            strTipoPQRS = strDatosIniciales[4];
            strSQL = "select r.txtTiempoRpta, r.txtNombre, r.txtReqRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strTipoPQRS + "'";       
            strDatosTipoPQRS = GestionSQL.getFila(strSQL,"Buzon");
            
            if (strIdEstadoActual.equals("RE")){
                
                // Notificación para el Cliente.
                
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";
                if (strDatosTipoPQRS[2].equals("N")){
                     strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] +  " dentro del Sistema de Gestión de PQRS de la Organización SIU.\n\n";
                    strMensaje = strMensaje + "Su solicitud ha sido enviada a la persona encargada.\n\n";
                }else{
                    strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] + "con consecutivo " + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema de Gestión de PQRS de la Organización SIU.\n\n";
                    strMensaje = strMensaje + "Su solicitud ha sido asignada a la persona encargada, quien le estará dando atención y respuesta en aproximadamente " + strDatosTipoPQRS[0] + " días. Para realizarle seguimiento a esta solicitud, puede ingresar a la dirección " + strURLApp[0] + " y buscarla con el consecutivo asignado.\n\n";
                }
                
                strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                strMensaje = strMensaje + "Cordialmente,\n\n";      
                strMensaje = strMensaje + "Organización SIU.";
                
                strDestino = strDatosIniciales[2];
                 if (strDatosTipoPQRS[2].equals("S")){
                    if (!(strDestino.equals("")) && !(strDestino.equals("Anonimo"))){
                        strAsunto = "Creación de solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema de Gestión de PQRS de la Organización SIU";                
                        mail.sendMail(strDestino, strAsunto, strMensaje);
                    }              
                 }
                
                // Notificación para el responsable de la solicitud.
                
                 strMensaje = "";                                                                                           
               
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosResponsable[0] + ".\n\n";
                strMensaje = strMensaje + "Se le ha asignado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema de Gestión de PQRS de la Organización SIU.\n\n";
                strMensaje = strMensaje + "Para revisarla, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] +"/login.jsp y búsquela con el consecutivo asignado.\n\n";
               if (strDatosTipoPQRS[2].equals("N")){
                    strMensaje = strMensaje + "Recuerde que este tipo de solicitud no requiere respuesta.\n\n";
                }else{
                    strMensaje = strMensaje + "Recuerde que tiene un plazo máximo de " + strDatosTipoPQRS[0] + " días para atenderla y dar respuesta al usuario.\n\n";
                }                
                strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                strMensaje = strMensaje + "Cordialmente,\n\n";
                strMensaje = strMensaje + "Sistema de Gestión de PQRS OSIU.";
                
                strDestino = strDatosResponsable[1];
                strAsunto = "Asignación de solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema de Gestión de PQRS de la Organización SIU";
                                        
                mail.sendMail(strDestino, strAsunto, strMensaje);
                
                // Notificación para el Asistente.
                
                strMensaje = "";
                
                strSQL = "SELECT g.txtAsistente from buzon_generales g where g.txtCodigo = 'frmGeneral'";       
                strDatosGenerales = GestionSQL.getFila(strSQL,"Buzon"); 
                
                
                strSQL = "select p.txtNombreCompleto, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strDatosGenerales[0] + "'";       
                strDatosAsistente = GestionSQL.getFila(strSQL,"Users"); 
                
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosAsistente[0] + ".\n\n";
                strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en estado " + strDatosEstadoActual[0] + " dentro del Sistema de Gestión de PQRS de la Organización SIU.\n\n";
                strMensaje = strMensaje + "Para conocer mayor información al respecto, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] + "/login.jsp y búsquela con el consecutivo asignado.\n\n"; 
                strMensaje = strMensaje + "Cordialmente,\n\n";
                strMensaje = strMensaje + "Sistema de Gestión de PQRS OSIU.";                        
                
                strDestino = strDatosAsistente[1];
                strAsunto = "Creación de solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema de Gestión de PQRS de la Organización SIU";
                
                mail.sendMail(strDestino, strAsunto, strMensaje);
            }
            
            if (strIdEstadoActual.equals("PR")){
                 // Notificación para el Cliente.
                
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";               
                
                if (!(strDatosIniciales[5]).equals("")){
                    String[] arrCadenaIni = null;

                    arrCadenaIni = strDatosIniciales[5].split(">");

                    for(int i=0;i<arrCadenaIni.length;i++){              
                        strMensaje = strMensaje + arrCadenaIni[i] + "\n";                                               
                    }                    
                    strMensaje = strMensaje + "\n";
                }else{
                    strMensaje = strMensaje + "La solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " se encuentra en el estado " + strDatosEstadoActual[0] + ".\n\n";
                    strMensaje = strMensaje + "Muy pronto estará obteniendo una respuesta de nuestra parte.\n\n";
                }
                
                strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                strMensaje = strMensaje + "Cordialmente,\n\n";
                strMensaje = strMensaje + strDatosResponsable[0] + "\n";
                strMensaje = strMensaje + "Organización SIU.";
                
                strDestino = strDatosIniciales[2];
                 if (strDatosTipoPQRS[2].equals("S")){
                    if (!(strDestino.equals("")) && !(strDestino.equals("Anonimo"))){
                        strAsunto = "Cambio de estado de la solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema de Gestión de PQRS de la Organización SIU";      
                        mail.sendMail(strDestino, strAsunto, strMensaje);
                    }             
                 }
            }
                        
            if (strIdEstadoActual.equals("AT")){
                // Notificación para el Cliente.
                
                strSQL = "SELECT p.txtRpta from buzon_pqrs p where p.txtRadicado = '" + strClaveEmail + "'";
                strRpta = GestionSQL.getFila(strSQL,"Buzon");               
                
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";
                strMensaje = strMensaje + "La solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " ha sido atentida. A continuación, se relaciona la respuesta obtenida:\n\n";
                strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
                
                 if (strRpta != null){
                    String[] arrCadena = null;

                    arrCadena = strRpta[0].split(">");

                    for(int i=0;i<arrCadena.length;i++){              
                        strMensaje = strMensaje + arrCadena[i] + "\n";                                               
                    }                    
                }else{
                     strMensaje = strMensaje + "Se presentó un inconveniente técnico en la recuperación de la respuesta.";
                }                
                 
                strMensaje = strMensaje + "\n";
                strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
                strMensaje = strMensaje + "Su caso ha sido cerrado por la Organización SIU. \n\n";
                strMensaje = strMensaje + "Seguimos trabajando día a día para ofrecerle un servicio oportuno y de calidad.\n\n";
                strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                strMensaje = strMensaje + "Cordialmente,\n\n";
                strMensaje = strMensaje + strDatosResponsable[0] + "\n";
                strMensaje = strMensaje + "Organización SIU.";
                
                strDestino = strDatosIniciales[2];
                if (strDatosTipoPQRS[2].equals("S")){
                    if (!(strDestino.equals("")) && !(strDestino.equals("Anonimo"))){
                        strAsunto = "Atención de la solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema de Gestión de PQRS de la Organización SIU";    
                        mail.sendMail(strDestino, strAsunto, strMensaje);
                    }
                }
            }                         
        }             
    }*/
    
    private String validarRegistro(String strClave,String strForm){
        
        String[] strResult = null;
        String strSQL = null;
        String strMensaje = null;
        
        // Estados.
        
        if (strForm.equals("frmEstado")){
                strSQL = "select e.txtCodigo from buzon_estados e where e.txtCodigo = '" + strClave + "'";
                strMensaje = "<html>\n";
                strMensaje = strMensaje + "<head>\n";
                strMensaje = strMensaje + "<body>\n";
                strMensaje = strMensaje + "<div class='TEXTOFALLO'>\n";
                strMensaje = strMensaje + "Ya existe un registro de estado con el código " + strClave + ". Por favor ingrese un código diferente.\n";
                strMensaje = strMensaje + "</div>\n";
                strMensaje = strMensaje + "</body>\n";
                strMensaje = strMensaje + "</html>";  
        }        
                
        if (strForm.equals("frmRetroalimentacion")){
                strSQL = "select r.txtCodigo from buzon_retroalimentacion r where r.txtCodigo = '" + strClave + "'";
                strMensaje = "<html>\n";
                strMensaje = strMensaje + "<head>\n";
                strMensaje = strMensaje + "<body>\n";
                strMensaje = strMensaje + "<div class='TEXTOFALLO'>\n";
                strMensaje = strMensaje + "Ya existe un registro de retroalimentación con el código " + strClave + ". Por favor ingrese un código diferente.\n";
                strMensaje = strMensaje + "</div>\n";
                strMensaje = strMensaje + "</body>\n";
                strMensaje = strMensaje + "</html>";  
        }
        
        if (strForm.equals("frmRol")){
                strSQL = "select r.txtCodigo from buzon_roles r where r.txtCodigo = '" + strClave + "'";
                strMensaje = "<html>\n";
                strMensaje = strMensaje + "<head>\n";
                strMensaje = strMensaje + "<body>\n";
                strMensaje = strMensaje + "<div class='TEXTOFALLO'>\n";
                strMensaje = strMensaje + "Ya existe un registro de rol con el código " + strClave + ". Por favor ingrese un código diferente.\n";
                strMensaje = strMensaje + "</div>\n";
                strMensaje = strMensaje + "</body>\n";
                strMensaje = strMensaje + "</html>";  
        }                
        
         if (strForm.equals("frmRolXUsuario")){
                String[] strDatos = strClave.split(",");
             
                strSQL = "select r.txtRol from buzon_rolesXusuario r where (r.txtRol = '" + strDatos[0] + "' and r.txtUsuario = '" + strDatos[1] + "')";
                strMensaje = "<html>\n";
                strMensaje = strMensaje + "<head>\n";
                strMensaje = strMensaje + "<body>\n";
                strMensaje = strMensaje + "<div class='TEXTOFALLO'>\n";
                strMensaje = strMensaje + "Ya existe un registro de rol por usuario con los valores seleccionados. Por favor seleccione valores diferentes.\n";
                strMensaje = strMensaje + "</div>\n";
                strMensaje = strMensaje + "</body>\n";
                strMensaje = strMensaje + "</html>";  
        }         
         
        if (strSQL != null){
            strResult = GestionSQL.getFila(strSQL,"Buzon");
            
            if (strResult != null){
                return strMensaje;
            }else{
                 return null;
            }
        }else{
            return null;
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
