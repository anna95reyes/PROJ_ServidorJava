/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.servidor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.milaifontanals.interficie.CPSingleton;
import org.milaifontanals.interficie.IGestioProjectes;

/**
 *
 * @author anna9
 */
public class Main {

        private static IGestioProjectes cp;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String nomFitxer = null;
        if (args.length == 0) {
            nomFitxer = "infoCapa.properties";
        } else {
            nomFitxer = args[0];
        }
        
        crearCapaPersistencia(nomFitxer);
        
        Servidor server = new Servidor(cp);
            try {
                server.run();
            } catch (Exception ex) {
                
            }
    }
    
    private static void crearCapaPersistencia(String nomFitxer){
        
        Properties props = new Properties();
        try {
            props.load(new FileReader(nomFitxer));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No es troba fitxer de propietats " + nomFitxer, 
                                "Error Capa de Persistencia", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error en carregar fitxer de propietats " + nomFitxer, 
                                "Error Capa de Persistencia", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nomCapa = props.getProperty("nomCapa");
        if (nomCapa == null || nomCapa.equals("")) {
            System.out.println("Fitxer de propietats " + nomFitxer + " no conté propietat nomCapa");
            return;
        }

        cp = null;
        
        try {
            cp = CPSingleton.getGestorProjectes(nomCapa);
        } catch (Exception ex) {
            System.out.println("Error en crear capa de persistencia");
            return;
        }
    }
    
}
