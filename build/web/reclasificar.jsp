<%-- 
    Document   : reclasificar
    Created on : 22/12/2015, 10:35:33 AM
    Author     : jorge.correa
--%>

<%@page import="java.util.Vector"%>
<%@page import="Conexion.GestionSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    
    String strSQL=null, strRadicado=null, strTipoSolAct=null;
    String[] strTemp=null;
    Vector arrCodigosPQRS = new Vector();
    Vector arrNombresPQRS = new Vector();
    
    strRadicado = request.getParameter("txtRadicado");
    strTipoSolAct = request.getParameter("txtTipoSolAct");

    strSQL = "select r.txtCodigo as Id, r.txtNombre as Nombre from buzon_retroalimentacion r ORDER BY r.txtNombre";
    Vector arrTiposPQRS = GestionSQL.consultaSQL(strSQL,"Buzon","MAESTROS");

    for (int i=0;i<arrTiposPQRS.size();i++){
        strTemp = arrTiposPQRS.get(i).toString().split(",");
        arrCodigosPQRS.add(strTemp[0]);
        arrNombresPQRS.add(strTemp[1]);
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/PQRS.css" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>    
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSPQRS.js"></script>
        <title>Reclasificar solicitud PQRSFD</title>
    </head>
    <body>
        <div align="center">
            <table cellspacing="0" cellpadding="0" width="90%" border="0">
                <tr><td style="height: 10px;"></td></tr>
                <tr>
                    <td class="TITULOFORM">RECLASIFICAR SOLICITUD # <%=strRadicado%></td>
                </tr>                
                <tr>
                    <td>
                        <form method="POST" id="frmReclasificar" name="frmReclasificar">
                            <input type="hidden" id="txtRadicado" name="txtRadicado" value="<%=strRadicado%>" />
                            <input type="hidden" id="txtTipoSolAct" name="txtTipoSolAct" value="<%=strTipoSolAct%>" />
                            <table cellspacing="0" cellpadding="5" width="100%" border="0" class="TABLAFORM">
                                <tr>                             
                                    <td class="LABELFORM"><label for="txtTipoSol" id="lblTipoSol">* Nuevo tipo de solicitud:</label></td>
                                    <td class="CELDACAMPOFORM">
                                        <select id="cbTipoPQRS" name="cbTipoPQRS" class="CAMPOFORM" style="width: 190px;">
                                            <option value="-1">Seleccione una opción</option>    
                                                <%for (int i=0;i<arrCodigosPQRS.size();i++){%>
                                                    <option value="<%=arrCodigosPQRS.get(i)%>"><%=arrNombresPQRS.get(i)%></option>
                                                <%}%>
                                        </select>
                                        <script type="text/javascript">
                                            $("#cbTipoPQRS").val("<%=strTipoSolAct%>");
                                        </script>
                                        &nbsp;&nbsp;<img src="Images/error.png" id="imgErrorTipoSol" alt="Campo obligatorio" class="IMGERROR" style="display: none;">                                            
                                    </td>                                   
                                </tr>  
                                <tr>
                                    <td colspan="2" style="text-align: center;">
                                        <input type="button" value="Reclasificar" id="btnReclasificar" class="BOTONFORM" onclick="reclasificar('<%=strRadicado%>','<%=strTipoSolAct%>')"> &nbsp;&nbsp;  
                                        <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                    </td>
                                </tr>
                                <tr><td style="height: 0px;"></td></tr>
                            </table>                  
                        </form>
                    </td>
                </tr>             
            </table>                                        
         </div>
         <div id="dMostrar"></div>
    </body>
</html>
