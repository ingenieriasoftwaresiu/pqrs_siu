/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
    // Instrucciones ejecutadas al cargar el formulario.
    var itGlosario;
    var strUsuario = $("#txtUsuario").val();
    
    itGlosario = $("#itGlosario");   
    
    if (strUsuario == "null"){
        var itUserConsecutivo, itUserId;
        
        itUserConsecutivo = $("#itUserConsecutivo");
        itUserId =  $("#itUserId");
        
        itUserConsecutivo.addClass("SELECTED");
        itUserId.removeClass("SELECTED");        
        itGlosario.removeClass("SELECTED");
        
        AJAX('POST','Visualizacion','txtAccion=PQRSUserConsecutivo','dMostrar');
        
    }else{
        
        var itConsecutivo, itEstado, itFechaCreacion, itNomServicio, itNomUsuario,itTipoSol,itSolPend,itlgResp,itLSolVencerse,itLCerrarSols,itLPararT, itSolEnv;
        
        itConsecutivo = $("#itConsecutivo");
        itEstado = $("#itEstado");
        itFechaCreacion = $("#itFechaCreacion");
        itNomServicio = $("#itNomServicio");
        itNomUsuario = $("#itNomUsuario");
        itTipoSol = $("#itTipoSol");
        itSolPend = $("#itSolPend");
        itlgResp = $("#itlgResp");
        itLSolVencerse = $("#itLSolVencerse");
        itLCerrarSols = $("#itLCerrarSols");
        itLPararT = $("#itLPararT");
        itSolEnv = $("#itSolEnv");
        
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");  
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.addClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
        itLPararT.removeClass("SELECTED");
        itGlosario.removeClass("SELECTED");
        itSolEnv.removeClass("SELECTED");
        
        AJAX('POST','Visualizacion','txtAccion=SolPend','dMostrar');        
    }    
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {   
    var itConsecutivo, itEstado, itFechaCreacion, itNomServicio, itNomUsuario,itTipoSol,itSolPend,itlgResp,itLSolVencerse,itLCerrarSols,itLPararT,itUserConsecutivo, itUserId, itSolEnv;
    
    itConsecutivo = $("#itConsecutivo");
    itEstado = $("#itEstado");
    itFechaCreacion = $("#itFechaCreacion");
    itNomServicio = $("#itNomServicio");
    itNomUsuario = $("#itNomUsuario");
    itTipoSol = $("#itTipoSol");
    itSolPend = $("#itSolPend");
    itlgResp = $("#itlgResp");
    itLSolVencerse = $("#itLSolVencerse");
    itLCerrarSols = $("#itLCerrarSols");
    itLPararT = $("#itLPararT");
    itUserConsecutivo = $("#itUserConsecutivo");
    itUserId = $("#itUserId");
    itSolEnv = $("#itSolEnv");
   
   itUserConsecutivo.click(function(){  
       itUserConsecutivo.addClass("SELECTED");
       itUserId.removeClass("SELECTED");       
       AJAX('POST','Visualizacion','txtAccion=PQRSUserConsecutivo','dMostrar');
    });
   
   itUserId.click(function(){  
       itUserConsecutivo.removeClass("SELECTED");
       itUserId.addClass("SELECTED");     
       AJAX('POST','Visualizacion','txtAccion=PQRSUserId','dMostrar');
    });
   
    itConsecutivo.click(function(){        
        itConsecutivo.addClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");  
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
        itLPararT.removeClass("SELECTED");
        itSolEnv.removeClass("SELECTED");
        AJAX('POST','Visualizacion','txtAccion=PQRSConsecutivo','dMostrar');
    });
    
     itEstado.click(function(){
         itEstado.addClass("SELECTED");   
         itConsecutivo.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=PQRSEstado','dMostrar');
    });
    
     itFechaCreacion.click(function(){
        itFechaCreacion.addClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=PQRSFechaC','dMostrar');
    });
    
     itNomServicio.click(function(){
        itNomServicio.addClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=PQRSServicio','dMostrar');
    });
    
     itNomUsuario.click(function(){
        itNomUsuario.addClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=PQRSEscritor','dMostrar');
    });
    
     itTipoSol.click(function(){
        itTipoSol.addClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");
        itSolPend.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=PQRSTipo','dMostrar');
    });    
    
     itSolPend.click(function(){
         itSolPend.addClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");        
        itlgResp.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
         itLPararT.removeClass("SELECTED");
         itSolEnv.removeClass("SELECTED");
         AJAX('POST','Visualizacion','txtAccion=SolPend','dMostrar');
    });   
    
    itSolEnv.click(function(){
        itLSolVencerse.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
         itSolPend.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");                       
        itLCerrarSols.removeClass("SELECTED");
        itLPararT.removeClass("SELECTED");
        itSolEnv.addClass("SELECTED");
        AJAX('POST','Visualizacion','txtAccion=SolEnv','dMostrar');
    });
    
    itlgResp.click(function(){
        itlgResp.addClass("SELECTED");
         itSolPend.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");               
        itLSolVencerse.removeClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
        itLPararT.removeClass("SELECTED");
        itSolEnv.removeClass("SELECTED");
        AJAX('POST','Visualizacion','txtAccion=LgResp','dMostrar');
    });
    
    itLSolVencerse.click(function(){
        itLSolVencerse.addClass("SELECTED");
        itlgResp.removeClass("SELECTED");
         itSolPend.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");                       
        itLCerrarSols.removeClass("SELECTED");
        itLPararT.removeClass("SELECTED");
        itSolEnv.removeClass("SELECTED");
        AJAX('POST','Acciones','txtAccion=LSolVencerse','dMostrar');
    });
             
    itLCerrarSols.click(function(){
        itLCerrarSols.addClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
         itSolPend.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");        
        itLPararT.removeClass("SELECTED");
        itSolEnv.removeClass("SELECTED");
        AJAX('POST','Acciones','txtAccion=LCerrarSols','dMostrar');
    });
    
    itLPararT.click(function(){
        itLPararT.addClass("SELECTED");
        itLCerrarSols.removeClass("SELECTED");
        itLSolVencerse.removeClass("SELECTED");
        itlgResp.removeClass("SELECTED");
         itSolPend.removeClass("SELECTED");
        itTipoSol.removeClass("SELECTED");   
        itConsecutivo.removeClass("SELECTED");
        itEstado.removeClass("SELECTED");
        itFechaCreacion.removeClass("SELECTED");
        itNomServicio.removeClass("SELECTED");
        itNomUsuario.removeClass("SELECTED");                
        itSolEnv.removeClass("SELECTED");
        AJAX('POST','Acciones','txtAccion=PararTareas','dMostrar');
    });
     
});
