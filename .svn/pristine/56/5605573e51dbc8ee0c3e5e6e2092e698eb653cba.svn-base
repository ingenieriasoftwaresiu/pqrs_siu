/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Negocio;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 *
 * @author jorge.correa
 */
public class Comunes {
     
    
    public Calendar incrementarDiasHabiles(Calendar fechaInicial, int intNroDias, Vector arrFeriados){
        Calendar fechaFinal = Calendar.getInstance();
        Calendar fechaFeriada = Calendar.getInstance();
        String[] strTemp=null;
        Boolean feriado = Boolean.FALSE;
        int cont=0;
        
        while (cont<=intNroDias){                        
            if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){               
                feriado=Boolean.FALSE;
                
                for (int i=0;i<arrFeriados.size();i++){
                    
                     strTemp = arrFeriados.get(i).toString().split("-");
                     fechaFeriada.set(Integer.parseInt(strTemp[0]), Integer.parseInt(strTemp[1]) - 1, Integer.parseInt(strTemp[2]));
                     
                     fechaFeriada.set(Calendar.SECOND, 0);
                     fechaFeriada.set(Calendar.MILLISECOND, 0);                   
                                                           
                    if (fechaFeriada.getTime().toString().equals(fechaInicial.getTime().toString())){        
                        feriado=Boolean.TRUE;
                        break;
                    }
                    
                    strTemp=null;
                    fechaFeriada = Calendar.getInstance();                    
                }
                
                if (feriado.equals(Boolean.FALSE)){                                  
                    cont++;       
                }
            }else{
                if (cont==0){
                    cont=1;
                }   
            }            
                   
            if (cont<=intNroDias){
                fechaInicial.add(Calendar.DATE, 1);
            }            
        }
  
        return fechaInicial;
    }
    
    public int getDiasHabiles(Calendar fechaInicial, Calendar fechaFinal, Vector arrFeriados) {

        int diffDays= 0;
        boolean feriado=false;
        Calendar fechaFeriada = Calendar.getInstance();
        String[] strTemp;
                
        fechaInicial.set(Calendar.SECOND, 0);
        fechaInicial.set(Calendar.MILLISECOND, 0);
        fechaFinal.set(Calendar.SECOND, 0);
        fechaFinal.set(Calendar.MILLISECOND, 0);
        
        //mientras la fecha inicial sea menor o igual que la fecha final se cuentan los dias
        
        while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

            //si el dia de la semana de la fecha minima es diferente de sabado o domingo
            if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {               
                feriado=false;
                
                for (int i=0;i<arrFeriados.size();i++){
                    
                     strTemp = arrFeriados.get(i).toString().split("-");
                     fechaFeriada.set(Integer.parseInt(strTemp[0]), Integer.parseInt(strTemp[1]) - 1, Integer.parseInt(strTemp[2]));
                     
                     fechaFeriada.set(Calendar.SECOND, 0);
                     fechaFeriada.set(Calendar.MILLISECOND, 0);                   
                                                           
                    if (fechaFeriada.getTime().toString().equals(fechaInicial.getTime().toString())){        
                        feriado=true;
                        break;
                    }
                    
                    strTemp=null;
                    fechaFeriada = Calendar.getInstance();
                }
                
                if (!feriado){                    
                    diffDays++;             
                }
            }
            
            //se suma 1 dia para hacer la validacion del siguiente dia.
            fechaInicial.add(Calendar.DATE, 1);

        }
        return diffDays;
    }
    
    public String aumentarDiasFecha(String strFechaBase, int intNumDias){
        Date dtNuevaFecha = null;
        String strAnio, strMes, strDia;
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
              
        strAnio = strFechaBase.substring(0,4);
        strMes= strFechaBase.substring(5,7);
        strDia = strFechaBase.substring(8,10);      
              
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(strAnio), Integer.parseInt(strMes)-1, Integer.parseInt(strDia));
        cal.add(Calendar.DATE,intNumDias);        
        
        return formato.format(cal.getTime());
    }
    
    public double redondearDecimales(double dblValor, int intNumDecimales){
        double dblNuevoValor = 0;
        
        if (intNumDecimales == 1){
            dblNuevoValor = Math.rint(dblValor*10)/10; 
        }
        
        if (intNumDecimales == 2){
            dblNuevoValor = Math.rint(dblValor*100)/100; 
        }
        
        if (intNumDecimales == 3){
            dblNuevoValor = Math.rint(dblValor*1000)/1000; 
        }
        
        return dblNuevoValor;        
    }
        
    public String getUltimaDiaFecha(String strAnio, String strMes){
        
        String strDia, strFecha="";
        Date dtFecha = null;
        
        Calendar calBase = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        calBase.set(Integer.parseInt(strAnio), Integer.parseInt(strMes)-1, 1);
        calBase.set(Integer.parseInt(strAnio), Integer.parseInt(strMes)-1, calBase.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        strDia = Integer.toString(calBase.get(Calendar.DATE));
        strMes = Integer.toString(calBase.get(Calendar.MONTH)+1);
        strAnio = Integer.toString(calBase.get(Calendar.YEAR));
        
        if (Integer.parseInt(strMes) < 10){
            strMes = "0" + strMes;
        }
        
        try{
            dtFecha = formato.parse(strAnio + "-" + strMes + "-" + strDia);
        }catch(ParseException  pe){
            pe.printStackTrace();
        }

        strFecha = formato.format(dtFecha);
        
        return strFecha;
    }
    
    public Date getDateFromString(String strFecha){
        Date dtFecha = null;
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            dtFecha = formato.parse(strFecha);
        }catch(ParseException  pe){
            pe.printStackTrace();
        }
        
        return dtFecha;
    }
    
    public long getDiasDiferenciaFechas(Date dtFechaInicio, Date dtFechaFin){
        long lgDiferencia = 0;
        
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
        lgDiferencia = ( dtFechaFin.getTime() - dtFechaInicio.getTime() )/MILLSECS_PER_DAY;
                  
        return lgDiferencia;
    }
    
    public long getDiasDiferenciaFechasEspecial(Date dtFechaInicio, Date dtFechaFin){
        long lgDiferencia = 0;
        
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
        lgDiferencia = ( dtFechaFin.getTime() - dtFechaInicio.getTime() )/MILLSECS_PER_DAY;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtFechaInicio);
        int month = cal.get(Calendar.MONTH);
                        
        if ((month == 1) && (lgDiferencia == 27 || lgDiferencia == 28 || lgDiferencia == 29 )){
            lgDiferencia = 30;
        }else{
            if (lgDiferencia >= 29){
                lgDiferencia = 30;
            }
        }
        
        return lgDiferencia;
    }
    
    public int getDiaFromFecha(String strFecha){
        Date dtFecha = null;        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            dtFecha = formato.parse(strFecha);
        }catch(ParseException  pe){
            pe.printStackTrace();
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtFecha);
        int dia = cal.get(Calendar.DATE);
        
        return dia;
    }
    
    public Calendar calcularFechaActual(){
        String strFechaActual= null;
        String[] strTemp = null;

        Calendar fechaActual = Calendar.getInstance();
        strFechaActual = getFechaActual();
        strTemp = strFechaActual.split("-");                           
        fechaActual.set(Integer.parseInt(strTemp[0]),(Integer.parseInt(strTemp[1])-1),Integer.parseInt(strTemp[2]));

        fechaActual.set(Calendar.SECOND, 0);
        fechaActual.set(Calendar.MILLISECOND, 0);

        return fechaActual;
      }
    
    public String getFechaActual(){
        //Obtener la fecha actual.
                
        Calendar c = Calendar.getInstance();
        String strDia, strMes, strAnio, strFechaActual;

        strDia = Integer.toString(c.get(Calendar.DATE));
        strMes = Integer.toString(c.get(Calendar.MONTH)+1);
        strAnio = Integer.toString(c.get(Calendar.YEAR));                

        if (Integer.parseInt(strMes) < 10){
            strMes = "0" + strMes;
        }

        strFechaActual = strAnio + "-" + strMes + "-" + strDia;
        
        return strFechaActual;
    }
    
    public String getFechaHoraActual(){
        
        String strFechaHora = "";
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date date = new Date(); 
        strFechaHora = dateFormat.format(date);
                        
        return strFechaHora;
    }
    
    public String marcarMiles(String strValor){
        int intValor;
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        
        intValor = Integer.parseInt(strValor);

        return (formateador.format (intValor).toString());
    }
    
    public String validarEstado(String strValor){
        
        if (strValor.equals("A")){
            return "Aprobado";
        }
        
        if (strValor.equals("PA")){
            return "Pendiente Aprobación";
        }
        
        if (strValor.equals("PRA")){
            return "PreAprobado";
        }
            
        if (strValor.equals("P")){
            return "Pendiente";
        }
        
        if (strValor.equals("E")){
            return "Ejecutado";
        }
        
        return "";
    }
    
    
  public double redondear(double valueToRound, int numberOfDecimalPlaces){
    double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
    double interestedInZeroDPs = valueToRound * multipicationFactor;
    return Math.round(interestedInZeroDPs) / multipicationFactor;
}
    
    public String validarVacio(String strValor){
        
        if ((strValor == null) || (strValor.equals("")) || (strValor.equals(" "))){
            return "Por asignar";
        }else{
            return strValor;
        }            
    }
        
    public String validarSiNo(String strValor){
        
        if (strValor.equals("S")){
            return "Si";
        }else{        
            if (strValor.equals( "N")){
                return "No";
            }else{
                if (strValor.equals("")){
                    return "0";
                }else{
                     return "N/A";
                }
            }
        }
    }
}
