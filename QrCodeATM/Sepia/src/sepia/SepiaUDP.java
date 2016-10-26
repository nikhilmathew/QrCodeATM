/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.io.PrintWriter;

/**
 *
 * @author Nikku
 */
public class SepiaUDP {
    public static void sendQRRequest(PrintWriter pw,String data){
        try{
		
		//DatagramSocket ds= new DatagramSocket();
		//byte[] buffer=data.getBytes();
		//DatagramPacket dp=new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),7790);
		//ds.send(dp);
                                            pw.println(data);
                	}catch(Exception e){
	System.out.println(e);
	}
    }
}
