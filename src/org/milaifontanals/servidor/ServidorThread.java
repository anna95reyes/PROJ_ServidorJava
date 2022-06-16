/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.interficie.IGestioProjectes;
import org.milaifontanals.model.Entrada;
import org.milaifontanals.model.Estat;
import org.milaifontanals.model.Projecte;
import org.milaifontanals.model.Tasca;
import org.milaifontanals.model.Usuari;

/**
 *
 * @author anna9
 */
public class ServidorThread extends Thread {
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;
    private IGestioProjectes cp;
    
    
    ServidorThread(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, IGestioProjectes interficie) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        cp = interficie;
    }
    
    @Override
    public void run() {
        Integer codi;
        while (true){
            try {
                codi = (int)ois.readObject();
                if (codi == -1){
                    System.out.println("Client " + this.socket + " envia sortida...");
                    System.out.println("Tancant aquesta connexió");
                    this.socket.close();
                    System.out.println("Connexió tancada");
                    break;
                }
                switch(codi){
                    case 1 :
                        String login = cp.Login((String)ois.readObject(), (String)ois.readObject());
                        if (login != null) {
                            oos.writeObject(login);
                        } else {
                            oos.writeObject("");
                        }
                        break;
                        
                    case 2 :
                        List<Projecte> projectes = cp.getLlistaProjectes((String)ois.readObject());
                        if (projectes != null) {
                            oos.writeObject(projectes);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 3:
                        List<Tasca> tasques = cp.getLlistaTasquesAssignades((String)ois.readObject(),(Integer)ois.readObject(), (Integer)ois.readObject());
                        if (tasques != null) {
                            oos.writeObject(tasques);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 4:
                        List<Estat> estats = cp.getLlistaEstats((String)ois.readObject());
                        if (estats != null) {
                            oos.writeObject(estats);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 5:
                        Tasca tasca = cp.getTasca((Integer)ois.readObject());
                        if (tasca != null) {
                            oos.writeObject(tasca);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 6: 
                        List<Entrada> entrades = cp.getLlistaEntrades((String)ois.readObject(), (Integer)ois.readObject());
                        if (entrades != null) {
                            oos.writeObject(entrades);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 7: 
                        List<Usuari> usuaris = cp.getLlistaUsuaris((String)ois.readObject());
                        if (usuaris != null) {
                            oos.writeObject(usuaris);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 8:
                        Entrada entrada = cp.getEntrada((Integer)ois.readObject(), (Integer)ois.readObject());
                        if (entrada != null) {
                            oos.writeObject(entrada);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 9:
                        Usuari usuari = cp.getUsuari((String)ois.readObject());
                        if (usuari != null) {
                            oos.writeObject(usuari);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                       
                    case 10:
                        Integer novaNumeracio = cp.nextNumeracioEntrada((Integer)ois.readObject());
                        if (novaNumeracio != null) {
                            oos.writeObject(novaNumeracio);
                        } else {
                            oos.writeObject(null);
                        }
                        break;
                        
                    case 11:
                        cp.afegirEntrada((Integer)ois.readObject(), (Entrada)ois.readObject());
                        cp.commit();
                        break;
                        
                    case 12:
                        cp.modificarEntrada((Integer)ois.readObject(), (Entrada)ois.readObject());
                        cp.commit();
                        break;
                        
                    default:
                        oos.writeObject("Entrada incorrecta");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
        
        try {
            this.ois.close();
            this.oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
