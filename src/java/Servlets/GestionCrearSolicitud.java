/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import Conexion.GestionSQL;
import Negocio.Comunes;
import Negocio.Notificacion;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author jorge.correa
 */
public class GestionCrearSolicitud extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
           
            String strTipoPQRS, strServicio, strIdResponsable, strAnonimo, strTipoEntidad, strNombreGrupo, strNomEntidad, strNombreUser, strTelefono, strEmail, strNumId, strValor, strGrupo_Entidad, strDescripcion =null;
            String strFechaNombre, strFechaActual, strNombreArchivo, strConsecutivo, strExtension, strFechaCreacion, strIdEstado, strReqRpta, strMensaje, strClasificacion;
            String[] strTemp = null;
            Comunes comun = new Comunes();
            Notificacion n = new Notificacion();
            strFechaActual = comun.getFechaActual(); 
            long lgSizeAdjunto=0;
            strTipoPQRS = "";
            strServicio = "";
            strIdResponsable = "";
            strAnonimo="";
            strTipoEntidad = "-";
            strNombreGrupo = "";
            strNomEntidad = "";
            strNombreUser = "";
            strTelefono = "";
            strEmail = "";
            strNumId = "";      
            strGrupo_Entidad = "";
            strDescripcion = "";
            strFechaNombre = "";  
            strNombreArchivo = "";
            strConsecutivo = "";
            strExtension = "";
            strFechaCreacion = "";
            strIdEstado = "";
            strReqRpta = "";
            strMensaje = "";
            strClasificacion = "";
            
            String strSQL = "select g.txtRutaArchivos from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";
            String[] strRuta = GestionSQL.getFila(strSQL, "Buzon");            
            String strRutaArchivo = strRuta[0].replace("/", "\\\\");      
            
            strConsecutivo = generarRadicado();
            
            File destino = new File(strRutaArchivo);
             ServletRequestContext src = new ServletRequestContext(request);

             if(ServletFileUpload.isMultipartContent(src)){
                     DiskFileItemFactory factory = new DiskFileItemFactory((1024*1024),destino);
                     ServletFileUpload upload = new  ServletFileUpload(factory);
                     try{
                         java.util.List lista = upload.parseRequest(src);                     
                         java.util.Iterator it = lista.iterator();

                         while(it.hasNext()){
                                 FileItem item=(FileItem)it.next();
                                  String strItem = item.getFieldName();                                                        
                                  
                                 if(item.isFormField()){                                                                                                                                                         
                                        
                                        if (strItem.equals("txtTipoPQRS")){
                                            strTipoPQRS = item.getString();                                            
                                        }                          
                                        
                                        if (strItem.equals("txtServicio")){
                                            strServicio = item.getString();                                            
                                        }  
                                        
                                        if (strItem.equals("txtIdResponsable")){
                                            strIdResponsable = item.getString();                                            
                                        } 
                                                                                
                                        if (strItem.equals("rdAnonimo")){
                                            strAnonimo= item.getString();                                            
                                        }                      
                                        
                                        if (strAnonimo.equals("S")){
                                            strTipoEntidad = "-";
                                            strNombreGrupo = "-";
                                            strNomEntidad = "-";
                                            strNombreUser = "-";
                                            strTelefono = "-";
                                            strEmail = "-";
                                            strNumId = "-";                                                                      
                                        }else{
                                            
                                            if (strItem.equals("rdTipoEntidad")){
                                                strTipoEntidad= item.getString();                                                
                                            } 
                                            
                                            if (strItem.equals("txtGrupo")){
                                                strValor = item.getString(); 
                                                strNombreGrupo= new String(strValor.getBytes("iso-8859-1"),"UTF-8");                                             
                                            } 
                                            
                                            if (strItem.equals("txtNomEntidad")){
                                                strValor = item.getString(); 
                                                strNomEntidad= new String(strValor.getBytes("iso-8859-1"),"UTF-8");                                                  
                                            }
                                            
                                            if (strItem.equals("txtNumId")){
                                                strNumId= item.getString();                                                
                                            }
                                            
                                            if (strItem.equals("txtNombreUser")){
                                                strValor = item.getString(); 
                                                strNombreUser= new String(strValor.getBytes("iso-8859-1"),"UTF-8");                                               
                                            }
                                            
                                            if (strItem.equals("txtTelefono")){
                                                strTelefono= item.getString();                                                
                                            }
                                            
                                            if (strItem.equals("txtEmail")){
                                                strEmail= item.getString();                                                
                                            }                                                                                                                                                                              
                                        }
                                        
                                        if (strItem.equals("txtMotivo")){
                                                strValor = item.getString();                                
                                                strDescripcion= new String(strValor.getBytes("iso-8859-1"),"UTF-8");                                                   
                                        }    
                                        
                                        if (strItem.equals("txtExtensionSoporte")){
                                            strExtension = item.getString();                                                
                                        }  
                                        
                                        if (strItem.equals("txtFechaCreacion")){
                                            strFechaCreacion = item.getString();                                                
                                        }  
                                        
                                        if (strItem.equals("txtIdEstado")){
                                            strIdEstado = item.getString();                                                
                                        }  
                                        
                                        if (strItem.equals("txtReqRpta")){
                                            strReqRpta= item.getString();                                                
                                        }  
                                                                                
                                 }else{                                                                         
                                                                             
                                        if(item.getFieldName().equals("txtSoporte")){
                                            lgSizeAdjunto = item.getSize();
                                            if (lgSizeAdjunto > 0){
                                                strFechaNombre = strFechaActual.replace("-", "").replace("-", "");
                                                strNombreArchivo= "Adjunto_Usuario_Solicitud_" + strConsecutivo + "_" + strFechaNombre + strExtension;
                                                item.write(new File(destino,strNombreArchivo));
                                                strRutaArchivo = strRutaArchivo + strNombreArchivo;                                  
                                                strRutaArchivo = strRutaArchivo.replace("\\\\", "/");        
                                            }else{
                                                strRutaArchivo = "-";
                                            }
                                        }                                            
                                 } 
                         }                             
                         
                         if (strNumId.equals("")){
                             strNumId = "-";
                         }
                         
                         if (strEmail.equals("")){
                             strEmail = "-";
                         }
                         
                        if (strNomEntidad.equals("")){
                            strGrupo_Entidad = strNombreGrupo;
                        }else{
                            strGrupo_Entidad = strNomEntidad;
                        }
                        
                        if (strIdEstado.equals("C")){
                            
                            strSQL = "select r.txtReqRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strTipoPQRS + "'";
                            strTemp = GestionSQL.getFila(strSQL, "Buzon");
                            strReqRpta = strTemp[0];
                            
                            if (strReqRpta.equals("S")){
                                   strIdEstado = "PR";
                                   strClasificacion = "PD";
                            }else{
                                   strIdEstado = "AT";
                                   strClasificacion = "P";
                            }            
                        }                                           
                        
                        strSQL = "INSERT INTO buzon_pqrs (txtRadicado,dtFechaCreacion,txtTipoPQRS,txtNomCargo,txtTipoEntidad,txtGrupo_Entidad,txtNombreUser,txtTelefono,txtEmail,txtServicio,txtDescripcion,txtIdEstado,txtAnonimo,txtReqRpta, txtNumId,txtRutaArchivoUsuario,txtClasificacion) values('" + strConsecutivo + "','" + 
                                  strFechaCreacion + "','" + strTipoPQRS + "','" + strIdResponsable+ "','" + strTipoEntidad  + "','" + strGrupo_Entidad + "','" + strNombreUser + "','" + strTelefono +"','" + strEmail + "','" + strServicio + "','" + strDescripcion +"','" + strIdEstado + "','" + strAnonimo + "','" + strReqRpta + "','" + strNumId + "','" + strRutaArchivo + "','" + strClasificacion + "');";
                        
                        strMensaje = "";
                        strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                
                        if (strMensaje == null){                            
                            strMensaje = "Su solicitud se ha asociado con el consecutivo "+strConsecutivo+".";
                            
                            n.NotificacionPQRS(strConsecutivo);
                         
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
                                          
                       response.sendRedirect("notificacion.jsp?mensaje=" + strMensaje);                        
                        
                     }catch(FileUploadException  fue){
                         fue.getMessage();
                     }catch(Exception e){
                         e.getMessage();
                     }  
             }
        } finally {
            out.close();
        }
    }
    
    private String generarRadicado(){
        String strSQL="", strRadicado="";
        
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
        
        return strRadicado;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
