<%-- 
    Document   : rpta_obs
    Created on : 5/11/2014, 11:28:14 AM
    Author     : jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strSQL = "", strRadicado, strUsuario, strIdObs, strCadena;
    strRadicado = request.getParameter("txtRadicado");
    strIdObs = request.getParameter("txtId");
    strUsuario = (String) session.getAttribute("txtCedula"); 
    
    strSQL = "select p.txtRpta from buzon_pqrs p where p.txtRadicado = '" + strRadicado + "'";
    String[] strRpta = GestionSQL.getFila(strSQL, "Buzon");
    
    strCadena =  strRpta[0].replaceAll(">", "<br>");
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
        <title>Respuesta a observación</title>
    </head>
    <body>
        <div align="center" style="margin-top: 5px;">    
            <input type="hidden" name="txtRadicado" id="txtRadicado" value="<%=strRadicado%>" />
            <input type="hidden" name="txtIdObs" id="txtIdObs" value="<%=strIdObs%>" />
            <table cellspacing="0" cellpadding="0" width="500px" border="0">
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>
                    <td colspan="2" class="TITULOFORM"> RESPUESTA A OBSERVACIÓN #<%=strIdObs%> DE LA SOLICITUD #<%=strRadicado%></td>
                </tr>
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>                    
                    <td colspan="2" class="LABELRPTAOBS">                  
                        Respuesta dada a la solicitud original:
                    </td>
                </tr>
                <tr>
                    <td colspan="2" >
                        <div id="dRptaInicial" class="DIVEDITABLE" contenteditable="false" style="width: 500px;">
                            <%=strCadena%>
                        </div>
                    </td>                    
                </tr>
                <tr>
                    <td colspan="2" style="height: 10px;"></td>
                </tr>
                <tr>
                    <td class="LABELFORM" style="text-align: left;padding: 0px;">
                        ¿Desea responder la observación con la misma respuesta de la solicitud?:&nbsp;<img src="Images/error.png" id="imgRpta" alt="Campo obligatorio" class="IMGERROR">
                    </td>
                    <td class="CELDARADIOFORM" style="font-weight: normal;font-size: 12.5px;vertical-align: middle;text-align: left;padding: 0px;">
                        <input type="radio" name="rdRpta" id="rdSI" value="S" />Si&nbsp;&nbsp;
                        <input type="radio" name="rdRpta" id="rdNO" value="N" />No
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="dRespuesta">
                            <table cellspacing="0" cellpadding="0" width="500px" border="0">
                                <tr>
                                    <td colspan="2" style="height: 10px;"></td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="LABELRPTAOBS" style="text-align: left;">
                                        * Ingrese la respuesta:&nbsp;&nbsp;<img src="Images/error.png" id="imgRptaNew" alt="Campo obligatorio" class="IMGERROR">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div class="DIVEDITABLE" id="dRptaNew" contenteditable="true" style="width: 500px;">                                           
                                        </div>
                                    </td>
                                </tr>  
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="CELDABOTONFORM">
                        <input type="button" name="btnResponder" id="btnResponder" value="Responder" class="BOTONFORM" />&nbsp;&nbsp;
                        <input type="button" name="btnSalir" id="btnSalir" value="Salir" class="BOTONFORM" onclick="javascript:window.close();" />
                    </td>
                </tr>
            </table>
        </div>      
        <div id="dMensaje">            
        </div>
    </body>
</html>
