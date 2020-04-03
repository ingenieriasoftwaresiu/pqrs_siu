/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizartareas;

import Negocio.Log;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author jorge.correa
 */
public class ProgramacionSolicitudesAVencerse {

    Integer intError=0;

    public void crearProgramacion() {

        try {
             Log.registroTraza("Inició la programación de la tarea SolicitudesAVencerse");
            
            // Creación de una instancia de Scheduler.
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start(); 
            
            intError = 1;
            
            // Creación de una instancia de JobDetail.
            JobDetail jobDetail = new JobDetail("SolicitudesAVencerseJob", scheduler.DEFAULT_GROUP, SolicitudesAVencerse.class);
            
             intError = 2;
            
            // Se crea el trigger para ejecución todos los días a las 8:00.            
            Trigger trigger = TriggerUtils.makeDailyTrigger(8,00);
            trigger.setName("SolicitudesAVencerseTrigger");
            trigger.setGroup("grupoSIU");
            trigger.setPriority(1);
            
             intError = 3;

            // Registro dentro del Scheduler.
            scheduler.scheduleJob(jobDetail, trigger);
            
             intError = 4;

           Log.registroTraza("Finalizó la programación de la tarea SolicitudesAVencerse.");

        } catch(Exception e) {
            Log.registroTraza("Se generó un error al ejecutar la tarea SolicitudesAVencerse. Variable error = " +  intError + "." + e.getMessage());
        }
    }    
}
