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
            $("#lblTiempoRpta").hide();
            $("#txtTiempoRpta").hide();
        }else{
            $("input#txtNombre").focus();
            var strReqRpta = $("input[name='rdReqRpta']:checked").val();
            
            if (strReqRpta == "N"){
                $("#lblTiempoRpta").hide();
                $("#txtTiempoRpta").hide();
            }else{
                $("#lblTiempoRpta").show();
                $("#txtTiempoRpta").show();
            }
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
        
        if(!($("input[name='rdReqRpta']:radio").is(':checked'))) {         
              $("img#imgReqRpta").show();
              return false;  
         }else{
             $("img#imgReqRpta").hide();
         }
        
        var strReqRpta = $("input[name='rdReqRpta']:checked").val();
        var strTiempoRpta = "0";
        
        if (strReqRpta == "S"){
             strTiempoRpta = $("input#txtTiempoRpta").val();  
        
            if (strTiempoRpta == ""){
                $("img#imgTiempoRpta").show();  
                $("input#txtTiempoRpta").focus();  
                return false;  
            }else{
                if (isNaN(strTiempoRpta)){
                    alert("El tiempo de respuesta debe ser un valor númerico válido");
                    $("img#imgTiempoRpta").show();  
                    $("input#txtTiempoRpta").focus();  
                    return false;  
                }else{
                    $("img#imgTiempoRpta").hide();
                }            
            }
        }                      
        
        var strDescripcion = $.trim($("#txtDescripcion").text());
                       
        if (strDescripcion == ""){
            $("img#imgDescripcion").show();  
            $("#txtDescripcion").focus();  
            return false; 
        }else{
            $("img#imgDescripcion").hide();  
        }
      
       // Instrucciones cuando se hace el submit correctamente.
        
        var strForm = $("input#txtForm").val();
        var strAccion = $("input#txtAccion").val();       
        
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtForm=" + strForm + "&txtAccion=" + strAccion + '&txtCodigo='+ strCodigo + '&txtNombre=' + strNombre + "&txtTiempoRpta=" +strTiempoRpta + "&txtDescripcion=" + strDescripcion + "&txtReqRpta=" + strReqRpta;           
        
        // Envío de petición AJAX.
        
        AJAX("POST","Registro",dataString,"dMensaje");
         
    });  
    
    // Mostrar el tiempo de respuesta.
    
    $( "#rdSI" ).on( "click", function(){
        $("#lblTiempoRpta").show();
        $("#txtTiempoRpta").show();
    });
    
     $( "#rdNO" ).on( "click", function(){
         $("#lblTiempoRpta").hide();
        $("#txtTiempoRpta").hide();
    });
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $("input#txtCodigo").val("");
        $("input#txtNombre").val("");     
        cargaInicial();
    }); 
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
});  






