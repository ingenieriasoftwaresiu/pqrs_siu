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
public class CerrarSolicitudes implements Job {
     // Método que se ejecutara cada cierto tiempo que lo programemos despues.
    
  public void execute(JobExecutionContext jec) throws JobExecutionException {
      
      Log.registroTraza( "Iniciando ejecución de la tarea CerrarSolicitudes");
      
      String strSQL,strFechaRpta,strRadicado;
      String[] strTemp, strObs, strTempFecha;
      boolean actualizar = false;
      int lgDiferencia, lgTiempoConfig;
      int intTotalCerradas = 0;
      Calendar fechaActual = null;
      Calendar fechaRpta = null;
      Comunes comun = new Comunes();
      
      try{
      
        strSQL = "select g.txtNroDiasCierre from buzon.buzon_generales g where g.txtCodigo = 'frmGeneral'";
        String[] strDatosGral = GestionSQL.getFila(strSQL, "Buzon");
        lgTiempoConfig = Integer.parseInt(strDatosGral[0]);

        strSQL = "select p.txtRadicado as Id, p.dtFechaRpta as Nombre from buzon.buzon_pqrs p where p.txtIdEstado = 'AT' order by p.dtFechaRpta";
        Vector arrSols = GestionSQL.consultaSQL(strSQL, "Buzon", "MAESTROS");
        
        //Obtener los feriados.

            Vector arrFechas = new Vector();
            strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
            arrFechas = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");        
                           
         if (arrSols != null){
            for(int i=0;i<arrSols.size();i++){
               strTemp = arrSols.get(i).toString().split(",");          
              strFechaRpta = strTemp[1];
              strRadicado = strTemp[0];    
              fechaActual = comun.calcularFechaActual();

              if(!(strFechaRpta.equals("null"))){
                  
                fechaRpta = Calendar.getInstance();                             

                 strTempFecha = strFechaRpta.split("-");                           
                 fechaRpta.set(Integer.parseInt(strTempFecha[0]),(Integer.parseInt(strTempFecha[1])-1),Integer.parseInt(strTempFecha[2]));
                 fechaRpta.set(Calendar.SECOND, 0);
                 fechaRpta.set(Calendar.MILLISECOND, 0);    
                                 
                  lgDiferencia = (comun.getDiasHabiles(fechaRpta, fechaActual, arrFechas)-1);                          

                  if (lgDiferencia > lgTiempoConfig){            
                      strSQL = "select count(*) from buzon.buzon_obs_x_sol o where o.txtAtendida = 'N' and o.txtRadicado = '" + strRadicado +"'";
                      strObs = GestionSQL.getFila(strSQL,"Buzon");
                      
                      if (strObs != null){
                        if (Integer.parseInt(strObs[0]) <= 0){
                            actualizar = true;
                        }else{
                            actualizar = false;
                        }
                      }else{
                          actualizar = true;
                      }
                  }
              }else{
                      actualizar = true;
              }

              if (actualizar){
                  strSQL = "update buzon.buzon_pqrs set txtIdEstado = 'CPU' where txtRadicado = '" + strRadicado + "'";
                  GestionSQL.ejecuta(strSQL, "Buzon");
                  intTotalCerradas = intTotalCerradas + 1;
              }  

              strRadicado = "";
              strFechaRpta = ""; 
              strTemp = null;
              actualizar = false;
              lgDiferencia = 0;
            }
        }
      }catch(Exception e){
        Log.registroTraza("Se generó un error en la tarea CerrarSolicitudes: " + e.toString());
     }
              
      SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
      Log.registroTraza( "Tarea CerrarSolicitudes invocada a la hora: " + formato.format(new Date()) + ". Solicitudes cerradas: " + intTotalCerradas);
  }
}
