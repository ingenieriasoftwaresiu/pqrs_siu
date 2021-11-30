/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $('.IMGERROR').hide();                 
        $("#dObs").hide();
        var strAccion = $("#txtAccion").val();
        
        if (strAccion == "V"){
            $( "#rdSI" ).attr("disabled","disabled");
            $( "#rdNO" ).attr("disabled","disabled");
        }
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {     
    $('.IMGERROR').hide();  
    $("#btnEnviar").click(function() {     
        
        // Validación de los campos del formulario.
        
        $('.IMGERROR').hide();  
        
        if(!($("input[name='rdSatisfaccion']:radio").is(':checked'))) {         
              $("img#imgSatisfaccion").show();
              return false;  
         }else{
             $("img#imgSatisfaccion").hide();
         }
         
        var strSatisfaccion = $("input[name='rdSatisfaccion']:checked").val();
        
        if (strSatisfaccion == "N"){
             var strObservacion = $.trim($("#txtObservacion").text());
             
            if (strObservacion == ""){
                $("img#imgObservacion").show();  
                $("#txtObservacion").focus();  
                return false; 
            }else{
                $("img#imgObservacion").hide();  
                
                strObservacion = strObservacion.replace(/[\r\n]/g, " ");
                strObservacion = strObservacion.replace(/\./g, ". ");
            }
        }    
        
        var strRadicado = $("#txtRadicado").val();
        var strForm =  $("#txtForm").val();
        var strAccion = "C";
        var strFecha= obtiene_fecha();
        var strAtendida = "N";
               
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtForm=" + strForm + "&txtAccion=" + strAccion + "&txtRadicado="+ strRadicado + "&txtFecha=" + strFecha + "&txtSatisfaccion=" + strSatisfaccion +  "&txtObs=" + strObservacion + "&txtAtendida=" + strAtendida;          
        
        // Envío de petición AJAX.
        
        AJAX("POST","Registro",dataString,"dMensaje");
        
        $("#btnEnviar").hide();
         $( "#rdSI" ).attr("disabled","disabled");
         $( "#rdNO" ).attr("disabled","disabled");
         $("#txtObservacion").attr("contenteditable","false");
        alert("Muchas gracias por darnos a conocer su satisfacción respecto a la respuesta recibida!. En caso de ser necesario, el responsable del servicio evaluado se pondrá en contacto con usted.");        
         
    });
            
    $( "#rdSI" ).on( "click", function(){
        $("#dObs").hide();
        $("img#imgSatisfaccion").hide();
    });
    
    $( "#rdNO" ).on( "click", function(){
        $("#dObs").show();
        $("#txtObservacion").focus();
        $("img#imgSatisfaccion").hide();
    });
    
    $("#txtObservacion").keypress(function(){
        var strObservacion = $.trim($("#txtObservacion").text());

        if (strObservacion == "") {  
            $("img#imgObservacion").show();  
        }else{
            $("img#imgObservacion").hide();  
        }
    });     
    
});  






