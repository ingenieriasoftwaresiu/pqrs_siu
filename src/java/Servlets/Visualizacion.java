/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Conexion.GestionSQL;
import Negocio.Comunes;
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

/**
 *
 * @author jorge.correa
 */
public class Visualizacion extends HttpServlet {

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
            
            String strUsuario = "";
            String strEsAsistente = "S";
            String strEsAdmin = "N";
            
            HttpSession session = request.getSession(true);            
            String strAccion = request.getParameter("txtAccion");  
            String strEvento = request.getParameter("txtEvento");            
            strUsuario =  (String) session.getAttribute("txtCedula");
            strEsAsistente = (String) session.getAttribute("strEsAsistente");
            strEsAdmin = (String) session.getAttribute("strEsAdmin");
            Comunes comun = new Comunes();
            
            if (strEsAdmin == null){
                strEsAdmin = "N";
            }
            
            if (strEsAsistente == null){
                strEsAsistente = "S";
            }
                        
            if (strEvento == null){
                strEvento = "";
            } 
            
            int intRegistrosAEmpezar;
            int intPaginaActual;
            int intRegistrosAMostrar;
            String[] strDatosGenerales = null;          
                        
            String strSQL = "";
            String strTitulo = "";
            String strHTML = "";         
            String strTipoConsulta = "";
            String strCabecera = "";
            String strBusqueda = "";
            String strSQLTotal = "";
            String strMsgError = "";  
            String strResult = null;
            String strCriterio = "";
            String strClave = "";
            
            strSQL = "select g.txtNroPaginas from buzon_generales g where g.txtCodigo = 'frmGeneral'";
            strDatosGenerales = GestionSQL.getFila(strSQL,"Buzon"); ;            
            intRegistrosAMostrar = Integer.parseInt(strDatosGenerales[0]);          
            
            if (request.getParameter("txtPagina") != null){
                intPaginaActual = Integer.parseInt(request.getParameter("txtPagina"));                
                intRegistrosAEmpezar = (intPaginaActual - 1) * intRegistrosAMostrar;                
            }else{
                intRegistrosAEmpezar = 0;
                intPaginaActual = 1;
            }
                                
            //Eliminación
            
            if (strEvento.equals("Eliminar")){                        
                String strMensaje = "";
                String strCodigo = request.getParameter("txtCodigo");                    
                                
                // Estado.
               
                if (strAccion.equals("estado")){              
                    
                    // Validar asociación con otros registros.
                    strResult = validarRegistro(strCodigo,"estado");                                        

                    if (strResult == null){          
                            strSQL = "delete from buzon_estados where txtCodigo = '" + strCodigo + "'";                             
                    }                   
                }     
                
                if (strAccion.equals("retroalimentacion")){              
                    
                    // Validar asociación con otros registros.
                    strResult = validarRegistro(strCodigo,"retroalimentacion");                                        

                    if (strResult == null){          
                            strSQL = "delete from buzon_retroalimentacion where txtCodigo = '" + strCodigo + "'";                             
                    }                   
                }
                
                 if (strAccion.equals("rol")){             
                     
                    // Validar asociación con otros registros.
                    strResult = validarRegistro(strCodigo,"rol");                          

                    if (strResult == null){          
                            strSQL = "delete from buzon_roles where txtCodigo = '" + strCodigo + "'";                                    
                    }                   
                }
                 
                 if (strAccion.equals("rolXpersona")){                     
                     
                        String[] strDatos = strCodigo.split(",");
                     
                        strSQL = "delete from buzon_rolesxusuario where txtRol = '" + strDatos[0] + "' and txtUsuario = '" + strDatos[1] + "'";                                          
                }                
                 
                if ((strAccion.equals("PQRSTipo")) || (strAccion.equals("PQRSEstado")) || (strAccion.equals("PQRSServicio")) || (strAccion.equals("PQRSEscritor")) || (strAccion.equals("PQRSFechaC")) || 
                         (strAccion.equals("PQRSConsecutivo"))){             
                    
                    strSQL = "delete from buzon_pqrs where txtRadicado = '" + strCodigo + "'";                                  
                                
                }
                                
                if (!strSQL.equals("")){
                    strMensaje = GestionSQL.ejecuta(strSQL,"Buzon");                             
                }
            }            
            
            // Visualizar Log cambio de responsable.
             
            if (strAccion.equals("LgResp")){       
                strTipoConsulta = "LOGRESP";
                strTitulo = "LOG DEL CAMBIO DE RESPONSABLE";
                strCabecera = "<td colspan='5' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE REGISTRO";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo de solicitud</option>";
                strBusqueda = strBusqueda + "<option value='F'>Rango fecha [FIni / FFin]</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                strSQL = "select l.txtRadicado, l.dtFecha, l.txtActor, l.txtResponsableIni, l.txtResponsableFin from buzon.buzon_log_cambio_responsable l order by l.dtFecha, l.txtActor LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon.buzon_log_cambio_responsable";            
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo de la solicitud</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha del cambio (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Autor del cambio</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Responsable inicial</td>\n";               
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Responsable final</td>";                
            }
            
            // Visualizar Estados.
             
            if (strAccion.equals("estado")){       
                strTipoConsulta = "MAESTROS";
                strTitulo = "CONSULTA DE ESTADOS";
                strCabecera = "<td colspan='5' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE ESTADOS";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Código</option>";
                strBusqueda = strBusqueda + "<option value='N'>Nombre</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                strSQL = "select e.txtCodigo as Id, e.txtNombre as Nombre from buzon_estados e ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon_estados";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Código</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre</td>\n";               
                strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";                
            } 
            
            // Visualizar Retroalimentaciones.
             
            if (strAccion.equals("retroalimentacion")){       
                strTipoConsulta = "APPS";
                strTitulo = "CONSULTA DE TIPOS DE SOLICITUD";
                strCabecera = "<td colspan='5' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE TIPO DE SOLICITUD";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Código</option>";
                strBusqueda = strBusqueda + "<option value='N'>Nombre</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                strSQL = "select r.txtCodigo, r.txtNombre, r.txtTiempoRpta, r.txtReqRpta from buzon_retroalimentacion r ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon_retroalimentacion";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Código</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre</td>\n";      
                strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Requiere respuesta?</td>\n";    
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tiempo de respuesta</td>\n";                
                strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";                
            }             
                    
            // Visualizar Rol.
             
            if (strAccion.equals("rol")){       
                strTipoConsulta = "MAESTROS";
                strTitulo = "CONSULTA DE ROLES";
                strCabecera = "<td colspan='5' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE ROLES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Código</option>";
                strBusqueda = strBusqueda + "<option value='N'>Nombre</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                strSQL = "select r.txtCodigo as Id, r.txtNombre as Nombre from buzon_roles r ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon_roles";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Código</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre</td>\n";               
                strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";                
            } 
            
            // Visualizar Rol por persona.
             
            if (strAccion.equals("rolXpersona")){       
                strTipoConsulta = "MAESTROS";
                strTitulo = "CONSULTA DE ROLES POR USUARIO";
                strCabecera = "<td colspan='5' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE ROL POR USUARIO";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Nombre del rol</option>";
                strBusqueda = strBusqueda + "<option value='N'>Nombre del usuario</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                strSQL = "select r.txtRol as Id, r.txtUsuario as Nombre from buzon_rolesxusuario r ORDER BY r.txtRol LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon_rolesxusuario";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del rol</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n";               
                strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";                
            }            
            
            // Visualizar PQRS por Tipo de Solicitud   
            
            if (strAccion.equals("PQRSTipo")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR TIPO DE SOLICITUD";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";                                                                     
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){   
                    strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{
                    strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }       
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";        
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n"; 
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";  
                }
            } 
            
            // Visualizar PQRS por Estado Actual  
            
            if (strAccion.equals("PQRSEstado")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR ESTADO ACTUAL";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{
                    strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }         
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";  
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";        
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n";      
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";  
                }
            }
            
            // Visualizar PQRS por Nombre del Servicio. 
            
            if (strAccion.equals("PQRSServicio")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR NOMBRE DEL SERVICIO";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{	
                    strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }               
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";     
                }
            }            
                    
            // Visualizar PQRS por Nombre del Usuario. 
            
            if (strAccion.equals("PQRSEscritor")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR NOMBRE DEL USUARIO";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{	
                    strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }          
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                    
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";   
                }
            }
                                
            // Visualizar PQRS por Fecha de Creación. 
            
            if (strAccion.equals("PQRSFechaC")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR FECHA DE CREACIÓN";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{	
                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }             
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";  
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                                       
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";   
                }
            }            
                    
            // Visualizar PQRS por Consecutivo. 
            
            if (strAccion.equals("PQRSConsecutivo")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR CONSECUTIVO";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY CAST(p.txtRadicado AS SIGNED), p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo)";
                }else{	
                    strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') ORDER BY CAST(p.txtRadicado AS SIGNED), p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "')";
                }                      
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";   
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";   
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";    
                }
            }
            
            if (strAccion.equals("PQRSUserConsecutivo")){              
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR CONSECUTIVO";
                strCabecera = "<td colspan='6' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)' disabled='disabled'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C' selected>Consecutivo</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Nro. consecutivo:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";               
                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY CAST(p.txtRadicado AS SIGNED), p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre LIMIT 1,1";     
                strSQLTotal = "select count(*) from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";                    
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";   
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                     
            }
            
            if (strAccion.equals("PQRSUserId")){              
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES POR NÚMERO DE IDENTIFICACIÓN";
                strCabecera = "<td colspan='6' class='TITULOMENU'>" + strTitulo + "</td>";
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)' disabled='disabled'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='I' selected>Identificación</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Nro. identificación:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";               
                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon.buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) ORDER BY CAST(p.txtRadicado AS SIGNED), p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre LIMIT 1,1";     
                strSQLTotal = "select count(*) from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";                    
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";   
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                     
            }
            
            // Visualizar PQRS Pendientes. 
            
            if (strAccion.equals("SolPend")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES PENDIENTES";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";
                if (strEsAsistente.equals("S")){  
                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N'))";
                }else{	
                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "'";
                }             
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";  
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                                       
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";   
                }
            }
            
            // Visualizar PQRS Enviadas. 
            
            if (strAccion.equals("SolEnv")){       
                strTipoConsulta = "PQRS";
                strTitulo = "CONSULTA DE SOLICITUDES ENVIADAS";
                if (strEsAdmin.equals("N")){
                    strCabecera = "<td colspan='7' class='TITULOMENU'>" + strTitulo + "</td>";
                }else{
                    strCabecera = "<td colspan='8' class='TITULOMENU'>" + strTitulo + "</td>";
                }
                strBusqueda = strBusqueda + "<form id='frmBusqueda' name='frmBusqueda' method='POST' action='#'>";
                strBusqueda = strBusqueda + "<table cellpadding='0' cellspacing='0' border='0' width='929px' class='TABLAFORM'>";
                strBusqueda = strBusqueda + "<tr>";
                strBusqueda = strBusqueda + "<td class ='TITULOBUSQUEDA'>";
                strBusqueda = strBusqueda + "BÚSQUEDA DE SOLICITUDES";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Criterio:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<select name='txtCriterio' id='txtCriterio' class='CAMPOFORM' style='height: 20px;' OnKeyPress='return disableKeyPress(event)'>&nbsp;";
                strBusqueda = strBusqueda + "<option value='S'>Seleccione una opción</option>";
                strBusqueda = strBusqueda + "<option value='C'>Consecutivo</option>";
                strBusqueda = strBusqueda + "<option value='E'>Estado actual</option>";
                strBusqueda = strBusqueda + "<option value='F'>Fecha de creación</option>"; 
                strBusqueda = strBusqueda + "<option value='R'>Nombre del usuario</option>"; 
                strBusqueda = strBusqueda + "<option value='V'>Nombre del servicio</option>";   
                strBusqueda = strBusqueda + "<option value='T'>Tipo de solicitud</option>";
                strBusqueda = strBusqueda + "</select>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class ='LABELFORM'>";
                strBusqueda = strBusqueda + "Palabra clave:&nbsp;";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDACAMPOFORM'>";
                strBusqueda = strBusqueda + "<input type='text' name='txtClave' id='txtClave' class='CAMPOFORM' OnKeyPress='return disableKeyPress(event)'>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "<td class='CELDAIMAGEN'>";
                strBusqueda = strBusqueda + "<a href=\"#\" onclick=\"buscarRegistro('" + strAccion + "')\"><img src='Images/lupa.gif'id='Buscar' class='IMAGENBUSQUEDA'></a>";
                strBusqueda = strBusqueda + "</td>";
                strBusqueda = strBusqueda + "</tr>";
                strBusqueda = strBusqueda + "</table>";
                strBusqueda = strBusqueda + "</form>";	
                strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                strSQLTotal = "select count(*) from buzon_pqrs p where p.txtNumId = '" + strUsuario + "'";
                strHTML = strHTML + "<td class='SUBTITULOMENU'></td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Fecha de creación (aaaa-mm-dd)</td>\n";
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del usuario</td>\n"; 
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Nombre del servicio</td>\n";     
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Estado actual</td>\n";  
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Tipo de solicitud</td>\n";                                       
                strHTML = strHTML + "<td class='SUBTITULOMENU'>Consecutivo</td>\n";     
                if (strEsAdmin.equals("S")){
                    strHTML = strHTML + "<td class='SUBTITULOMENU'>¿Eliminar?</td>";   
                }
            }
                                                       
            if (strEvento != null){                  
                if (strEvento.equals("busqueda")){                    
                    strCriterio = request.getParameter("txtCriterio");
                    strClave = request.getParameter("txtClave");              
                    
                    if (strAccion.equals("LgResp")){       
                        if (strCriterio.equals("C")){
                            strSQL = "select tbl.txtRadicado, tbl.dtFecha, tbl.txtActor, tbl.txtResponsableIni, tbl.txtResponsableFin from buzon.buzon_log_cambio_responsable tbl where tbl.txtRadicado = '" + strClave + "' order by tbl.dtFecha, tbl.txtActor LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                            strSQLTotal = "select count(*) from buzon.buzon_log_cambio_responsable tbl where tbl.txtRadicado = '" + strClave + "'";
                        }
                        
                        if (strCriterio.equals("F")){
                            String strFeIni, strFeFin;
                            String[] strTemp = strClave.split("/");
                            
                            strFeIni = strTemp[0].trim();
                            strFeFin = strTemp[1].trim();
                                                        
                            strSQL = "select tbl.txtRadicado, tbl.dtFecha, tbl.txtActor, tbl.txtResponsableIni, tbl.txtResponsableFin from buzon.buzon_log_cambio_responsable tbl where (tbl.dtFecha BETWEEN '" + strFeIni + "' and '" + strFeFin + "') order by tbl.dtFecha, tbl.txtActor LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;     
                            strSQLTotal = "select count(*) from buzon.buzon_log_cambio_responsable tbl where (tbl.dtFecha BETWEEN '" + strFeIni + "' and '" + strFeFin + "')";
                        }                        
                    }
                                        
                    if (strAccion.equals("estado")){
                        // Clave = Código.
                        if (strCriterio.equals("C")){
                            strSQL = "select tbl.txtCodigo as Id, tbl.txtNombre as Nombre from buzon_estados tbl where tbl.txtCodigo = '" + strClave + "' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_estados tbl where tbl.txtCodigo = '" + strClave + "'";
                        }
                        
                         // Clave = Nombre.
                        if (strCriterio.equals("N")){
                            strSQL = "select tbl.txtCodigo as Id, tbl.txtNombre as Nombre from buzon_estados tbl where tbl.txtNombre like '%" + strClave + "%' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_estados tbl where tbl.txtNombre like '%" + strClave + "%'";
                        }  
                    }  
                    
                    if (strAccion.equals("retroalimentacion")){
                        // Clave = Código.
                        if (strCriterio.equals("C")){
                            strSQL = "select tbl.txtCodigo, tbl.txtNombre, tbl.txtTiempoRpta from buzon_retroalimentacion tbl where tbl.txtCodigo = '" + strClave + "' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_retroalimentacion tbl where tbl.txtCodigo = '" + strClave + "'";
                        }
                        
                         // Clave = Nombre.
                        if (strCriterio.equals("N")){
                            strSQL = "select tbl.txtCodigo, tbl.txtNombre, tbl.txtTiempoRpta from buzon_retroalimentacion tbl where tbl.txtNombre like '%" + strClave + "%' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_retroalimentacion tbl where tbl.txtNombre like '%" + strClave + "%'";
                        }  
                    }
                    
                    if (strAccion.equals("rol")){
                        // Clave = Código.
                        if (strCriterio.equals("C")){
                            strSQL = "select tbl.txtCodigo as Id, tbl.txtNombre as Nombre from buzon_roles tbl where tbl.txtCodigo = '" + strClave + "' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_roles tbl where tbl.txtCodigo = '" + strClave + "'";
                        }
                        
                         // Clave = Nombre.
                        if (strCriterio.equals("N")){
                            strSQL = "select tbl.txtCodigo as Id, tbl.txtNombre as Nombre from buzon_roles tbl where tbl.txtNombre like '%" + strClave + "%' ORDER BY tbl.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon_roles tbl where tbl.txtNombre like '%" + strClave + "%'";
                        }  
                    }
                                                           
                    if (strAccion.equals("rolXpersona")){                        
                        // Clave = Código.
                        if (strCriterio.equals("C")){
                            strSQL = "select r.txtCodigo as Id, p.txtIdentificacion as Nombre from buzon.buzon_roles r, buzon.buzon_rolesxusuario rxu, users.users_personas p where (r.txtCodigo = rxu.txtRol) and (p.txtIdentificacion = rxu.txtUsuario) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon.buzon_roles r, buzon.buzon_rolesxusuario rxu, users.users_personas p where (r.txtCodigo = rxu.txtRol) and (p.txtIdentificacion = rxu.txtUsuario) and r.txtNombre like '%" + strClave + "%'";
                        }
                        
                         // Clave = Nombre.
                        if (strCriterio.equals("N")){
                            strSQL = "select r.txtCodigo as Id, p.txtIdentificacion as Nombre from buzon.buzon_roles r, buzon.buzon_rolesxusuario rxu, users.users_personas p where (r.txtCodigo = rxu.txtRol) and (p.txtIdentificacion = rxu.txtUsuario) and p.txtNombreCompleto like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                            strSQLTotal = "select count(*) from buzon.buzon_roles r, buzon.buzon_rolesxusuario rxu, users.users_personas p where (r.txtCodigo = rxu.txtRol) and (p.txtIdentificacion = rxu.txtUsuario) and p.txtNombreCompleto like '%" + strClave + "%'";
                        }                         
                    }
                    
                    if (strAccion.equals("PQRSTipo")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }else{
                                strSQL = "select r.txtNombre, s.txtNombre, p.txtNombreUser, e.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }                            
                        }
                    }
                    
                    if (strAccion.equals("PQRSEstado")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                            }else{
                                 strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                            }                           
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                            }else{
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                            }else{
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "'";
                            }else{
                                strSQL = "select e.txtNombre, r.txtNombre, s.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }                            
                        }
                    }
                    
                    if (strAccion.equals("PQRSServicio")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "'";
                            }else{
                                strSQL = "select s.txtNombre, e.txtNombre, r.txtNombre, p.txtNombreUser, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }                            
                        }
                    }
                    
                    if (strAccion.equals("PQRSEscritor")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "'";
                            }else{
                                 strSQL = "select p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.dtFechaCreacion, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }                           
                        }
                    }
                    
                    if (strAccion.equals("PQRSFechaC")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                             if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                             }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                             }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                             }                            
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                             }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                             }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "'";
                             }else{
                                 strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                             }                            
                        }
                    }
                    
                   if (strAccion.equals("PQRSUserConsecutivo")){
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){            
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtRadicado = '" + strClave +"' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon_pqrs p where p.txtRadicado = '" + strClave +"'";
                        }
                    }
                   
                   if (strAccion.equals("PQRSUserId")){
                        // Clave = Consecutivo.
                        if (strCriterio.equals("I")){            
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strClave +"' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon_pqrs p where p.txtNumId = '" + strClave +"'";
                        }
                    }                   
                    
                    if (strAccion.equals("PQRSConsecutivo")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and r.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%' ORDER BY r.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and r.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and e.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%' ORDER BY e.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and e.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and s.txtNombre like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%' ORDER BY s.txtNombre LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and s.txtNombre like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtNombreUser like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%' ORDER BY p.txtNombreUser LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtNombreUser like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.dtFechaCreacion like '%" + strClave + "%'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%' ORDER BY p.dtFechaCreacion LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.dtFechaCreacion like '%" + strClave + "%'";
                            }                            
                        }
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                            if (strEsAsistente.equals("S")){
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and p.txtRadicado = '" + strClave + "'";
                            }else{
                                strSQL = "select p.txtRadicado, p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "' ORDER BY p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_servicios s, buzon_estados e where (p.txtTipoPQRS = r.txtCodigo) and (p.txtServicio = s.txtCodigo) and (p.txtIdEstado = e.txtCodigo) and (p.txtNomCargo = '" + strUsuario + "') and p.txtRadicado = '" + strClave + "'";
                            }                            
                        }
                    }
                    
                    if (strAccion.equals("SolPend")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){
                                if (strEsAsistente.equals("S")){		 
                                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and r.txtNombre like '%" + strClave + "%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;		 
                                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and r.txtNombre like '%" + strClave + "%'";
                                }else{
                                        strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and r.txtNombre like '%" + strClave + "%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                        strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and r.txtNombre like '%" + strClave + "%'";
                                }                            
	}
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                                if (strEsAsistente.equals("S")){
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and e.txtNombre like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and e.txtNombre like '%" + strClave +"%'";
                                    }else{
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and e.txtNombre like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and e.txtNombre like '%" + strClave +"%'";
                                    }                            
	}
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                                if (strEsAsistente.equals("S")){
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and s.txtNombre like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and s.txtNombre like '%" + strClave +"%'";
                                    }else{
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and s.txtNombre like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and s.txtNombre like '%" + strClave +"%'";
                                    }                            
	}
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                                if (strEsAsistente.equals("S")){
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNombreUser like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNombreUser like '%" + strClave +"%'";
                                    }else{
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.txtNombreUser like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.txtNombreUser like '%" + strClave +"%'";
                                    }                            
	}
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){
                                if (strEsAsistente.equals("S")){
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.dtFechaCreacion like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.dtFechaCreacion like '%" + strClave +"%'";
                                    }else{
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.dtFechaCreacion like '%" + strClave +"%' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.dtFechaCreacion like '%" + strClave +"%'";
                                    }                            
	}
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){
                                if (strEsAsistente.equals("S")){
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtRadicado = '" + strClave +"' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtRadicado = '" + strClave +"'";
                                    }else{
                                            strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.txtRadicado = '" + strClave +"' ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                            strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where ((p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') or (p.txtIdEstado = 'AT' and o.txtAtendida = 'N')) and p.txtNomCargo = '" + strUsuario + "' and p.txtRadicado = '" + strClave +"'";
                                    }                            
	}
                    }
                    
                    if (strAccion.equals("SolEnv")){   
                        // Clave = Tipo de PQRS.
                        if (strCriterio.equals("T")){                             
                                strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and r.txtNombre like '%" + strClave + "%' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and r.txtNombre like '%" + strClave + "%' GROUP BY p.txtRadicado";
                        }
                        
                        // Clave = Estado actual.
                        if (strCriterio.equals("E")){
                                strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and e.txtNombre like '%" + strClave +"%' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and e.txtNombre like '%" + strClave +"%' GROUP BY p.txtRadicado";
	}
                        
                         // Clave = Nombre del servicio.
                        if (strCriterio.equals("V")){
                                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and s.txtNombre like '%" + strClave +"%' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and s.txtNombre like '%" + strClave +"%' GROUP BY p.txtRadicado";
	}
                        
                        // Clave = Nombre del escritor.
                        if (strCriterio.equals("R")){
                                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.txtNombreUser like '%" + strClave +"%' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.txtNombreUser like '%" + strClave +"%' GROUP BY p.txtRadicado";
	}
                        
                        // Clave = Fecha de creación.
                        if (strCriterio.equals("F")){  
                                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.dtFechaCreacion like '%" + strClave +"%' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.dtFechaCreacion like '%" + strClave +"%' GROUP BY p.txtRadicado";
   	}
                        
                        // Clave = Consecutivo.
                        if (strCriterio.equals("C")){              
                                    strSQL = "select p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado, p.txtIdEstado, p.dtFechaRpta, p.txtTipoPQRS from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.txtRadicado = '" + strClave +"' GROUP BY p.txtRadicado ORDER BY p.dtFechaCreacion, p.txtNombreUser, s.txtNombre, e.txtNombre, r.txtNombre, p.txtRadicado LIMIT " + intRegistrosAEmpezar + "," + intRegistrosAMostrar;
                                    strSQLTotal = "select count(*) from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) INNER JOIN users.users_servicios s on (p.txtServicio = s.txtCodigo) INNER JOIN buzon.buzon_estados e on (p.txtIdEstado = e.txtCodigo) LEFT JOIN buzon_obs_x_sol o on (p.txtRadicado = o.txtRadicado) where p.txtNumId = '" + strUsuario + "' and p.txtRadicado = '" + strClave +"' GROUP BY p.txtRadicado";
                       }
                    }
                }               
            }                
                        
            Vector arrDatos = GestionSQL.consultaSQL(strSQL,"Buzon",strTipoConsulta);
            
            if (arrDatos == null){            
                out.println("<html>");
                out.println("<head>");                
                out.println("</head>");
                out.println("<body OnKeyPress='return disableKeyPress(event)'>");
                out.println("<div class='TEXTOFALLO'>");
                out.println("No se encontraron registros para visualizar.");
                out.println("</div>");
                if (strEvento.equals("busqueda")){
                    out.println("<br>");
                    out.println("<span class='TEXTOEXITO'>");
                    out.println("<a href='#' style='text-decoration:underline;' onclick=\"AJAX('POST','Visualizacion','txtAccion=" + strAccion + "','dMostrar');\">Regresar</a>");
                    out.println("</span>");
                }                
                out.println("</body>");
                out.println("</html>");
            }else{                 
                
                String strTablaRefresh = "";
                
                strTablaRefresh += "<table cellpadding='3' cellspacing='0' border='0' width='929px'>\n";
                strTablaRefresh += "<tr>\n";
                strTablaRefresh += "<td style='text-align:left; vertical-align: middle;width:30px;'>\n";
                strTablaRefresh += "<img src='Images/Refresh.png' width='30px' height='29px' border='0' onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "','dMostrar');\">\n";
                strTablaRefresh += "</td>\n";
                strTablaRefresh += "<td class='TEXTOREFRESH'>\n";
                strTablaRefresh += "<a href='#' onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "','dMostrar');\">Actualizar datos</a>\n";
                strTablaRefresh += "</td>\n";
                strTablaRefresh += "</tr>\n";
                strTablaRefresh += "</table>\n";
                
                out.println("<html>");
                out.println("<head>");                
                out.println("</head>");
                out.println("<body OnKeyPress='return disableKeyPress(event)'>");
                out.println(strBusqueda);
                out.println(strTablaRefresh);    
                out.println("<table cellpadding='5' cellspacing='0' border='0' width='929px' class='TABLARESULT'>");
                out.println("<tr>");
                out.println(strCabecera);
                out.println("</tr>");
                out.println("<tr>");
                out.println(strHTML);
                out.println("</tr>");
                
                String[] strTemp = null;                                   
                
                //Obtener los feriados.

                Vector arrFechas = new Vector();
                strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
                arrFechas = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");
                
                // Obtener tiempo de alerta desde la configuración.
                
                strSQL = "select g.txtNroDiasAlerta from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";
                String[] strDatosGral = GestionSQL.getFila(strSQL, "Buzon");
                int lgTiempoConfig = Integer.parseInt(strDatosGral[0]);
                
                //Obtener la fecha actual.
                
                Calendar fechaActual = null;    
                Calendar fechaRecibo = null;
                Calendar fechaRpta = null;
                int lgRestante, lgTiempo;
                String[] strTempFechas = null;
                                               
                for(int i=0;i<arrDatos.size();i++){
                    strTemp = arrDatos.get(i).toString().split(","); 
                    fechaActual = comun.calcularFechaActual();
                    fechaRecibo = null;
                    lgRestante=0;
                    lgTiempo=0;
                    strTempFechas = null;
                    out.println("<tr class='FILARESULT'>");                               
                     
                     if (strAccion.equals("LgResp")){
                         out.println("<td class=\"TEXTORESULT\">" + strTemp[0] + "</td>");
                         out.println("<td class=\"TEXTORESULT\">" + strTemp[1] + "</td>");
                         out.println("<td class=\"TEXTORESULT\">" + getNomPersona(strTemp[2]) + "</td>");
                         out.println("<td class=\"TEXTORESULT\">" + getNomPersona(strTemp[3]) + "</td>");
                         out.println("<td class=\"TEXTORESULT\">" + getNomPersona(strTemp[4]) + "</td>");  
                     }
                    
                     if (strAccion.equals("estado")){
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegEstado('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegEstado('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegEstado('" + strTemp[0] + "')\"></td>");                                            
                     }    
                     
                     if (strAccion.equals("retroalimentacion")){
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegRetroalimentacion('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegRetroalimentacion('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegRetroalimentacion('" + strTemp[0] + "')\">" + validarSiNo(validarVacio(strTemp[3])) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegRetroalimentacion('" + strTemp[0] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegRetroalimentacion('" + strTemp[0] + "')\"></td>");                                          
                     }
                     
                     if (strAccion.equals("rol")){
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegRol('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegRol('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegRol('" + strTemp[0] + "')\"></td>");                           
                     }                     
                             
                     if (strAccion.equals("rolXpersona")){                           
                            String[] strDatos = null;
                            strSQL = "select r.txtNombre from buzon_roles r where r.txtCodigo = '" + strTemp[0]  + "'";
                            strDatos = GestionSQL.getFila(strSQL,"Buzon");                         
                            out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegRolXUsuario('" + strTemp[0] + "," + strTemp[1] +  "')\">" + validarVacio(strDatos[0]) + "</a></td>");

                            strSQL = "select p.txtNombreCompleto from users_personas p where p.txtIdentificacion = '" + strTemp[1]  + "'";
                            strDatos = GestionSQL.getFila(strSQL,"Users");                         
                            out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegRolXUsuario('" + strTemp[0] + "," + strTemp[1] + "')\">" + validarVacio(strDatos[0]) + "</a></td>");
                            out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegRolXPersona('" + strTemp[0] + "," + strTemp[1] + "')\"></td>");                             
                    }
                     
                     // PQRS.
                     int vencida = 0;
                     String strFechaRecibo="";
                     String strRoja = "", strVerde = "", strNaranja = "";
                     
                     if ((strAccion.equals("PQRSTipo")) || (strAccion.equals("PQRSEstado")) || (strAccion.equals("PQRSServicio")) || (strAccion.equals("PQRSEscritor")) || strAccion.equals("PQRSFechaC") || strAccion.equals("PQRSConsecutivo") || strAccion.equals("PQRSUserConsecutivo") || strAccion.equals("PQRSUserId") || strAccion.equals("SolPend") || strAccion.equals("SolEnv")){
                                               
                        if ((strAccion.equals("PQRSTipo")) || (strAccion.equals("PQRSEstado")) || (strAccion.equals("PQRSServicio")) || (strAccion.equals("PQRSEscritor"))){
                            strFechaRecibo = strTemp[4];
                        }
                    
                        if (strAccion.equals("PQRSFechaC") || strAccion.equals("SolPend") || strAccion.equals("SolEnv")){
                            strFechaRecibo = strTemp[0];
                        }
                        
                        if (strAccion.equals("PQRSConsecutivo") || strAccion.equals("PQRSUserConsecutivo") || strAccion.equals("PQRSUserId")){
                            strFechaRecibo = strTemp[1];
                        }          
                     
                        String strIdEstado = strTemp[6];                    
                        String strFechaRpta= strTemp[7];
                        String strTipoPQRS = strTemp[8];
                        String[] strDatosTipoPQRS = null;                                           

                        fechaRecibo = Calendar.getInstance();                              
                        strTempFechas = strFechaRecibo.split("-");                           
                        fechaRecibo.set(Integer.parseInt(strTempFechas[0]),(Integer.parseInt(strTempFechas[1])-1),Integer.parseInt(strTempFechas[2]));
                        fechaRecibo.set(Calendar.SECOND, 0);
                        fechaRecibo.set(Calendar.MILLISECOND, 0);    
                                                                    
                        strSQL = "select r.txtTiempoRpta from buzon_retroalimentacion r where r.txtCodigo = '" + strTipoPQRS + "'";       
                        strDatosTipoPQRS = GestionSQL.getFila(strSQL,"Buzon");
                        lgTiempo = Integer.parseInt(strDatosTipoPQRS[0]);
                        
                        fechaRpta= Calendar.getInstance();
                        fechaRpta.set(Calendar.SECOND, 0);
                        fechaRpta.set(Calendar.MILLISECOND, 0);    
                        fechaRpta = comun.incrementarDiasHabiles(fechaRecibo, lgTiempo, arrFechas);       
                        
                        lgRestante = (comun.getDiasHabiles(fechaActual, fechaRpta, arrFechas)-1);
                        
                        if (lgTiempo > 0){
                            if ((!strIdEstado.equals("AT")) && (!strIdEstado.equals("CPU"))){
                                
                                if ((lgRestante <= lgTiempoConfig) && (lgRestante > 0)){
                                    vencida = 2;
                                }else{
                                    if (lgRestante < 0){
                                        vencida = 1;
                                    }
                                }                                                                                                
                                
                            }else{
                                
                                fechaRpta = Calendar.getInstance();                              
                                strTempFechas = strFechaRpta.split("-");                           
                                fechaRpta.set(Integer.parseInt(strTempFechas[0]),(Integer.parseInt(strTempFechas[1])-1),Integer.parseInt(strTempFechas[2]));
                                fechaRpta.set(Calendar.SECOND, 0);
                                fechaRpta.set(Calendar.MILLISECOND, 0);
                                
                                fechaRecibo = Calendar.getInstance();                              
                                strTempFechas = strFechaRecibo.split("-");                           
                                fechaRecibo.set(Integer.parseInt(strTempFechas[0]),(Integer.parseInt(strTempFechas[1])-1),Integer.parseInt(strTempFechas[2]));
                                fechaRecibo.set(Calendar.SECOND, 0);
                                fechaRecibo.set(Calendar.MILLISECOND, 0); 

                                lgRestante = (comun.getDiasHabiles(fechaRecibo, fechaRpta, arrFechas)-1);

                                if (lgRestante > lgTiempo){
                                    vencida = 1;
                                }else{
                                    vencida = 0;
                                }                                 
                            }  
                        }else{
                            vencida = 0;
                        }                                          
                        
                        strRoja ="<td class='TEXTORESULT'><img src='Images/Rojo.png' width='11px' height='11px'></td>";
                        strVerde = "<td class='TEXTORESULT'><img src='Images/Verde.png' width='12px' height='12px'></td>";                     
                        strNaranja = "<td class='TEXTORESULT'><img src='Images/Orange.png' width='11px' height='11px'></td>";
                     }                     
                                          
                     if (strAccion.equals("PQRSTipo")){  
                         if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                            
                         }                             
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[5] + "')\"></td>");                            
                         }
                     }
                     
                     if (strAccion.equals("PQRSEstado")){  
                       if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                                      
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                  
                         }
                     }                 
                     if (strAccion.equals("PQRSServicio")){  
                        if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                                    
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                 
                         }   
                     }
                     
                     if (strAccion.equals("PQRSEscritor")){  
                        if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                                     
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                 
                         }  
                     }
                     
                     if (strAccion.equals("PQRSFechaC")){  
                         if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                            
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                 
                         }
                     }
                                            
                     if (strAccion.equals("PQRSConsecutivo")){  
                        if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[0] + "')\"></td>");                                 
                         }
                     }                        
                     
                     if (strAccion.equals("PQRSUserConsecutivo")){                
                         if (!(strClave.equals(""))){                     
                                if (vencida==0){
                                    out.println(strVerde);
                                }else{
                                    if (vencida==1){
                                        out.println(strRoja);
                                    }else{
                                        out.println(strNaranja);
                                    }                     
                                }
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                                //out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");  
                         }
                     }
                     
                      if (strAccion.equals("PQRSUserId")){                
                         if (!(strClave.equals(""))){                     
                                if (vencida==0){
                                    out.println(strVerde);
                                }else{
                                    if (vencida==1){
                                        out.println(strRoja);
                                    }else{
                                        out.println(strNaranja);
                                    }                     
                                }
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                                //out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                                out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[0] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");  
                         }
                     }
                                                               
                     if (strAccion.equals("SolPend")){  
                         if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                            
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                 
                         }
                     }
                     
                     if (strAccion.equals("SolEnv")){  
                         if (vencida==0){
                             out.println(strVerde);
                         }else{
                             if (vencida==1){
                                 out.println(strRoja);
                             }else{
                                 out.println(strNaranja);
                             }                     
                        }                            
                         out.println("<td class=\"TEXTORESULT\"><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[0]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[1]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[2]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[3]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[4]) + "</a></td>");
                         out.println("<td class='TEXTORESULT'><a href=\"#\" onclick=\"abrirRegPQRS('" + strTemp[5] + "')\">" + validarVacio(strTemp[5]) + "</a></td>");
                         if (strEsAdmin.equals("S")){
                             out.println("<td class='TEXTORESULT'><img src='Images/Delete.png' width='11px' height='11px' onclick=\"eliminarRegPQRSXTipo('" + strTemp[4] + "')\"></td>");                                 
                         }
                     }
                     
                    out.println("</tr>");                    
                }     
                out.println("</table>");
                out.println("<br>");
                
                //Paginación.
                
                int  intPagAnt, intPagSig, intPagUlt, intNroRegistros;
                float ftRes;
                                
                String[] strDatos = GestionSQL.getFila(strSQLTotal,"Buzon");
                intNroRegistros = Integer.parseInt(strDatos[0]);                    

                intPagAnt = intPaginaActual - 1;
                intPagSig = intPaginaActual + 1;
                intPagUlt = (intNroRegistros/intRegistrosAMostrar);
                               
                ftRes = (intNroRegistros % intRegistrosAMostrar);
                
                if (ftRes>0){
                    intPagUlt = ((int)intPagUlt) + 1;
                }                
                
                if (strEvento.equals("busqueda")){
                    out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtEvento=busqueda&txtCriterio=" + strCriterio + "&txtClave=" + strClave + "&txtAccion=" + strAccion + "&txtPagina=1','dMostrar');\" class=\"TEXTOPAGINACION\">Primera</a>");
                    if (intPaginaActual > 1){
                        out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtEvento=busqueda&txtCriterio=" + strCriterio + "&txtClave=" + strClave + "&txtAccion=" + strAccion + "&txtPagina=" + intPagAnt + "','dMostrar');\" class=\"TEXTOPAGINACION\">Anterior</a>");
                    }
                    out.println("<span class='TEXTOPAGINACION' style='font-weight: bold;'>Página " + intPaginaActual + "/" + intPagUlt + "</span>");
                    if (intPaginaActual < intPagUlt){                        
                        out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtEvento=busqueda&txtCriterio=" + strCriterio + "&txtClave=" + strClave + "&txtAccion=" + strAccion + "&txtPagina=" + intPagSig + "','dMostrar');\" class=\"TEXTOPAGINACION\">Siguiente</a>");
                    } 
                    out.println("<a href=\"#\"  onclick=\"AJAXC('POST','Visualizacion','txtEvento=busqueda&txtCriterio=" + strCriterio + "&txtClave=" + strClave + "&txtAccion=" + strAccion + "&txtPagina=" + intPagUlt + "','dMostrar');\" class=\"TEXTOPAGINACION\">Última</a>");
                }else{
                    out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "&txtPagina=1','dMostrar');\" class=\"TEXTOPAGINACION\">Primera</a>");
                    if (intPaginaActual > 1){
                        out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "&txtPagina=" + intPagAnt + "','dMostrar');\" class=\"TEXTOPAGINACION\">Anterior</a>");
                    }
                    out.println("<span class='TEXTOPAGINACION' style='font-weight: bold;'>Página " + intPaginaActual + "/" + intPagUlt + "</span>");
                    if (intPaginaActual < intPagUlt){
                        out.println("<a href=\"#\" onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "&txtPagina=" + intPagSig + "','dMostrar');\" class=\"TEXTOPAGINACION\">Siguiente</a>");
                    } 
                    out.println("<a href=\"#\"  onclick=\"AJAXC('POST','Visualizacion','txtAccion=" + strAccion + "&txtPagina=" + intPagUlt + "','dMostrar');\" class=\"TEXTOPAGINACION\">Última</a>");
                }
                
                // Convenciones.
                
                if ((strAccion.equals("PQRSTipo")) || (strAccion.equals("PQRSEstado")) || (strAccion.equals("PQRSServicio")) || (strAccion.equals("PQRSEscritor")) || strAccion.equals("PQRSFechaC") || strAccion.equals("PQRSConsecutivo") || strAccion.equals("PQRSUserConsecutivo") || strAccion.equals("PQRSUserId") || strAccion.equals("SolPend") || strAccion.equals("SolEnv")){
                    
                    out.println("<div align='left'>");
                    out.println("<table cellspacing='0' cellpadding='0' border='0'>");
                    out.println("<tr>");
                    out.println("<td colspan='2' class='TEXTOCONVENCION'>Convenciones:</td>");
                    out.println("<tr>");
                    out.println("<td style='height:4px;'></td>");
                    out.println("</tr>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td><img src='Images/Rojo.png' width='10px' height='10px'></td>");
                    out.println("<td class='CELDACONVENCION'>Solicitud vencida o no cerrada a tiempo.</td>");
                    out.println("</tr>"); 
                    out.println("<td><img src='Images/Orange.png' width='10px' height='10px'></td>");
                    out.println("<td class='CELDACONVENCION'>Solicitud a punto de vencerse.</td>");
                    out.println("</tr>");
                    out.println("<td><img src='Images/Verde.png' width='11px' height='11px'></td>");
                    out.println("<td class='CELDACONVENCION'>Solicitud a tiempo.</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</div>");
                }
                
                if (!(strResult == null)){   
                    out.println("<br><br>");
                    out.println(strResult);                                
                }
                out.println("</body>");
                out.println("</html>");                
            }           
            
        } finally {            
            out.close();
        }
    }    
    
    private String getNomPersona(String strId){
        String[] strNomPersona;
        String strSQL, strNombre;
                
        strSQL = "select p.txtNombreCompleto from users.users_personas p where p.txtIdentificacion = '" + strId + "'";
        strNomPersona = GestionSQL.getFila(strSQL, "Users");
        
        if (strNomPersona == null){
            strNombre = "";
        }else{
            strNombre = strNomPersona[0];
        }
        
        return strNombre;
    }
    
    private String validarVacio(String strValor){
        
        if ((strValor == null) || (strValor.equals("")) || (strValor.equals(" "))){
            return "N/A";
        }else{
            return strValor;
        }            
    }
    
    private String validarSiNo(String strValor){
        
        if (strValor.equals("S")){
            return "Si";
        }else{        
            if (strValor.equals( "N")){
                return "No";
            }else{
                if (strValor.equals("")){
                    return "0";
                }else{
                     return "N/A";
                }
            }
        }
    }
    
    private String validarEstado(String strValor){
        
        if (strValor.equals("A")){
            return "Activo";
        }else{
            return "Inactivo";
        }            
    }
    
    private String validarRegistro(String strClave,String strAccion){
        
        String[] strResult = null;
        String strSQL = null;
        String strMensaje = null;
        
        // Estado.
        
        if (strAccion.equals("estado")){              
            strSQL = "select p.txtRadicado from buzon_pqrs p where p.txtIdEstado = '" + strClave + "'";        
            strMensaje = "<html>\n";
            strMensaje = strMensaje + "<head></head>\n";
            strMensaje = strMensaje + "<body>\n";
            strMensaje = strMensaje + "<span class='TEXTOFALLO'>\n";
            strMensaje = strMensaje + "No se puede eliminar el registro de estado seleccionado debido a que se encuentra asociado con PQRS existentes.\n";
            strMensaje = strMensaje + "</span>\n";    
            strMensaje = strMensaje + "</body>\n";
            strMensaje = strMensaje + "</html>";                       
        } 
        
        // Retroalimentación.
        
        if (strAccion.equals("retroalimentacion")){              
            strSQL = "select p.txtRadicado from buzon_pqrs p where p.txtTipoPQRS = '" + strClave + "'";        
            strMensaje = "<html>\n";
            strMensaje = strMensaje + "<head></head>\n";
            strMensaje = strMensaje + "<body>\n";
            strMensaje = strMensaje + "<span class='TEXTOFALLO'>\n";
            strMensaje = strMensaje + "No se puede eliminar el registro de retroalimentacion seleccionado debido a que se encuentra asociado con PQRS existentes.\n";
            strMensaje = strMensaje + "</span>\n";    
            strMensaje = strMensaje + "</body>\n";
            strMensaje = strMensaje + "</html>";                       
        } 
        
        // Rol.
        
        if (strAccion.equals("rol")){              
            strSQL = "select r.txtRol from buzon_rolesxusuario r where r.txtRol = '" + strClave + "'";        
            strMensaje = "<html>\n";
            strMensaje = strMensaje + "<head></head>\n";
            strMensaje = strMensaje + "<body>\n";
            strMensaje = strMensaje + "<span class='TEXTOFALLO'>\n";
            strMensaje = strMensaje + "No se puede eliminar el registro de rol seleccionado debido a que se encuentra asociado con usuarios existentes.\n";
            strMensaje = strMensaje + "</span>\n";    
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
