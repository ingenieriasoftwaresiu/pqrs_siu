function AJAX(type,url,dataString,div){
     $.ajax({  
            type: type,  
            url: url,  
            data: dataString,  
            success: function(data) {               
                     $("#" + div).html(data)                  
                    .hide()  
                    .slideDown(1000, function() {                          
                    }); 
            }  
     });
}

function AJAXC(type,url,dataString,div){
     $.ajax({  
            type: type,  
            url: url,  
            data: dataString,  
            success: function(data) {               
                     $("#" + div).html(data)                             
            }  
     });
}

function AJAX_REDIRECT(type,url,dataString,div,redirect){
     $.ajax({  
            type: type,  
            url: url,  
            data: dataString,  
            success: function(data) {    
                    var arrData = data.split("=");
                    if (arrData[0] == "txtCedula"){
                        $("#" + div).html(" ");
                        location.href = redirect + "?" + data;
                    }else{
                        $("#" + div).html(data)                  
                        .hide()  
                        .slideDown(1000, function() {                          
                        });
                    }
            }  
     });
}