<%-- 
    Document   : cerrar
    Created on : 18/04/2012, 11:07:33 AM
    Author     : jorge.correa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%                
    String strMensaje = (String) request.getParameter("mensaje");           
%>

<html>
    <head>        
        <link rel="SHORTCUT ICON" href="Images/favicon.ico" />
        <script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script> 
        <link rel='stylesheet' type='text/css' href='Styles/Comunes.css'/>              
        <title>Notificación del sistema</title>
        <script type="text/javascript">
            function salir(){
                javascript:window.close();
            }
            
            document.onkeydown= function(evt) {         
                if (!evt){
                    evt = event;
                }
                           
                if ((evt.keyCode == 116) || (evt.which == 8) || (evt.ctrlKey && evt.keyCode == 116)){   
                    evt.preventDefault();
                }                                
            }
            
           $(function(){
                var rx = /INPUT|SELECT|TEXTAREA/i;

                $(document).bind("keydown keypress", function(e){
                    if(e.which == 8){ // 8 == backspace
                        if(!rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly ){
                            e.preventDefault();
                        }
                    }
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <br /><br /><br /><br/>
        <div class='TEXTOMENSAJE' align="center">
            <%=strMensaje%>            
            <br /><br/>
            <input type="button" value="Regresar" class="BOTONFORM" onclick="salir()" />          
        </div>        
        <br /><br/><br/>
        <jsp:include page="footer.jsp" />     
    </body>
</html>
           


