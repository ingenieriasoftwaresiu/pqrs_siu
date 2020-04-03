<%-- 
    Document   : index
    Created on : 23-ago-2013, 08:09:01
    Author     : Jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strRadicado = request.getParameter("keysearch");
    String strAccion = request.getParameter("action");
    String strSQL = "";
    String[] strDatos = null;
    String[] strDatosObs = null;
    String[] strTieneObs = null;
    
    if ((strRadicado == null) || (strRadicado.equals(""))){
        response.sendRedirect("cerrar.jsp");
    }
    
    strSQL = "select r.txtNombre, pe.txtNombreCompleto, p.dtFechaRpta, p.txtIdEstado from buzon.buzon_pqrs p, buzon.buzon_retroalimentacion r, users.users_personas pe where (p.txtTipoPQRS = r.txtCodigo) and (p.txtNomCargo = pe.txtIdentificacion) and p.txtRadicado = '" + strRadicado +"'";
    strDatos = GestionSQL.getFila(strSQL, "Buzon");

    strSQL = "select o.txtObs from buzon.buzon_obs_x_sol o where o.txtRadicado = '" + strRadicado + "' and o.txtAtendida = 'N'";
    strTieneObs = GestionSQL.getFila(strSQL, "Buzon");
    
    if (strAccion.equals("V")){
        strSQL = "select o.txtObs from buzon.buzon_obs_x_sol o where o.txtIdObs = '" + strRadicado + "'";
        strDatosObs = GestionSQL.getFila(strSQL, "Buzon");
    }  
        
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/Eval_Satisfaccion.css" />
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSComunes.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSEvalSatisfaccion.js"></script>
        <title>Evaluación de satisfacción de respuesta</title>
        <script language="javascript">
                function redirect(){
                    location.href = "principal.jsp?txtCedula=null";
                }
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div style="padding-top: 50px;"></div>
        <%if (strAccion.equals("C")){%>
            <%if (!(strDatos[3].equals("CPU"))){%>
                <%if (strTieneObs != null){%>
                    <br />                    
                    <div align="center">
                        <table cellspacing="0" cellpadding="0" border="0" width="800px">
                            <tr>
                                <td class="MENSAJEESTADOCERRADO" style="text-decoration: underline;">ADVERTENCIA:</td>
                            </tr>
                            <tr>
                                <td class="MENSAJEESTADOCERRADO">
                                    <br>No es posible crear una observación para la solicitud con consecutivo #<%=strRadicado%>, debido a que ya se tiene una observación creada previamente sin respuesta. <br /><br />    
                                </td>
                            </tr>
                            <tr>
                                <td class="TEXTOCIERRE">                              
                                    <input type="button" value="Regresar" class="BOTONFORM" onclick="redirect();"/>
                                </td>
                            </tr>
                        </table>                    
                    </div>
                    <br>
                <%}else{%>
                    <div align="center">
                        <form id="frmObs" name="frmObs">
                            <input type="hidden" id="txtRadicado" name="txtRadicado" value="<%=strRadicado%>" />
                            <input type="hidden" id="txtForm" name="txtForm" value="frmObs" />
                            <input type="hidden" id="txtAccion" name="txtAccion" value="C" />
                            <table border="0" cellspacing="0" cellpadding="0" width="800px" class="TABLAFORM">
                                <tr>
                                    <td class="TITULOFORM" colspan="4">EVALUACIÓN DE SATISFACCIÓN DE RESPUESTA</td>
                                </tr>
                                <tr>
                                    <td class="SUBTITULOFORM" colspan="4">Datos de la solicitud</td>
                                </tr>
                                <tr>
                                    <td class="LABELFORM">Consecutivo:</td>
                                    <td class="CELDACAMPOFORM"><input type="text" name="txtRadicado" id="txtRadicado" value="<%=strRadicado%>" class="CAMPOFORMREAD" style="width: 220px;" readOnly/></td>
                                    <td class="LABELFORM">Tipo de solicitud:</td>
                                    <td class="CELDACAMPOFORM"><input type="text" name="txtTipoSol" id="txtTipoSol" value="<%=strDatos[0]%>" class="CAMPOFORMREAD"   readOnly/></td>
                                </tr>
                                <tr>
                                    <td class="LABELFORM" style="padding-bottom: 10px;">Responsable:</td>
                                    <td class="CELDACAMPOFORM" style="padding-bottom: 10px;"><input type="text" name="txtResponsable" id="txtResponsable" value="<%=strDatos[1]%>" class="CAMPOFORMREAD"  style="width: 220px;"  readOnly/></td>
                                    <td class="LABELFORM" style="padding-bottom: 10px;">Fecha de respuesta:<br>(aaaa-mm-dd)</td>
                                    <td class="CELDACAMPOFORM" style="padding-bottom: 10px;"><input type="text" name="txtFecha" id="txtFecha" value="<%=strDatos[2]%>" class="CAMPOFORMREAD"   readOnly/></td>
                                </tr>
                                <tr>
                                    <td class="SUBTITULOFORM" colspan="4">Evaluación</td>
                                </tr>
                                <tr>
                                    <td class="LABELFORM" colspan="2">* ¿Se considera satisfecho(a) con la respuesta obtenida?:</td>
                                    <td class="CELDARADIOFORM" colspan="2" style="font-weight: normal;font-size: 12.5px;vertical-align: middle;">
                                        <input type="radio" name="rdSatisfaccion" id="rdSI" value="S">Si&nbsp;&nbsp;
                                        <input type="radio" name="rdSatisfaccion" id="rdNO" value="N">No
                                        &nbsp;&nbsp;<img src="Images/error.png" id="imgSatisfaccion" alt="Campo obligatorio" class="IMGERROR">
                                    </td>
                                </tr>                
                                <tr>
                                    <td colspan="4">
                                        <div id="dObs">
                                            <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                                <tr>
                                                    <td class="LABELFORM" style="width: 100px;">* Observación:</td>
                                                    <td colspan="3" style="padding-top: 10px;">
                                                        <div id="txtObservacion" contenteditable="true" class="TEXTOOBS"></div>
                                                        <span class="MSGAVISOOBLG" style="color: #116043;">
                                                            <u>NOTA</u>: Su observación será enviada vía e-mail al responsable de la solicitud.  &nbsp;&nbsp;<img src="Images/error.png" id="imgObservacion" alt="Campo obligatorio" class="IMGERROR">
                                                        </span>                                        
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>                   
                                </tr>                            
                                <tr>
                                    <td colspan="4" class="CELDABOTONFORM">
                                        <input type="button" value="Enviar" id="btnEnviar" class="BOTONFORM"/> &nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="cerrarSesion()">
                                    </td>
                                </tr>
                                <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                            </table>
                        </form>
                    <br>
                        <div id="dMensaje">                
                        </div>
                    </div>
                <%}%>                
            <%}else{ %>
                <br>
                <div align="center">
                    <table cellspacing="0" cellpadding="0" border="0" width="800px">
                        <tr>
                            <td class="MENSAJEESTADOCERRADO" style="text-decoration: underline;">ADVERTENCIA:</td>
                        </tr>
                        <tr>
                            <td class="MENSAJEESTADOCERRADO">
                                <br />No es posible crear una observación para la solicitud con consecutivo #<%=strRadicado%>, debido a que el sistema ha cerrado la solicitud. <br /><br />   
                            </td>
                        </tr>
                        <tr>
                            <td class="TEXTOCIERRE">
                                <input type="button" value="Regresar" class="BOTONFORM" onclick="redirect();"/>
                            </td>
                        </tr>
                    </table>                    
                </div>
                <br>
            <%}%>
        <%}else{%>
            <div align="center">
                    <form id="frmObs" name="frmObs">
                        <input type="hidden" id="txtRadicado" name="txtRadicado" value="<%=strRadicado%>" />
                        <input type="hidden" id="txtForm" name="txtForm" value="frmObs" />
                        <input type="hidden" id="txtAccion" name="txtAccion" value="V" />
                        <table border="0" cellspacing="0" cellpadding="0" width="800px" class="TABLAFORM">
                            <tr>
                                <td class="TITULOFORM" colspan="4">EVALUACIÓN DE SATISFACCIÓN DE RESPUESTA</td>
                            </tr>
                            <tr>
                                <td class="SUBTITULOFORM" colspan="4">Datos de la solicitud</td>
                            </tr>
                            <tr>
                                <td class="LABELFORM">Consecutivo:</td>
                                <td class="CELDACAMPOFORM"><input type="text" name="txtRadicado" id="txtRadicado" value="<%=strRadicado%>" class="CAMPOFORMREAD" style="width: 220px;" readOnly/></td>
                                <td class="LABELFORM">Tipo de solicitud:</td>
                                <td class="CELDACAMPOFORM"><input type="text" name="txtTipoSol" id="txtTipoSol" value="<%=strDatos[0]%>" class="CAMPOFORMREAD"   readOnly/></td>
                            </tr>
                            <tr>
                                <td class="LABELFORM" style="padding-bottom: 10px;">Responsable:</td>
                                <td class="CELDACAMPOFORM" style="padding-bottom: 10px;"><input type="text" name="txtResponsable" id="txtResponsable" value="<%=strDatos[1]%>" class="CAMPOFORMREAD"  style="width: 220px;"  readOnly/></td>
                                <td class="LABELFORM" style="padding-bottom: 10px;">Fecha de respuesta:<br>(aaaa-mm-dd)</td>
                                <td class="CELDACAMPOFORM" style="padding-bottom: 10px;"><input type="text" name="txtFecha" id="txtFecha" value="<%=strDatos[2]%>" class="CAMPOFORMREAD"   readOnly/></td>
                            </tr>
                            <tr>
                                <td class="SUBTITULOFORM" colspan="4">Evaluación</td>
                            </tr>
                            <tr>
                                <td class="LABELFORM" colspan="2">¿Se considera satisfecho con la respuesta obtenida?:</td>
                                <td class="CELDARADIOFORM" colspan="2" style="font-weight: normal;font-size: 12.5px;vertical-align: middle;">
                                    <input type="radio" name="rdSatisfaccion" id="rdSI" value="S" >Si&nbsp;&nbsp;
                                    <input type="radio" name="rdSatisfaccion" id="rdNO" value="N" checked="">No
                                    &nbsp;&nbsp;<img src="Images/error.png" id="imgSatisfaccion" alt="Campo obligatorio" class="IMGERROR">
                                </td>
                            </tr>                
                            <tr>
                                <td colspan="4">
                                    <div id="dObs">
                                        <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                            <tr>
                                                <td class="LABELFORM" style="width: 100px;">Observación:</td>
                                                <td colspan="3" style="padding-top: 10px;">
                                                    <div id="txtObservacion" class="TEXTOOBS"></div>                
                                                    <script type="text/javascript">
                                                        $("#txtObservacion").text("<%=strDatosObs[0]%>");
                                                </script>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>                   
                            </tr>                            
                            <tr>
                                <td colspan="4" class="CELDABOTONFORM">
                                    <input type="button" value="Enviar" id="btnEnviar" class="BOTONFORM"/> &nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="cerrarSesion()">
                                </td>
                            </tr>
                            <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                        </table>
                    </form>
                <br>
                    <div id="dMensaje">                
                    </div>
                </div>
        <%}%>          
        <div style="padding-top: 30px;"></div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
