function abrirRegPQRS(strCodigo){
    window.open("../pqrs.jsp?txtAccion=V&txtCodigo=" + strCodigo,"PQRS",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=0');
}

function setCalendario(campo,imagen){
    Calendar.setup({
        inputField: campo,
        ifFormat: "%Y-%m-%d",
        button: imagen,
        align: "TI",
        singleClick: true
    });
}

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.    
       
       $('.IMGERROR').hide();
      $("#dMostrarInforme").hide();            
      $("#dTipoPQRS").hide();
      $("#dServicio").hide();
      $("#dRangoFechas").hide();
      setCalendario("txtFechaInicial","imgFechaI");
      setCalendario("txtFechaFinal","imgFechaF");
}

function validarFormParametros(strAccion){
    
        $('.IMGERROR').hide();
        var strCriterio = "";
        
        if ($("#cbTipoPQRS").is(':checked')){
            strCriterio += "T;";
            var strTipoPQRS = $("#txtTipoPQRS").val()
        
            if (strTipoPQRS == '-1'){        
                $("img#imgTipoPQRS").show();  
                $("#txtTipoPQRS").focus();  
                return false;  
            }else{
                $("img#imgTipoPQRS").hide();
            }
        }
        
        if ($("#cbServicio").is(':checked')){
            strCriterio += "S;";
            var strServicio = $("#txtServicio").val()
        
            if (strServicio == '-1'){        
                $("img#imgServicio").show();  
                $("#txtServicio").focus();  
                return false;  
            }else{
                $("img#imgServicio").hide();
            }
        }
        
        if ($("#cbRangoFechas").is(':checked')){
            strCriterio += "R";
            var strFechaInicial = $("#txtFechaInicial").val();           
            
            if (strFechaInicial == ""){        
                $("img#imgFechaInicial").show();  
                $("#txtFechaInicial").focus();  
                return false;  
            }else{
                $("img#imgFechaInicial").hide();
            }
            
            var strFechaFinal = $("#txtFechaFinal").val();            
        
            if (strFechaFinal == ""){        
                $("img#imgFechaFinal").show();  
                $("#txtFechaFinal").focus();  
                return false;  
            }else{
                $("img#imgFechaFinal").hide();
            }
        }
        
        if (strCriterio == ""){
            strCriterio = "*";
        }
                        
        var dataString = "txtAccion=" + strAccion + "&txtFiltro=" + strCriterio + "&txtTipoPQRS=" + strTipoPQRS + "&txtFechaInicial=" + strFechaInicial + "&txtFechaFinal=" + strFechaFinal + "&txtServicio=" + strServicio;
        
        AJAX("POST","..//InformeSegPQRS",dataString,"dMostrarInforme");
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {
    $("#btnGenerar").click(function(){            
        if (validarFormParametros("G") != false){
            $("#dMostrarInforme").show();            
        }
    });
    
    $("#btnExportar").click(function(){
       validarFormParametros("E");                
    });
    
    $("#btnLimpiar").click(function(){
        $("#cbRangoFechas").prop('checked',false);
        $("#cbTipoPQRS").prop('checked',false);
        $("#cbServicio").prop('checked',false);
        $("#txtTipoPQRS").val("-1");
        $("#txtServicio").val("-1");
        $("#txtFechaInicial").val("");
        $("#txtFechaFinal").val("");
        cargaInicial();
    });
            
    $("#cbRangoFechas").change(function(){
        if ($("#cbRangoFechas").is(':checked')){  
            $("#dRangoFechas").show();
        }else{
            $("#dRangoFechas").hide();
        }
    });
    
    $("#cbTipoPQRS").change(function(){
        
        if ($("#cbTipoPQRS").is(':checked')){
            $("#dTipoPQRS").show();
        }else{
            $("#dTipoPQRS").hide();
        }        
    });    
    
    $("#cbServicio").change(function(){
        
        if ($("#cbServicio").is(':checked')){
            $("#dServicio").show();
        }else{
            $("#dServicio").hide();
        }        
    });
});