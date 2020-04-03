/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 *
 * @author jorge.correa
 */
public class Log {
    
    public static void registroTraza(String strTraza){
        
        Calendar c = Calendar.getInstance();
        String strDia = Integer.toString(c.get(Calendar.DATE));
        String strMes = Integer.toString(c.get(Calendar.MONTH)+1);
        String strAnio = Integer.toString(c.get(Calendar.YEAR));
        String strFecha = strDia + "-" + strMes + "-" + strAnio;
        String strHora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String strMinutos = Integer.toString(c.get(Calendar.MINUTE));
        String strSegundos = Integer.toString(c.get(Calendar.SECOND));
        String strTiempo = strHora + ":" + strMinutos + ":" + strSegundos;
        
        try {
            FileWriter fw = new FileWriter("C:\\WebAPPS\\pqrs\\Logs\\Log" + strFecha + ".txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.println(strFecha + " " + strTiempo + ": " + strTraza);
            salida.close();
            bw.close();
            fw.close();           
        }catch(java.io.IOException ioex) {
            System.out.println("se presento el error: " + ioex.toString());
        }
    }
}
