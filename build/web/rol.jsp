<%-- 
    Document   : estado
    Created on : 20/04/2012, 10:48:52 AM
    Author     : Jorge.correa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Conexion.GestionSQL"%>
<!DOCTYPE html>
<% 
    String strAccion = (String) request.getParameter("txtAccion");        
    String strCodigo = (String) request.getParameter("txtCodigo");   
    
    String strSQL = "";
    String[] strDatosRol = null;
    
     if (strAccion == null) {
        response.sendRedirect("cerrar.jsp");
     }else{
         if (strAccion.equals("V")){        
             strSQL = "select r.txtCodigo, r.txtNombre from buzon_roles r where r.txtCodigo = '" + strCodigo + "'";
             strDatosRol = GestionSQL.getFila(strSQL,"Buzon");
        }
     }           
%>
<html>
    <head>
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/Rol.css" />        
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" src="Scripts/JSRol.js"></script>
        <title>Administración: Rol</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div align="center">
            <br><br>
            <% if (strAccion.equals("C")){%>
                <table cellspacing="0" cellpadding="0" width="800px" border="0">
                    <tr>
                        <td class="TITULOFORM">NUEVO REGISTRO DE ROL</td>
                    </tr>                
                    <tr>
                        <td>
                            <form method="POST" id="frmRol" name="frmRol">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmRol">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="C">
                                <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                <table cellspacing="0" cellpadding="5" width="800px" border="0" class="TABLAMAESTRO">
                                    <tr><td style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtCodigo" id="lblCodigo">* Código:</label></td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtCodigo" id="txtCodigo" class="CAMPOFORM" style="width: 230px;">&nbsp;<img src="Images/error.png" id="imgErrorCodigo" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM"><label for="txtNombre" id="lblNombre">* Nombre:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNombre" id="txtNombre" class="CAMPOFORM" style="width: 230px;">&nbsp;<img src="Images/error.png" id="imgErrorNombre" alt="Campo obligatorio" class="IMGERROR">                                               
                                        </td>
                                     </tr>                                     
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr><td colspan="4" class="CELDABOTONFORM"><input type="button" value="Guardar" id="btnGuardar" class="BOTONFORM">&nbsp;&nbsp;<input type="button" value="Limpiar" id="btnLimpiar" class="BOTONFORM"> &nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"></td></tr>
                                    <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                </table>            
            <%}else{%>
                <table cellspacing="0" cellpadding="0" width="800px" border="0">
                    <tr>
                        <td class="TITULOFORM">REGISTRO DE ROL</td>
                    </tr>                
                    <tr>
                        <td>
                            <form method="POST" id="frmRol" name="frmRol">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmRol">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="V">
                                <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                <table cellspacing="0" cellpadding="5" width="800px" border="0" class="TABLAMAESTRO">
                                    <tr><td style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtCodigo" id="lblCodigo">* Código:</label></td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtCodigo" id="txtCodigo" value="<%=strDatosRol[0]%>" class="CAMPOFORM" style="width: 230px;" readOnly>&nbsp;<img src="Images/error.png" id="imgErrorCodigo" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM"><label for="txtNombre" id="lblNombre">* Nombre:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNombre" id="txtNombre" value="<%=strDatosRol[1]%>" class="CAMPOFORM" style="width: 230px;">&nbsp;<img src="Images/error.png" id="imgErrorNombre" alt="Campo obligatorio" class="IMGERROR">                                               
                                        </td>
                                     </tr>                                     
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr><td colspan="4" class="CELDABOTONFORM"><input type="button" value="Guardar" id="btnGuardar" class="BOTONFORM">&nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"></td></tr>
                                    <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                </table>
            <%}%>
            <br>
            <div id="dMensaje">                
            </div>
            <br>
        </div>        
        <jsp:include page="footer.jsp" />	
    </body>
</html>