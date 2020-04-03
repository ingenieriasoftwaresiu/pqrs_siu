/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Conexion.GestionSQL;
import Negocio.Comunes;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;

/**
 *
 * @author jorge.correa
 */
public class InformeSegPQRS extends HttpServlet {

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
            
            Calendar fechaInicial = null;
            Calendar fechaFinal = null;
            int nroDiasHabiles;
            String strAccion = request.getParameter("txtAccion");
            String strFiltro= request.getParameter("txtFiltro");                     
            String strSQL = "", strDescripcion=null;            
            String strValor, strFechaRecepcion = null;
            String strReqRpta, strFechaIni, strFechaFin = null;
            String[] strTemp = null;
            Vector arrTipos = new Vector();
            Vector arrTiposUsuario = new Vector();
            Vector arrUsuarios = new Vector();
            Vector arrEscritores = new Vector();
            Vector arrDescripciones = new Vector();
            Vector arrFechasRecepcion = new Vector();
            Vector arrFechasRespuesta = new Vector();
            Vector arrNroDias = new Vector();
            Vector arrRadicados = new Vector();
            Vector arrServicios = new Vector();     
            Vector arrReqRpta = new Vector();
            Vector arrClasificaciones = new Vector();
            Comunes comun = new Comunes();            
           
            if (strFiltro.equals("*")){           
                strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre";
            }else{    
                
                strTemp = strFiltro.split(";");
                boolean esTipo = false;
                boolean esRango = false;
                boolean esServicio = false;
                
                for (int i=0;i<strTemp.length;i++){                    
                    if (strTemp[i].toString().equals("T")){
                        esTipo = true;
                    }
                    
                    if (strTemp[i].toString().equals("S")){
                        esServicio = true;
                    }
                    
                    if (strTemp[i].toString().equals("R")){
                        esRango = true;
                    }                    
                }
                
                String strTipoPQRS = request.getParameter("txtTipoPQRS");
                String strFechaInicial = request.getParameter("txtFechaInicial");
                String strFechaFinal = request.getParameter("txtFechaFinal");
                String strServicio = request.getParameter("txtServicio");
                
                // Combinaciones de los filtros.
                
                if ((esTipo) && (esServicio) && (esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (r.txtCodigo = '" + strTipoPQRS + "') and (p.dtFechaCreacion BETWEEN '" + strFechaInicial + "' and '" + strFechaFinal + "') and s.txtCodigo = '" + strServicio + "' GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                }
                
                if ((esTipo) && (esServicio) && (!esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (r.txtCodigo = '" + strTipoPQRS + "') and s.txtCodigo = '" + strServicio + "' GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                }
                
                if ((esTipo) && (!esServicio) && (esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (r.txtCodigo = '" + strTipoPQRS + "') and (p.dtFechaCreacion BETWEEN '" + strFechaInicial + "' and '" + strFechaFinal + "') GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                }
                 
               if ((!esTipo) && (esServicio) && (esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.dtFechaCreacion BETWEEN '" + strFechaInicial + "' and '" + strFechaFinal + "') and s.txtCodigo = '" + strServicio + "' GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                }
                
                if ((!esTipo) && (esServicio) && (!esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and s.txtCodigo = '" + strServicio + "' GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                }   
                
                if ((esTipo) && (!esServicio) && (!esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (r.txtCodigo = '" + strTipoPQRS + "') GROUP BY p.txtRadicado ORDER BY s.txtNombre, p.txtNombreUser";
                }   
                
                if ((!esTipo) && (!esServicio) && (esRango)){
                    strSQL = "SELECT r.txtNombre, s.txtNombre, p.txtTipoEntidad, p.txtGrupo_Entidad, p.txtNombreUser, p.txtDescripcion, p.dtFechaCreacion, p.dtFechaRpta, p.txtRadicado, p.txtReqRpta, p.txtClasificacion FROM buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.dtFechaCreacion BETWEEN '" + strFechaInicial + "' and '" + strFechaFinal + "') GROUP BY p.txtRadicado ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser";
                } 
            }
                       
            Vector arrPQRS = GestionSQL.consultaSQL(strSQL, "Buzon", "INFORMESEGPQRSNEW");
            
            if (arrPQRS == null){            
                out.println("<html>");
                out.println("<head>");                
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='TEXTOFALLO'>");
                out.println("No se encontraron registros que concuerden con el filtro aplicado a la consulta.");
                out.println("</div>");             
                out.println("</body>");
                out.println("</html>");
            }else{                 
                for(int i=0;i<arrPQRS.size();i++){
                    strTemp = arrPQRS.get(i).toString().split("<");                                
                    arrTipos.add(strTemp[0]);
                    arrServicios.add(strTemp[1]);
                    arrTiposUsuario.add(strTemp[2]);
                    arrUsuarios.add(strTemp[3]);
                    arrEscritores.add(strTemp[4]);                                             
                    arrDescripciones.add(strTemp[5]);
                    arrFechasRecepcion.add(strTemp[6]);
                    arrFechasRespuesta.add(strTemp[7]);           
                    arrRadicados.add(strTemp[8]);                   
                    arrReqRpta.add(strTemp[9]);
                    arrClasificaciones.add(strTemp[10]);
                    arrNroDias.add("0");
                }
                                          
                // Consulta de días no hábiles.
                
                Vector arrFechas = new Vector();
                strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
                arrFechas = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");
                                     
                for(int i=0;i<arrFechasRespuesta.size();i++){
                    strValor = arrFechasRespuesta.get(i).toString();
                    strFechaRecepcion = arrFechasRecepcion.get(i).toString();
                    strReqRpta = arrReqRpta.get(i).toString();                    
                    
                    if (strReqRpta.equals("N")){
                            arrFechasRespuesta.setElementAt("N/A", i);
                            arrNroDias.setElementAt("N/A", i);               
                    }else{     
                            if ((strValor == null) || (strValor.equals("null"))){
                                arrFechasRespuesta.setElementAt("Por asignar", i);
                                arrNroDias.setElementAt("Por asignar", i);               
                            }else{
                                
                                fechaInicial = null;
                                fechaFinal = null;
                                nroDiasHabiles = 0;
                                strTemp = null;
                                strFechaIni = "";
                                strFechaFin = "";
                                
                                fechaInicial = Calendar.getInstance();
                                fechaFinal = Calendar.getInstance();
                                
                                strTemp = strFechaRecepcion.split("-");                           
                                fechaInicial.set(Integer.parseInt(strTemp[0]),(Integer.parseInt(strTemp[1])-1),Integer.parseInt(strTemp[2]));
                                fechaInicial.set(Calendar.SECOND, 0);
                                fechaInicial.set(Calendar.MILLISECOND, 0);  
                                
                                strTemp = strValor.split("-");       
                                fechaFinal.set(Integer.parseInt(strTemp[0]),(Integer.parseInt(strTemp[1])-1),Integer.parseInt(strTemp[2]));
                                fechaFinal.set(Calendar.SECOND, 0);
                                fechaFinal.set(Calendar.MILLISECOND, 0);  
                                
                                strFechaIni = fechaInicial.getTime().toString();
                                strFechaFin = fechaFinal.getTime().toString();
                                                                                                                                                 
                                nroDiasHabiles = comun.getDiasHabiles(fechaInicial, fechaFinal,arrFechas);
                                if (nroDiasHabiles == 1){                                                
                                    if (strFechaIni.equals(strFechaFin)){                                       
                                        nroDiasHabiles=0;
                                    }else{                                       
                                        nroDiasHabiles=1;
                                    }                                    
                                }else{                          
                                    nroDiasHabiles--;                                    
                                }                                
                                arrNroDias.setElementAt(String.valueOf(nroDiasHabiles),i);                                
                        }
                    }                                                
                }

                if (strAccion.equals("G")){
                    out.println("<html>");
                    out.println("<head>");                 
                    out.println("</head>");
                    out.println("<body>");                       
                    out.println("<table cellspacing='0' cellpadding='5' width='1280px' border='0' class='TABLARESULT'>");
                    out.println("<tr>");
                    out.println("<td class='TITULOTABLAIZQ'>TIPO DE SOLICITUD</td>");
                    out.println("<td class='TITULOTABLACENTER'>NOMBRE DEL<br>SERVICIO</td>");
                    out.println("<td class='TITULOTABLACENTER'>ENTIDAD O GRUPO</td>");
                    out.println("<td class='TITULOTABLACENTER'>NOMBRE DE QUIEN<br>PRESENTA EL ESCRITO</td>");
                    out.println("<td class='TITULOTABLACENTER'>DESCRIPCIÓN DE LA SOLICITUD</td>");
                    out.println("<td class='TITULOTABLACENTER'>FECHA DE RECIBO<br>(aaaa-mm-dd)</td>");
                    out.println("<td class='TITULOTABLACENTER'>FECHA DE RESPUESTA<br>(aaaa-mm-dd)</td>");
                    out.println("<td class='TITULOTABLACENTER'>No. DE DÍAS PARA RESPUESTA</td>");
                    out.println("<td class='TITULOTABLACENTER'>No. CONSECUTIVO SOLICITUD</td>");          
                    out.println("<td class='TITULOTABLACENTER'>CLASIFICACIÓN</td>");  
                    out.println("</tr>");

                    for(int i=0;i<arrTipos.size();i++){
                        out.println("<tr class='FILARESULT'>");
                        out.println("<td class='TEXTOCELDAIZQ'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrTipos.get(i) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrServicios.get(i) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + obtenerUsuario(arrTiposUsuario.get(i).toString(), arrUsuarios.get(i).toString()) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrEscritores.get(i) + "</a></td>");                    
                                                            
                        strTemp = arrDescripciones.get(i).toString().split(">");
                        strValor = "";
                        for (int k=0;k<strTemp.length;k++){                                                
                            strValor += strTemp[k] + "\n";
                        }

                        out.println("<td class='TEXTOCELDACENTER' style='text-align: left;width:300px;'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + strValor + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrFechasRecepcion.get(i) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrFechasRespuesta.get(i) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrNroDias.get(i) + "</a></td>");
                        out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>" + arrRadicados.get(i) + "</a></td>");   
                        if (arrClasificaciones.get(i).equals("P")){
                            out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>Procedente</a></td>"); 
                        }else{
                            if (arrClasificaciones.get(i).equals("NP")){
                                out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>No Procedente</a></td>"); 
                            }else{                     
                                out.println("<td class='TEXTOCELDACENTER'><a href='#' onclick='abrirRegPQRS(" + arrRadicados.get(i) + ")'>Por Definir</a></td>");             
                            }
                        }                            
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");
                }else{
                    
                    try{
                        strSQL = "select g.txtRutaSegPRQS from buzon_generales g where g.txtCodigo = 'frmGeneral'";
                        String[] strRuta = GestionSQL.getFila(strSQL, "Buzon");

                        WorkbookSettings ws = new WorkbookSettings();
                        ws.setLocale(new Locale("en", "EN"));
                        WritableWorkbook workbook = Workbook.createWorkbook(new File(strRuta[0]), ws);
                        WritableSheet s = workbook.createSheet("Seguimiento a PQRSFD", 0);                        
                        writeHeaderSheet(s);
                        
                        WritableFont wfContent = new WritableFont(WritableFont.ARIAL, 9);
                        WritableCellFormat cfContent = new WritableCellFormat(wfContent);
                        cfContent.setWrap(true);
                        cfContent.setVerticalAlignment(VerticalAlignment.CENTRE);
                        cfContent.setAlignment(Alignment.CENTRE);
                        cfContent.setBorder( Border.ALL, BorderLineStyle.THIN);
                        
                        WritableFont wfDesc = new WritableFont(WritableFont.ARIAL, 9);
                        WritableCellFormat cfDesc = new WritableCellFormat(wfDesc);
                        cfDesc.setWrap(true);
                        cfDesc.setVerticalAlignment(VerticalAlignment.CENTRE);
                        cfDesc.setAlignment(Alignment.LEFT);
                        cfDesc.setBorder( Border.ALL, BorderLineStyle.THIN);
                        
                        int cont = 9;
                        
                         for(int i=0;i<arrTipos.size();i++){
                            Label lblTipo = new Label(0,cont,arrTipos.get(i).toString(),cfContent);
                            s.addCell(lblTipo);
                            s.mergeCells(0, cont, 1, cont+1);         
                            
                            Label lblServicio = new Label(2,cont,arrServicios.get(i).toString(),cfContent);
                            s.addCell(lblServicio);
                            s.mergeCells(2, cont, 4, cont+1);

                            Label lblEntidad = new Label(5,cont,obtenerUsuario(arrTiposUsuario.get(i).toString(), arrUsuarios.get(i).toString()),cfContent);
                            s.addCell(lblEntidad);
                            s.mergeCells(5, cont, 7, cont+1);

                            Label lblEscritor = new Label(8,cont,arrEscritores.get(i).toString(),cfContent);
                            s.addCell(lblEscritor);
                            s.mergeCells(8, cont, 10, cont+1);
                            
                            strTemp = arrDescripciones.get(i).toString().split(">");
                            strValor = "";
                            for (int k=0;k<strTemp.length;k++){                                                
                                strValor += strTemp[k] + " ";
                            }

                            Label lblDescripcion = new Label(11,cont,strValor,cfDesc);
                            s.addCell(lblDescripcion);
                            s.mergeCells(11, cont, 16, cont+1);

                            Label lblFechaRecibo = new Label(17,cont,arrFechasRecepcion.get(i).toString(),cfContent);
                            s.addCell(lblFechaRecibo);
                            s.mergeCells(17, cont, 18, cont+1);

                            Label lblFechaRpta = new Label(19,cont,arrFechasRespuesta.get(i).toString(),cfContent);
                            s.addCell(lblFechaRpta);
                            s.mergeCells(19, cont, 20, cont+1);

                            Label lblNroDias = new Label(21,cont,arrNroDias.get(i).toString(),cfContent);
                            s.addCell(lblNroDias);
                            s.mergeCells(21, cont, 22, cont+1);

                            Label lblRadicado = new Label(23,cont,arrRadicados.get(i).toString(),cfContent);
                            s.addCell(lblRadicado);
                            s.mergeCells(23, cont, 24, cont+1);                              
                            
                            String strClasificacion;
                            if(arrClasificaciones.get(i).equals("P")){
                                strClasificacion = "Procedente";
                            }else{
                                strClasificacion = "No Procedente";
                            }
                                                        
                            Label lblClasificacion = new Label(25,cont,strClasificacion,cfContent);
                            s.addCell(lblClasificacion);
                            s.mergeCells(25, cont, 26, cont+1);
                            
                            cont = cont + 2;
                         }                        
                        
                        workbook.write();
                        workbook.close();

                        out.println("<html>");
                        out.println("<head>");               
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='TEXTOEXITO'>El informe fue correctamente exportado a un archivo Excel ubicado en la ruta " + strRuta[0] + ".</div>");
                        out.println("</body>");
                        out.println("</html>");
                    }catch (IOException e){
                        out.println("<html>");
                        out.println("<head>");               
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='TEXTOFALLO'> Se generó el siguiente error: " + e.getMessage() + ".</div>");
                        out.println("</body>");
                        out.println("</html>");             
                        e.printStackTrace();
                    }catch (WriteException e){
                        out.println("<html>");
                        out.println("<head>");               
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='TEXTOFALLO'> Se generó el siguiente error: " + e.getMessage() + ".</div>");
                        out.println("</body>");
                        out.println("</html>");
                        e.printStackTrace();
                    }
                }              
            }
        } finally {            
            out.close();
        }
    }
    
    private String obtenerUsuario(String strTipoUsuario, String strUsuario){
        String strNombreUsuario = "";
        String strSQL = null;
        String[] strDatos = null;
        
        if (strTipoUsuario.equals("I")){
            strSQL = "select txtNombre from users.users_grupos_siu where txtCodigo = '" + strUsuario + "'";
            strDatos = GestionSQL.getFila(strSQL, "Users");
            strNombreUsuario = strDatos[0];
        }
        
        if ((strTipoUsuario.equals("U")) || (strTipoUsuario.equals("E")) || (strTipoUsuario.equals("-"))){
            strNombreUsuario = strUsuario;
        }
        
        return strNombreUsuario;
    }

    private void writeHeaderSheet(WritableSheet s) 
    throws WriteException
    {
        /* Creates Label and writes image to one cell of sheet*/      
         /* Format the Font */
        WritableFont wfHeader = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableCellFormat cfHeader = new WritableCellFormat(wfHeader);
        cfHeader.setWrap(true);
        cfHeader.setVerticalAlignment(VerticalAlignment.CENTRE);
        cfHeader.setAlignment(Alignment.CENTRE);
        cfHeader.setBorder( Border.ALL, BorderLineStyle.THIN);
        
        String strHeader = "SEGUIMIENTO A PQRSFD\n";
        strHeader += "Proceso Mejoramiento Continuo\n";
        strHeader += "Administración de la SIU";
        
        Label l = new Label(4,0,strHeader,cfHeader);
        s.addCell(l);
        s.mergeCells(4, 0, 22, 5);    
      
        WritableImage wiSiu = new WritableImage(1, 0, 2, 6, new File("C:/Files/log-udea.png"));  
        s.addImage(wiSiu);
        
        Label imgSiu = new Label(0,0,"",cfHeader);
        s.addCell(imgSiu);
        s.mergeCells(0, 0, 3, 5);
        
        WritableImage wiUdeA = new WritableImage(24, 0, 2, 6, new File("C:/Files/log-siu.png"));  
        s.addImage(wiUdeA);               
        
        Label imgUdeA = new Label(23,0,"",cfHeader);
        s.addCell(imgUdeA);
        s.mergeCells(23, 0, 26, 5);
        
        // Titulos de las columnas.
        
        WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat cf = new WritableCellFormat(wf);
        cf.setWrap(true);
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);
        cf.setAlignment(Alignment.CENTRE);
        cf.setBackground(Colour.LIGHT_GREEN);
        cf.setBorder( Border.ALL, BorderLineStyle.THIN);
        
        Label lblTipo = new Label(0,7,"Tipo (PQRSFD)",cf);
        s.addCell(lblTipo);
        s.mergeCells(0, 7, 1, 8);
        
        Label lblServicio = new Label(2,7,"Nombre del servicio",cf);
        s.addCell(lblServicio);
        s.mergeCells(2, 7, 4, 8);
        
        Label lblEntidad = new Label(5,7,"Entidad o Grupo de Investigación",cf);
        s.addCell(lblEntidad);
        s.mergeCells(5, 7, 7, 8);
        
        Label lblEscritor = new Label(8,7,"Nombre de quien presenta el escrito",cf);
        s.addCell(lblEscritor);
        s.mergeCells(8, 7, 10, 8);
        
        Label lblDescripcion = new Label(11,7,"Descripción de la PQRSFD",cf);
        s.addCell(lblDescripcion);
        s.mergeCells(11, 7, 16, 8);
        
        Label lblFechaRecibo = new Label(17,7,"Fecha de recibo\n(aaaa-mm-dd)",cf);
        s.addCell(lblFechaRecibo);
        s.mergeCells(17, 7, 18, 8);
        
        Label lblFechaRpta = new Label(19,7,"Fecha de respuesta\n(aaaa-mm-dd)",cf);
        s.addCell(lblFechaRpta);
        s.mergeCells(19, 7, 20, 8);
        
        Label lblNroDias = new Label(21,7,"Nro. de días para respuesta",cf);
        s.addCell(lblNroDias);
        s.mergeCells(21, 7, 22, 8);
        
        Label lblRadicado = new Label(23,7,"No. de consecutivo",cf);
        s.addCell(lblRadicado);
        s.mergeCells(23, 7, 24, 8);         
        
        Label lblClasificacion = new Label(25,7,"Clasificación",cf);
        s.addCell(lblClasificacion);
        s.mergeCells(25, 7, 26, 8); 
        
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
