/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $("img#imgErrorRol").hide();
        $("img#imgErrorUsuario").hide();
        
        var strAccion = $("input#txtAccion").val();

        if (strAccion == "C"){
            $("input#txtRol").focus();
        }       
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {     
    $('.IMGERROR').hide();  
    $("#btnGuardar").click(function() {     
        
        // Validación de los campos del formulario.
        
        $('.IMGERROR').hide();       
        
        var strIdRol = $("#txtRol").val();
        
        if (strIdRol == '-1'){        
            $("img#imgErrorRol").show();  
            $("#txtRol").focus();  
            return false;  
        }else{
            $("img#imgErrorRol").hide();
        }           
        
        var strIdUsuario = $("#txtUsuario").val()
        
        if (strIdUsuario == '-1'){
            $("img#imgErrorUsuario").show();  
            $("#txtUsuario").focus();  
            return false;  
        }else{
            $("img#imgErrorUsuario").hide();
        }
               
       // Instrucciones cuando se hace el submit correctamente.
        
        var strForm = $("input#txtForm").val();
        var strAccion = $("input#txtAccion").val();        
        
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtForm=" + strForm + "&txtAccion=" + strAccion + '&txtIdRol='+ strIdRol + '&txtIdUsuario=' + strIdUsuario;          
        
        // Envío de petición AJAX.
        
        AJAX("POST","Registro",dataString,"dMensaje");
         
    });  
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $("#txtRol").val("-1");
        $("#txtUsuario").val("-1");     
        cargaInicial();
        $("#dMensaje").hide();
    }); 
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
});  






