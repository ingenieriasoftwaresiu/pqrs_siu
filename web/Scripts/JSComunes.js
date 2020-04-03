
function formatoDivEditable(strCadenaInicial){
    var nav = detectarNav();
    var strCadenaNew = "";

    if ((nav == "IE") || (nav == "-1")){
        strCadenaNew = strCadenaInicial.replace(/<br\s*[\/]?>/gi, ">");
        strCadenaNew = strCadenaInicial.replace(/<\s*[\/]?p\s*[\/]?>/gi, ">");
    }

    if (nav == "Firefox"){
        strCadenaNew = strCadenaInicial.replace(/<br\s*[\/]?>/gi, ">");
    }

    if (nav == "Chrome"){
        strCadenaNew = strCadenaInicial.replace(/<br\s*[\/]?>/gi, "");
        strCadenaNew = strCadenaInicial.replace(/<div\s*[\/]?>/gi, ">");    
        strCadenaNew = strCadenaInicial.replace(/<\s*[\/]?div\s*[\/]?>/gi, "");
    }
    
    return strCadenaNew;
}

function detectarNav(){
    var navegador = navigator.userAgent;
    if (navigator.userAgent.indexOf('MSIE') !=-1) {
      return "IE";
    } else if (navigator.userAgent.indexOf('Firefox') !=-1) {
      return "Firefox";
    } else if (navigator.userAgent.indexOf('Chrome') !=-1) {
      return "Chrome";
    } else if (navigator.userAgent.indexOf('Opera') !=-1) {
      return "Opera";
    } else {
      return "-1";
    }    
}

function validarCampoVacio(campo,nombre){
	
    if (campo.value == ""){
            return nombre + "\n";
    }	
    return "";	
}

function validarCampoSelect(campo,nombre){
    if (campo.selectedIndex == 0){
        return nombre + "\n";
    }
    return "";
}

function cerrarSesion(){
    window.location = "cerrar.jsp";
}

function obtiene_fecha() {  
      
    var fecha_actual = new Date()  
  
    var dia = fecha_actual.getDate();  
    var mes = fecha_actual.getMonth() + 1;
    var anio = fecha_actual.getFullYear();  
  
    if (mes < 10)  
        mes = '0' + mes;  
  
    if (dia < 10)  
        dia = '0' + dia; 
  
    return (anio + "-" + mes + "-" + dia);
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

function deleteSpecialSigns(strCadena){   
    
    strCadena = strCadena.replace("#","Nro.");   
    
    return strCadena;
    
}

function trim(strCadena){
    
    return strCadena.replace(/^\s+/g,'').replace(/\s+$/g,'');
}

function hideAjaxMsg(strId){
    
    strValor = "<html>\n";
    strValor += "<head>\n";
    strValor += "</head>\n";
    strValor += "<body>\n";
    strValor += "</body>\n";
    strValor += "</html>";    
    
    mensaje = document.getElementById(strId)
    mensaje.innerHTML = strValor;
    mensaje.style.display = "none";
}

function showAjaxMsg(strId){
    document.getElementById(strId).style.display = "block";
}

function validarSiNumero(numero){
    var numero = false;
    
    if (!/^([0-9])*$/.test(numero)){
        numero = false;
    }else{
        numero = true;
    }        
  }

function mostrarAviso(){
    var strMensaje="";
    
    window.open("Informacion.jsp","Informacion",'top='+(((screen.availHeight)/2)-200)+',left='+(((screen.availWidth)/2)-470)+',width=950px,height=400px,toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
    
}

function validarEmail(email) {
    var expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!expr.test(email) ){
        return false;
    }else{
        return true;
    }        
}