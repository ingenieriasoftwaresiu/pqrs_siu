function abrirApp(strURL){
    window.open(strURL ,'','top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function onLoadBody(){
    getXMLHTTPRequest('..//Visualizacion?txtAccion=persona','dMostrar');
}

function abrirRegEstado(strCodigo){
     window.open("estado.jsp?txtAccion=V&txtCodigo=" + strCodigo,"Estado",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function abrirRegRetroalimentacion(strCodigo){
     window.open("retroalimentacion.jsp?txtAccion=V&txtCodigo=" + strCodigo,"Retroalimentacion",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function abrirRegRol(strCodigo){
     window.open("rol.jsp?txtAccion=V&txtCodigo=" + strCodigo,"Rol",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function abrirRegRolXUsuario(strCodigo){
     window.open("rolXpersona.jsp?txtAccion=V&txtCodigo=" + strCodigo,"Rol_por_usuario",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

function abrirRegPQRS(strCodigo){
    window.open("pqrs.jsp?txtAccion=V&txtCodigo=" + strCodigo,"PQRS",'top=0,left=0,width='+(screen.availWidth)+',height ='+(screen.availHeight)+',toolbar=0 ,location=0,directories=0,status=0,menubar=0,resizable=1,scrolling=1,scrollbars=yes');
}

// Métodos de eliminación.

function eliminarRegEstado(strCodigo){
        
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){    
        AJAXC("POST","Visualizacion","txtAccion=estado&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegRetroalimentacion(strCodigo){
        
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){    
        AJAXC("POST","Visualizacion","txtAccion=retroalimentacion&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");          
    }
}

function eliminarRegRol(strCodigo){
        
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){    
        AJAXC("POST","Visualizacion","txtAccion=rol&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");           
    }
}

function eliminarRegRolXPersona(strCodigo){
        
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=rolXpersona&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXTipo(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSTipo&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXEstado(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSEstado&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXServicio(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSServicio&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXEscritor(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSEscritor&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXFechaC(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSFechaC&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function eliminarRegPQRSXConsecutivo(strCodigo){
    if (confirm('¿Está seguro que desea eliminar el registro seleccionado?')){   
       AJAXC("POST","Visualizacion","txtAccion=PQRSConsecutivo&txtEvento=Eliminar&txtCodigo=" + strCodigo,"dMostrar");   
    }
}

function buscarRegistro(strAccion){
    var form = document.frmBusqueda;  

    var strMsg = "";
    var strHead = "Antes de continuar debe diligenciar los siguientes campos:\n\n";
	
    strMsg += validarCampoSelect(form.txtCriterio,"Criterio");
    
     if (strAccion == "PQRSUserConsecutivo"){      
        strMsg += validarCampoVacio(form.txtClave,"Nro. consecutivo");
    }else{
         if (strAccion == "PQRSUserId"){
             strMsg += validarCampoVacio(form.txtClave,"Nro. identificación");
         }else{
            strMsg += validarCampoVacio(form.txtClave,"Palabra clave");
        }
    }       
    
    var strCriterio = form.txtCriterio[form.txtCriterio.selectedIndex].value;
    var strClave = form.txtClave.value;
    
    if (strMsg != ""){
        strMsg = strHead + strMsg;
        alert(strMsg);                      

        if ((strCriterio.selectedIndex == 0) && (strClave == "")){
                form.txtCriterio.focus();
        }else{
                if (strCriterio.selectedIndex == 0){
                        form.txtCriterio.focus();
                }else{
                        form.txtClave.focus();
                }
        }

        return false;
      }else{
          if (strCriterio == "I"){
            if (strClave == "-"){
                form.txtClave.value = "";
                return false;
            }
        }          
      }
    
      AJAXC("POST","Visualizacion","txtAccion=" + strAccion + "&txtEvento=busqueda&txtCriterio=" + form.txtCriterio.value + "&txtClave=" + form.txtClave.value,"dMostrar");   
}
