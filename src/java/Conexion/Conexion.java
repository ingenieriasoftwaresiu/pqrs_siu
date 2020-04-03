
package Conexion;

import java.io.File;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 *
 * @author jorge.correa
 */
public class Conexion {

    /* //Parámetros conexión SIU
    
    private String strDriver = "com.mysql.jdbc.Driver";
    private String strURL = "jdbc:mysql://172.30.100.37/muestras";
    private String strLogin = "admin";
    private String strPwd = "admin";    
    
    
    /* //Parámetros conexión GEORGE
    
    private String strDriver = "com.mysql.jdbc.Driver";
    private String strURL = "jdbc:mysql://localhost/muestras";
    private String strLogin = "root";
    private String strPwd = "root";
    */
    
    public Connection getConnection(String strBaseDatos){
        Connection cn = null;
        String[] strDatos = null;
        String strDriver;
        String strURL;
        String strLogin;
        String strPwd;
        
        try{            
            strDatos = readXMLFile(strBaseDatos);
            
            if (strDatos != null){
                strDriver = strDatos[0];
                strURL = strDatos[1];
                strLogin = strDatos[2];
                strPwd = strDatos[3];                
                
                Class.forName(strDriver).newInstance();
                cn = DriverManager.getConnection(strURL, strLogin, strPwd);
            }else{
                System.out.println("No se recuperaron los parámetros de conexión a la BD.");
            }            
        }catch(SQLException e){
            System.out.println(e.toString());
            cn = null;
        }catch(Exception e){
            System.out.println(e.toString());
            cn = null;
        }
        return cn;
    }
    
    private String[] readXMLFile(String strBaseDatos){
        
        String[] strDatos = null;
        String strRuta;
        
        try {
            
            //SIU-WEB1            
            //strRuta = "D:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\pqrs\\WEB-INF\\confBD.xml";
            
            //SIU-WEB
           //strRuta = "C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0_Tomcat7041\\webapps\\pqrs\\WEB-INF\\confBD.xml";
                 
            //ING-SOFTWARE
            strRuta = "C:\\WebApps\\pqrs\\sistema-pqrs-osiu\\web\\WEB-INF\\confBD.xml";    
            
            //PRUEBAS-DESARROLLO    
            //strRuta = "C:\\WebApps\\pqrs\\sistema-pqrs-osiu\\web\\WEB-INF\\confBD.xml";    
            
            //SIU-CIDMOVIL
            //strRuta = "C:\\WebApps\\pqrs\\sistema-pqrs-osiu\\web\\WEB-INF\\confBD.xml";
            
            //GEORGE
            //strRuta = "C:\\Users\\George Belt\\Documents\\NetBeansProjects\\pqrs\\web\\WEB-INF\\confBD.xml";
            
            //LOCAL
            //strRuta = "C:\\apache-tomcat-7.0.29\\webapps\\pqrs\\WEB-INF\\confBD.xml";
                                        
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(strRuta));
            
            // normalize text representation
            doc.getDocumentElement ().normalize ();
            
            NodeList listOfPersons = doc.getElementsByTagName(strBaseDatos);
            int totalPersons = listOfPersons.getLength();
            Node firstPersonNode = listOfPersons.item(0);
            
            if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
                
                strDatos = new String[4];
                
                Element firstPersonElement = (Element)firstPersonNode;
      
                NodeList firstNameList = firstPersonElement.getElementsByTagName("driver");
                Element firstNameElement = (Element)firstNameList.item(0);

                NodeList textFNList = firstNameElement.getChildNodes();
                strDatos[0] = ((Node)textFNList.item(0)).getNodeValue().trim();
      
                NodeList lastNameList = firstPersonElement.getElementsByTagName("url");
                Element lastNameElement = (Element)lastNameList.item(0);

                NodeList textLNList = lastNameElement.getChildNodes();                
                strDatos[1] = ((Node)textLNList.item(0)).getNodeValue().trim();
 
                NodeList ageList = firstPersonElement.getElementsByTagName("userBD");
                Element ageElement = (Element)ageList.item(0);

                NodeList textAgeList = ageElement.getChildNodes();                
                strDatos[2] = ((Node)textAgeList.item(0)).getNodeValue().trim();
                
                NodeList passwdBD = firstPersonElement.getElementsByTagName("passwdBD");
                Element pwdElement = (Element)passwdBD.item(0);

                NodeList textPwdList = pwdElement.getChildNodes();                 
                if ((Node)textPwdList.item(0) != null){
                    String strTemp = ((Node)textPwdList.item(0)).getNodeValue();
                
                    if (strTemp.equals("")){
                        strDatos[3] = "";
                    }else{
                        strDatos[3] = strTemp.trim();
                    } 
                }else{
                    strDatos[3] = "";
                }
            }
        }catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();
        }catch (Throwable t) {
            t.printStackTrace ();
        }
        return strDatos;
    }        
}
