
package Conexion;

import Negocio.Log;
import java.sql.*;
import java.util.Vector;

/**
 *
 * @author jorge.correa
 */
public class GestionSQL {

    static public String ejecuta(String strSQL, String strBaseDatos){
        Statement st = null;
        Connection cn = null;
        String strMensaje = null;

        try{         
            Conexion db = new Conexion();
            cn = db.getConnection(strBaseDatos);

            if (cn == null){
                strMensaje = "No hay conexión a la base de datos";
            }else{
                st = cn.createStatement();
                st.execute(strSQL);
                st.close();
                cn.close();
                Log.registroTraza(strSQL);
            }
        }catch(SQLException e){
            strMensaje = e.getMessage();
        }catch(Exception e){
            strMensaje = e.getMessage();
        }

        return strMensaje;
    }
   
    static public Vector consultaSQL(String strSQL, String strBaseDatos, String strAccion){
        Statement st = null;
        Connection cn = null;
        ResultSet rs = null;
        Vector arrDatos = new Vector();
        
        try{
            Conexion db = new Conexion();
            cn = db.getConnection(strBaseDatos);

            if (cn == null){
                arrDatos = null;
            }else{
                st = cn.createStatement();
                rs = st.executeQuery(strSQL);                   
                
                if (!rs.next()){
                    arrDatos = null;
                }else{
                    rs.beforeFirst();
                }                                   
                                                        
                if (strAccion.equals("LOGRESP")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5));
                   }
                }
                
                if (strAccion.equals("APPS")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4));
                   }
                }
                
                if (strAccion.equals("PERSONASXCARGO")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3));
                   }
                }
                
                 if (strAccion.equals("TIPOSSOL")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1) + ">" + rs.getString(2) + ">" + rs.getString(3));
                   }
                }
                 
                 if (strAccion.equals("OBS")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1) + ">" + rs.getString(2) + ">" + rs.getString(3) + ">" + rs.getString(4) + ">" + rs.getString(5));
                   }
                }
                                                
                if (strAccion.equals("MAESTROS")){
                    while (rs.next()){
                        arrDatos.add(rs.getString("Id") + "," + rs.getString("Nombre"));
                   }
                }
                
                if (strAccion.equals("FECHAS")){
                    while (rs.next()){
                        arrDatos.add(rs.getString(1));
                   }
                }
                
                if (strAccion.equals("PQRS")){
                    String strCadena;
                  
                    while (rs.next()){
                        strCadena = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7) + "," + rs.getString(8) + "," + rs.getString(9);
                        arrDatos.add(strCadena);
                    }                        
                }
                
                if (strAccion.equals("INFORMESEGPQRS")){
                    String strCadena;
                  
                    while (rs.next()){
                        strCadena = rs.getString(1) + "<" + rs.getString(2) + "<" + rs.getString(3) + "<" + rs.getString(4) + "<" + rs.getString(5) + "<" + rs.getString(6) + "<" + rs.getString(7) + "<" + rs.getString(8) + "<" + rs.getString(9) + "<" + rs.getString(10);
                        arrDatos.add(strCadena);
                    }                        
                }
                
                if (strAccion.equals("INFORMESEGPQRSNEW")){
                    String strCadena;
                  
                    while (rs.next()){
                        strCadena = rs.getString(1) + "<" + rs.getString(2) + "<" + rs.getString(3) + "<" + rs.getString(4) + "<" + rs.getString(5) + "<" + rs.getString(6) + "<" + rs.getString(7) + "<" + rs.getString(8) + "<" + rs.getString(9) + "<" + rs.getString(10) + "<" + rs.getString(11);
                        arrDatos.add(strCadena);
                    }                        
                }
                    
                if (strAccion.equals("VISUALIZACION")){
                    String strCadena;
                  
                    while (rs.next()){
                        strCadena = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4);
                        arrDatos.add(strCadena);
                    }                        
                }     
                
                if (strAccion.equals("ALERTASSOLS")){
                    String strCadena;
                  
                    while (rs.next()){
                        strCadena = rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getString(5);
                        arrDatos.add(strCadena);
                    }                        
                }
                
                rs.close();
                st.close();
                cn.close();
            }
        }catch(SQLException e){
           arrDatos = null;
        }catch(Exception e){
           arrDatos = null;
        }
        return arrDatos;
    }
    
    static public Vector consulta(String strSQL, String strBaseDatos){

        Vector regs = new Vector();
        Statement st = null;
        Connection cn = null;
        ResultSet rs = null;
        ResultSetMetaData rm = null;
        int intCols = 0;

        try{
            Conexion db = new Conexion();
            cn = db.getConnection(strBaseDatos);

            if (cn == null){
                regs = null;
            }else{
                st = cn.createStatement();
                rs = st.executeQuery(strSQL);                
                rm = rs.getMetaData();
                intCols = rm.getColumnCount();
                
                String[] strTitCols = new String[intCols];
                
                for(int i=0;i<intCols;i++){
                    strTitCols[i] = rm.getColumnName(i+1); 
                }
                regs.add(strTitCols);
                
                while(rs.next()){
                    String[] reg = new String[intCols];
                    
                    for(int i=0;i<intCols;i++){
                        reg[i] = rs.getString(i+1);
                    }
                    regs.add(reg);
                }
                
                rs.close();
                st.close();
                cn.close();
            }

        }catch(SQLException e){
            regs = null;
        }catch(Exception e){
            regs = null;
        }
        return regs;
    }
    
    static public String[] getFila(String strSQL, String strBaseDatos){
        Vector vector = consulta(strSQL, strBaseDatos);
        String[] fila = null;
        
        if (vector != null){
            if (vector.size() > 1){
                fila = (String[]) vector.get(1);
            }
        }
        return fila;
    }
    
    static public String getCampo(String strSQL,String strBaseDatos){
        String[] fila = getFila(strSQL,strBaseDatos);
        String campo = null;
        
        if (fila != null){
            campo = fila[0];
        }
        return campo;
    }

    public static String[] getFila(String strSQL) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
