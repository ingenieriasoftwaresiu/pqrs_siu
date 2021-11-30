<%-- 
    Document   : cerrar
    Created on : 18/04/2012, 11:07:33 AM
    Author     : jorge.correa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    try{
            if (session != null){          
                session.invalidate();
            }
    }catch(IllegalStateException ise){
        response.sendRedirect("principal.jsp?txtCedula=null");
    }
%>

<html>
    <head>        
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <link rel='stylesheet' type='text/css' href='Styles/Cerrar.css'/> 
        <meta HTTP-EQUIV="REFRESH" content="5; url=principal.jsp?txtCedula=null">
        <script language="javascript">
                function redirect(){
                    location.href = "principal.jsp?txtCedula=null";
                }
        </script>
        <title>Sesión finalizada</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class='TEXTOCIERRE'>
            ¡Abandonaste la aplicación!. Para cerrar esta ventana, utilice el botón "X" ubicado en la esquina superior derecha de su navegador.<br /><br />
            <input type="button" value="Regresar" class="BOTONFORM" onclick="redirect();" />          
        </div>
        <br><br><br><br><br>
        <jsp:include page="footer.jsp" />     
    </body>
</html>
           


