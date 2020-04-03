/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizartareas;

/**
 *
 * @author jorge.correa
 */
public class LanzadorTareaSolicitudesAVencerse {
    public static void main(String[] args) {
        try {
            new ProgramacionSolicitudesAVencerse().crearProgramacion();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
