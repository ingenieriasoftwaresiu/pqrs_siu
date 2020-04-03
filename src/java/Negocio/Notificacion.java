/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Conexion.GestionSQL;
import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author jorge.correa
 */
public class Notificacion {
    private String strMensaje = "";
    private String strDestino = "";
    private String strAsunto = "";
    private String strIdResponsable = "";
    private String strTipoPQRS = "";
    private String strSQL = "";
    private String strClaveEmail = "";
    private String strIdEstadoActual = "";
    private String[] strDatosIniciales = null;    
    private String[] strDatosEstadoActual = null;
    private String[] strDatosGenerales = null;
    private String[] strDatosAsistente = null;
    private String[] strDatosResponsable = null;
    private String[] strDatosTipoPQRS = null;
    private  String[] strURLApp = null;
    private String[] strRpta = null;
    private EnviarEmail mail=null;
    
    public Notificacion(){
        mail = new EnviarEmail();
        
        // Obtener la ruta de la aplicación de PQRSFD desde el sistema de Usuarios SIU.
        
        strSQL = "select a.txtUrlAcceso from users_apps a where a.txtCodigo = 'PQRS'";
        strURLApp = GestionSQL.getFila(strSQL,"Users");   
    }
    
    public void notificarObs(String strRadicado, String strObs){
         strMensaje = "";
         strSQL = "select pe.txtNombreCompleto, pe.txtEmailC, p.txtNombreUser, r.txtNombre, p.txtTelefono, p.txtEmail from buzon.buzon_pqrs p, users.users_personas pe, buzon.buzon_retroalimentacion r where (p.txtNomCargo = pe.txtIdentificacion) and (p.txtTipoPQRS = r.txtCodigo) and p.txtRadicado = '" + strRadicado + "'";
         String[] strDatos = GestionSQL.getFila(strSQL, "Buzon");
        
        strDestino = strDatos[1];
        strAsunto = "ALERTA: Insatisfacción por parte del usuario de la solicitud de " + strDatos[3] + " con consecutivo #" + strRadicado + ".";

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatos[0] + ".\n\n";
        strMensaje = strMensaje + "El usuario " + strDatos[2] + " de la solicitud de " + strDatos[3] + " con consecutivo #" + strRadicado + " ha creado la siguiente observación de insatisfacción con la respuesta brindada:\n\n";
        strMensaje = strMensaje + "-------------------------------------------------------------------------------------------------------------\n\n";
        strMensaje = strMensaje + strObs + "\n\n";
        strMensaje = strMensaje + "-------------------------------------------------------------------------------------------------------------\n\n";
        strMensaje = strMensaje + "Recuerde dar respuesta oportuna a este recurso, para atender la insatisfacción del usuario.\n\n";
        strMensaje = strMensaje + "Atentamente,\n\n";             
        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU";
        mail.sendMail(strDestino, strAsunto, strMensaje);         
    }    
    
    public void responderObs(String strRadicado, String strObs){
        
        String[] strDatosUsuario = null;
        String strRptaObs=null;
        
        strSQL = "select p.txtNombreUser, p.txtEmail, r.txtNombre, o.txtRpta from buzon_pqrs p, buzon_retroalimentacion r, buzon_obs_x_sol o where (p.txtTipoPQRS = r.txtCodigo) and (p.txtRadicado = o.txtRadicado) and p.txtRadicado = '" + strRadicado + "' and o.txtIdObs = '" + strObs + "'";
        strDatosUsuario = GestionSQL.getFila(strSQL,"Buzon");               

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosUsuario[0] + ".\n\n";
        strMensaje = strMensaje + "La observación creada para la solicitud de " + strDatosUsuario[2] +  " con consecutivo #" + strRadicado + " ha sido respondida. A continuación, se relaciona la respuesta obtenida por parte del responsable:\n\n";
        strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";

        strRptaObs = strDatosUsuario[3];
        
        if (strRptaObs != null){
            String[] arrCadena = null;

            arrCadena = strRptaObs.split(">");

            for(int i=0;i<arrCadena.length;i++){              
                strMensaje = strMensaje + arrCadena[i] + "\n";                                               
            }                    
        }else{
                strMensaje = strMensaje + "Se presentó un inconveniente técnico en la recuperación de la respuesta.";
        }                

        strMensaje = strMensaje + "\n";
        strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
        strMensaje = strMensaje + "Su solicitud ha sido cerrada por la Administración de la SIU.\n\n";
        strMensaje = strMensaje + "Seguimos trabajando día a día para ofrecerle un servicio oportuno y de calidad.\n\n";
        strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
        strMensaje = strMensaje + "Atentamente,\n\n";
        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU.";

        strAsunto = "Respuesta a observación de la solicitud de " + strDatosUsuario[2] +  " con consecutivo #" + strRadicado + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";    

        strDestino = strDatosUsuario[1];         
        
        if (!(strDestino.equals("")) && !(strDestino.equals("-"))){ 
            mail.sendMail(strDestino, strAsunto, strMensaje);            
        }
        
    }    
    
    public void NotificacionSolAVencer(String strRadicado, Long lgTiempoRestante, String strIdResp){
        
        String strSQL="", strAsunto = "", strDestino = "";
        String[] strDatosResp, strDatosPQRS;
        strMensaje = "";        
  
        strSQL = "select p.txtNombreCompleto, p.txtEmailC from users.users_personas p where p.txtIdentificacion = '" + strIdResp +"'";
        strDatosResp = GestionSQL.getFila(strSQL, "Users");

        strAsunto = "ALERTA: Solicitud PQRSFD próxima a vencerse.";
        strDestino = strDatosResp[1];

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosResp[0] + ".\n\n";
        strMensaje = strMensaje + "La siguiente solicitud se encuentra a su nombre sin tener una respuesta, con observación(es) pendiente(s) de cerrar o próxima a cumplir el tiempo de vencimiento:\n\n";        

        strSQL = "select p.txtNombreUser, r.txtNombre from buzon.buzon_pqrs p, buzon_retroalimentacion r where (r.txtCodigo = p.txtTipoPQRS) and p.txtRadicado = '" + strRadicado+ "'" ;
        strDatosPQRS = GestionSQL.getFila(strSQL, "Buzon");

        strMensaje = strMensaje + "---------------------------------------------------------------------------------------\n";
        strMensaje = strMensaje + "CONSECUTIVO: " + strRadicado  + ".\n";
        strMensaje = strMensaje + "NOMBRE DEL USUARIO: " + strDatosPQRS[0]  + ".\n";
        strMensaje = strMensaje + "TIPO DE SOLICITUD: " + strDatosPQRS[1]  + ".\n";    
        strMensaje = strMensaje + "TIEMPO ANTES DEL VENCIMIENTO: " + lgTiempoRestante.toString()  + " días.\n";        
        strMensaje = strMensaje + "---------------------------------------------------------------------------------------\n\n";
        
        strMensaje = strMensaje + "Para revisarla(s), por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] +"/login.jsp y búsquela(s) con el consecutivo asignado.\n\n";
        strMensaje = strMensaje + "Recuerde brindar una respuesta oportuna a cada una de las solicitudes de sus usuarios.\n\n";
        strMensaje = strMensaje + "Atentamente,\n\n";

        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU";
        
        Log.registroTraza( "NotificacionSolAVencer: Enviando notificación...");
        
        mail.sendMail(strDestino, strAsunto, strMensaje);                    
        
        Log.registroTraza( "NotificacionSolAVencer: Notificación enviada a " + strDestino);
    }
    
    public void NotificacionSolVencidas(String strRadicado, Calendar fechaRpta, String strIdResp){
        
        String strSQL="", strAsunto = "", strDestino = "";
        String[] strDatosResp, strDatosPQRS;
        Date dtFechaRpta = new java.sql.Date(fechaRpta.getTimeInMillis());
        strMensaje = "";        
  
        strSQL = "select p.txtNombreCompleto, p.txtEmailC from users.users_personas p where p.txtIdentificacion = '" + strIdResp +"'";
        strDatosResp = GestionSQL.getFila(strSQL, "Users");

        strAsunto = "ALERTA: Solicitud PQRSFD vencida.";
        strDestino = strDatosResp[1];

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosResp[0] + ".\n\n";
        strMensaje = strMensaje + "La siguiente solicitud se encuentra vencida y sin ninguna respuesta a la fecha:\n\n";        

        strSQL = "select p.txtNombreUser, r.txtNombre from buzon.buzon_pqrs p, buzon_retroalimentacion r where (r.txtCodigo = p.txtTipoPQRS) and p.txtRadicado = '" + strRadicado+ "'" ;
        strDatosPQRS = GestionSQL.getFila(strSQL, "Buzon");

        strMensaje = strMensaje + "---------------------------------------------------------------------------------------\n";
        strMensaje = strMensaje + "CONSECUTIVO: " + strRadicado  + ".\n";
        strMensaje = strMensaje + "NOMBRE DEL USUARIO: " + strDatosPQRS[0]  + ".\n";
        strMensaje = strMensaje + "TIPO DE SOLICITUD: " + strDatosPQRS[1]  + ".\n";    
        strMensaje = strMensaje + "FECHA DE RESPUESTA (aaaa-mm-dd): " + dtFechaRpta.toString() + ".\n";        
        strMensaje = strMensaje + "---------------------------------------------------------------------------------------\n\n";
        
        strMensaje = strMensaje + "Para revisarla(s), por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] +"/login.jsp y búsquela(s) con el consecutivo asignado.\n\n";
        strMensaje = strMensaje + "Recuerde brindar una respuesta oportuna a cada una de las solicitudes de sus usuarios.\n\n";
        strMensaje = strMensaje + "Atentamente,\n\n";

        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU";
        
        Log.registroTraza( "NotificacionSolVencidas: Enviando notificación...");
        
        mail.sendMail(strDestino, strAsunto, strMensaje);           
        
        Log.registroTraza( "Notificación enviada a " + strDestino);
    }
    
    public void NotificacionPQRS(String strRadicado){
        
        this.strClaveEmail = strRadicado;        
                
        // Obtener los datos de la Solicitud PQRSFD a notificar.
        
        strSQL = "SELECT p.txtIdEstado, p.txtNombreUser, p.txtEmail, p.txtNomCargo, p.txtTipoPQRS, p.txtAnonimo from buzon_pqrs p where p.txtRadicado = '" + strClaveEmail + "'";       
        strDatosIniciales = GestionSQL.getFila(strSQL,"Buzon");
        
        // Calcular el responsable de la solicitud.
            
        strIdResponsable = strDatosIniciales[3];
        strSQL = "select p.txtNombreCompleto, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strIdResponsable + "'";       
        strDatosResponsable = GestionSQL.getFila(strSQL,"Users");   
        
         // Calcular el tiempo de respuesta y nombre del tipo de solicitud.
            
        strTipoPQRS = strDatosIniciales[4];
        strSQL = "select r.txtTiempoRpta, r.txtNombre, r.txtReqRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strTipoPQRS + "'";       
        strDatosTipoPQRS = GestionSQL.getFila(strSQL,"Buzon");
            
         strIdEstadoActual = strDatosIniciales[0];         
                 
        if (strIdEstadoActual != null){  
            strSQL = "SELECT e.txtNombre FROM buzon_estados e where e.txtCodigo = '" + strIdEstadoActual + "'";
            strDatosEstadoActual = GestionSQL.getFila(strSQL,"Buzon");                       
        }
    }
    
    public void notificarClienteRE(){
        
        // Notificación para el Cliente.
        
        if (strDatosIniciales[5].equals("N")){
           if (strDatosTipoPQRS[2].equals("S")){
                strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";
                strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] + " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema para la Atención de PQRSFD de la Administración de la SIU.\n\n";
                strMensaje = strMensaje + "Su solicitud ha sido asignada a la persona encargada, quien le estará dando atención y respuesta a más tardar en " + strDatosTipoPQRS[0] + " días hábiles.\n\nPara realizarle seguimiento a esta solicitud, puede ingresar a la dirección " + strURLApp[0] + " y buscarla con el consecutivo asignado.\n\n";
                strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                strMensaje = strMensaje + "Atentamente,\n\n";      
                strMensaje = strMensaje + "Administración de la SIU.";

                strAsunto = "Creación de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";
                
                strDestino = strDatosIniciales[2];                    
                
                if (!(strDestino.equals("")) && !(strDestino.equals("-"))){ 
                    Log.registroTraza( "notificarClienteRE: Enviando notificación...");
                    mail.sendMail(strDestino, strAsunto, strMensaje);
                    Log.registroTraza( "notificarClienteRE: Notificación enviada a " + strDestino);
                }else{
                    Log.registroTraza( "notificarClienteRE: No se envió notificación al usuario, ya que no tiene un correo electrónico registrado.");
                }
            }                    
        }else{
             Log.registroTraza( "notificarClienteRE: El usuario de la solicitud es anónimo.");
        }            
    }
    
    public void notificarResponsableRE(){
        // Notificación para el responsable de la solicitud.

        strMensaje = "";                                                                                           

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosResponsable[0] + ".\n\n";
        strMensaje = strMensaje + "Se le ha asignado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema para la Atención de PQRSFD de la Administración de la SIU.\n\n";
        strMensaje = strMensaje + "Para revisarla, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] +"/login.jsp y búsquela con el consecutivo asignado.\n\n";
        if (strDatosTipoPQRS[2].equals("N")){
            strMensaje = strMensaje + "Recuerde que este tipo de solicitud no requiere respuesta para el usuario.\n\n";
        }else{
            strMensaje = strMensaje + "Recuerde que tiene un plazo máximo de " + strDatosTipoPQRS[0] + " días hábiles, a partir de la fecha de creación de la solicitud, para atenderla y dar respuesta al usuario.\n\n";
        }                
        strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
        strMensaje = strMensaje + "Atentamente,\n\n";
        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU";

        strDestino = strDatosResponsable[1];
        strAsunto = "Asignación de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";

        Log.registroTraza( "notificarResponsableRE: Enviando notificación...");
        mail.sendMail(strDestino, strAsunto, strMensaje);
        
        Log.registroTraza( "notificarResponsableRE: Notificación enviada a " + strDestino);
    }
    
    public void notificarResponsableAT(){
        // Notificación para el responsable de la solicitud.

        strMensaje = "";                                                                                           
        
        if (strDatosTipoPQRS[2].equals("N")){
            strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosResponsable[0] + ".\n\n";
            strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema para la Atención de PQRSFD de la Administración de la SIU.\n\n";
            strMensaje = strMensaje + "Para revisarla, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] +"/login.jsp y búsquela con el consecutivo asignado.\n\n";
            strMensaje = strMensaje + "Recuerde que este tipo de solicitud no requiere respuesta para el usuario.\n\n";             
            strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
            strMensaje = strMensaje + "Atentamente,\n\n";
            strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
            strMensaje = strMensaje + "Administración de la SIU";

            strDestino = strDatosResponsable[1];
            strAsunto = "Creación de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";
            
            Log.registroTraza( "notificarResponsableAT: Enviando notificación...");
            mail.sendMail(strDestino, strAsunto, strMensaje);
            
            Log.registroTraza( "notificarResponsableAT: Notificación enviada a " + strDestino);
        }
    }
    
    public void notificarAsistenteRE(){
        // Notificación para el Asistente.

        strMensaje = "";

        strSQL = "SELECT g.txtAsistente from buzon_generales g where g.txtCodigo = 'frmGeneral'";       
        strDatosGenerales = GestionSQL.getFila(strSQL,"Buzon"); 

        strSQL = "select p.txtNombreCompleto, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strDatosGenerales[0] + "'";       
        strDatosAsistente = GestionSQL.getFila(strSQL,"Users"); 

        strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosAsistente[0] + ".\n\n";
        strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema para la Atención de PQRSFD de la Administración de la SIU.\n\n";
        strMensaje = strMensaje + "Para conocer mayor información al respecto, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] + "/login.jsp y búsquela con el consecutivo asignado.\n\n"; 
        strMensaje = strMensaje + "Atentamente,\n\n";
        strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
        strMensaje = strMensaje + "Administración de la SIU";                      

        strDestino = strDatosAsistente[1];
        strAsunto = "Creación de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";
        
        Log.registroTraza( "notificarAsistenteRE: Enviando notificación...");
        mail.sendMail(strDestino, strAsunto, strMensaje);
        
        Log.registroTraza( "notificarAsistenteRE: Notificación enviada a " + strDestino);
    }
    
    public void notificarAsistenteAT(){
        // Notificación para el Asistente.

        strMensaje = "";
        
        if (strDatosTipoPQRS[2].equals("N")){
            strSQL = "SELECT g.txtAsistente from buzon_generales g where g.txtCodigo = 'frmGeneral'";       
            strDatosGenerales = GestionSQL.getFila(strSQL,"Buzon"); 

            strSQL = "select p.txtNombreCompleto, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strDatosGenerales[0] + "'";       
            strDatosAsistente = GestionSQL.getFila(strSQL,"Users"); 

            strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosAsistente[0] + ".\n\n";
            strMensaje = strMensaje + "Se ha creado la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el estado " + strDatosEstadoActual[0] + " dentro del Sistema para la Atención de PQRSFD de la Administración de la SIU.\n\n";
            strMensaje = strMensaje + "Para conocer mayor información al respecto, por favor ingrese con las credenciales asignadas a la dirección " + strURLApp[0] + "/login.jsp y búsquela con el consecutivo asignado.\n\n"; 
            strMensaje = strMensaje + "Atentamente,\n\n";
            strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
            strMensaje = strMensaje + "Administración de la SIU";                       

            strDestino = strDatosAsistente[1];
            strAsunto = "Creación de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";
            
            Log.registroTraza( "notificarAsistenteAT: Enviando notificación...");
            mail.sendMail(strDestino, strAsunto, strMensaje);
            Log.registroTraza( "notificarAsistenteAT: Notificación enviada a " + strDestino);
        }
    }
    
    /*public void notificarClientePR(){
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
        strMensaje = strMensaje + "Atentamente,\n\n";
        strMensaje = strMensaje + strDatosResponsable[0] + "\n";
        strMensaje = strMensaje + "Administración de la SIU.";

        strDestino = strDatosIniciales[2];
        if (strDatosTipoPQRS[2].equals("S")){
            if (!(strDestino.equals("")) && !(strDestino.equals("Anonimo"))){
                strAsunto = "Cambio de estado de la solicitud de " + strDatosTipoPQRS[1] +  " #" + strClaveEmail + " en el Sistema para la Atención de PQRS de la Administración de la SIU";      
                mail.sendMail(strDestino, strAsunto, strMensaje);
            }             
        }
    }*/
    
   public void notificarClienteAT(){
        // Notificación para el Cliente.
                   
        if (strDatosIniciales[5].equals("N")){                
                 if (strDatosTipoPQRS[2].equals("N")){                                 
                     
                    strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";
                    strMensaje = strMensaje + "La Administración de la SIU le agredece el haberse tomado el tiempo para enviarnos su solicitud de " + strDatosTipoPQRS[1] +  ".\n\n";
                    strMensaje = strMensaje + "Seguimos trabajando día a día para ofrecerle un servicio oportuno y de calidad.\n\n";           
                    strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                    strMensaje = strMensaje + "Atentamente,\n\n";      
                    strMensaje = strMensaje + "Administración de la SIU.";

                    strAsunto = "Recepción de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";
                 }else{                            
                     
                    strSQL = "SELECT p.txtRpta from buzon_pqrs p where p.txtRadicado = '" + strClaveEmail + "'";
                    strRpta = GestionSQL.getFila(strSQL,"Buzon");               

                    strMensaje = strMensaje + "Cordial saludo Sr(a). " + strDatosIniciales[1] + ".\n\n";
                    strMensaje = strMensaje + "La solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " ha sido atentida. A continuación, se relaciona la respuesta obtenida por parte del responsable:\n\n";
                    strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";

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
                    strMensaje = strMensaje + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
                    //strMensaje = strMensaje + "Su solicitud ha sido cerrada por la Administración de la SIU. Para evaluar su satisfacción frente a la respuesta obtenida, por favor ingresar al siguiente enlace: " + strURLApp[0] + "/eval_satisfaccion.jsp?keysearch=" + strClaveEmail + "&action=C  \n\n";
                    strMensaje = strMensaje + "Su solicitud ha sido cerrada. La Administración de la SIU sigue trabajando día a día para ofrecerle un servicio oportuno y de calidad. \n\n";                    
                    strMensaje = strMensaje + "Muchas gracias por su atención.\n\n";
                    strMensaje = strMensaje + "Atentamente,\n\n";
                    strMensaje = strMensaje + strDatosResponsable[0] + "\n";
                    strMensaje = strMensaje + "Administración de la SIU.";

                    strAsunto = "Atención de la solicitud de " + strDatosTipoPQRS[1] +  " con consecutivo #" + strClaveEmail + " en el Sistema para la Atención de PQRSFD de la Administración de la SIU";    
                 }                        
                 
                strDestino = strDatosIniciales[2];                                 
                
                if (!(strDestino.equals("")) && !(strDestino.equals("-"))){ 
                    Log.registroTraza( "notificarClienteAT: Enviando notificación...");
                    mail.sendMail(strDestino, strAsunto, strMensaje);                          
                    Log.registroTraza( "notificarClienteAT: Notificación enviada a " + strDestino);
                }else{
                    Log.registroTraza( "notificarClienteAT: No se envió notificación al cliente, ya que no tiene un correo electrónico registrado.");
                }
        }else{
            Log.registroTraza( "notificarClienteAT: El usuario de la solicitud es anónimo.");
        }
    }
}
