/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizartareas;

import Conexion.GestionSQL;
import Negocio.Comunes;
import Negocio.Log;
import Negocio.Notificacion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author jorge.correa
 */
public class SolicitudesAVencerse implements Job {
    // Método que se ejecutara cada cierto tiempo que lo programemos despues.
    
  public void execute(JobExecutionContext jec) throws JobExecutionException {
      
    // Aca pueden poner la tarea o el job que desean automatizar.
    // Por ejemplo enviar correo, revisar ciertos datos, etc.
      
    Log.registroTraza( "Iniciando ejecución de la tarea SolicitudesAVencerse");
      
    String strSQL, strFechaRecibo, strReqRpta, strRadicado, strIdResp = "";
    int totalSolAlertadas=0, totalSolVencidas=0;
    String[] strTemp = null;
    int lgTiempoRpta, lgRestante, lgTiempoConfig;            
    Vector arrConsecutivos = new Vector();
    Vector arrFechasCreacion = new Vector();
    Vector arrReqRpta = new Vector();
    Vector arrTiempoRpta = new Vector();
    Vector arrIdsResp = new Vector();
    Comunes comun = new Comunes();
    Notificacion n = new Notificacion();
    Calendar fechaRecibo = null;
    Calendar fechaRpta = null;
    Calendar fechaActual = null;
        
    try{
        strSQL = "select g.txtNroDiasAlerta from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";
        String[] strDatosGral = GestionSQL.getFila(strSQL, "Buzon");
        lgTiempoConfig = Integer.parseInt(strDatosGral[0]);
        
        strSQL = "select DISTINCT p.txtRadicado, p.dtFechaCreacion, r.txtReqRpta, r.txtTiempoRpta, p.txtNomCargo from buzon_pqrs p INNER JOIN buzon_retroalimentacion r on (p.txtTipoPQRS = r.txtCodigo) where (p.txtIdEstado <> 'AT' and p.txtIdEstado <> 'CPU') ORDER BY CAST(p.txtRadicado AS SIGNED)";
        Vector arrSols = GestionSQL.consultaSQL(strSQL,"Buzon","ALERTASSOLS");
        
        if (arrSols != null){        
            for (int i=0;i<arrSols.size();i++){
                strTemp = arrSols.get(i).toString().split(",");
                arrConsecutivos.add(strTemp[0]);
                arrFechasCreacion.add(strTemp[1]);
                arrReqRpta.add(strTemp[2]);
                arrTiempoRpta.add(strTemp[3]);          
                arrIdsResp.add(strTemp[4]);
            }   

            //Obtener los feriados.

            Vector arrFechas = new Vector();
            strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
            arrFechas = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");        

            for(int i=0;i<arrConsecutivos.size();i++){
                strRadicado = arrConsecutivos.get(i).toString();            
                strReqRpta = arrReqRpta.get(i).toString();
                strIdResp = arrIdsResp.get(i).toString();
                lgRestante = 0;            
                fechaRecibo = null;
                fechaRpta = null;
                fechaActual = comun.calcularFechaActual();

                if (strReqRpta.equals("S")){                
                    lgTiempoRpta = Integer.parseInt(arrTiempoRpta.get(i).toString());
                    fechaRecibo = Calendar.getInstance();                             

                    strFechaRecibo = arrFechasCreacion.get(i).toString();
                    strTemp = strFechaRecibo.split("-");                           
                    fechaRecibo.set(Integer.parseInt(strTemp[0]),(Integer.parseInt(strTemp[1])-1),Integer.parseInt(strTemp[2]));
                    fechaRecibo.set(Calendar.SECOND, 0);
                    fechaRecibo.set(Calendar.MILLISECOND, 0);    

                    fechaRpta= Calendar.getInstance();
                    fechaRpta.set(Calendar.SECOND, 0);
                    fechaRpta.set(Calendar.MILLISECOND, 0);    
                    fechaRpta = comun.incrementarDiasHabiles(fechaRecibo, lgTiempoRpta, arrFechas);                                 

                    lgRestante = (comun.getDiasHabiles(fechaActual, fechaRpta, arrFechas)-1);              

                    if ((lgRestante <= lgTiempoConfig) && (lgRestante > 0)){                            
                       n.NotificacionSolAVencer(strRadicado, Long.valueOf(lgRestante + 1), strIdResp);               
                       totalSolAlertadas = totalSolAlertadas + 1;
                    }else{
                        if (lgRestante<0){
                            n.NotificacionSolVencidas(strRadicado, fechaRpta, strIdResp);
                            totalSolVencidas = totalSolVencidas + 1;
                        }                        
                    }                                   
                }                           
            }
        }
    }catch(Exception e){
        Log.registroTraza("Se generó un error en la tarea SolicitudesAVencerse: " + e.getMessage());
    }

    SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
    Log.registroTraza( "Tarea SolicitudesAVencerse invocada a la hora: " + formato.format(new Date()) + ". Solicitudes alertadas: " + totalSolAlertadas + ". Solicitudes vencidas: " + totalSolVencidas);
  }  
  
}
