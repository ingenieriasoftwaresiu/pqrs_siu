<%-- 
    Document   : rpta_obs
    Created on : 5/11/2014, 11:28:14 AM
    Author     : jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strSQL = "", strRadicado, strUsuario, strIdObs, strObsUsuario, strRpta;
    strRadicado = request.getParameter("txtRadicado");
    strIdObs = request.getParameter("txtId");
    strUsuario = (String) session.getAttribute("txtCedula"); 
    
    strSQL = "SELECT * from buzon_obs_x_sol o where o.txtIdObs = '" + strIdObs + "' and o.txtRadicado = '" + strRadicado + "'";
    String[] strObs = GestionSQL.getFila(strSQL, "Buzon");
    
    strObsUsuario = strObs[3].replaceAll(">", "<br>");
    strRpta = strObs[6].replaceAll(">", "<br>");
 %>
<html>
    <head>
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link rel="stylesheet" type="text/css" href="Styles/PQRS.css" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" src="Scripts/JSComunes.js"></script>   
        <script type="text/javascript" src="Scripts/JSRptaObs.js"></script>
        <title>Detalle de observación</title>
    </head>
    <body>
        <div align="center" style="margin-top: 5px;">    
            <input type="hidden" name="txtRadicado" id="txtRadicado" value="<%=strRadicado%>" />
            <input type="hidden" name="txtIdObs" id="txtIdObs" value="<%=strIdObs%>" />
            <table cellspacing="0" cellpadding="0" width="500px" border="0">
                <tr>
                    <td colspan="2" style="height: 5px;"></td>
                </tr>
                <tr>
                    <td colspan="2" class="TITULOFORM"> DETALLE DE OBSERVACIÓN DE LA SOLICITUD #<%=strRadicado%></td>
                </tr>
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>                    
                    <td colspan="2" class="LABELRPTAOBS">                  
                        Observación enviada por el usuario:
                    </td>
                </tr>         
                <tr>
                    <td colspan="2" >
                        <div id="dRptaInicial" class="DIVEDITABLE" contenteditable="false" style="width: 500px;">
                            <%=strObsUsuario%>
                        </div>
                    </td>                    
                </tr>
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>
                    <td colspan="2" class="LABELRPTAOBS">                  
                        Respuesta a la observación:
                    </td>
                </tr>                
                <tr>
                    <td colspan="2">
                        <div class="DIVEDITABLE" id="dRptaNew" contenteditable="false" style="width: 500px;">                 
                            <%=strRpta%>
                        </div>
                    </td>                    
                </tr>
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>
                    <td class="LABELRPTAOBS">Fecha de creación:&nbsp;(aaaa-mm-dd)</td>
                    <td>
                        <input type="text" id="dtfechaCreacion" name="dtfechaCreacion" value="<%=strObs[2]%>" readonly="true" class="CAMPOFORM" />
                    </td>
                </tr>       
                <tr>
                    <td class="LABELRPTAOBS">Fecha de respuesta:&nbsp;(aaaa-mm-dd)</td>
                    <td>
                        <input type="text" id="dtfechaRpta" name="dtfechaRpta" value="<%=strObs[5]%>" readonly="true" class="CAMPOFORM" />
                    </td>
                </tr>
               <tr>
                    <td colspan="2" class="CELDABOTONFORM">             
                        <input type="button" name="btnSalir" id="btnSalir" value="Salir" class="BOTONFORM" onclick="javascript:window.close();" />
                    </td>
                </tr>
            </table>
        </div>      
        <div id="dMensaje">            
        </div>
    </body>
</html>
