<%-- 
    Document   : pqrs
    Created on : 11-sep-2012, 11:07:14
    Author     : jorge.correa
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Conexion.GestionSQL"%>
<!DOCTYPE html>
<%    
   String[] strTemp = null;
    Vector arrCodigosPQRS = new Vector();
    Vector arrNombresPQRS = new Vector();        
    Vector arrCodigosS = new Vector();
    Vector arrNombresS = new Vector();    
       
    String strSQL = "select r.txtCodigo as Id, r.txtNombre as Nombre from buzon_retroalimentacion r ORDER BY r.txtNombre";
    Vector arrTiposPQRS = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");
                
    for (int i=0;i<arrTiposPQRS.size();i++){
        strTemp = arrTiposPQRS.get(i).toString().split(",");
        arrCodigosPQRS.add(strTemp[0]);
        arrNombresPQRS.add(strTemp[1]);
    }
    
    strSQL = "select s.txtCodigo as Id, s.txtNombre as Nombre from users_servicios s order by s.txtNombre";
    Vector arrServicios = GestionSQL.consultaSQL(strSQL,"Users","MAESTROS");

    for (int i=0;i<arrServicios.size();i++){
        strTemp = arrServicios.get(i).toString().split(",");
        arrCodigosS.add(strTemp[0]);
        arrNombresS.add(strTemp[1]);
    }
       
%>
<html>
    <head>
        <link rel="SHORTCUT ICON" href="../Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../Styles/Informes.css" />        
        <link rel="stylesheet" type="text/css" href="../Styles/calendar-system.css" />
        <script charset="UTF-8" type="text/javascript" src="../Scripts/JSCalendar.js"></script>
        <script charset="UTF-8" type="text/javascript" src="../Scripts/JSCalendar-es.js"></script>
        <script charset="UTF-8" type="text/javascript" src="../Scripts/JSCalendar-setup.js"></script>
        <script type="text/javascript" charset="UTF-8" src="../Scripts/JSComunes.js"></script>
        <script type="text/javascript" charset="UTF-8" src="../Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="../Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" charset="UTF-8" src="../Scripts/JSInformeSegPQRS.js"></script>
        <title>Informe: Seguimiento a PQRSD</title>
    </head>
    <body>
        <jsp:include page="headerPQRS.jsp" />
        <div align="center">            
            <br><br>              
            <form method="POST" action="" id="frmParametros" name="frmParametros">
                <table cellspacing="0" cellpadding="0" width="980px" border="0" class="TABLAFORM">                     
                    <tr>
                        <td colspan="3" class="TITULOFORM">PARÁMETROS PARA LA GENERACIÓN DEL INFORME</td>
                    </tr>
                    <tr><td style="height: 10px;"></td></tr>
                    <tr>
                        <td class="LABELFORM" style="width: 100px;">Filtros:</td>
                        <td class="CELDACAMPOFORM" style="width: 220px;text-align: left;">
                            <input type="checkbox" name="cbFiltro" id="cbTipoPQRS" value="T">Tipo de solicitud<br>
                            <input type="checkbox" name="cbFiltro" id="cbServicio" value="S">Servicio<br>
                            <input type="checkbox" name="cbFiltro" id="cbRangoFechas" value="R">Rango de fechas<br>              
                            <img src="../Images/error.png" id="imgCriterio" alt="Campo obligatorio" class="IMGERROR">
                        </td>
                        <td style="vertical-align: middle;text-align: left;">                            
                            <div id="dTipoPQRS">
                                <br>
                                <label for="txtTipoPQRS" class="LABELFORM">* Tipo de solicitud:</label>&nbsp;&nbsp;                         
                                <select name="txtTipoPQRS" id="txtTipoPQRS" class="CAMPOFORM">
                                    <option value="-1">Seleccione una opción</option>
                                    <%for (int i=0;i<arrCodigosPQRS.size();i++){%>
                                        <option value="<%=arrCodigosPQRS.get(i)%>"><%=arrNombresPQRS.get(i)%></option>
                                    <%}%>
                                </select>    
                                <img src="../Images/error.png" id="imgTipoPQRS" alt="Campo obligatorio" class="IMGERROR">
                            </div>                            
                            <div id="dServicio">
                                <br>
                                <label for="txtServicio" class="LABELFORM">* Servicio:</label>&nbsp;&nbsp;                         
                                <select name="txtServicio" id="txtServicio" class="CAMPOFORM" style="width: 300px;">
                                    <option value="-1">Seleccione una opción</option>
                                    <%for (int i=0;i<arrCodigosS.size();i++){%>
                                        <option value="<%=arrCodigosS.get(i)%>"><%=arrNombresS.get(i)%></option>
                                    <%}%>
                                </select>    
                                <img src="../Images/error.png" id="imgServicio" alt="Campo obligatorio" class="IMGERROR">
                            </div>  
                            <div id="dRangoFechas">
                                <br>
                                <label for="txtFechaInicial" class="LABELFORM">* Fecha inicial:</label>&nbsp;&nbsp;                      
                                <input type="text" name="txtFechaInicial" id="txtFechaInicial" class="CAMPOFORM" style="width: 150px;" readOnly>&nbsp;&nbsp;<img src="../Images/Calendario.JPG" id="imgFechaI" style="vertical-align: middle;">&nbsp;<img src="../Images/error.png" id="imgFechaInicial" alt="Campo obligatorio" class="IMGERROR">&nbsp;              
                                <label for="txtFechaFinal" class="LABELFORM">* Fecha final:</label>&nbsp;&nbsp;                                                  
                                <input type="text" name="txtFechaFinal" id="txtFechaFinal" class="CAMPOFORM" style="width: 150px;" readOnly>&nbsp;&nbsp; <img src="../Images/Calendario.JPG" id="imgFechaF" style="vertical-align: middle;">&nbsp;<img src="../Images/error.png" id="imgFechaFinal" alt="Campo obligatorio" class="IMGERROR">                         
                            </div>
                            <br>
                        </td>
                    </tr>
                     <tr><td style="height: 10px;"></td></tr>
                    <tr>
                        <td colspan="3" class="CELDABOTONFORM">
                            <input type="button" name="btnGenerar" id="btnGenerar" value="Generar" class="BOTONFORM">
                            <input type="button" name="btnLimpiar" id="btnLimpiar" value="Limpiar" class="BOTONFORM">
                            <input type="button" name="btnExportar" id="btnExportar" value="Exportar" class="BOTONFORM">
                            <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                        </td>                        
                    </tr>
                    <tr><td style="height: 10px;" colspan="3" class="MSGAVISO">NOTA: Al presionar el botón Generar, si no selecciona ningún filtro, el sistema generará el informe con todos los registros disponibles.</td></tr>
                </table>
            </form>                 
            <br>          
            <div id="dMostrarInforme">                
            </div>
            <br>
        </div>        
        <jsp:include page="footerPQRS.jsp" />	
    </body>
</html>