/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Nikku
 */
public class Sepia {
    public final static String atmid="ATM0000000001";
    
    public static int verifyATM()  {
        int p = -1;
       
        try {
            DatagramSocket ds=new DatagramSocket();
            DatagramSocket ds2= new DatagramSocket(6667);
            DatagramPacket dp;
            byte b[]=atmid.getBytes();
            dp=new DatagramPacket(b,b.length,InetAddress.getByName("localhost"),6666);
            ds.send(dp);
            byte b2[]= new byte[256];
            DatagramPacket dp1= new DatagramPacket(b2,b2.length);
            ds2.receive(dp1);
              
            String aa=new String(b2,0,b2. length);
            p=Integer.parseInt(aa.trim());
            
            System.out.println(aa+"this is the string returned in atm ....... "+ p);
          ds.close();
                  ds2.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Sepia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SepiaWindow sp= new SepiaWindow();
       int checkatm=verifyATM();
       int flag=0;
       System.out.println(checkatm);
       if(checkatm<=0){
           System.out.println("This ATM machine could not be authenticated by the server");
           }
       else{
            System.out.println("connecting to server on tcp port"+checkatm);
          
           
        sp.SepiaATMClient(checkatm);
        // there still lies the problem of repeated invocation of this shit!!!!!!!!!!!!!!!!
         
            }
    
       }
     //  
    
   
}
