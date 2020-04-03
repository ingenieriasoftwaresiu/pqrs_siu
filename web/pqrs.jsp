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
    String strCedula = null, strMostrarDatosUsuario="N";
            
    strCedula = (String) session.getAttribute("txtCedula");     
    
    if (strCedula == null){
        strMostrarDatosUsuario = "N";
    }else{
        strMostrarDatosUsuario = "S";
    }
        
    int intNumObs=0;
    String strSQL = "";
    String strRol = "";
    String strRadicado = "";
    String strEsAsistente = "";
    String strIdEstado = "";
    String strEstado = "";
    String strRpta = "";
    String[] strDatosPQRS = null;
    String[] strDatosEstado = null;
    String[] strDatosEstadoInicial = null;
    String[] strConsecutivo = null;
    String strCadena = null;
    String[] arrCadena = null;
    String[] arrRpta = null;
    String[] strTemp = null;
    String[] strDatosObs = null;
    Vector arrCodigosPQRS = new Vector();
    Vector arrNombresPQRS = new Vector();
    Vector arrIdsPersonas = new Vector();
    Vector arrPersonasXCargos = new Vector();
    Vector arrCodigosG = new Vector();
    Vector arrNombresG = new Vector();
    Vector arrCodigosS = new Vector();
    Vector arrNombresS = new Vector();    
    Vector arrIdCoord = new Vector();
    Vector arrNomCoord = new Vector();
    Vector arrNombresP = new Vector();
    Vector arrIdsP = new Vector();
    Vector arrCargosP = new Vector();
    Vector arrIdsR = new Vector();
    Vector arrNombresR = new Vector();
    
     if (strAccion == null) {
        response.sendRedirect("cerrar.jsp");
     }else{                  
        if (strCedula != null){            
            if (!strCedula.equals("null")){                       
                strSQL = "select r.txtRol as Id, r.txtUsuario as Nombre from buzon_rolesxusuario r WHERE r.txtUsuario = '" + strCedula + "'";
                Vector arrRoles = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");
                
                if (arrRoles != null){
                    for (int i=0;i<arrRoles.size();i++){
                        strTemp = arrRoles.get(i).toString().split(",");
                        strRol = strTemp[0];

                        if (strRol.equals("AT")){
                            strEsAsistente = "S";
                        }                  
                    }
                }else{
                      strEsAsistente = "N";
                }
            }else{
                 strEsAsistente = "N";
                 strCedula = " ";
            }                                
        }else{
            strEsAsistente = "N";
            strCedula = " ";
        }
        
        if (strAccion.equals("C")){            
            strSQL = "select e.txtNombre from buzon_estados e where e.txtCodigo = 'C'";
            strDatosEstadoInicial = GestionSQL.getFila(strSQL,"Buzon"); 
        }
                        
        strSQL = "select r.txtCodigo as Id, r.txtNombre as Nombre from buzon_retroalimentacion r ORDER BY r.txtNombre";
        Vector arrTiposPQRS = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");
                
        for (int i=0;i<arrTiposPQRS.size();i++){
            strTemp = arrTiposPQRS.get(i).toString().split(",");
            arrCodigosPQRS.add(strTemp[0]);
            arrNombresPQRS.add(strTemp[1]);
        }
        
        strSQL = "select p.txtIdentificacion, p.txtNombreCompleto, c.txtNombre  from users_personas p, users_cargos c where (p.txtCargo = c.txtIdentificacion) ORDER BY p.txtNombreCompleto";
        Vector arrPersonasCargos = GestionSQL.consultaSQL(strSQL,"Users","PERSONASXCARGO");
        
        for (int i=0;i<arrPersonasCargos.size();i++){
            strTemp = arrPersonasCargos.get(i).toString().split(",");
            arrIdsPersonas.add(strTemp[0]);
            arrPersonasXCargos.add(strTemp[1] + " - " + strTemp[2]);
        }
        
        strSQL = "select g.txtCodigo as Id, g.txtNombre as Nombre from users_grupos_siu g order by g.txtNombre";
        Vector arrGrupos = GestionSQL.consultaSQL(strSQL,"Users","MAESTROS");
                
        for (int i=0;i<arrGrupos.size();i++){
            strTemp = arrGrupos.get(i).toString().split(",");
            arrCodigosG.add(strTemp[0]);
            arrNombresG.add(strTemp[1]);
        }
        
        strSQL = "select s.txtCodigo as Id, s.txtNombre as Nombre from users_servicios s order by s.txtNombre";
        Vector arrServicios = GestionSQL.consultaSQL(strSQL,"Users","MAESTROS");
                       
        for (int i=0;i<arrServicios.size();i++){
            strTemp = arrServicios.get(i).toString().split(",");
            arrCodigosS.add(strTemp[0]);
            arrNombresS.add(strTemp[1]);
        }
                        
        if (strAccion.equals("V")){               
        
             strSQL = "select * from buzon_pqrs p where p.txtRadicado='" + strCodigo + "'";
             strDatosPQRS = GestionSQL.getFila(strSQL,"Buzon");
             
             strCadena = strDatosPQRS[10];                         
             strCadena =  strCadena.replaceAll(">", "<br>");
             //arrCadena = strCadena.split(">");           
                                      
             strIdEstado = strDatosPQRS[11];
             
             if (strIdEstado.equals("C")){
                 strIdEstado = "PR";                 
             }
             
             strSQL = "select e.txtNombre from buzon_estados e where e.txtCodigo = '" + strIdEstado + "'";
             strDatosEstado = GestionSQL.getFila(strSQL,"Buzon");           

             strRpta = strDatosPQRS[12];     
             if(strRpta != null){
                  arrRpta = strRpta.split(">");    
             }else{
                 arrRpta = null;
             }
                                              
            strSQL = "select COUNT(o.txtIdObs) from buzon_obs_x_sol o where o.txtRadicado = '" + strCodigo + "'";
            strDatosObs = GestionSQL.getFila(strSQL, "Buzon");
            intNumObs = Integer.parseInt(strDatosObs[0]);
                                      
            strSQL = "select p.txtIdentificacion, p.txtNombreCompleto, c.txtNombre from users_personas p, users_cargos c where (p.txtCargo = c.txtIdentificacion) and p.txtEstadoActual = 'A' and p.txtGrupoPertenece = 'GRADMIN' order by p.txtNombreCompleto";
            Vector arrPersonas = GestionSQL.consultaSQL(strSQL,"Users","PERSONASXCARGO");

            for (int i=0;i<arrPersonas.size();i++){
                strTemp = arrPersonas.get(i).toString().split(",");
                arrIdsP.add(strTemp[0]);
                arrNombresP.add(strTemp[1]);
                arrCargosP.add(strTemp[2]);
            }
            
            strSQL = "select r.txtIdentificacion as Id, r.txtNombre as Nombre from users.users_redes r ORDER BY r.txtNombre";
            Vector arrRedes = GestionSQL.consultaSQL(strSQL,"Users","MAESTROS");
            
             for (int i=0;i<arrRedes.size();i++){
                strTemp = arrRedes.get(i).toString().split(",");
                arrIdsR.add(strTemp[0]);
                arrNombresR.add(strTemp[1]);
            }
        }
     }           
%>
<html>
    <head>      
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/PQRS.css" />        
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSComunes.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" src="Scripts/jquery.filestyle.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSPQRS.js"></script>
        <title>Solicitud PQRSFD</title>
    </head>
    <body>       
        <jsp:include page="header.jsp" />
        <div align="center">
            <br>          
            <% if (strAccion.equals("C")){%>
                <div id="dPQRS">
                    <table cellspacing="0" cellpadding="0" width="880px" border="0">
                        <tr>
                            <td class="TITULOFORM">NUEVO REGISTRO DE SOLICITUD</td>
                        </tr>                            
                        <tr>
                            <td>
                                <form method="POST" id="frmPQRS" name="frmPQRS" enctype="multipart/form-data" action="GestionCrearSolicitud">
                                    <input type="hidden" name="txtForm" id="txtForm" value="frmPQRS">
                                    <input type="hidden" name="txtAccion" id="txtAccion" value="C">                              
                                    <input type="hidden" name="txtIdEstado" id="txtIdEstado" value="C">           
                                    <input type="hidden" name="txtEsAsistente" id="txtEsAsistente" value="<%=strEsAsistente%>">                                    
                                    <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                    <input type="hidden" name="txtCedula" id="txtCedula" value="<%=strCedula%>">
                                    <table cellspacing="0" cellpadding="0" width="880px" border="0" class="TABLAPQRS">
                                        <tr><td style="height: 10px;"></td></tr>
                                        <tr>
                                            <td class="LABELFORM" style="width:170px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <label for="txtFechaCreacion" id="lblFechaCreacion">
                                                    Fecha de creación:<br>&nbsp;&nbsp;(aaaa-mm-dd)
                                                </label>
                                            </td>
                                            <td class="CELDACAMPOFORM" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <input type="text" name="txtFechaCreacion" id="txtFechaCreacion" class="CAMPOFORMREAD" style="width: 180px;" readOnly>&nbsp;<img src="Images/error.png" id="imgFechaCreacion" alt="Campo obligatorio" class="IMGERROR">                                         
                                            </td>
                                            <td class="LABELFORM" style="width:200px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;padding-left: 0px;">
                                                <label for="txtEstado" id="lblTelefono">Estado actual de la solicitud:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <input type="text" name="txtEstado" id="txtEstado" value="<%=strDatosEstadoInicial[0]%>" class="CAMPOFORMREAD" style="width: 185px;" readOnly>&nbsp;<img src="Images/error.png" id="imgEstado" alt="Campo obligatorio" class="IMGERROR">                                         
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="LABELFORM" style="width: 180px;vertical-align: middle;">
                                                <label for="txtTipoPQRS" id="lblTipo">* Tipo de solicitud:</label>&nbsp;<a href="#" onclick="mostrarAviso();"><img src="Images/info.jpg" id="imgInfoPQRS" alt="Información" width="15" height="15" style="vertical-align: middle;"></a>
                                            </td>                                        
                                            <td class="CELDACAMPOFORM" style="width: 220px;">                                                
                                                <select name="txtTipoPQRS" id="txtTipoPQRS" class="CAMPOFORM" style="width: 180px;">
                                                    <option value="-1">Seleccione una opción</option>    
                                                    <%for (int i=0;i<arrCodigosPQRS.size();i++){%>
                                                        <option value="<%=arrCodigosPQRS.get(i)%>"><%=arrNombresPQRS.get(i)%></option>
                                                    <%}%>
                                                </select>
                                                &nbsp;<img src="Images/error.png" id="imgTipoPQRS" alt="Campo obligatorio" class="IMGERROR">
                                            </td>
                                            <td colspan="2">
                                                <div id="dTipoPQRS">                                                    
                                                </div>
                                            </td>                                            
                                        </tr>
                                        <tr>                                            
                                                <td class="LABELFORM"  colspan="2" style="width:442px;vertical-align: middle;">
                                                    <label for="txtServicio" id="lblServicio">* Nombre del servicio que recibiste desde la Administración de la SIU:</label>
                                                </td>
                                                <td class="CELDACAMPOFORM" colspan="2">
                                                    <select name="txtServicio" id="txtServicio" class="CAMPOFORM" style="width: 385px;">
                                                        <option value="-1">Seleccione una opción</option>
                                                        <%for (int i=0;i<arrCodigosS.size();i++){%>
                                                            <option value="<%=arrCodigosS.get(i)%>"><%=arrNombresS.get(i)%></option>
		<%}%>
                                                    </select>                           
                                                    &nbsp;<img src="Images/error.png" id="imgServicio" alt="Campo obligatorio" class="IMGERROR">            
                                                </td>                                                     
                                        </tr>
                                        <tr>
                                            <td class="LABELFORM" colspan="2" style="width:250px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <label for="txtNombreCargo" id="lblNombreCargo">Nombre y cargo del responsable del servicio:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" colspan="2" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <input type="hidden" name="txtIdResponsable" id="txtIdResponsable"/>
                                                <div id="dNombreCargo">                                                    
                                                </div>                                                                             
                                            </td>                                        
                                        </tr>            
                                        <tr>
                                            <td colspan="2" class="LABELFORM">
                                                * ¿Desea figurar como usuario anónimo en la solicitud?:
                                            </td>
                                            <td colspan="2" class="CELDARADIOFORM" style="font-weight: normal;font-size: 12.5px;vertical-align: middle;text-align: left;">
                                                    <input type="radio" name="rdAnonimo" id="rdSI" value="S">Si
                                                    <input type="radio" name="rdAnonimo" id="rdNO" value="N">No
                                                    &nbsp;<img src="Images/error.png" id="imgAnonimo" alt="Campo obligatorio" class="IMGERROR">
                                            </td>
                                        </tr>                                        
                                        <tr>
                                            <td colspan="4">
                                                <%if (!strCedula.equals(" ")){%>
                                                    <div id="dDatosUsuario">
                                                        <%
                                                            strSQL = "select p.txtIdentificacion, p.txtNombreCompleto, p.txtTelOficina, p.txtEmailC from users_personas p where p.txtIdentificacion = '" + strCedula + "'";
                                                            String[] strDatosPersona = GestionSQL.getFila(strSQL, "Users");                                                            
                                                        %>
                                                        <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                                            <tr>
                                                                <td class="LABELFORM" style="width: 189px;"><label for="txtTipoEntidad" id="lblTipoEntidad">* Tipo de entidad:</label></td>
                                                                <td class="CELDARADIOFORM" style="width: 230px;font-weight: normal;font-size: 12.5px;vertical-align: middle;">
                                                                    <input type="radio" name="rdTipoEntidad" id="rdInt" value="I" checked>SIU
                                                                    <input type="radio" name="rdTipoEntidad" id="rdUdeA" value="U">UdeA No SIU
                                                                    <input type="radio" name="rdTipoEntidad" id="rdExt" value="E">Externa
                                                                    &nbsp;<img src="Images/error.png" id="imgTipoEntidad" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>        
                                                                <td class="LABELFORM" colspan="2" style="padding-left: 0px;">                                                                    
                                                                    <div id="dInterno">
                                                                        <label for="txtGrupo" id="lblGrupo">* Grupo:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                        <select name="txtGrupo" id="txtGrupo" class="CAMPOFORM" style="width: 311px;">
                                                                            <option value="-1">Seleccione una opción</option>    
                                                                            <%for (int i=0;i<arrCodigosG.size();i++){%>
                                                                                <option value="<%=arrCodigosG.get(i)%>"><%=arrNombresG.get(i)%></option>
                                                                            <%}%>
                                                                        </select>
                                                                        <script type="text/javascript">                                                                            
                                                                             $("#txtGrupo").val("GRADMIN");
                                                                        </script>
                                                                        &nbsp;<img src="Images/error.png" id="imgGrupo" alt="Campo obligatorio" class="IMGERROR">      
                                                                    </div>                                             
                                                                    <div id="dExterno">
                                                                        <label for="txtNomEntidad" id="lblNomEntidad">* Nombre de la entidad:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                        <input type="text" name="txtNomEntidad" id="txtNomEntidad" class="CAMPOFORM" style="width: 224px;">&nbsp;<img src="Images/error.png" id="imgNomEntidad" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                    </div>      
                                                                </td>                                       
                                                            </tr>
                                                            <tr>
                                                                <td class="LABELFORM" colspan="2" style=" vertical-align: middle;">
                                                                    Número de identificación de quien presenta la solicitud:
                                                                </td>
                                                                <td class="CELDACAMPOFORM" style=" vertical-align: middle;">
                                                                    <input type="text" id="txtNumId" name="txtNumId" value="<%=strDatosPersona[0]%>" class="CAMPOFORM" />&nbsp;<img src="Images/error.png" id="imgNumId" alt="Campo obligatorio" class="IMGERROR">
                                                                </td>
                                                                <td class="TEXTOAVISOID">
                                                                    <b>Nota</b>: El número de identificación se utilizará para la posterior búsqueda de sus solicitudes, en caso de ser necesario.
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="LABELFORM" colspan="2" style="width:250px;"><label for="txtNombreUser" id="lblNombreUser">* Nombres y apellidos de quien presenta la solicitud:</label></td>
                                                                <td class="CELDACAMPOFORM" colspan="2" style="width:300px;padding-left: 0px;">
                                                                    <input type="text" name="txtNombreUser" id="txtNombreUser" value="<%=strDatosPersona[1]%>" class="CAMPOFORM" style="width: 379px;">&nbsp;<img src="Images/error.png" id="imgNombreUser" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td rowspan="2" class="LABELFORM" style="width:140px;">
                                                                    <label for="txtTelefono" id="lblTelefono">* Teléfono:</label>
                                                                </td>
                                                                <td rowspan="2" class="CELDACAMPOFORM">
                                                                    <input type="text" name="txtTelefono" id="txtTelefono" value="<%=strDatosPersona[2]%>" class="CAMPOFORM" style="width: 170px;">&nbsp;<img src="Images/error.png" id="imgTelefono" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>
                                                                <td class="LABELFORM" style="width: 180px;padding-left: 0px;">
                                                                    <label for="txtEmail" id="lblEmail">Correo electrónico:</label>
                                                                </td>                                                    
                                                                <td class="CELDACAMPOFORM" style="width: 220px;">
                                                                    <input type="text" name="txtEmail" id="txtEmail" value="<%=strDatosPersona[3]%>" class="CAMPOFORM" style="width: 190px;">&nbsp;<img src="Images/error.png" id="imgEmail" alt="Campo obligatorio" class="IMGERROR">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                 <%}else{%>
                                                    <div id="dDatosUsuario">
                                                        <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                                            <tr>
                                                                <td class="LABELFORM" style="width: 189px;"><label for="txtTipoEntidad" id="lblTipoEntidad">* Tipo de entidad:</label></td>
                                                                <td class="CELDARADIOFORM" style="width: 230px;font-weight: normal;font-size: 12.5px;vertical-align: middle;">
                                                                    <input type="radio" name="rdTipoEntidad" id="rdInt" value="I">SIU
                                                                    <input type="radio" name="rdTipoEntidad" id="rdUdeA" value="U">UdeA No SIU
                                                                    <input type="radio" name="rdTipoEntidad" id="rdExt" value="E">Externa
                                                                    &nbsp;<img src="Images/error.png" id="imgTipoEntidad" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>        
                                                                <td class="LABELFORM" colspan="2" style="padding-left: 0px;">
                                                                    <div id="dInterno">
                                                                        <label for="txtGrupo" id="lblGrupo">* Grupo:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                        <select name="txtGrupo" id="txtGrupo" class="CAMPOFORM" style="width: 311px;">
                                                                            <option value="-1">Seleccione una opción</option>    
                                                                            <%for (int i=0;i<arrCodigosG.size();i++){%>
                                                                                <option value="<%=arrCodigosG.get(i)%>"><%=arrNombresG.get(i)%></option>
                                                                            <%}%>
                                                                        </select>
                                                                        &nbsp;<img src="Images/error.png" id="imgGrupo" alt="Campo obligatorio" class="IMGERROR">      
                                                                    </div>      
                                                                    <div id="dExterno">
                                                                        <label for="txtNomEntidad" id="lblNomEntidad">* Nombre de la entidad:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                        <input type="text" name="txtNomEntidad" id="txtNomEntidad" class="CAMPOFORM" style="width: 224px;">&nbsp;<img src="Images/error.png" id="imgNomEntidad" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                    </div>      
                                                                </td>                                       
                                                            </tr>
                                                            <tr>
                                                                <td class="LABELFORM" colspan="2" style=" vertical-align: middle;">
                                                                    Número de identificación de quien presenta la solicitud:
                                                                </td>
                                                                <td class="CELDACAMPOFORM" style=" vertical-align: middle;padding-left: 0px;">
                                                                    <input type="text" id="txtNumId" name="txtNumId" class="CAMPOFORM" />&nbsp;<img src="Images/error.png" id="imgNumId" alt="Campo obligatorio" class="IMGERROR">
                                                                </td>
                                                                <td class="TEXTOAVISOID">
                                                                    <b>Nota</b>: El número de identificación se utilizará para la posterior búsqueda de sus solicitudes, en caso de ser necesario.
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="LABELFORM" colspan="2" style="width:250px;"><label for="txtNombreUser" id="lblNombreUser">* Nombres y apellidos de quien presenta la solicitud:</label></td>
                                                                <td class="CELDACAMPOFORM" colspan="2" style="width:300px;padding-left: 0px;">
                                                                    <input type="text" name="txtNombreUser" id="txtNombreUser" class="CAMPOFORM" style="width: 379px;">&nbsp;<img src="Images/error.png" id="imgNombreUser" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td rowspan="2" class="LABELFORM" style="width:140px;">
                                                                    <label for="txtTelefono" id="lblTelefono">* Teléfono:</label>
                                                                </td>
                                                                <td rowspan="2" class="CELDACAMPOFORM">
                                                                    <input type="text" name="txtTelefono" id="txtTelefono" class="CAMPOFORM" style="width: 170px;">&nbsp;<img src="Images/error.png" id="imgTelefono" alt="Campo obligatorio" class="IMGERROR">                                         
                                                                </td>
                                                                <td class="LABELFORM" style="width: 180px;padding-left: 0px;">
                                                                    <label for="txtEmail" id="lblEmail">Correo electrónico:</label>
                                                                </td>                                                    
                                                                <td class="CELDACAMPOFORM" style="width: 220px;">
                                                                    <input type="text" name="txtEmail" id="txtEmail" class="CAMPOFORM" style="width: 190px;">&nbsp;<img src="Images/error.png" id="imgEmail" alt="Campo obligatorio" class="IMGERROR">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                 <%}%>                                                
                                            </td>
                                        </tr>     
                                        <tr><td colspan="4" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;"></td></tr>
                                        <tr>
                                            <td class="LABELFORM">
                                                <label for="txtDescripcion" id="lblDescripcion">* Motivo de la solicitud:</label>&nbsp;&nbsp;&nbsp;<img src="Images/error.png" id="imgDescripcion" alt="Campo obligatorio" class="IMGERROR"> 
                                            </td>
                                            <td colspan="3" class="CELDACAMPOFORM" style="width: 250px;">          
                                                <input type="hidden" name="txtMotivo" id="txtMotivo" />
                                                <div id="txtDescripcion" contenteditable="true" class="DIVEDITABLE"></div>
                                            </td>                                   
                                        </tr>                
                                        <tr>
                                            <td class="LABELFORM">
                                                <label for="txtSoporte" id="lblSoporte">Adjuntar archivo:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" colspan="3">              
                                                <input type="hidden" name="txtExtensionSoporte" id="txtExtensionSoporte" />
                                                <input type="file" name="txtSoporte" id="txtSoporte" class="CAMPOFORM" style="width: 250px;" />
                                            </td>
                                        </tr>
                                        <tr><td colspan="4" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;"></td></tr>
                                        <tr>
                                            <td colspan="4" class="CELDABOTONFORM">                                                                                                   
                                                    <input type="button" value="Guardar y Salir" id="btnGuardar" class="BOTONFORM" style="width: 100px;">&nbsp;&nbsp;
                                                    <input type="button" value="Limpiar" id="btnLimpiar" class="BOTONFORM"> &nbsp;&nbsp;                                         
                                                    <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                                    &nbsp;&nbsp;&nbsp;&nbsp;<img src="Images/loader.gif" id="imgLoad" class="IMGLOAD">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </form>
                            </td>
                        </tr>
                    </table>   
                </div>                          
            <%}else{%>
                <div id="dPQRS">
                    <table cellspacing="0" cellpadding="0" width="880px" border="0">
                        <tr>
                            <td class="TITULOFORM">REGISTRO DE SOLICITUD # <%=strDatosPQRS[0]%></td>
                        </tr>                
                        <tr>
                            <td>
                                <form method="POST" id="frmPQRS" name="frmPQRS">
                                    <input type="hidden" name="txtForm" id="txtForm" value="frmPQRS">
                                    <input type="hidden" name="txtAccion" id="txtAccion" value="V">                              
                                    <input type="hidden" name="txtIdEstado" id="txtIdEstado" value="<%=strDatosPQRS[11]%>">
                                    <input type="hidden" name="txtRadicado" id="txtRadicado" value="<%=strDatosPQRS[0]%>">
                                    <input type="hidden" name="txtEsAsistente" id="txtEsAsistente" value="<%=strEsAsistente%>">                                    
                                    <input type="hidden" name="txtCodigoM" id="txtCodigoM" value=" ">
                                    <input type="hidden" name="txtUsuarioActual" id="txtUsuarioActual" value="<%=strCedula%>">
                                    <table cellspacing="0" cellpadding="0" width="880px" border="0" class="TABLAPQRS">
                                        <tr><td style="height: 10px;"></td></tr>
                                        <tr>
                                            <td class="LABELFORM" style="width:170px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <label for="txtFechaCreacion" id="lblFechaCreacion">
                                                    Fecha de creación:<br>&nbsp;&nbsp;(aaaa-mm-dd)
                                                </label>
                                            </td>
                                            <td class="CELDACAMPOFORM" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <input type="text" name="txtFechaCreacion" id="txtFechaCreacion" value="<%=strDatosPQRS[1]%>" class="CAMPOFORMREAD" style="width: 180px;" readOnly>
                                            </td>
                                            <td class="LABELFORM" style="width:200px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;padding-left: 0px;">
                                                <label for="txtEstado" id="lblTelefono">Estado actual de la solicitud:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <input type="text" name="txtEstado" id="txtEstado" value="<%=strDatosEstado[0]%>" class="CAMPOFORMREAD" style="width: 185px;" readOnly>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="LABELFORM" style="width: 180px;">
                                                <label for="txtTipoPQRS" id="lblTipo">* Tipo de solicitud:</label>
                                            </td>                                        
                                            <td class="CELDACAMPOFORM" style="width: 220px;">
                                                <select name="txtTipoPQRS" id="txtTipoPQRS" class="CAMPOFORM" style="width: 180px;" disabled="disabled">
                                                    <option value="-1">Seleccione una opción</option>    
                                                    <%for (int i=0;i<arrCodigosPQRS.size();i++){%>
                                                        <option value="<%=arrCodigosPQRS.get(i)%>"><%=arrNombresPQRS.get(i)%></option>
                                                    <%}%>
                                            </select>
                                            <script type="text/javascript">
                                                $("#txtTipoPQRS").val("<%=strDatosPQRS[2]%>");
                                            </script>                                               
                                            </td>
                                            <td colspan="2" class="CELDACAMPOFORM">
                                                <input type="hidden" id="txtReqRpta" class="CAMPOFORM" value="<%=strDatosPQRS[15]%>">
                                            </td>
                                        </tr>                                                   
                                        <tr>                                            
                                                <td class="LABELFORM"  colspan="2" style="width:442px;vertical-align: middle;">
                                                    <label for="txtServicio" id="lblServicio">* Nombre del servicio que recibiste desde la Administración de la SIU:</label>
                                                </td>
                                                <td class="CELDACAMPOFORM" colspan="2">
                                                    <select name="txtServicio" id="txtServicio" class="CAMPOFORM" style="width: 385px;" disabled="disabled">
                                                        <option value="-1">Seleccione una opción</option>
                                                        <%for (int i=0;i<arrCodigosS.size();i++){%>
                                                            <option value="<%=arrCodigosS.get(i)%>"><%=arrNombresS.get(i)%></option>
		<%}%>
                                                </select>                           
                                                <script type="text/javascript">
                                                    $("#txtServicio").val("<%=strDatosPQRS[9]%>");
                                                </script>
                                                </td>                                                     
                                        </tr>
                                        <tr>
                                            <td class="LABELFORM" colspan="2" style="width:250px;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <label for="txtNombreCargo" id="lblNombreCargo">Nombre y cargo del responsable del servicio:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" colspan="2" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                <% 
                                                    String strIdUsuario = strDatosPQRS[3];
                                                    strSQL = "select p.txtNombreCompleto, c.txtNombre from users_personas p, users_cargos c where (p.txtCargo = c.txtIdentificacion) and p.txtIdentificacion = '" +  strIdUsuario + "'";
                                                    String[] strNomUsuario = GestionSQL.getFila(strSQL,"Users");           
                                                  %>
                                                <input type='text' id='txtNombreCargo' class='CAMPOFORMREAD' value="<%=strIdUsuario%>" style="width: 400px;display:none;" readonly>
                                                <input type='text' id='txtNombreCargoC' class='CAMPOFORMREAD' value="<%=strNomUsuario[0]%> - <%=strNomUsuario[1]%>" style="width: 383px;" readonly>      
                                            </td>                                        
                                        </tr>              
                                        <tr>
                                            <td colspan="4">
                                                <div id="dResponsable" style="text-align: center;vertical-align: middle;border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;">
                                                        <br>
                                                        <label for="txtResponsable" class="LABELFORM" id="lblResponsable">* Nuevo responsable:</label>&nbsp;&nbsp;
                                                        <select id="txtResponsable" class="CAMPOFORM" style="width: 400px; vertical-align: middle;">
                                                            <option value="-1">Seleccione una opción</option>
                                                             <%for (int i=0;i<arrIdsP.size();i++){%>
                                                                <option value="<%=arrIdsP.get(i)%>"><%=arrNombresP.get(i)%> - <%=arrCargosP.get(i)%></option>
                                                            <%}%>
		</select>
		&nbsp;<img src="Images/error.png" id="imgResponsable" alt="Campo obligatorio" class="IMGERROR"><br>
                                                    <br>
                                                    <input type="button" value="Guardar nuevo responsable" id="btnGuardarResp" class="BOTONFORM" style="width: 160px;">&nbsp;
                                                    <input type="button" value="Ocultar sección" id="btnOcultarS" class="BOTONFORM" style="width: 100px;">
                                                    <br><br>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                                <td colspan="2" class="LABELFORM">
                                                        * ¿Desea figurar como usuario anónimo en la solicitud?:
                                                </td>
                                                <td colspan="2" class="CELDARADIOFORM" style="font-weight: normal;font-size: 12.5px;vertical-align: middle;text-align: left;">
                                                                <input type="radio" name="rdAnonimo" id="rdSI" value="S">Si
                                                                <input type="radio" name="rdAnonimo" id="rdNO" value="N">No
                                                                &nbsp;<img src="Images/error.png" id="imgAnonimo" alt="Campo obligatorio" class="IMGERROR">
                                                                <script type="text/javascript">
                                                                        
                                                                        var strAnonimo = "<%=strDatosPQRS[14]%>";                                                       

                                                                        if (strAnonimo == "S"){
                                                                            $("#rdSI").attr('checked', 'checked');                                                      
                                                                        }else{                                 
                                                                            $("#rdNO").attr('checked', 'checked');   
                                                                        }
                                                                        $("#rdSI").attr('disabled', 'disabled'); 
                                                                        $("#rdNO").attr('disabled', 'disabled'); 
                                                                </script>
                                                </td>                                                
                                        </tr>
                                        <tr>
                                            <td colspan="4">
                                                <%if (strMostrarDatosUsuario.equals("S")){%>
                                                    <div id="dDatosUsuario">
                                                            <table cellspacing="0" cellpadding="0" border="0" width="100%">
                                                                    <tr>
                                                                        <td class="LABELFORM" style="width: 189px;"><label for="txtTipoEntidad" id="lblTipoEntidad">* Tipo de entidad:</label></td>
                                                                        <td class="CELDARADIOFORM" style="width: 230px;font-weight: normal;font-size: 12.5px;vertical-align: middle;">
                                                                            <input type="radio" name="rdTipoEntidad" id="rdInt" value="I" disabled="disabled">Interna
                                                                            <input type="radio" name="rdTipoEntidad" id="rdUdeA" value="U" disabled="disabled">UdeA No SIU
                                                                            <input type="radio" name="rdTipoEntidad" id="rdExt" value="E" disabled="disabled">Externa
                                                                            <script type="text/javascript">
                                                                                $("[name=rdTipoEntidad]").filter("[value='<%=strDatosPQRS[4]%>']").prop("checked",true);                                                   
                                                                            </script>                                                
                                                                        </td>        
                                                                        <td class="LABELFORM" colspan="2" style="padding-left: 0px;">
                                                                            <div id="dInterno">
                                                                                <label for="txtGrupo" id="lblGrupo">* Grupo:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                <select name="txtGrupo" id="txtGrupo" class="CAMPOFORM" style="width: 311px;" disabled="disabled">
                                                                                    <option value="-1">Seleccione una opción</option>    
                                                                                    <%for (int i=0;i<arrCodigosG.size();i++){%>
                                                                                        <option value="<%=arrCodigosG.get(i)%>"><%=arrNombresG.get(i)%></option>
                                                                                    <%}%>
                                                                                </select>                                                    
                                                                            </div>      
                                                                            <div id="dExterno">
                                                                                <label for="txtNomEntidad" id="lblNomEntidad">* Nombre de la entidad:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                <input type="text" name="txtNomEntidad" id="txtNomEntidad" class="CAMPOFORM" style="width: 224px;" readOnly>
                                                                            </div>       
                                                                                <script type="text/javascript">
                                                                                    var strTipoEntidad = $("input[name='rdTipoEntidad']:checked").val()

                                                                                    if (strTipoEntidad == "I"){
                                                                                        $("#txtGrupo").val('<%=strDatosPQRS[5]%>');
                                                                                    }else{
                                                                                        $("#txtNomEntidad").val('<%=strDatosPQRS[5]%>');
                                                                                    }
                                                                                </script>
                                                                        </td>                                       
                                                                    </tr>                                                                
                                                                    <tr>
                                                                        <td class="LABELFORM" colspan="2" style=" vertical-align: middle;">
                                                                            Número de identificación de quien presenta la solicitud:
                                                                        </td>
                                                                        <td class="CELDACAMPOFORM" colspan="2" style=" vertical-align: middle;">
                                                                            <input type="text" id="txtNumId" name="txtNumId" value="<%=strDatosPQRS[16]%>" class="CAMPOFORM" readOnly />&nbsp;<img src="Images/error.png" id="imgNumId" alt="Campo obligatorio" class="IMGERROR">
                                                                        </td>                                                             
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="LABELFORM" colspan="2" style="width:250px;"><label for="txtNombreUser" id="lblNombreUser">* Nombres y apellidos de quien presenta la solicitud:</label></td>
                                                                        <td class="CELDACAMPOFORM" colspan="2" style="width:300px;padding-left: 0px;">
                                                                            <input type="text" name="txtNombreUser" id="txtNombreUser" value="<%=strDatosPQRS[6]%>" class="CAMPOFORM" style="width: 379px;" readOnly />
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="LABELFORM" style="width:140px;">
                                                                            <label for="txtTelefono" id="lblTelefono">* Teléfono:</label>
                                                                        </td>
                                                                        <td class="CELDACAMPOFORM">
                                                                            <input type="text" name="txtTelefono" id="txtTelefono" value="<%=strDatosPQRS[7]%>" class="CAMPOFORM" style="width: 170px;" readOnly>
                                                                        </td>
                                                                        <td class="LABELFORM" style="width: 180px;padding-left: 0px;">
                                                                            <label for="txtEmail" id="lblEmail">* Correo electrónico:</label>
                                                                        </td>                                        
                                                                        <td class="CELDACAMPOFORM" style="width: 220px;">
                                                                            <input type="text" name="txtEmail" id="txtEmail" value="<%=strDatosPQRS[8]%>" class="CAMPOFORM" style="width: 190px;" readOnly>
                                                                        </td>
                                                                    </tr>                                                                          
                                                            </table>
                                                        </div>
                                                    <%}%>
                                                </td>
                                        </tr>
                                        <tr><td colspan="4" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;"></td></tr>
                                        <tr>
                                            <td class="LABELFORM"><label for="txtDescripcion" id="lblDescripcion">* Motivo del escrito:</label></td>
                                            <td colspan="3" class="CELDACAMPOFORM">                                                
                                                <div id="txtDescripcion" contenteditable="false" class="DIVEDITABLE">
                                                    <%=strCadena%>
                                                </div>                
                                            </td>
                                        </tr>
                                        <tr>                                            
                                            <%if(!(strDatosPQRS[19].equals("-"))){%>
                                                <td class="LABELFORM" style="width: 130px;">
                                                    <label for="txtSoporte" id="lblSoporte">Archivo adjunto:</label>
                                                </td>
                                                <td colspan="2" class="CELDACAMPOFORM" style="text-align: left;">                                            
                                                    <input type="button" value="Descargar" id="btnDescargar" class="BOTONFORM" onclick="descargarArchivo('<%=strDatosPQRS[19]%>');" />
                                                </td>
                                            <%}else{%>
                                                <td class="LABELFORM">
                                                    <label for="txtSoporte" id="lblSoporte">Archivo adjunto:</label>
                                                </td>
                                                <td colspan="2" class="TEXTOFALLO" style="text-align: left;font-size: 12px;vertical-align: bottom;">
                                                    [No se ha adjuntado ningún archivo a la solicitud]
                                                </td>
                                            <%}%>
                                        </tr>
                                        <tr><td colspan="4" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #116043;padding-bottom: 10px;"></td></tr>                      
                                        <tr>
                                            <td colspan="4" class="CELDABOTONFORM">                                                     
                                                    <input type="button" value="Respuesta" id="btnRespuesta" class="BOTONFORM"> &nbsp;&nbsp;      
                                                    <%if(strEsAsistente.equals("S")){%>
                                                        <input type="button" value="Reclasificar" id="btnReclasificar" class="BOTONFORM" onclick="abrirReclasificar('<%=strDatosPQRS[0]%>','<%=strDatosPQRS[2]%>')"> &nbsp;&nbsp;      
                                                    <%}%>
                                                    <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();"> &nbsp;&nbsp;                                                    
                                                    <%
                                                        String strEstadoActual = strDatosPQRS[11];
                                                        if( (!strEstadoActual.equals("CPU") && (strCedula.equals(strDatosPQRS[3].toString()))) || (!strEstadoActual.equals("CPU") && strEsAsistente.equals("S"))){%>
                                                                 <input type="button" value="Cambiar responsable" id="btnCambiarResp" class="BOTONFORM" style="width: 130px;"> &nbsp;&nbsp;                                                                 
                                                           <% }                                                          
                                                    %>                                                    
                                                    &nbsp;&nbsp;&nbsp;&nbsp;<img src="Images/loader.gif" id="imgLoad" class="IMGLOAD">
                                            </td>
                                        </tr>
                                        <tr><td colspan="4" class="MSGAVISOOBLG">Los campos marcados con (*) son obligatorios.</td></tr>
                                    </table>
                                </form>
                            </td>
                        </tr>
                    </table>   
                </div>                                         
                <form method="POST" id="frmRpta" name="frmRpta" enctype="multipart/form-data" action="GestionArchivos">
                    <div id="dRpta">                                        
                            <input type="hidden" name="txtExtension" id="txtExtension" />
                            <input type="hidden" name="txtIdEstado" id="txtIdEstado" value="<%=strDatosPQRS[11]%>">
                            <input type="hidden" name="txtRadicado" id="txtRadicado" value="<%=strDatosPQRS[0]%>">
                            <table cellspacing="0" cellpadding="0" width="880px" border="0" class="TABLAPQRS">
                                <tr>
                                    <td class="TITULOFORM" colspan="4" >REGISTRO DE SOLICITUD # <%=strDatosPQRS[0]%></td>
                                </tr>                               
                                <%if (strIdEstado.equals("PR")){%>                            
                                        <input type="hidden" name="txtRadicado" id="txtRadicado" value="<%=strDatosPQRS[0]%>">
                                        <tr>
                                            <td class="LABELFORM"><label for="txtRpta" id="lblRpta">* Respuesta final:</label></td>
                                            <td colspan="3" class="CELDACAMPOFORM">
                                                <%if (strCedula.equals(strDatosPQRS[3].toString())){%>
                                                    <textarea rows="10" cols="105" name="txtRpta" id="txtRpta" class="TEXTAREA" style="resize: none;"></textarea>&nbsp;&nbsp;
                                                <%}else{%>
                                                    <textarea rows="10" cols="105" name="txtRpta" id="txtRpta" class="TEXTAREA" style="resize: none;" readOnly></textarea>&nbsp;&nbsp;
                                                <%}%>                                        
                                                <script language="javascript" type="text/javascript">    
                                                    var strValor = "";
                                                    <%
                                                        if (arrRpta != null){
                                                            for (int i=0;i<arrRpta.length;i++){%>        
                                                                strValor += trim("<%=arrRpta[i]%>") + "\n";                                                        
                                                    <%   }
                                                         }%>    
                                                    if (trim(strValor) == ""){
                                                        strValor = "";
                                                    }
                                                    $("#txtRpta").val(strValor);
                                                </script>
                                                <img src="Images/error.png" id="imgRpta" alt="Campo obligatorio" class="IMGERROR">
                                            </td> 
                                        </tr>
                                        <%if (strCedula.equals(strDatosPQRS[3].toString())){%>
                                            <tr>
                                                <td class="LABELFORM">
                                                    <label for="txtClasificacion" id="lblClasificacion">* Clasificación PQRSFD:</label>
                                                </td>
                                                <td class="CELDACAMPOFORM">                          
                                                    <select id="txtClasificacion" name="txtClasificacion" class="CAMPOFORM" style="width: 180px;">
                                                        <option value="-1">Seleccione una opción</option>
                                                        <option value="PD">Por Definir</option>
                                                        <option value="P">Procedente</option>    
                                                        <option value="NP">No Procedente</option>                                                                                               
                                                    </select>
                                                    &nbsp;<img src="Images/error.png" id="imgClasificacion" alt="Campo obligatorio" class="IMGERROR">                                         
                                                </td>
                                                <td class="LABELFORM">
                                                    <label for="txtAnexo" id="lblAnexo">Adjuntar archivo:</label>
                                                </td>
                                                <td class="CELDACAMPOFORM">                                            
                                                    <input type="file" name="txtAnexo" id="txtAnexo" class="CAMPOFORM" style="width: 200px;"  />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="LABELFORM">
                                                    <label for="txtRed" id="lblRed">Red de ingreso:</label>
                                                </td>
                                                 <td class="CELDACAMPOFORM">                          
                                                    <select id="txtRed" name="txtRed" class="CAMPOFORM" style="width: 180px;">
                                                        <option value="-1">Seleccione una opción</option>              
                                                        <%for (int i=0;i<arrIdsR.size();i++){%>
                                                        <option value="<%=arrIdsR.get(i)%>"><%=arrNombresR.get(i)%></option>
                                                    <%}%>
                                                    </select>
                                                    &nbsp;<img src="Images/error.png" id="imgRed" alt="Campo obligatorio" class="IMGERROR">                                         
                                                </td>
                                            </tr>
                                        <%}else{%>  
                                            <tr>
                                                <td class="LABELFORM">
                                                    <label for="txtClasificacion" id="lblClasificacion">Clasificación PQRSFD:</label>
                                                </td>
                                                <td class="CELDACAMPOFORM">
                                                    <select id="txtClasificacion" name="txtClasificacion" class="CAMPOFORM" style="width: 180px;" readOnly disabled="disabled">
                                                        <option value="-1">Seleccione una opción</option>
                                                        <option value="PD">Por Definir</option>
                                                        <option value="P">Procedente</option>
                                                        <option value="NP">No Procedente</option>
                                                    </select>
                                                    &nbsp;<img src="Images/error.png" id="imgClasificacion" alt="Campo obligatorio" class="IMGERROR">                                         
                                                </td>
                                                <td class="LABELFORM">
                                                    <label for="txtAnexo" id="lblAnexo">Archivo adjunto:</label>
                                                </td>
                                                <td class="TEXTOFALLO" style="text-align: left;font-size: 12px;vertical-align: bottom;">
                                                    [No se ha adjuntado ningún archivo a la solicitud]
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="LABELFORM">
                                                    <label for="txtRed" id="lblRed">Red de ingreso:</label>
                                                </td>
                                                 <td class="CELDACAMPOFORM">                          
                                                    <select id="txtRed" name="txtRed" class="CAMPOFORM" style="width: 180px;" readOnly disabled="disabled">
                                                        <option value="-1">Seleccione una opción</option>              
                                                        <%for (int i=0;i<arrIdsR.size();i++){%>
                                                        <option value="<%=arrIdsR.get(i)%>"><%=arrNombresR.get(i)%></option>
                                                    <%}%>
                                                    </select>
                                                    &nbsp;<img src="Images/error.png" id="imgRed" alt="Campo obligatorio" class="IMGERROR">                                         
                                                </td>
                                            </tr>
                                        <%}%>            
                                        <tr><td colspan="4" style="height: 10px;"></td></tr>
                                        <tr>
                                            <td class="CELDABOTONFORM" colspan="4">
                                                <input type="button" value="Volver" id="btnVerPQRS" class="BOTONFORM">&nbsp; &nbsp;                               
                                                    <%if (strCedula.equals(strDatosPQRS[3].toString())){%>
                                                        <input type="button" value="Cerrar solicitud" id="btnAtender" class="BOTONFORM" style="width: 100px;" onclick="validarFormRpta();">&nbsp; &nbsp; 
                                                    <%}%>                                     
                                                <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                            </td>
                                        </tr>                    
                                <%}else{%>
                                    <tr>
                                        <td class="LABELFORM"><label for="txtRpta" id="lblRpta">Respuesta final:</label></td>
                                        <td colspan="3" class="CELDACAMPOFORM" style="text-align: left;">
                                            <textarea rows="10" cols="108" name="txtRpta" id="txtRpta" class="TEXTAREA" style="resize: none;" readonly></textarea>       
                                            <script language="javascript" type="text/javascript">    
                                                var strValor = "";
                                                <%
                                                    if (arrRpta != null){
                                                        for (int i=0;i<arrRpta.length;i++){%>        
                                                            strValor += trim("<%=arrRpta[i]%>") + "\n";                                                        
                                                <%   }
                                                    }%>   
                                                if (trim(strValor) == ""){
                                                    strValor = "";
                                                }
                                                $("#txtRpta").val(strValor);
                                            </script>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4" style="height: 7px;"></td></tr>
                                    <tr>
                                        <td class="LABELFORM">
                                            <label for="txtClasificacion" id="lblClasificacion">Clasificación PQRSFD:</label>
                                        </td>
                                        <td class="CELDACAMPOFORM">                          
                                            <select id="txtClasificacion" name="txtClasificacion" class="CAMPOFORM" style="width: 180px;" disabled="disabled">
                                                <option value="-1">Seleccione una opción</option>
                                                <option value="PD">Por Definir</option>
                                                <option value="P">Procedente</option>
                                                <option value="NP">No Procedente</option>
                                            </select>
                                            <script type="text/javascript">
                                                    $("#txtClasificacion").val("<%=strDatosPQRS[17]%>");
                                                </script>
                                            &nbsp;<img src="Images/error.png" id="imgClasificacion" alt="Campo obligatorio" class="IMGERROR">                                         
                                        </td>
                                        <%if(!(strDatosPQRS[18].equals("-"))){%>
                                            <td class="LABELFORM" style="width: 130px;">
                                                <label for="txtAnexo" id="lblAnexo">Archivo adjunto:</label>
                                            </td>
                                            <td class="CELDACAMPOFORM" style="text-align: left;">                                            
                                                <input type="button" value="Descargar" id="btnDescargar" class="BOTONFORM" onclick="descargarArchivo('<%=strDatosPQRS[18]%>');" />
                                            </td>
                                        <%}else{%>
                                            <td class="LABELFORM">
                                                    <label for="txtAnexo" id="lblAnexo">Archivo adjunto:</label>
                                                </td>
                                                <td class="TEXTOFALLO" style="text-align: left;font-size: 12px;vertical-align: bottom;">
                                                    <input type="file" name="txtAnexo" id="txtAnexo" class="CAMPOFORM" style="width: 180px;"  />&nbsp;
                                                    <img src="Images/guardar.png" id="imgGuardar" alt="Campo obligatorio" style="padding-left: 30px;height: 18px;width: 18px;" />               
                                                </td>
                                        <%}%>
                                    </tr>
                                    <tr>
                                        <td class="LABELFORM">
                                            <label for="txtRed" id="lblRed">Red de ingreso:</label>
                                        </td>
                                         <td class="CELDACAMPOFORM">                          
                                            <select id="txtRed" name="txtRed" class="CAMPOFORM" style="width: 180px;" readOnly disabled="disabled">
                                                <option value="-1">Seleccione una opción</option>              
                                                <%for (int i=0;i<arrIdsR.size();i++){%>
                                                <option value="<%=arrIdsR.get(i)%>"><%=arrNombresR.get(i)%></option>
                                            <%}%>
                                            </select>
                                             <script type="text/javascript">
                                                    $("#txtRed").val("<%=strDatosPQRS[20]%>");
                                                </script>
                                            &nbsp;<img src="Images/error.png" id="imgRed" alt="Campo obligatorio" class="IMGERROR">                                         
                                        </td>
                                        <td class="LABELFORM"><label for="txtFechaRpta" id="lblRpta">Fecha de respuesta:<br>&nbsp;&nbsp;(aaaa-mm-dd)</label></td>
                                        <% String strFechaRpta="";
                                                if (strDatosPQRS[13] == null){
                                                    strFechaRpta = "Por asignar";
                                                }else{
                                                    strFechaRpta = strDatosPQRS[13].toString();
                                                }
                                        %>
                                        <td class="CELDACAMPOFORM" style="text-align: left;"><input type="text" name="dtFechaRpta" id="dtFechaRpta" value="<%=strFechaRpta%>" class="CAMPOFORM" readOnly></td>
                                </tr>
                                <tr><td colspan="4" style="height: 10px;"></td></tr>
                                <tr>
                                    <td class="CELDABOTONFORM" colspan="4">
                                            <input type="button" value="Volver" id="btnVerPQRS" class="BOTONFORM">&nbsp; &nbsp;                                             
                                            <%
                                                    if (intNumObs > 0){
                                                            strEstadoActual = strDatosPQRS[11];
                                                            if((strEstadoActual.equals("AT")) || (strEstadoActual.equals("CPU"))) {%>
                                                                    <input type="button" value="Ver Observaciones" id="btnVerObs" class="BOTONFORM" style="width: 120px;">&nbsp; &nbsp;                                                                 
                                            <%          }
                                                    }%>
                                            <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                    </td>
                                </tr>
                                <%}%>                                                 
                                <tr><td colspan="4" class="MSGAVISOOBLG" style="padding-left: 5px;padding-bottom: 5px;">Los campos marcados con (*) son obligatorios.</td></tr>
                            </table>                                  
                    </div>                      
                </form>
            <%}%>
            <br>
            <div id="dMensaje">                                
            </div>
            <br>
        </div>        
        <jsp:include page="footer.jsp" />	
    </body>
</html>