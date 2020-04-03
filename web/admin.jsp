<%-- 
    Document   : admin
    Created on : 10-jul-2012, 14:08:05
    Author     : jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String strUsuario = null;
    String[] strDatos= null; 
    String[] strNombreUsuario = null;
    String strSQL = "";
    strUsuario = (String) request.getParameter("txtCedula");   
    
    if (strUsuario != null){
        strSQL = "select g.txtConsecutivo from buzon_generales g where g.txtCodigo = 'frmGeneral'";                  
        strDatos = GestionSQL.getFila(strSQL,"Buzon");
        
        strSQL = "select p.txtNombreCompleto from users_personas p where p.txtIdentificacion = '" + strUsuario + "'";
        strNombreUsuario = GestionSQL.getFila(strSQL,"Users");
    }      
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="Styles/Comunes.css" />
        <link rel="stylesheet" type="text/css" href="Styles/Admin.css" />
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSComunes.js"></script>
        <script type='text/javascript' charset="UTF-8" src='Scripts/JSAdmin.js'></script>
        <script type='text/javascript' charset="UTF-8" src='Scripts/JSVisualizacion.js'></script>
        <script type="text/javascript">
            function disableKeyPress(evt){               
                var evt = (evt) ? evt : ((event) ? event : null); 
                var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
                if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
            }
        </script>
        <title>Parámetros del Sistema</title>
    </head>
    <body OnKeyPress="return disableKeyPress(event)">        
        <jsp:include page="header.jsp" />
        <div align="center">            	   
            <br>
            <table cellspacing="0" cellpadding="0" width="1200px" border="0">
                <tr>
                    <% if(strNombreUsuario != null){ %>
                        <td class="USERLOGED"><b>Bienvenido,</b> <%=strNombreUsuario[0]%></td>             
                    <%}else{%>
                        <td class="USERLOGED"><b>Bienvenido!</b></td>            
                    <%}%>                    
                    <td class="CIERRESESION" style="width: 100px;border-left: 1px solid #116043;">
                        <input type="button" value="Salir" class="BOTONFORM" onclick="window.close();" />                        
                    </td>                    
                </tr>
            </table>
            <br>
             <table cellspacing="0" cellpadding="0" width="1200px" border="0">
                <tr>
                    <td width="225px" style="vertical-align: text-top;">
                        <table cellspacing="0" cellpadding="7" width="225px" border="0" class="TABLAMENU">
                            <tr><td class="TITULOMENU" style="font-size: 15px;">MENÚ ADMINISTRATIVO</td></tr>
                            <tr><td class="SUBTITULOMENU">Parámetros del Sistema</td></tr>
                            <tr>
                                <td>
                                    <ul class="sidenav">
                                        <li class="ITEMMENU">
                                            <a href="#"> Estados
                                                <span onclick="javascript:window.open('estado.jsp?txtAccion=C&txtCodigo=-1','Estado','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Crear nuevo estado</span>
                                                <span onclick="AJAX('POST','Visualizacion','txtAccion=estado','dMostrar');">Consultar estados</span>  
                                             </a>
                                        </li>                  
                                        <%if (strDatos == null){%> 
                                            <li class="ITEMMENU">
                                                <a href="#">Generales
                                                    <span onclick="javascript:window.open('general.jsp?txtAccion=C&txtCodigo=-1','Generales','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Crear configuración general</span>                                                        
                                                </a>
                                            </li>                                                
                                        <%}else{%>
                                            <li class="ITEMMENU">
                                                <a href="#">Generales
                                                    <span onclick="javascript:window.open('general.jsp?txtAccion=V&txtCodigo=-1','Generales','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Consultar configuración general</span>                                                        
                                                </a>
                                            </li> 
                                        <%}%> 
                                        <li class="ITEMMENU">
                                            <a href="#">Roles
                                                <span onclick="javascript:window.open('rol.jsp?txtAccion=C&txtCodigo=-1','Rol','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Crear nuevo rol</span>
                                                <span onclick="AJAX('POST','Visualizacion','txtAccion=rol','dMostrar');">Consultar roles</span>
                                            </a>
                                        </li>
                                        <li class="ITEMMENU">
                                            <a href="#">Roles por usuario
                                                <span onclick="javascript:window.open('rolXpersona.jsp?txtAccion=C&txtCodigo=-1','Rol por usuario','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Crear nuevo rol por usuario</span>
                                                <span onclick="AJAX('POST','Visualizacion','txtAccion=rolXpersona','dMostrar');">Consultar roles por usuario</span>
                                            </a>
                                        </li>            
                                        <li class="ITEMMENU">
                                            <a href="#">Tipos de solicitud
                                                <span onclick="javascript:window.open('retroalimentacion.jsp?txtAccion=C&txtCodigo=-1','Retroalimentación','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',scrollbars=yes')">Crear nueva tipo de solicitud</span>
                                                <span onclick="AJAX('POST','Visualizacion','txtAccion=retroalimentacion','dMostrar');">Consultar tipos de solicitud</span>
                                            </a>
                                        </li>                                         
                                    </ul>
                                </td>
                            </tr>                            
                         </table>
                    </td>
                    <td width="20px"></td>
                    <td style="width:810px;text-align: center;vertical-align: top;">
                    <div id="dMostrar">                            
                    </div>
                    </td>
                </tr>
            </table>
        </div>
        <div style="padding-top: 30px;"></div>			
        <jsp:include page="footer.jsp" />
     </body>
</html>
