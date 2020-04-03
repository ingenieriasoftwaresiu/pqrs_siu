<%-- 
    Document   : estado
    Created on : 20/04/2012, 10:48:52 AM
    Author     : Jorge.correa
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Conexion.GestionSQL"%>
<!DOCTYPE html>
<% 
    String strAccion = (String) request.getParameter("txtAccion");    
    String strCodigo = (String) request.getParameter("txtCodigo");
    String strNombre = (String) session.getAttribute("strNombre");
    String strSQL = "";
    String[] strDatos = null;
    Vector arrCodigos = new Vector();
    Vector arrNombresR = new Vector();
    Vector arrIds = new Vector();
    Vector arrNombresU = new Vector();   ;
    String[] strTemp = null;
    
     if (strAccion == null) {
        response.sendRedirect("cerrar.jsp");
     }else{
               
        strSQL = "select r.txtCodigo as Id, r.txtNombre as Nombre from buzon_roles r order by r.txtNombre";
        Vector arrRoles = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");
                
        for (int i=0;i<arrRoles.size();i++){
            strTemp = arrRoles.get(i).toString().split(",");
            arrCodigos.add(strTemp[0]);
            arrNombresR.add(strTemp[1]);
        }
        
        strSQL = "select p.txtIdentificacion as Id, p.txtNombreCompleto as Nombre from users_personas p ORDER BY p.txtNombreCompleto";
        Vector arrUsuarios = GestionSQL.consultaSQL(strSQL,"Users","MAESTROS");
  
        for (int i=0;i<arrUsuarios.size();i++){
            strTemp = arrUsuarios.get(i).toString().split(",");
            arrIds.add(strTemp[0]);
            arrNombresU.add(strTemp[1]);
        }
        
        if (strAccion.equals("V")){
            strTemp = strCodigo.split(",");
            strSQL = "select r.txtRol, r.txtUsuario from buzon_rolesxusuario r where r.txtRol = '" + strTemp[0] + "' and r.txtUsuario = '" + strTemp[1] + "'";
            strDatos = GestionSQL.getFila(strSQL,"Buzon");
        }
     }           
%>

<html>
    <head>
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/RolXUsuario.css" />        
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" src="Scripts/JSRolXUsuario.js"></script>
        <title>Administración: Rol por usuario</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div align="center">
            <br><br>
            <% if (strAccion.equals("C")){%>
                <table cellspacing="0" cellpadding="0" width="800px" border="0">
                    <tr>
                        <td class="TITULOFORM">NUEVO REGISTRO DE ROL POR USUARIO</td>
                    </tr>                
                    <tr>
                        <td>
                            <form method="POST" id="frmRolXUsuario" name="frmRolXUsuario">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmRolXUsuario">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="C">
                                <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                <table cellspacing="0" cellpadding="5" width="800px" border="0" class="TABLAMAESTRO">
                                    <tr><td style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtRol" id="lblCodigo">* Rol:</label></td>
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtRol" id="txtRol" class="CAMPOFORM" style="width: 230px;">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrCodigos.size();i++){%>
                                                    <option value="<%=arrCodigos.get(i)%>"><%=arrNombresR.get(i)%></option>
                                                <%}%>
                                           </select>
                                            &nbsp;<img src="Images/error.png" id="imgErrorRol" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM"><label for="txtUsuario" id="lblNombre">* Usuario:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtUsuario" id="txtUsuario" class="CAMPOFORM" style="width: 230px;">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrIds.size();i++){%>
                                                    <option value="<%=arrIds.get(i)%>"><%=arrNombresU.get(i)%></option>
                                                <%}%>
                                           </select>
                                            &nbsp;<img src="Images/error.png" id="imgErrorUsuario" alt="Campo obligatorio" class="IMGERROR">                                               
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
                        <td class="TITULOFORM">NUEVO REGISTRO DE ROL POR USUARIO</td>
                    </tr>                
                    <tr>
                        <td>
                            <form method="POST" id="frmRolXUsuario" name="frmRolXUsuario">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmRolXUsuario">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="V">
                                <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                <table cellspacing="0" cellpadding="5" width="800px" border="0" class="TABLAMAESTRO">
                                    <tr><td style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtRol" id="lblCodigo">* Rol:</label></td>
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtRol" id="txtRol" class="CAMPOFORM" style="width: 230px;" disabled="disabled">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrCodigos.size();i++){%>
                                                    <option value="<%=arrCodigos.get(i)%>"><%=arrNombresR.get(i)%></option>
                                                <%}%>                                                
                                           </select>
                                           <script type="text/javascript">
                                                     $("#txtRol option[value='<%=strDatos[0]%>']").attr('selected', 'selected');
                                            </script>
                                            &nbsp;<img src="Images/error.png" id="imgErrorRol" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM"><label for="txtUsuario" id="lblNombre">* Usuario:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtUsuario" id="txtUsuario" class="CAMPOFORM" style="width: 230px;" disabled="disabled">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrIds.size();i++){%>
                                                    <option value="<%=arrIds.get(i)%>"><%=arrNombresU.get(i)%></option>
                                                <%}%>
                                           </select>
                                           <script type="text/javascript">
                                                     $("#txtUsuario option[value='<%=strDatos[1]%>']").attr('selected', 'selected');
                                            </script>
                                            &nbsp;<img src="Images/error.png" id="imgErrorUsuario" alt="Campo obligatorio" class="IMGERROR">                                               
                                        </td>
                                     </tr>                                     
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr><td colspan="4" class="CELDABOTONFORM"><input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"></td></tr>
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