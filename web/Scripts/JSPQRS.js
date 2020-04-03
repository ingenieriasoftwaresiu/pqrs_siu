/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function cargaInicial(){   
    
        // Instrucciones ejecutadas al cargar el formulario.    
                               
        $("#dExterno").hide();   
        $("#dInterno").hide(); 
        $("#dRpta").hide();
         $("#dResponsable").hide();  
         $('.IMGERROR').hide();                  
          $(".IMGLOAD").hide();
                      
        var strAccion = $("input#txtAccion").val();
        
        if (strAccion == "C"){
            
            var strFechaActual = $("#txtFechaCreacion").val();
            $("#txtIdEstado").val("C");
                       
            if (strFechaActual == ""){
                var strFechaCreacion = obtiene_fecha();
                $("#txtFechaCreacion").val(strFechaCreacion);
            }
            
            $("input#txtTipoPQRS").focus();                                  

            $("#btnGuardar").show();
            $("#btnLimpiar").show();                 
            $("#lblNombreCargo").hide();
            $("#dNombreCargo").hide();
             $("#dDatosUsuario").hide();
             
             document.getElementById("btnGuardar").disabled = false;       
            
        }else{           
            
            var strTipoEntidad = $("input[name='rdTipoEntidad']:checked").val()
            
            if (strTipoEntidad == "I"){
                $("#dExterno").hide();   
                $("#dInterno").show(); 
            }else{
                $("#dExterno").show();   
                $("#dInterno").hide(); 
            }
            
            var strAnonimo = $("input[name='rdAnonimo']:checked").val();
            
            if ((strAnonimo == "S" || strAnonimo == undefined)){
                $("#dDatosUsuario").hide();
             }else{
                $("#dDatosUsuario").show();
            }
                        
            var strReqRpta = $("#txtReqRpta").val();                
                    
            if (strReqRpta == "S"){
                $("#btnRespuesta").show();  
            }else{
                $("#btnRespuesta").hide();
            }
        }                     
}

function abrirReclasificar(strRadicado, strTipoSolActual){
    window.open("reclasificar.jsp?txtRadicado=" + strRadicado + "&txtTipoSolAct=" + strTipoSolActual,"Reclasificar",'top='+(((screen.availHeight)/2)-100)+',left='+(((screen.availWidth)/2)-270)+',width=500px,height=200px,toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function reclasificar(strRadicado, strTipoSolActual){
    
    var strTipoSolNuevo = $("#cbTipoPQRS").val();
    var strAccion = "Reclasificar";
    
    if (strTipoSolNuevo == "-1"){
        $("#cbTipoPQRS").focus();
        $("#imgErrorTipoSol").show();
        return false;        
    }else{
        $("#imgErrorTipoSol").hide();
        
        if (strTipoSolNuevo == strTipoSolActual){
            alert("El nuevo tipo de solicitud debe ser diferente al tipo de solicitud actual");
            return false;
        }else{            
            var dataString = "txtAccion=" + strAccion  +"&txtRadicado=" + strRadicado + "&txtTipoSolNuevo=" + strTipoSolNuevo;
            AJAXC("POST","Acciones",dataString,"dMostrar");            
        }
    }    
}

function validarFormRpta(){   
        
        var strRpta = trim($("textarea#txtRpta").val());  

        if (strRpta == "") {  
            $("img#imgRpta").show();  
            $("textarea#txtRpta").focus();  
            return false;
        }else{
            $("img#imgRpta").hide();     
        }
           
        var strClasificacion = $("#txtClasificacion").val();
               
        if ((strClasificacion == "-1") || (strClasificacion == "PD")) {  
            $("#imgClasificacion").show();      
            $("#txtClasificacion").focus();  
            return false;
        }else{
            $("#imgClasificacion").hide();  
        }
        
        var strRed = $("#txtRed").val();
                
        var strAnexo = $("#txtAnexo").val();
        var extension = "";
        
        if (strAnexo != ""){
            extension = (strAnexo.substring(strAnexo.lastIndexOf("."))).toLowerCase();
        }else{
            extension = "-";
        }
                
        $("#txtExtension").val(extension);              
        $(".IMGLOAD").show();
        document.frmRpta.submit();
        document.getElementById("btnAtender").disabled = true;
}

function descargarArchivo(strRuta){    
    while(strRuta.indexOf("/") != -1){
        strRuta = strRuta.replace("/","\\");
    }    
    
    window.open("Descargar.jsp?txtRuta=" + strRuta);
}

$(document).ready(function(){    
    
    // Instrucciones ejecutadas cuando el formulario esté listo.
    
    cargaInicial();
});

$(function() {                   
    
    $("#txtDescripcion").bind("paste",function(e) {
        alert("La funcionalidad de pegar texto se encuentra deshabilitada en este campo para conservar la integridad de la información. Por favor digite manualmente el motivo de la solicitud.");
        e.preventDefault();                  
      });  
      
      $("#txtRpta").bind("paste",function(e) {
        alert("La funcionalidad de pegar texto se encuentra deshabilitada en este campo para conservar la integridad de la información. Por favor digite manualmente la respuesta de la solicitud.");
        e.preventDefault();                  
      });  
    
    $('.IMGERROR').hide(); 
    $("#btnGuardar").click(function() {     
        
        // Validación de los campos del formulario.
        
        $('.IMGERROR').hide();  
                
        var strTipoPQRS = $("#txtTipoPQRS").val();
        
        if (strTipoPQRS == '-1'){        
            $("img#imgTipoPQRS").show();  
            $("#txtTipoPQRS").focus();  
            return false;  
        }else{
            $("img#imgTipoPQRS").hide();
        }
                
        var strServicio = $("#txtServicio").val();  
        var strNombreCargo = $("#txtNombreCargo").val();  
        $("#txtIdResponsable").val(strNombreCargo);  

        if (strServicio == '-1') {  
            $("img#imgServicio").show();  
            $("input#txtServicio").focus();  
            return false;  
        }else{
            $("img#imgServicio").hide();  
        }
        
        if(!($("input[name='rdAnonimo']:radio").is(':checked'))) { 
            $("img#imgAnonimo").show();
            return false;            
        }else{
            $("img#imgAnonimo").hide();
        }
        
        var strAnonimo = $("input[name='rdAnonimo']:checked").val();
        
        if (strAnonimo == "N"){        
            if(!($("input[name='rdTipoEntidad']:radio").is(':checked'))) {         
                $("img#imgTipoEntidad").show();
                return false;  
            }else{
                $("img#imgTipoEntidad").hide();
            }

            var strTipoEntidad = $("input[name='rdTipoEntidad']:checked").val();
            var strNombreGrupo = "";
            var strNomEntidad = "";

            if (strTipoEntidad == "I"){
                    strNombreGrupo = $("#txtGrupo").val();  

                    if (strNombreGrupo == '-1') {  
                        $("img#imgGrupo").show();  
                        $("input#txtGrupo").focus();  
                        return false;  
                    }else{
                        $("img#imgGrupo").hide();  
                    }
            }else{
                    if ((strTipoEntidad == "E") || (strTipoEntidad == "U")){
                        strNomEntidad = $("#txtNomEntidad").val(); 

                        if (strNomEntidad == ""){
                                $("img#imgNomEntidad").show();  
                                $("input#txtNomEntidad").focus();  
                                return false;  
                        }else{
                            $("img#imgNomEntidad").hide();
                        }
                    }              
            }
            
            var strNumId = $("#txtNumId").val();        
            var strNombreUser = $("#txtNombreUser").val(); 

                if (strNombreUser == ""){
                    $("img#imgNombreUser").show();  
                    $("input#txtNombreUser").focus();  
                    return false;  
                }else{
                    $("img#imgNombreUser").hide();
                }

                var strTelefono = $("#txtTelefono").val(); 

                if (strTelefono == ""){
                    $("img#imgTelefono").show();  
                    $("input#txtTelefono").focus();  
                    return false;  
                }else{
                    $("img#imgTelefono").hide();
                }

            var strEmail = trim($("#txtEmail").val()); 

            if (strEmail != ""){      
                if (!validarEmail(strEmail)){
                    alert("La dirección de correo electrónico ingresada no es correcta. Por favor ingrese una dirección válida para continuar.");
                    $("img#imgEmail").show();  
                    $("input#txtEmail").focus();  
                    return false;
                }else
                    $("img#imgEmail").hide();  
                }                     
            }
                                                         
        var strDescripcion = trim($("#txtDescripcion").html());           
        
        if ((strDescripcion == "") || (strDescripcion == null) || (strDescripcion == "<br>")) {  
            $("img#imgDescripcion").show();  
            $("#txtDescripcion").html("");
            $("#txtDescripcion").focus();  
            return false;  
        }else{ 
            $("img#imgDescripcion").hide();  
            var nav = detectarNav();
            
            if ((nav == "IE") || (nav == "-1")){
                strDescripcion = strDescripcion.replace(/<br\s*[\/]?>/gi, ">");
                strDescripcion = strDescripcion.replace(/<\s*[\/]?p\s*[\/]?>/gi, ">");
            }
            
            if (nav == "Firefox"){
                strDescripcion = strDescripcion.replace(/<br\s*[\/]?>/gi, ">");
            }
            
            if (nav == "Chrome"){
                strDescripcion = strDescripcion.replace(/<br\s*[\/]?>/gi, "");
                strDescripcion = strDescripcion.replace(/<div\s*[\/]?>/gi, ">");    
                strDescripcion = strDescripcion.replace(/<\s*[\/]?div\s*[\/]?>/gi, "");
            }            
  
            $("#txtMotivo").val(strDescripcion);
        } 
        
        var strSoporte = $("#txtSoporte").val();
        var extension = "";
        
        if (strSoporte != ""){
            extension = (strSoporte.substring(strSoporte.lastIndexOf("."))).toLowerCase();
        }else{
            extension = "-";
        }
                
        $("#txtExtensionSoporte").val(extension);                                  
        $(".IMGLOAD").show();
        document.getElementById("btnGuardar").disabled = true;        
        document.frmPQRS.submit();        
        
    });  
    
    
    // Cargar los servicios del responsable seleccionado.
    
    $("#txtServicio").change(function(){        
        
        var strServicio = $("#txtServicio").val();
        
        if (strServicio != "-1"){
            var strAccion = "Servicio";
        
            var dataString = "txtAccion=" + strAccion  +"&txtServicio=" + strServicio;
            AJAXC("POST","Acciones",dataString,"dNombreCargo");

            $("#lblNombreCargo").show();
            $("#dNombreCargo").show();
        }else{
            $("#lblNombreCargo").hide();
            $("#dNombreCargo").hide();
        }        
    });
    
    $("#rdSI").on("click",function(){
        if(!($("input[name='rdAnonimo']:radio").is(':checked'))) {         
              $("img#imgAnonimo").show();
              return false;  
         }else{
             $("img#imgAnonimo").hide();
         }
        $("#dDatosUsuario").hide();
        alert("ADVERTENCIA: Si figura como usuario Anónimo, no podrá recibir la respuesta a su solicitud vía correo electrónico.");
    });
    
    $("#rdNO").on("click",function(){
        if(!($("input[name='rdAnonimo']:radio").is(':checked'))) {         
              $("img#imgAnonimo").show();
              return false;  
         }else{
             $("img#imgAnonimo").hide();
         }
        $("#dDatosUsuario").show();
        
        var strCedula = $("#txtCedula").val();
        if (strCedula != " "){
            $("#dInterno").show();
        }             
    });
    
    // Función para limpiar los todos los campos del formulario.
    
    $("#btnLimpiar").click(function(){
        $("#txtTipoPQRS").val("");
        $("#txtNomEntidad").val("");
        $("#txtNumId").val("");
        $("#txtNombreUser").val("");
        $("#txtTelefono").val("");
        $("#txtEmail").val("");
        $("input[name='rdTipoEntidad']:radio").attr('checked', false);
        $("input[name='rdAnonimo']:radio").attr('checked', false);
        $("select").val("-1");     
        $("#txtDescripcion").html("");     
        cargaInicial();
    });             
    
    // Función para ocultar la respuesta AJAX al momento de ingresar nueva información.
    
    $(".CAMPOFORM").focus(function(){
       $("#dMensaje").hide();
    });
    
    // Función para mostrar/ocultar campos en el Tipo de entidad.
    
    $("#rdInt").click(function(){
         $("#dExterno").hide();
         $("#dInterno").show();
         $("#txtNomEntidad").val("");
    });
    
    $("#rdUdeA").click(function(){
         $("#dInterno").hide();
         $("#dExterno").show();
         $("#txtGrupo").val("-1");
    });
    
    $("#rdExt").click(function(){
         $("#dInterno").hide();
         $("#dExterno").show();
         $("#txtGrupo").val("-1");
    });
        
    $("#btnRespuesta").click(function(){     
         $("#dPQRS").hide();
         $("#dRpta").show();
         $("#txtRpta").focus();
         $(".IMGLOAD").hide();
    });
    
     $("#btnVerPQRS").click(function(){
         $("#dPQRS").show();
         $("#dRpta").hide();
         $(".IMGLOAD").hide();
    });
    
    //Quitar imagen de validación al diligenciar el campo.
    
    $("#txtTipoPQRS").change(function(){
        var strTipoPQRS = $("#txtTipoPQRS").val();       
         var strAccion = "TipoPQRS";
        
        if (strTipoPQRS == '-1'){        
            $("img#imgTipoPQRS").show();       
        }else{
            $("img#imgTipoPQRS").hide();       
            
            var dataString = "txtAccion=" + strAccion  +"&txtIdTipoQRS=" + strTipoPQRS;
            AJAXC("POST","Acciones",dataString,"dTipoPQRS");    
        }        
    });
    
    $("#txtServicio").change(function(){
        var strServicio = $("#txtServicio").val();
        
        if (strServicio == '-1'){        
            $("img#imgServicio").show();       
        }else{
            $("img#imgServicio").hide();
        }        
    });
        
    $("#rdNo").change(function(){
        if(!($("input[name='rdAnonimo']:radio").is(':checked'))) {         
              $("img#imgTipoEntidad").show();
              return false;  
         }else{
             $("img#imgTipoEntidad").hide();
         }
    });
    
    $("#rdInt").change(function(){
        if(!($("input[name='rdTipoEntidad']:radio").is(':checked'))) {         
              $("img#imgTipoEntidad").show();
              return false;  
         }else{
             $("img#imgTipoEntidad").hide();
         }
    });
    
    $("#rdUdeA").change(function(){
        if(!($("input[name='rdTipoEntidad']:radio").is(':checked'))) {         
              $("img#imgTipoEntidad").show();
              return false;  
         }else{
             $("img#imgTipoEntidad").hide();
         }
    });
    
    $("#rdExt").change(function(){
        if(!($("input[name='rdTipoEntidad']:radio").is(':checked'))) {         
              $("img#imgTipoEntidad").show();
              return false;  
         }else{
             $("img#imgTipoEntidad").hide();
         }
    });
    
    $("#txtGrupo").change(function(){
            var strNombreGrupo = $("#txtGrupo").val();  

            if (strNombreGrupo == '-1') {  
                $("img#imgGrupo").show();  
            }else{
                $("img#imgGrupo").hide();  
            }
    });
    
    $("#txtNomEntidad").keypress(function(){
           var strNomEntidad = $("#txtNomEntidad").val(); 

            if (strNomEntidad == ""){
                 $("img#imgNomEntidad").show();  
            }else{
                $("img#imgNomEntidad").hide();
            }
    });
    
    $("#txtNumId").keypress(function(){
        var strNumId = $("#txtNumId").val(); 
               
        if (strNumId == ""){
            $("img#imgNumId").show();  
        }else{
            $("img#imgNumId").hide();
        }
    });
    
    $("#txtNombreUser").keypress(function(){
         var strNombreUser = $("#txtNombreUser").val(); 
               
        if (strNombreUser == ""){
            $("img#imgNombreUser").show();  
        }else{
            $("img#imgNombreUser").hide();
        }
    });
    
    $("#txtTelefono").keypress(function(){
         var strTelefono = $("#txtTelefono").val(); 
               
        if (strTelefono == ""){
            $("img#imgTelefono").show();  
        }else{
            $("img#imgTelefono").hide();
        }
    });    
    
    $("#txtEmail").keypress(function(){
        var strEmail = $("#txtEmail").val(); 
               
        if (strEmail == ""){
            $("img#imgEmail").show();       
        }else{
            $("img#imgEmail").hide();
        }
    });
            
    $("#txtDescripcion").keypress(function(){
         var strDescripcion = $("#txtDescripcion").html();  

        if (strDescripcion == "") {  
            $("img#imgDescripcion").show();  
        }else{
            $("img#imgDescripcion").hide();  
        }
    });     
    
    $("#btnCambiarResp").click(function(){
        $("#txtResponsable").val("-1");
        $("#imgResponsable").hide();
        $("#dResponsable").show();          
    });
    
    $("#btnGuardarResp").click(function(){
            var strResponsable = $("#txtResponsable").val();            
            var strAccion = "Responsable";
            var strRadicado = $("#txtRadicado").val();
            var strResponsableIni = $("#txtNombreCargo").val();
            var strUsuarioActual = $("#txtUsuarioActual").val();

            if (strResponsable == "-1"){
                $("#imgResponsable").show();
                $("#btnGuardarResp").focus();
                return false;
            }else{
                $("#imgResponsable").hide();
            }
            
            if (strResponsable == strResponsableIni){
                alert("ADVERTENCIA: El nuevo responsable no debe ser igual al responsable original de la solicitud. Por favor seleccione un responsable diferente.");
                $("#txtResponsable").val("-1");  
                return false;
            }
            
            $("#txtNombreCargo").val(strResponsable);
             $("#dResponsable").hide(); 
            
            var dataString = "txtAccion=" + strAccion  +"&txtResponsable=" + strResponsable + "&txtRadicado=" + strRadicado + "&txtResponsableIni=" + strResponsableIni + "&txtUsuarioActual=" + strUsuarioActual;
            AJAXC("POST","Acciones",dataString,"dResponsable");
                        
            alert("El responsable fue actualizado correctamente!. La solicitud será cerrada para reflejar el cambio.");
            window.close();
    });
    
    $("#txtResponsable").change(function(){
         var strResponsable = $("#txtResponsable").val();            

            if (strResponsable == "-1"){
                $("#imgResponsable").show();
            }else{
                $("#imgResponsable").hide();
            }
    });
    
    $("#btnOcultarS").click(function(){
        $("#dResponsable").hide();
    });
    
    $("#btnVerObs").click(function(){
        var  strRadicado = $("#txtRadicado").val();
        window.open("listar_obs.jsp?txtRadicado=" + strRadicado,"Observaciones",'top='+(((screen.availHeight)/2)-200)+',left='+(((screen.availWidth)/2)-470)+',width=950px,height=300px,toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
    });   
    
    $("#imgGuardar").click(function(){
        
        var strAnexo = $.trim($("#txtAnexo").val());
        var extension = "";
        
        if (strAnexo != ""){
            extension = (strAnexo.substring(strAnexo.lastIndexOf("."))).toLowerCase();
        }else{
            extension = "-";
            alert("Debe seleccionar el archivo a adjuntar para poder continuar con esta operación!.");
            return false;
        }
        
        if(confirm("¿Está seguro(a) que desea adjuntar el archivo de nombre " + strAnexo + "?")){                
            $("#txtExtension").val(extension);               
            document.frmRpta.submit();
         }       

    });
    
    $("input[type=file]").filestyle({ 
        image: "Images/lupa.gif",
        imageheight : 17,
        imagewidth : 35,
        width : 180       
    });
});  

function CerrarObs(strId,strRadicado){       
        window.open("rpta_obs.jsp?txtRadicado=" + strRadicado + "&txtId=" + strId,"Rpta_Obs",'top='+(((screen.availHeight)/2)-250)+',left='+(((screen.availWidth)/2)-270)+',width=550px,height=500px,toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function VerObs(strId,strRadicado){
    window.open("observacion.jsp?txtRadicado=" + strRadicado + "&txtId=" + strId,"Rpta_Obs",'top='+(((screen.availHeight)/2)-250)+',left='+(((screen.availWidth)/2)-270)+',width=550px,height=500px,toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}





