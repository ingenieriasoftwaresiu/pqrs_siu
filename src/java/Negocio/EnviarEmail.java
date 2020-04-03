/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Jorge.correa
 */
import Conexion.GestionSQL;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author jorge.correa
 */
public class EnviarEmail {
    
    private String mailSMTPServer;
    private String mailSMTPServerPort;
    private String mailSenha;

    public void sendMail(String to, String subject, String message){

        Properties props = new Properties();      
        String from = null;     
        String strSQL = "";
        String[] strDatosGenerales = null;

        strSQL = "select txtNomServidor, txtNumPuerto, txtUsuario, txtPassword from buzon_generales g where g.txtCodigo = 'frmGeneral'";    
        strDatosGenerales = GestionSQL.getFila(strSQL,"Buzon");

        if (strDatosGenerales == null){
            Log.registroTraza( "Error: Recuperando los parámetros, no se encuentran diligenciados en el documento de configuración general.");            
        }else{
            mailSMTPServer = strDatosGenerales[0];
            mailSMTPServerPort = strDatosGenerales[1];
            mailSenha = strDatosGenerales[3];
            from = strDatosGenerales[2];           

            props.put("mail.transport.protocol","smtp");
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.host",mailSMTPServer);        
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.user",from);
            props.put("mail.smtp.debug","true");       
            props.put("mail.smtp.port",mailSMTPServerPort);                       
            props.put("mail.smtp.socketFactory.port",mailSMTPServerPort);            
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback","false");
        }

        SimpleAuth auth =  null;
        auth = new SimpleAuth(from,mailSenha);

        Session session = Session.getDefaultInstance(props,auth);
        session.setDebug(false);

        Message msg = new MimeMessage(session);

        try{
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);            
            msg.setText(message);

        }catch(Exception e){
            Log.registroTraza( "Error el enviar la notificación: " + e.getMessage());
        }

        Transport tr;
        try{
            tr = session.getTransport("smtp");
            tr.connect(mailSMTPServer,from,mailSenha);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        }catch(Exception e){
            Log.registroTraza( "Error el enviar la notificación: " + e.getMessage());
        }
    }        
}

class SimpleAuth extends Authenticator{
    public String username = null;
    public String password = null;
    
    public SimpleAuth(String user, String pwd){
        username = user;
        password = pwd;
    }
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username,password);
    }
}

