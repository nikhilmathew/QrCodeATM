/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiabank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Nikku
 */
public class SepiaBankUDP {
    public static void sendadddata(String data){
        try{
		
		DatagramSocket ds= new DatagramSocket();
		byte[] buffer=data.getBytes();
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),7777);
		ds.send(dp);
                	}catch(Exception e){
	System.out.println(e);
	}
    }
    public static void sendaddATMdata(String data){
        try{
		
		DatagramSocket ds= new DatagramSocket();
                                            System.out.println(data+" in udp sender");
		byte[] buffer=data.getBytes();
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),7778);
		ds.send(dp);
                	}catch(Exception e){
	System.out.println(e);
	}
    }
    public static String[] receiveuserdata(){
       String str=null,retstr[]=null;
        try{
		 Socket s= new Socket("localhost",7780);
                                                System.out.println("socket started");
		//DatagramSocket ds= new DatagramSocket();
                                           // System.out.println(data+" in udp sender");
                                            String str1="userdata";
		//byte buffer[]= str1.getBytes();
                                            //byte buffera[]= new byte[512];
		//DatagramPacket dp=new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),7779);
                                            //DatagramPacket dpr = new DatagramPacket(buffera, buffera.length);
		//ds.send(dp);
                                            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
                                           pw.println(str);
                                           System.out.println("sent userdata string");
                                            BufferedReader br= new BufferedReader(new InputStreamReader(s.getInputStream()));
                                            int rows= Integer.parseInt(br.readLine());
                                           retstr  = new String[rows];
                                           for(int i=0;i<rows;i++){
                                                    str=br.readLine();
                                                    retstr[i]=str;
                                                            System.out.println(str);
                                            }
                                           s.close();
                                            //ds.receive(dpr);
                                            //str= new String(dpr.getData(),0,dpr.getLength());
                                            //System.out.println("in receive userdata  "+str);
                                            
                	}catch(Exception e){
	System.out.println(e);
	}
        return retstr;
    }
}
