<%-- 
    Document   : rol
    Created on : 19/04/2012, 02:13:44 PM
    Author     : jorge.correa
--%>

<%@page import="Conexion.GestionSQL"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<% 
    String strAccion = (String) request.getParameter("txtAccion");      
   
    String strFirma = "";
    String strCadena = null;
    String[] strDatosGenerales = null;
    String[] arrCadena = null;
    String[] strDatos = null;
    String strSQL = "";
    Vector arrIds = new Vector();
    Vector arrNombres = new Vector();   ;
    String[] strTemp = null;
    
     if (strAccion == null){
            response.sendRedirect("cerrar.jsp");
     }else{
        strSQL = "SELECT r.txtUsuario as Id, p.txtNombreCompleto as Nombre FROM buzon.buzon_rolesxusuario r, users.users_personas p where (r.txtUsuario = p.txtIdentificacion) and r.txtRol = 'AT'";
        Vector arrUsuarios = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");       
        
        for (int i=0;i<arrUsuarios.size();i++){
            strTemp = arrUsuarios.get(i).toString().split(",");
            arrIds.add(strTemp[0]);
            arrNombres.add(strTemp[1]);
        }
               
        if (strAccion.equals("V")){
            strDatosGenerales = GestionSQL.getFila("SELECT * FROM buzon_generales g where g.txtCodigo = 'frmGeneral'","Buzon");                               
        }
     }    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">           
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="Styles/Comunes.css" />
        <link rel="stylesheet" type="text/css" href="Styles/Generales.css" />
        <script language="JavaScript" type="text/javascript" src="Scripts/JSComunes.js"></script>
        <script language="JavaScript" type="text/javascript" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>        
        <script language="JavaScript" type="text/javascript" src="Scripts/JSGenerales.js"></script>
        <title>Administración: Parámetros Generales</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div align="center">            
            <br><br>
            <% if (strAccion.equals("C")){%>
                <table cellspacing="0" cellpadding="0" width="900px" border="0">
                    <tr>
                        <td class="TITULOFORM" colspan="4">NUEVO REGISTRO DE PARÁMETROS GENERALES</td>
                    </tr>
                    <tr>
                        <td>
                            <form method="POST" action="Registro" id="frmGeneral" name="frmGeneral">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmGeneral">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="C">                         
                                <table cellspacing="0" cellpadding="2" width="900px" border="0" class="TABLAMAESTRO">
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM">* Consecutivo actual:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtConsecutivo" id="txtConsecutivo" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgConsecutivo" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">* Nro. registros por página:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroPaginas" id="txtNroPaginas" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNroPaginas" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtAsistente" id="lblNombre">* Asistente a notificar:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtAsistente" id="txtAsistente" class="CAMPOFORM" style="width: 180px;">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrIds.size();i++){%>
                                                    <option value="<%=arrIds.get(i)%>"><%=arrNombres.get(i)%></option>
                                                <%}%>
                                           </select>
                                            &nbsp;<img src="Images/error.png" id="imgErrorAsistente" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">* Nro. días cierre de solicitudes:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroDiasCierre" id="txtNroDiasCierre" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNroDiasCierre" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Nro. días alertar vencimiento:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroDiasAlerta" id="txtNroDiasAlerta" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNroDiasAlerta" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos para el envío de E-Mails</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Nombre del servidor:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNombreServidor" id="txtNombreServidor" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNombreServidor" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">Número del puerto:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNumeroPuerto" id="txtNumeroPuerto" class="CAMPOFORM">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Usuario:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtUsuario" id="txtUsuario" class="CAMPOFORM" style="width: 180px;">&nbsp;<img src="Images/error.png" id="imgUsuario" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM">* Contraseña:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="password" name="txtPassword" id="txtPassword" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgPassword" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>    
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos de los Informes</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM" colspan="2">* Ruta de almacenamiento del archivo de Seguimiento PQRS:</td>
                                        <td class="CELDACAMPOFORM" colspan="2">
                                            <input type="text" name="txtRutaSegPQRS" id="txtRutaSegPQRS" class="CAMPOFORM" style="width: 300px;">&nbsp;<img src="Images/error.png" id="imgRutaSegPQRS" alt="Campo obligatorio" class="IMGERROR">
                                        </td>                                        
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos de los Archivos Cargados</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM" colspan="2">* Ruta de almacenamiento de los archivos cargados:</td>
                                        <td class="CELDACAMPOFORM" colspan="2">
                                            <input type="text" name="txtRutaArchivos" id="txtRutaArchivos" class="CAMPOFORM" style="width: 300px;">&nbsp;<img src="Images/error.png" id="imgRutaArchivos" alt="Campo obligatorio" class="IMGERROR">
                                        </td>                                        
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>                                            
                                    <tr><td colspan="4" class="CELDABOTONFORM"><input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" class="BOTONFORM">&nbsp;&nbsp;<input type="button" value="Limpiar" name="btnLimpiar" id="btnLimpiar" class="BOTONFORM"> &nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"></td></tr>
                                    <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                                </table>
                            </form>
                        </td>
                    </tr>                            
                </table>
            <%}else{%>
                <table cellspacing="0" cellpadding="0" width="900px" border="0">
                    <tr>
                        <td class="TITULOFORM" colspan="4">REGISTRO DE PARÁMETROS GENERALES</td>
                    </tr>
                    <tr>
                        <td>
                            <form method="POST" action="Registro" id="frmGeneral" name="frmGeneral">
                                <input type="hidden" name="txtForm" id="txtForm" value="frmGeneral">
                                <input type="hidden" name="txtAccion" id="txtAccion" value="V">                         
                                <table cellspacing="0" cellpadding="2" width="900px" border="0" class="TABLAMAESTRO">
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM">* Consecutivo actual:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtConsecutivo" id="txtConsecutivo" value="<%=strDatosGenerales[1]%>" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgConsecutivo" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">* Nro. registros por página:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroPaginas" id="txtNroPaginas" value="<%=strDatosGenerales[2]%>" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNroPaginas" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtAsistente" id="lblNombre">* Asistente a notificar:</label></td>                                        
                                        <td class="CELDACAMPOFORM">
                                            <select name="txtAsistente" id="txtAsistente" class="CAMPOFORM" style="width: 180px;">
                                                <option value="-1">Seleccione una opción</option>    
                                                 <%for (int i=0;i<arrIds.size();i++){%>
                                                    <option value="<%=arrIds.get(i)%>"><%=arrNombres.get(i)%></option>
                                                <%}%>
                                           </select>
                                           <script type="text/javascript">
                                                     $("#txtAsistente option[value='<%=strDatosGenerales[8]%>']").attr('selected', 'selected');
                                            </script>
                                            &nbsp;<img src="Images/error.png" id="imgErrorAsistente" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">* Nro. días cierre de solicitudes:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroDiasCierre" id="txtNroDiasCierre" value="<%=strDatosGenerales[9]%>" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNroDiasCierre" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Nro. días alertar vencimiento:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNroDiasAlerta" id="txtNroDiasAlerta" class="CAMPOFORM" value="<%=strDatosGenerales[11]%>">&nbsp;<img src="Images/error.png" id="imgNroDiasAlerta" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos para el envío de E-Mails</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Nombre del servidor:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNombreServidor" id="txtNombreServidor" value="<%=strDatosGenerales[3]%>" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgNombreServidor" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                        <td class="LABELFORM">Número del puerto:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtNumeroPuerto" id="txtNumeroPuerto" value="<%=strDatosGenerales[4]%>" class="CAMPOFORM">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">* Usuario:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="text" name="txtUsuario" id="txtUsuario" value="<%=strDatosGenerales[5]%>" class="CAMPOFORM" style="width: 180px;">&nbsp;<img src="Images/error.png" id="imgUsuario" alt="Campo obligatorio" class="IMGERROR">                                            
                                        </td>
                                        <td class="LABELFORM">* Contraseña:</td>
                                        <td class="CELDACAMPOFORM">
                                            <input type="password" name="txtPassword" id="txtPassword" value="<%=strDatosGenerales[6]%>" class="CAMPOFORM">&nbsp;<img src="Images/error.png" id="imgPassword" alt="Campo obligatorio" class="IMGERROR">
                                        </td>
                                    </tr>          
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos de los Informes</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM" colspan="2">* Ruta de almacenamiento del archivo de Seguimiento PQRS:</td>
                                        <td class="CELDACAMPOFORM" colspan="2">
                                            <input type="text" name="txtRutaSegPQRS" id="txtRutaSegPQRS" value="<%=strDatosGenerales[7]%>" class="CAMPOFORM" style="width: 300px;">&nbsp;<img src="Images/error.png" id="imgRutaSegPQRS" alt="Campo obligatorio" class="IMGERROR">
                                        </td>                                        
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>
                                    <tr>
                                        <td class="SUBTITULOMENU1" colspan="4" >Datos de los Archivos Cargados</td>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM" colspan="2">* Ruta de almacenamiento de los archivos cargados:</td>
                                        <td class="CELDACAMPOFORM" colspan="2">
                                            <input type="text" name="txtRutaArchivos" id="txtRutaArchivos" value="<%=strDatosGenerales[10]%>" class="CAMPOFORM" style="width: 300px;">&nbsp;<img src="Images/error.png" id="imgRutaArchivos" alt="Campo obligatorio" class="IMGERROR">
                                        </td>                                        
                                    </tr>
                                    <tr><td colspan="4" style="height: 10px;"></td></tr>                                            
                                    <tr><td colspan="4" class="CELDABOTONFORM"><input type="button" name="btnGuardar" id="btnGuardar" value="Guardar" class="BOTONFORM">&nbsp;&nbsp;<input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"></td></tr>
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
