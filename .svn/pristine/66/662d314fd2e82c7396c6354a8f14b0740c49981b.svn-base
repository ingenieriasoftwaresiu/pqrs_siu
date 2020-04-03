<%-- 
    Document   : estado
    Created on : 20/04/2012, 10:48:52 AM
    Author     : Jorge.correa
--%>

<%@page import="java.util.Vector"%>
<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strSQL = "";
    String[] strTemp = null;
    Vector arrNomTiposSol = new Vector();
    Vector arrDescripciones = new Vector();
    Vector arrTiemposRpta = new Vector();
    
    // Se recupera desde la base de datos la información de los Tipos de Solicitud.
    
    strSQL = "select r.txtNombre, r.txtDescripcion, r.txtTiempoRpta from buzon_retroalimentacion r ORDER BY r.txtNombre";
    Vector arrTiposSol = GestionSQL.consultaSQL(strSQL,"Buzon","TIPOSSOL");
    
    for (int i=0;i<arrTiposSol.size();i++){
        strTemp = arrTiposSol.get(i).toString().split(">");
        arrNomTiposSol.add(strTemp[0]);
        arrDescripciones.add(strTemp[1]);
        arrTiemposRpta.add(strTemp[2]);
    }
%>
<html>
    <head>
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/PQRS.css" />
        <title>Información</title>
    </head>
    <body>       
        <div align="center" style="margin-top: 20px;">    
            <table cellspacing="0" cellpadding="0" width="900px" border="0">
                <tr>
                    <td style="height: 10px;"></td>
                </tr>
                <tr>
                    <td class="TITULOFORM">INFORMACIÓN DEL TIPO DE SOLICITUD</td>
                </tr>
                <tr>
                    <td>
                        <table cellspacing="0" cellpadding="0" width="900px" border="0" class="TABLAINFO">       
                            <tr>
                                <td class="SUBTITULOFORM" style="border-right: 1px solid #116043; width: 100px;">Tipo de solicitud</td>
                                <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">Definición</td>
                                <td class="SUBTITULOFORM">Tiempo de respuesta <BR>(Días)</td>
                            </tr>
                            
                            <% for(int i=0;i<arrNomTiposSol.size();i++){%>
                                <tr>
                                    <td class="TEXTOTINFO">
                                        <%=arrNomTiposSol.get(i)%>
                                    </td>
                                    <td class="TEXTOINFO" style="border-right: 1px solid #116043;text-align: justify;">
                                        <%=arrDescripciones.get(i)%>
                                    </td>
                                     <td class="TEXTOINFO" style="text-align: center;">
                                        <%=arrTiemposRpta.get(i)%>
                                    </td>
                                 </tr>
                                <tr>
                                    <td colspan="3" style="border-bottom: 1px solid #116043;"></td>
                                </tr>
                            <% }%>                            
                            <tr>
                                <td style="text-align: center; padding-top: 10px;" colspan="2">
                                    <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="height: 10px;"></td>
                            </tr>
                        </table>
                    </td>                
                </tr>                
            </table>
        </div>         	
    </body>
</html>