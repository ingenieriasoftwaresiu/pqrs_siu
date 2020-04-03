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
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author jorge.correa
 */
public class GestionArchivos extends HttpServlet {

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
            HttpSession session = request.getSession(true); 
            String strCedula = "";
            if (session.getAttribute("txtCedula") != null){
                strCedula = (String) session.getAttribute("txtCedula");
            }else{
                request.getRequestDispatcher("cerrar.jsp").forward(request, response);
            }
            
            String strSQL = "select g.txtRutaArchivos from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";
            String[] strRuta = GestionSQL.getFila(strSQL, "Buzon");
            
            String strRutaArchivo = strRuta[0].replace("/", "\\\\");          
            String strMensaje, strNombreArchivo, strRpta, strClasificacion, strConsecutivo, strIdEstado,  strExtension, strCadena, strFechaNombre, strRed, strIdEstadoActual;
            long lgSizeAdjunto=0;
            long lgSizeFormato=0;
            Comunes comun = new Comunes();
            String strFechaActual = comun.getFechaActual(); 
            strSQL="";
            strConsecutivo="";
            strRpta = "";
            strClasificacion = "";
            strNombreArchivo = "";
            strIdEstado = "AT";
            strExtension = "-";
            strCadena = "";
            strRed = "";
            strIdEstadoActual = "";
            
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
                                                                             
                                        if (strItem.equals("txtRpta")){
                                            strRpta = item.getString();                                    
                                            strRpta = new String(strRpta.getBytes("iso-8859-1"),"UTF-8");                                          
                                        }                               
                                        
                                        if (strItem.equals("txtClasificacion")){
                                            strClasificacion = item.getString();
                                        }
                                        
                                        if (strItem.equals("txtIdEstado")){
                                            strIdEstadoActual = item.getString();                                            
                                        }
                                        
                                        if (strItem.equals("txtRed")){
                                            strRed = item.getString();
                                        }
                                        
                                        if (strItem.equals("txtRadicado")){
                                            strConsecutivo = item.getString();
                                        }
                                        
                                        if (strItem.equals("txtExtension")){
                                            strExtension = item.getString();
                                        }
                                        
                                 }else{                                                                         
                                                                             
                                        if(item.getFieldName().equals("txtAnexo")){
                                            lgSizeAdjunto = item.getSize();
                                            if (lgSizeAdjunto > 0){
                                                strFechaNombre = strFechaActual.replace("-", "").replace("-", "");
                                                strNombreArchivo= "Adjunto_Solicitud_" + strConsecutivo + "_" + strFechaNombre + strExtension;
                                                item.write(new File(destino,strNombreArchivo));
                                                strRutaArchivo = strRutaArchivo + strNombreArchivo;                                  
                                                strRutaArchivo = strRutaArchivo.replace("\\\\", "/");        
                                            }else{
                                                strRutaArchivo = "-";
                                            }
                                        }                                                
                                 } 
                         }                             
                 
                     }catch(FileUploadException  fue){
                         fue.getMessage();
                     }catch(Exception e){
                         e.getMessage();
                     }   
                                                                                                
                    String[] strTemp = strRpta.split("[\\r\\n]+");
                    int i=0;
                    
                    while(i<strTemp.length){
                        if ((i+1) == strTemp.length){
                            strCadena = strCadena + strTemp[i].trim();
                       }else{
                           strCadena = strCadena + strTemp[i].trim() + ">>";
                       }          
                       i++;
                    }                 
                    
                    if (strRed.equals("-1")){
                        strRed = "AW";
                    }
                    
                    if (!strIdEstadoActual.equals("AT")){
                        strSQL = "update buzon_pqrs set txtRpta = '" + strCadena + "', dtFechaRpta = '" + strFechaActual + "', txtClasificacion = '" + strClasificacion + "', txtRutaArchivo = '" + strRutaArchivo + "', txtIdEstado = '" + strIdEstado + "', txtRedIngreso = '" + strRed + "' where txtRadicado = '" + strConsecutivo + "'";                                                     
                    }else{
                        strSQL = "update buzon_pqrs set txtRutaArchivo = '" + strRutaArchivo + "' where txtRadicado = '" + strConsecutivo + "'";                                                     
                    }                                                                              
                      
                   strMensaje = null;
                   strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");
                
                    if (strMensaje == null){                     
                        
                        if (!strIdEstadoActual.equals("AT")){
                            Notificacion n = new Notificacion();                        
                            n.NotificacionPQRS(strConsecutivo);
                            n.notificarClienteAT();
                        }
                        
                        strMensaje = "Los datos fueron ingresados correctamente.";                        
             
                    }              
                    
                    response.sendRedirect("notificacion.jsp?mensaje=" + strMensaje);
                                   
             }
            
        } finally {
            out.close();
        }
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
