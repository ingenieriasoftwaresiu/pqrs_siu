<%-- 
    Document   : principal
    Created on : 18/04/2012, 11:00:36 AM
    Author     : jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String strUsuario = null;
    String strCedula = null;
    String strPreload = null;
    String[] strDatosAdmin= null; 
    String[] strNombreUsuario = null;
    String[] strDatosAsistente = null;
    String[] strDatosDllo = null;
    String strSQL = "";
    String strEsAsistente = "S";    
    String strEsAdmin = "N";
    strUsuario = (String) request.getParameter("txtCedula");          
    strPreload = request.getParameter("preload");    
    
    if ((strPreload == null) || (strPreload.equals("null"))){
        strPreload = null;
    }
        
    if (strUsuario == null){
        response.sendRedirect("cerrar.jsp");
    }
        
    if (!(strUsuario.equals("null"))){
        strSQL = "select r.txtRol from buzon_rolesxusuario r where r.txtRol = 'AD' and r.txtUsuario = '" + strUsuario + "'";                  
        strDatosAdmin = GestionSQL.getFila(strSQL,"Buzon");
        
        strSQL = "select r.txtRol from buzon_rolesxusuario r where r.txtRol = 'AT' and r.txtUsuario = '" + strUsuario + "'";                  
        strDatosAsistente = GestionSQL.getFila(strSQL,"Buzon");
        
        strSQL = "select r.txtRol from buzon_rolesxusuario r where r.txtRol = 'DE' and r.txtUsuario = '" + strUsuario + "'";                  
        strDatosDllo = GestionSQL.getFila(strSQL,"Buzon");
        
        strSQL = "select p.txtNombreCompleto from users_personas p where p.txtIdentificacion = '" + strUsuario + "'";
        strNombreUsuario = GestionSQL.getFila(strSQL,"Users");
        
        if (strDatosAdmin != null){
            strEsAdmin = "S";
        }
                
        if (strDatosAsistente != null){
             strEsAsistente = "S";
        }else{
            strEsAsistente = "N";
        }                  
        session.setAttribute("txtCedula", strUsuario);        
    }         
    session.setAttribute("strEsAsistente", strEsAsistente);
    session.setAttribute("strEsAdmin", strEsAdmin);
    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="Styles/Comunes.css" />
        <link rel="stylesheet" type="text/css" href="Styles/Menu_Principal.css" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSComunes.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>        
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSPrincipal.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSVisualizacion.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>             
        <script type="text/javascript">
            function disableKeyPress(evt){               
                var evt = (evt) ? evt : ((event) ? event : null); 
                var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
                if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
            }
        </script>
        <title>Menú Principal</title>
    </head>
    <body onKeyPress="disableKeyPress(event);">        
        <input type="hidden" name="txtUsuario" id="txtUsuario" value="<%=strUsuario%>">
        <jsp:include page="header.jsp" />
        <div align="center">            	   
            <br>
            <table cellspacing="0" cellpadding="0" width="1200px" border="0">                
                    <tr>
                        <% if(strNombreUsuario != null){ %>
                            <td class="USERLOGED" style="text-align: left;"><b>Bienvenido(a),</b><span style="font-weight: normal;"> <%=strNombreUsuario[0]%></span></td>             
                        <%}else{%>
                            <td class="USERLOGED" style="text-align: left;"><b>Bienvenido(a)!</b></td> 
                            <td class="USERLOGED" style="width: 110px;border-left: 1px solid #116043;">
                                <input type="button" value="Iniciar sesión" class="BOTONFORM" style="width: 90px;" onclick="location.href ='login.jsp';" />                                  
                            </td>
                        <%}%>
                        <%if (strPreload == null){%>
                            <td class="USERLOGED" style="width: 100px;border-left: 1px solid #116043;">
                                <input type="button" value="Salir" class="BOTONFORM" onclick="cerrarSesion();" />                     
                            </td>
                        <%}else{%>
                            <td class="USERLOGED" style="width: 100px;border-left: 1px solid #116043;">
                                <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();" />                       
                            </td>
                        <%}%>                                            
                    </tr>                                
            </table>
            <br>
             <table cellspacing="0" cellpadding="0" width="1200px" border="0">
                <tr>
                    <td width="250px" style="vertical-align: text-top;">
                        <% if(!(strUsuario.equals("null"))){%>
                            <table cellspacing="0" cellpadding="5" width="250px" border="0" class="TABLAMENU">
                                <tr><td class="TITULOMENU" style="font-size: 15px;">MENÚ PRINCIPAL</td></tr>                         
                                <tr><td class="ITEMMENU" id="itGlosario" onclick="mostrarAviso();"><a href="#">Glosario de términos</a></td></tr>
                                <tr><td class="SUBTITULOMENU">Creación de solicitud</td></tr>
                                <tr><td class="ITEMMENU" onclick="javascript:window.open('pqrs.jsp?txtAccion=C&txtCodigo=-1&txtCedula=' + <%=strUsuario%>,'PQRS','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')"><a href="#" onclick="javascript:window.open('pqrs.jsp?txtAccion=C&txtCodigo=-1&txtCedula=' + <%=strUsuario%>,'PQRS','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Nueva solicitud</a></td></tr>
                                <tr><td class="SUBTITULOMENU">Consulta de solicitudes</td></tr>
                                <tr><td class="ITEMMENU" id="itConsecutivo"><a href="#">Consecutivo</a></td></tr>
                                <tr><td class="ITEMMENU" id="itEstado"><a href="#">Estado actual</a></td></tr>
                                <tr><td class="ITEMMENU" id="itFechaCreacion"><a href="#">Fecha de creación</a></td></tr>                                                       
                                <tr><td class="ITEMMENU" id="itNomServicio"><a href="#">Nombre del servicio</a></td></tr>
                                <tr><td class="ITEMMENU" id="itNomUsuario"><a href="#">Nombre del usuario</a></td></tr>
                                <tr><td class="ITEMMENU" id="itTipoSol"><a href="#">Tipo de solicitud</a></td></tr>                                              
                                <tr><td class="ITEMMENU" id="itSolPend"><a href="#">Solicitudes pendientes</a></td></tr>                 
                                <tr><td class="ITEMMENU" id="itSolEnv"><a href="#">Solicitudes enviadas</a></td></tr>        
                                <% if(strDatosAsistente != null){ %>
                                    <tr><td class="SUBTITULOMENU">Informes</td></tr>
                                    <tr><td class="ITEMMENU"><a href="#" onclick="javascript:window.open('Informes/seguimientoPQRS.jsp','Administracion','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Seguimiento a PQRSFD</a></td></tr>
                                <%}%>
                                <% if (strDatosAdmin != null){ %>
                                    <tr><td class="SUBTITULOMENU">Administración</td></tr>
                                    <tr><td class="ITEMMENU"><a href="#" onclick="javascript:window.open('admin.jsp?txtCedula=<%=strUsuario%>','Administracion','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Parámetros del sistema</a></td></tr>
                            <%}%>
                            <% if (strDatosDllo != null){ %>
                                    <tr><td class="SUBTITULOMENU">Desarrollador</td></tr>
                                    <tr><td class="ITEMMENU" id="itlgResp"><a href="#">Log cambio de responsable</a></td></tr> 
                                    <tr><td class="ITEMMENU" id="itLSolVencerse"><a href="#">Lanzar tarea de solicitudes a vencerse</a></td></tr>
                                    <tr><td class="ITEMMENU" id="itLCerrarSols"><a href="#">Lanzar tarea de cerrar solicitudes</a></td></tr>
                                    <tr><td class="ITEMMENU" id="itLPararT"><a href="#">Detener tareas programadas</a></td></tr>
                            <%}%>
                            </table>
                      <%}else{%>
                            <table cellspacing="0" cellpadding="5" width="250px" border="0" class="TABLAMENU">
                                <tr><td class="TITULOMENU" style="font-size: 15px;">MENÚ PRINCIPAL</td></tr>                         
                                <tr><td class="ITEMMENU" id="itGlosario" onclick="mostrarAviso();"><a href="#">Glosario de términos</a></td></tr>
                                <tr><td class="SUBTITULOMENU">Creación de solicitud</td></tr>
                                <tr><td class="ITEMMENU" onclick="javascript:window.open('pqrs.jsp?txtAccion=C&txtCodigo=-1&txtCedula=' + <%=strUsuario%>,'PQRS','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')"><a href="#" onclick="javascript:window.open('pqrs.jsp?txtAccion=C&txtCodigo=-1&txtCedula=' + <%=strUsuario%>,'PQRS','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Nueva solicitud</a></td></tr>
                                <tr><td class="SUBTITULOMENU">Consulta de solicitudes</td></tr>
                                <tr><td class="ITEMMENU" id="itUserConsecutivo"><a href="#">Consecutivo</a></td></tr>
                                <tr><td class="ITEMMENU" id="itUserId"><a href="#">Número de identificación</a></td></tr>                                            
                            </table>
                      <%}%>
                    </td>
                    <td width="20px"></td>
                    <td style="width:850px;text-align: center;vertical-align: top;">
                        <div id="dMostrar">                            
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <br>	
        <jsp:include page="footer.jsp" />
     </body>
</html>
