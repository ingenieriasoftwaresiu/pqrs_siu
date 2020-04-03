/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.
    
        $('.IMGERROR').hide();                 
        $("#dRespuesta").hide();
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {     
    $('.IMGERROR').hide();  
    
    $("#dRptaNew").bind("paste",function(e) {
        alert("La funcionalidad de pegar texto se encuentra deshabilitada en este campo para conservar la integridad de la información. Por favor digite manualmente el motivo de la solicitud.");
        e.preventDefault();                  
      }); 
    
    $("#btnResponder").click(function() {     
        
        // Validación de los campos del formulario.
        
        $('.IMGERROR').hide();  
        
        if(!($("input[name='rdRpta']:radio").is(':checked'))) {         
              $("img#imgRpta").show();
              return false;  
         }else{
             $("img#imgRpta").hide();
         }
         
        var strRpta = $("input[name='rdRpta']:checked").val();
        var strRptaNew = "";
        
        if (strRpta == "N"){
             strRptaNew = $.trim($("#dRptaNew").html());
             
             if ((strRptaNew == "") || (strRptaNew == null) || (strRptaNew == "<br>")) {  
                $("img#imgRptaNew").show();  
                $("#dRptaNew").html("");
                $("#dRptaNew").focus();  
                return false;  
            }else{ 
                $("img#imgRptaNew").hide();  
                strRptaNew = formatoDivEditable(strRptaNew);
            }                         
        }else{
            var strRptaInicial = $.trim($("#dRptaInicial").html());
            strRptaNew = formatoDivEditable(strRptaInicial);   
        }    
                       
        var strRadicado = $("#txtRadicado").val();
        var strIdObs = $("#txtIdObs").val();
          
        // Construcción de parámetros para el Servlet.
        
        var dataString = "txtAccion=Obs&txtId=" + strIdObs + "&txtRadicado="+ strRadicado + "&txtRptaNew=" + strRptaNew + "&txtRpta=" + strRpta;          
                      
        // Envío de petición AJAX.
        
        AJAXC("POST","Acciones",dataString,"dMensaje");     
                 
    });
            
    $( "#rdSI" ).on( "click", function(){
        $("#dRespuesta").hide();
        $("img#imgRptaNew").hide();
    });
    
    $( "#rdNO" ).on( "click", function(){
        $("#dRespuesta").show();
        $("#dRptaNew").focus();
        $("img#imgRptaNew").hide();
    });
              
});  






