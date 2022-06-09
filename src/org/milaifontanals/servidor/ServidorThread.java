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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.interficie.IGestioProjectes;

/**
 *
 * @author anna9
 */
public class ServidorThread extends Thread {
    
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private IGestioProjectes cp;
    
    
    ServidorThread(Socket s, DataInputStream dis, DataOutputStream dos, IGestioProjectes interficie) {
        this.socket = s;
        this.dis = dis;
        this.dos = dos;
        cp = interficie;
    }
    
    @Override
    public void run() {
        Integer codi;
        String retornar;
        while (true){
            try {
                codi = dis.readInt();
                if (codi == -1){
                    System.out.println("Client " + this.socket + " envia sortida...");
                    System.out.println("Tancant aquesta connexió");
                    this.socket.close();
                    System.out.println("Connexió tancada");
                    break;
                }
                
                switch(codi){
                    case 1 :
                        retornar = cp.Login(dis.readUTF(), dis.readUTF());
                        dos.writeUTF(retornar);
                        break;
                        
                    default:
                        dos.writeUTF("Entrada incorrecta");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
        
        try {
            this.dis.close();
            this.dos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
