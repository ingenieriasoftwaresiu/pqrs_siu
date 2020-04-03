/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import Negocio.Log;
import automatizartareas.ProgramacionCerrarSolicitudes;
import automatizartareas.ProgramacionSolicitudesAVencerse;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Web application lifecycle listener.
 *
 * @author jorge.correa
 */
public class EjecutarJobs implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            new ProgramacionCerrarSolicitudes().crearProgramacion();
            new ProgramacionSolicitudesAVencerse().crearProgramacion();
        } catch (Exception ex) {
            Log.registroTraza(ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try{
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
           scheduler.shutdown(false);
        } catch (Exception ex) {
            Log.registroTraza(ex.getMessage());
        }
    }
}
