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
    String strSQL = "", strRadicado, strUsuario;
    strRadicado = request.getParameter("txtRadicado");
    strUsuario = (String) session.getAttribute("txtCedula"); 
    String[] strTemp;
    Vector arrIds = new Vector();
    Vector arrFechas = new Vector();
    Vector arrObs = new Vector();
    Vector arrEstados = new Vector();
    Vector arrFechasCierre = new Vector();
            
    strSQL = "select o.txtIdObs,o.dtFecha,o.txtObs,o.txtAtendida, o.dtFechaCierre from buzon.buzon_obs_x_sol o where o.txtRadicado = '" + strRadicado + "' order by o.dtFecha";
    Vector arrDatosObs = GestionSQL.consultaSQL(strSQL, "Buzon", "OBS");
    
    if (arrDatosObs != null){
        for (int i=0;i<arrDatosObs.size();i++){
            strTemp = arrDatosObs.get(i).toString().split(">");
            arrIds .add(strTemp[0]);
            arrFechas.add(strTemp[1]);
            arrObs.add(strTemp[2]);
            arrEstados.add(strTemp[3]);
            arrFechasCierre.add(strTemp[4]);
        }
    }
%>
<html>
    <head>
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/PQRS.css" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>          
        <script type="text/javascript" src="Scripts/JSPQRS.js"></script>
        <title>Observaciones</title>
        <script type="text/javascript">
            function actualizar(){
                window.setTimeout(function(){location.href='listar_obs.jsp?txtRadicado=' + <%=strRadicado%>;},1000);   
            }
        </script>
    </head>
    <body>       
        <div align="center" style="margin-top: 20px;">    
            <form id="frmActualizar" name="frmActualizar">
                <input type="button" id="btnActualizar" style="display: none" onclick="actualizar();" />
            </form>
            <table cellspacing="0" cellpadding="0" width="900px" border="0">
                <tr>
                    <td style="height: 10px;"></td>
                </tr>
                <tr>
                    <td class="TITULOFORM">OBSERVACIÓN(ES) DE LA SOLICITUD #<%=strRadicado%></td>
                </tr>
                <tr>
                    <td>                
                        <%if (strUsuario != null){%>
                            <table cellspacing="0" cellpadding="0" width="900px" border="0" class="TABLAINFO">       
                                <tr>
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043; width: 100px;">Fecha de creación<br>(aaaa-mm-dd)</td>                                    
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">Observación del usuario</td>                                
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">¿Respondida?</td>
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">Fecha de respuesta<br>(aaaa-mm-dd)</td>
                                    <td class="SUBTITULOFORM">Acción</td>                                       
                                </tr>                            
                                <% for(int i=0;i<arrIds.size();i++){%>
                                    <tr>
                                        <td class="TEXTOINFO" style="border-right: 1px solid #116043;width: 110px;">
                                            <%=arrFechas.get(i)%>
                                        </td>
                                        <td class="TEXTOINFO" style="text-align: left;width: 620px;border-right: 1px solid #116043">
                                            <%=arrObs.get(i)%>
                                        </td>
                                        <td class="TEXTOINFO" style="border-right: 1px solid #116043;width: 60px;">
                                            <%
                                                String strIdEstado = arrEstados.get(i).toString();

                                                if (strIdEstado.equals("S")){%>
                                                    Si
                                            <% }else{%>
                                                    No
                                                <%} %>                  
                                        </td>                                        
                                        <td class="TEXTOINFO" style="width: 100px;border-right: 1px solid #116043;">
                                            <% if (strIdEstado.equals("N")){%>
                                                Por definir
                                             <%}else{%>
                                                <%=arrFechasCierre.get(i).toString()%>
                                              <%}%>                                            
                                        </td>
                                        <td class="TEXTOINFO" style="width: 50px;">                                      
                                                <% if (strIdEstado.equals("N")){%>
                                                    <a href="#" onclick="CerrarObs('<%=arrIds.get(i).toString()%>','<%=strRadicado%>')">Responder</>
                                                <%}else{%>      
                                                    <a href="#" onclick="VerObs('<%=arrIds.get(i).toString()%>','<%=strRadicado%>')">Ver</>
                                                <%}%>
                                        </td>                                        
                                    </tr>
                                    <tr>
                                        <td colspan="5" style="border-bottom: 1px solid #116043;"></td>
                                    </tr>
                                <% }%>                            
                                <tr>
                                    <td style="text-align: center; padding-top: 10px;" colspan="4">
                                        <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="height: 10px;"></td>
                                </tr>
                            </table>
                        <%}else{%>
                            <table cellspacing="0" cellpadding="0" width="900px" border="0" class="TABLAINFO">       
                                <tr>
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043; width: 100px;">Fecha de creación<br>(aaaa-mm-dd)</td>                                    
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">Observación del usuario</td>                                                
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">¿Respondida?</td>
                                    <td class="SUBTITULOFORM" style="border-right: 1px solid #116043;">Fecha de respuesta<br>(aaaa-mm-dd)</td>
                                    <td class="SUBTITULOFORM">Acción</td>
                                </tr>                            
                                <% for(int i=0;i<arrIds.size();i++){%>
                                    <tr>
                                        <td class="TEXTOINFO" style="border-right: 1px solid #116043;width: 110px;">
                                            <%=arrFechas.get(i)%>
                                        </td>
                                        <td class="TEXTOINFO" style="border-right: 1px solid #116043;text-align: left;width: 620px;">
                                            <%=arrObs.get(i)%>
                                        </td>
                                        <td class="TEXTOINFO" style="width: 60px;border-right: 1px solid #116043;">
                                            <%
                                                String strIdEstado = arrEstados.get(i).toString();

                                                if (strIdEstado.equals("S")){%>
                                                    Si
                                            <% }else{%>
                                                    No
                                                <%}%>                  
                                        </td>           
                                        <td class="TEXTOINFO" style="width: 100px;border-right: 1px solid #116043;">                                      
                                                <% if (strIdEstado.equals("N")){%>
                                                    Por definir
                                                <%}else{%>
                                                    <%=arrFechasCierre.get(i).toString()%>
                                                <%}%>                 
                                        </td>
                                        <% if (strIdEstado.equals("S")){%>
                                            <td class="TEXTOINFO">
                                                <a href="#" onclick="VerObs('<%=arrIds.get(i).toString()%>','<%=strRadicado%>')">Ver</>
                                            </td>
                                        <%}else{%>
                                           <td class="TEXTOINFO">
                                                -
                                            </td>
                                        <%}%>
                                    </tr>                                    
                                    <tr>
                                        <td colspan="5" style="border-bottom: 1px solid #116043;"></td>
                                    </tr>
                                <% }%>                            
                                <tr>
                                    <td style="text-align: center; padding-top: 10px;" colspan="5">
                                        <input type="button" value="Salir" class="BOTONFORM" onclick="javascript:window.close();">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="height: 10px;"></td>
                                </tr>
                            </table>
                        <%}%>                        
                    </td>                
                </tr>                
            </table>
        </div>
        <div id="dMensaje" align="center">
        </div>        
    </body>
</html>