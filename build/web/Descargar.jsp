<%-- 
    Document   : Descargar
    Created on : 13-ene-2014, 16:43:23
    Author     : jorge.correa
--%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.OutputStream"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page language="java" import="java.io.InputStream,java.io.File,java.net.URL,java.io.BufferedInputStream,java.io.FileInputStream"%><%
    String txtRuta =  request.getParameter("txtRuta");
    int intPos = txtRuta.lastIndexOf("\\");    
    String strFilename = txtRuta.substring(intPos+1, txtRuta.length());    
    
    BufferedInputStream filein = null;
    BufferedOutputStream outputs = null;
    try {
        File file = new File(txtRuta);
        byte b[] = new byte[2048];
        int len = 0;
        filein = new BufferedInputStream(new FileInputStream(file));
        outputs = new BufferedOutputStream(response.getOutputStream());
        response.setHeader("Content-Length", ""+file.length());
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition","attachment;filename=" + strFilename);
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        while ((len = filein.read(b)) > 0) {
            outputs.write(b, 0, len);
            outputs.flush();
        }
        filein.close();
        outputs.close();
    }catch(Exception e){
        out.println(e);
    }finally{
        if (filein != null){
            filein.close();
        }        
        if (outputs != null){
            outputs.close();
        }        
    }
    
    /*
    response.setContentType("application/pdf"); 
    response.setHeader("Content-Disposition","attachment;filename=" + strFilename);
    
    ServletOutputStream  outs = null;
    
    try{
        File fileToDownload = new File(txtRuta);
        InputStream in =new FileInputStream(fileToDownload);
        outs = response.getOutputStream();
        int bit = 256;

        while ((bit) >= 0){
            bit = in.read();
            outs.write(bit);
        }
        outs.flush();
        outs.close();
        in.close(); 
    }catch(IllegalStateException ise){
        ise.getMessage();
    }catch(Exception e){
       e.printStackTrace();
    }finally{
        if (outs != null){
            try{
                outs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }*/
%>