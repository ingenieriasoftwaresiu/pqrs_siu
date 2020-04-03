/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automatizartareas;

import Conexion.GestionSQL;
import Negocio.EnviarEmail;
import java.util.Vector;

/**
 *
 * @author jorge.correa
 */
public class NotificarUsuarios {
    
    public static void main(String[] args){
        
        EnviarEmail email = new EnviarEmail();
        String strMensaje="", strAsunto="";
        String[] strTemp=null;
        
        Vector arrNombres = new Vector();
        Vector arrUsuarios = new Vector();
        Vector arrPasswords = new Vector();
        Vector arrEmails = new Vector();
        
        String strSQL = "select p.txtNombreCompleto, p.txtUsuario, p.txtPassword, p.txtEmailC  from users_personas p where p.txtUsuario = 'jorge.correa' and p.txtEmailC <> ''";
        Vector arrPersonas = GestionSQL.consultaSQL(strSQL, "Users", "APPS");
        
        strAsunto = "Credenciales de acceso al Sistema para la Atención de PQRSFD SIU [Correcto]";
        
        if (arrPersonas != null){
            for(int i=0;i<arrPersonas.size();i++){
                strTemp = arrPersonas.get(i).toString().split(",");
                arrNombres.add(strTemp[0]);
                arrUsuarios.add(strTemp[1]);
                arrPasswords.add(strTemp[2]);
                arrEmails.add(strTemp[3]);
            }
            
            int a;
            
            for(a=0;a<arrNombres.size();a++){
                strMensaje = strMensaje + "Cordial saludo " +arrNombres.get(a).toString() + ".\n\n" ;                
                strMensaje = strMensaje + "Sus credenciales de acceso al Sistema para la Atención de PQRSFD SIU son:\n\n";
                strMensaje = strMensaje + "Usuario: " + arrUsuarios.get(a).toString() + "\n";
                strMensaje = strMensaje + "Contraseña: " + arrPasswords.get(a).toString() + "\n";
                strMensaje = strMensaje + "Ruta de acceso: http://siuweb.udea.edu.co:8080/Sistema_Integrador. \n\n";
                strMensaje = strMensaje + "Luego del acceso, buscar la aplicación llamada 'Sistema para la Atención de PQRSFD SIU' y presionar sobre el botón respectivo en la columna 'Ingresar'.\n\n";
                strMensaje = strMensaje + "Cualquier inquietud o dificultad al respecto, favor comunicarse con el área de Ingeniería de Software (Ext: 6429).\n\n";
                strMensaje = strMensaje + "Cordialmente,\n\n";
                strMensaje = strMensaje + "Sistema para la Atención de PQRSFD\n";
                strMensaje = strMensaje + "Administración de la SIU";
                
                email.sendMail(arrEmails.get(a).toString(), strAsunto, strMensaje);
                System.out.println("Nombre: " + arrNombres.get(a).toString());
                System.out.println("Correo: " + arrEmails.get(a).toString() + "\n");
            }
            
            System.out.println("Total de usuarios notificados: " + (a));
        }
        
    }
}
