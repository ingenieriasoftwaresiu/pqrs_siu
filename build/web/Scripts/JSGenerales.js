/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $('.IMGERROR').hide();  
        
        var strAccion = $("input#txtAccion").val();   
        $("input#txtConsecutivo").focus();   
         
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
        
        var strConsecutivo = $("input#txtConsecutivo").val();  

        if (strConsecutivo == "") {  
            $("img#imgConsecutivo").show();  
            $("input#txtConsecutivo").focus();  
            return false;  
        }else{
             $("img#txtConsecutivo").hide();  
        } 
        
        var strNroPaginas = $("input#txtNroPaginas").val();  

        if (strNroPaginas == "") {  
            $("img#imgNroPaginas").show();  
            $("input#txtNroPaginas").focus();  
            return false;  
        }else{
             $("img#txtNroPaginas").hide();  
        }
        
        var strAsistente = $("#txtAsistente").val();
        
        if (strAsistente == "-1"){
            $("img#imgErrorAsistente").show();  
            $("#txtAsistente").focus();  
            return false;  
        }else{
            $("img#imgErrorAsistente").hide();
        }
        
        var strNroDiasCierre = $("#txtNroDiasCierre").val();
        
        if (strNroDiasCierre == ""){
            $("img#imgNroDiasCierre").show();  
            $("#txtNroDiasCierre").focus();  
            return false;  
        }else{
            $("img#imgNroDiasCierre").hide();
        }
        
        var strNroDiasAlerta = $("#txtNroDiasAlerta").val();
        
        if (strNroDiasAlerta == ""){
            $("img#imgNroDiasAlerta").show();  
            $("#txtNroDiasAlerta").focus();  
            return false;  
        }else{
            $("img#imgNroDiasAlerta").hide();
        }
        
        var strNomServidor = $("input#txtNombreServidor").val();  

        if (strNomServidor == "") {  
            $("img#imgNombreServidor").show();  
            $("input#txtNombreServidor").focus();  
            return false;  
        }else{
             $("img#txtNombreServidor").hide();  
        }
        
        var strUsuario = $("input#txtUsuario").val();  

        if (strUsuario == "") {  
            $("img#imgUsuario").show();  
            $("input#txtUsuario").focus();  
            return false;  
        }else{
             $("img#txtUsuario").hide();  
        }
        
        var strPassword = $("input#txtPassword").val();  

        if (strPassword == "") {  
            $("img#imgPassword").show();  
            $("input#txtPassword").focus();  
            return false;  
        }else{
             $("img#txtPassword").hide();  
        }
        
        var strRutaSegPQRS = $("input#txtRutaSegPQRS").val();

        if (strRutaSegPQRS == "") {  
            $("img#imgRutaSegPQRS").show();  
            $("input#txtRutaSegPQRS").focus();  
            return false;  
        }else{
             $("img#imgRutaSegPQRS").hide();  
        }
        
        var strRutaArchivos= $("#txtRutaArchivos").val();

        if (strRutaArchivos == "") {  
            $("img#imgRutaArchivos").show();  
            $("input#txtRutaArchivos").focus();  
            return false;  
        }else{
             $("img#imgRutaArchivos").hide();  
        }
        
       // Instrucciones cuando se hace el submit correctamente.
        
        var strForm = $("input#txtForm").val();
        var strAccion = $("input#txtAccion").val();
        var strNumPuerto = $("input#txtNumeroPuerto").val();        
        
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtForm=" + strForm + "&txtAccion=" + strAccion + '&txtConsecutivo='+ strConsecutivo + '&txtNroPaginas=' + strNroPaginas + "&txtNombreServidor=" + strNomServidor + "&txtNumeroPuerto=" + strNumPuerto +
                                "&txtUsuario=" + strUsuario + "&txtPassword=" + strPassword + "&txtRutaSegPQRS=" + strRutaSegPQRS + "&txtAsistente=" + strAsistente + "&txtNroDiasCierre=" + strNroDiasCierre + "&txtRutaArchivos=" + strRutaArchivos + 
                                "&txtNroDiasAlerta=" + strNroDiasAlerta;
        
        // Envío de petición AJAX.
        
        AJAX("POST","Registro",dataString,"dMensaje");
         
    });  
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $(".CAMPOFORM").val("");        
        cargaInicial();
    }); 
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
});  






