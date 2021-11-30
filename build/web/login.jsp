<%-- 
    Document   : index
    Created on : 06-jul-2012, 14:09:01
    Author     : Jorge.correa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles/Login.css" />
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSComunes.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSAjax.js"></script>
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="Scripts/JSLogin.js"></script>
        <title>Sistema para la atención de Peticiones, Quejas, Reclamos, Sugerencias y Denuncias (PQRSFD) - ASIU</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div style="padding-top: 50px;"></div>
        <div align="center">      
            <br>
            <form id="frmLogin" name="frmLogin" method="POST" action="Ingreso">
                    <table cellspacing="0" cellpadding="0" width="290px" class="TABLAFORM" border="0">                        
                        <tr>
                                <td colspan="3" style="text-align: center;" class="TITULOFORM">INICIO DE SESIÓN</td>				
                        </tr>                   
                        <tr>
                                <td class="LABELFORM" style="width: 80px;">Usuario:</td>
                                <td class="CELDACAMPOFORM" style="width: 100px;">
                                    <input type="text" name="txtUsuario" id="txtUsuario" class="CAMPOFORM">
                                </td>
                                <td style="width: 30px;text-align: left"><img src="Images/error.png" id="imgUsuario" alt="Campo obligatorio" class="IMGERROR"></td>
                        </tr>
                        <tr>
                                <td class="LABELFORM" style="width: 80px;">Contraseña:</td>
                                <td class="CELDACAMPOFORM" style="width: 100px;">
                                    <input type="password" name="txtPwd" id="txtPwd" class="CAMPOFORM">
                                </td>
                                <td style="width: 30px;text-align: left"><img src="Images/error.png" id="imgPwd" alt="Campo obligatorio" class="IMGERROR"></td>
                        </tr>
                        <tr>
                                <td colspan="3" class="CELDABOTONFORM">
                                        <input type="button" value="Ingresar" id="btnIngresar" class="BOTONFORM">
                                        <input type="button" value="Limpiar" id="btnLimpiar" class="BOTONFORM">
                                        <input type="button" value="Regresar" id="btnRegresar" class="BOTONFORM">
                                </td>		
                        </tr>
                    </table>				
            </form>
            <br>
            <div id="dMensaje" class="TEXTOFALLO">                
            </div>
        </div>  
        <div style="padding-top: 30px;"></div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
