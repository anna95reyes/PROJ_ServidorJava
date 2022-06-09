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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.interficie.GestioProjectesException;
import org.milaifontanals.interficie.IGestioProjectes;

/**
 *
 * @author anna9
 */
public class Servidor {

    private IGestioProjectes cp;
    private Integer port = 5056;
    
    Servidor(IGestioProjectes interficie) {
        cp = interficie;
    }
    
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        
        while (true) {

            Socket socket = null;

            try {
                socket = serverSocket.accept();

                System.out.println("S'ha connectat un client nou: " + socket);

                DataInputStream dis = new DataInputStream (socket.getInputStream());
                DataOutputStream dos = new DataOutputStream (socket.getOutputStream());

                System.out.println("S'està assignant un fil nou per a aquest client");

                Thread t = new ServidorThread(socket, dis, dos, cp);

                t.run();

            } catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }

        
    }
    
}
