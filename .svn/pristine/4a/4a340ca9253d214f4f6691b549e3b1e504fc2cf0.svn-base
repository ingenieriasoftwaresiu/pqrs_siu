/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $('.IMGERROR').hide();  
        
        var strAccion = $("input#txtAccion").val();

        if (strAccion == "C"){
            $("input#txtCodigo").focus();
        }else{
            $("input#txtNombre").focus();
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
        var strCodigo = $("input#txtCodigo").val();  

        if (strCodigo == "") {  
            $("img#imgErrorCodigo").show();  
            $("input#txtCodigo").focus();  
            return false;  
        }else{
             $("img#imgErrorCodigo").hide();  
        } 

        var strNombre = $("input#txtNombre").val();  

        if (strNombre == "") {  
            $("img#imgErrorNombre").show();  
            $("input#txtNombre").focus();  
            return false;  
        }else{
            $("img#imgErrorNombre").hide();
        }             
       
       // Instrucciones cuando se hace el submit correctamente.
        
        var strForm = $("input#txtForm").val();
        var strAccion = $("input#txtAccion").val();        
        
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtForm=" + strForm + "&txtAccion=" + strAccion + '&txtCodigo='+ strCodigo + '&txtNombre=' + strNombre;          
        
        // Envío de petición AJAX.
        
        AJAX("POST","Registro",dataString,"dMensaje");
         
    });  
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $("input#txtCodigo").val("");
        $("input#txtNombre").val("");
        $("input[name='group']:radio").attr('checked', false);
        cargaInicial();
    }); 
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
});  






