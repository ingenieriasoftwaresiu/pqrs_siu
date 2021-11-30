/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $('.IMGERROR').hide();                 
        $("input#txtUsuario").focus();              
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {     
    $('.IMGERROR').hide();  
    $("#btnIngresar").click(function() {     
        
        // Validación de los campos del formulario.
        
        $('.IMGERROR').hide();  
        var strUsuario = $("input#txtUsuario").val();  

        if (strUsuario == "") {  
            $("img#imgUsuario").show();  
            $("input#txtUsuario").focus();  
            return false;  
        }else{
             $("img#imgUsuario").hide();  
        } 

        var strPwd = $("input#txtPwd").val();  

        if (strPwd == "") {  
            $("img#imgPwd").show();  
            $("input#txtPwd").focus();  
            return false;  
        }else{
            $("img#imgPwd").hide();
        }             
        
        // Control de SQL Injection.
      
        if (strUsuario == "' or true -- "){
            $("#txtUsuario").val("");  
            $("#txtPwd").val("");  
            $("#txtUsuario").focus();
            return false;
        }
       
       // Instrucciones cuando se hace el submit correctamente.        
        
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtUsuario="+ strUsuario + "&txtPwd=" + strPwd;          
        
        // Envío de petición AJAX.
        
        AJAX_REDIRECT("POST","Ingreso",dataString,"dMensaje","principal.jsp");
         
    });  
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $("input#txtUsuario").val("");
        $("input#txtPwd").val("");        
        cargaInicial();
    }); 
    
    // Función para regresar al Menú principal.
    
     $("#btnRegresar").click(function(){
        window.location.href = "principal.jsp?txtCedula=null";
    });     
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
    $("#txtUsuario").keypress(function(event){
        if (event.which == 13){
            event.preventDefault();
            $("#btnIngresar").click();
        }
   });
    
    $("#txtPwd").keypress(function(event){
        if (event.which == 13){
            event.preventDefault();
            $("#btnIngresar").click();
        }
    });     
    
});  






