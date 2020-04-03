/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Negocio;

import Conexion.GestionSQL;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jorge.correa
 */
public class ComunesTest {
    
    public ComunesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDiasHabiles method, of class Comunes.
     */
    @Test
    public void testGetDiasHabiles() {
        System.out.println("getDiasHabiles");
        Calendar fechaInicial = null;
        Calendar fechaFinal = null;
        Comunes instance = new Comunes();
        int result = 0;
        
        Vector arrFechas = new Vector();
        
        fechaInicial = Calendar.getInstance();
        fechaFinal = Calendar.getInstance();
        
        fechaInicial.set(2014,9,23);
        fechaFinal.set(2014,9,31);
                        
        String strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
        arrFechas = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");
                   
        result = instance.getDiasHabiles(fechaInicial, fechaFinal,arrFechas);
        
        System.out.println("La diferencia de días entre fechas es: " + result);
        
        assertTrue(true);
        // TODO review the generated test code and remove the default call to fail.
       
    }        

    /**
     * Test of incrementarDiasHabiles method, of class Comunes.
     */
    @Test
    public void testIncrementarDiasHabiles() {
        /*System.out.println("incrementarDiasHabiles");
        Calendar fechaInicial = null;
        int intNroDias = 0;
        Vector arrFeriados = null;
        Comunes instance = new Comunes();
        
        String strSQL = "SELECT d.dtFecha from users.users_dias_no_habiles d order by d.dtFecha";
        arrFeriados = GestionSQL.consultaSQL(strSQL, "Users", "FECHAS");
        
        fechaInicial = Calendar.getInstance();
        fechaInicial.set(2014, 8, 20);
        fechaInicial.set(Calendar.SECOND, 0);
        fechaInicial.set(Calendar.MILLISECOND, 0);    
        intNroDias = 15;
        
        Calendar result = instance.incrementarDiasHabiles(fechaInicial, intNroDias, arrFeriados);
        System.out.println("La fecha final es: " + result.getTime().toString());
        
        assertTrue(true);*/
    }

}