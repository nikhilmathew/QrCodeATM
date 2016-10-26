/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sepiadbcon.SepiaDBQuery;
import static sepiaserver.ServerJSON.SHA512id;
/**
 *
 * @author Nikku
 */
public class SepiaServerUDP implements Runnable {
    Thread t1=null,t2=null,t3=null,t4=null,t5=null,t6=null;
    final int n=20;
    int j=0;
    ServerSocket ss;
Socket s[]= new Socket[n];
Boolean sused[]= new Boolean[n];
        int i=0;
        int portfortcp=0;
    Thread t[]=new Thread[n];
   BufferedReader br[] = new BufferedReader[n];
    PrintWriter p[] = new PrintWriter[n];
    int num=0;
    public SepiaServerUDP(){
          
      }  
    public void SepiaAndroidLogin(int port){
        Boolean res=false;
        String s="denied";
        try {
            DatagramSocket ds=new DatagramSocket(13000);
            
            System.out.println("Android Login Server started at port  "+port);
            DatagramPacket dp;
            
            do{
                byte[] buffer=new byte[256];
               dp=new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);
            InetAddress retadd= dp.getAddress();
            String d= new String(buffer,0,buffer. length);
            d=d.trim();
            System.out.println(d+"this is my msg");
            res=ServerJSON.verifyAndroidUserCredentials(d);
             System.out.println(res);
            int portreply=dp.getPort();
            if(res==true){
          s="approved";
            }
            else{
            s="denied";
            }
           dp=new DatagramPacket(s.getBytes(),s.length(),retadd,portreply);
            ds.send(dp);
            
            }while(true);
          /*  ServerSocket ss= new ServerSocket(port);
             for(;;){
               // HttpServer server=HttpServer.create(new InetSocketAddress(8080));
               Socket client= ss.accept();
                BufferedReader in= new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out=new PrintWriter(client.getOutputStream(),true);
                String line;
                while((line=in.readLine())!=null){
                    if(line.length()!=0)
                            break;
                    System.out.println(line);
                 }
                 //out.close();
                 //in.close();
                 //client.close();
            }*/
        } catch (Exception ex) {
            Logger.getLogger(SepiaServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public SepiaServerUDP(int port){
          try{
              ss=new ServerSocket(port);
              portfortcp=port;
              System.out.println("Server tcp started at port"+port);
              for(int i=0;i<n;i++){
                  sused[i]=false;// initailize each socket isused identifier to false
              }
          }
          catch(Exception e){
              System.out.println(e);
          }
      }  
      
    public void checkATM()throws IOException{
        
        System.out.println("check atm running in port 6666");
         SepiaDBQuery con=new SepiaDBQuery();
         int port=portfortcp;
         int ret=-1;
        try {DatagramSocket ds=new DatagramSocket(6666);
            DatagramPacket dp;
            byte[] buffer= new byte[256];
            dp=new DatagramPacket(buffer, buffer.length);
            do{
            ds.receive(dp);
            InetAddress retadd= dp.getAddress();
            String d= new String(buffer,0,buffer. length);
            d=d.trim();
            
            System.out.println(d+".   atmid , this is sent by atm");
            ret=con.ATMverify(d);
            System.out.println(ret+ "in udp");
            Boolean a= false;
            byte[] bb;
            if(ret!=-1){
                if(ret==1){
                    a=true;
                    String pppp=Integer.toString(port);
           bb = pppp.toString().getBytes(); 
            
                }else{
                    a=false;
                 String pppp=Integer.toString(-1);
            bb = pppp.toString().getBytes(); 
           }
            DatagramPacket dp1= new DatagramPacket(bb,bb.length, retadd, 6667);
            ds.send(dp1);
            Boolean diditconnect=false;
            while(diditconnect==false){
                System.out.println("inside while1");
                if(sused[i]==false){
                                  System.out.println("inside method  is used");
     
                    s[i]=ss.accept();
                         sused[i]=true;
                         diditconnect=true;
                        t[i]=new Thread(this);
                        t[i].start();
                }else{
                    i=(i+1)%n;
                }
            }
            System.out.println("Client"+i+" has connected");
            i++;
            System.out.println(d);
           
            ret=-1;
            
            
            }
            }while(true);
           
        } catch (Exception ex) {
            Logger.getLogger(SepiaServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    public  void returnholderdata() {
         System.out.println("returnholderdata running in port 7780");    
        try{
                                            SepiaDBQuery con=new SepiaDBQuery();
		//DatagramSocket ds= new DatagramSocket(7779);
		byte[] buffer=new byte[256];
		String str;
                                            System.out.println("return holder data running");
                                             ServerSocket ss= new ServerSocket(7780);
                                            Socket s=ss.accept();
                                              System.out.println("return holder data connected");
                                            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()) );
                                            str=br.readLine();
                                            System.out.println(str +"  return holder data running");
		//DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
                                            //DatagramPacket dpr;
                                           // ds.receive(dp);
                                            //receive a signal paacket to start sendingdata
                                           // if (str.equals("userdata")){
                                            ResultSet rr= con.readallaccount();
                                            rr.last();
                                              int rows= rr.getRow();
                                                    rr.beforeFirst();
                                                    
                                            
                                            PrintWriter pw= new PrintWriter(s.getOutputStream(),true);
                                            pw.println(rows);
                                            while(rr.next()){
                                                String sendit=ServerJSON.convertrstoJSON(rr.getString(1),rr.getString(2),rr.getString(3),rr.getString(4),rr.getString(5),rr.getString(6),rr.getString(7),rr.getString(8),rr.getString(9));
                                                System.out.println(sendit);
                                                pw.println(sendit);
                                                
                                            }
                                            //}
                                            ss.close();
                                            
		
	}catch(Exception e){
	System.out.println("returnholderdata"+e);
	}
    }
    public  void receiveadddata(){
         System.out.println("receive add data running in port 7777");    
        try{
                                            
		DatagramSocket ds= new DatagramSocket(7777);
		byte[] buffer=new byte[256];
		String str;
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
		do{
		ds.receive(dp);
                                            System.out.println(dp+"in receiver meth");
		str= new String(dp.getData(),0,dp.getLength());
		ServerJSON.addJSON(str);
		}while(true);
	}catch(Exception e){
	System.out.println(e);
	}
    } 
    public  void receiveaddATMdata(){
         System.out.println("receive addATM running in port 7778");    
        
        try{
                                            
		DatagramSocket ds= new DatagramSocket(7778);
		byte[] buffer=new byte[256];
		String str;
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
		do{
		ds.receive(dp);
                                            System.out.println(dp+"in receiver meth");
		str= new String(dp.getData(),0,dp.getLength());
		ServerJSON.addATMJSON(str);
		}while(true);
	}catch(Exception e){
	System.out.println(e);
	}
    } 
    public void listentoqrgeneraterequests(){
         System.out.println("listento qrgeneraterequest running in port 7790");
        try{
                                            
		DatagramSocket ds= new DatagramSocket(7790);
		byte[] buffer=new byte[256];
		String str;
		DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
		do{
		ds.receive(dp);
                                            System.out.println(dp+"in listener to qr generate meth");
		str= new String(dp.getData(),0,dp.getLength());
		ServerJSON.createQRGenReq(str);
		}while(true);
	}catch(Exception e){
	System.out.println(e);
	}
    }

    @Override
    public void run() {
        try {
        while(true){
        if(Thread.currentThread()==t1){
           // SepiaServerUDP r1= new SepiaServerUDP();
             receiveadddata();
        }else
          if(Thread.currentThread()==t2){
            //SepiaServerUDP r2= new SepiaServerUDP();
              receiveaddATMdata();
        }else
          if(Thread.currentThread()==t3){
            //SepiaServerUDP r2= new SepiaServerUDP();
              returnholderdata();
        }else
          if(Thread.currentThread()==t4){
            //SepiaServerUDP r2= new SepiaServerUDP();
             listentoqrgeneraterequests();
        }else
          if(Thread.currentThread()==t5){
            
                //SepiaServerUDP r2= new SepiaServerUDP();
                checkATM();
          
        }else
          if(Thread.currentThread()==t6){
            
                //SepiaServerUDP r2= new SepiaServerUDP();
                SepiaAndroidLogin(13000);
          
        }else{
        for( j=0;j<n;j++){
             SepiaDBQuery con = new SepiaDBQuery();
                   if(Thread.currentThread()==t[j]  && sused[j]==true){
                       String pintemplate=null;
                        System.out.println("thread  "+j+" this is in run method");
                                 br[j]=new BufferedReader(new InputStreamReader(s[j].getInputStream()));
                                 p[j]=new PrintWriter(new OutputStreamWriter(s[j].getOutputStream()),true);
                                 System.out.println("successfully connect reader and writer classes");
                                 //p[j].println("initiating contact with client");
                                 String atmid,requestid,time;
                                 atmid=br[j].readLine();//1
                                 System.out.println("read  atmid from client" +atmid);
                                 requestid=br[j].readLine();//2
                                 System.out.println("read  reqid from client");
                                 time=br[j].readLine();//3
                                 System.out.println("read all details from server");
                                 String time1=time.replaceAll(" |:|\\.|-","");
                                 
                                 // now we have all the details to build the transaction
                                 
                                 String txn=ServerJSON.buildTransactionID(requestid, atmid, time1);
                                 System.out.println(txn+"this is the transaction ID");//till here its ok
                                   String  pintemp=PinTemp.generatePinTemplate();
                                    con.initiateTranactionphase(txn, atmid, time,pintemp);
                                    p[j].println("got the message sending txn id next");//4this is not delivered the second time
                                    p[j].println(txn);//5
                                   //now check for changes to the db entry till it gets a status 1 from db
                                   
                                    long currentTime= System.currentTimeMillis();
                                    long targetTime=System.currentTimeMillis() +TimeUnit.SECONDS.toMillis(15);
                                   int flag=0;
                                   //// not responding somewhere here
                                   System.out.println(br[j].readLine());//6
                                   System.out.println(currentTime+"    "+targetTime+"     "+(targetTime-currentTime));
                                    while(currentTime<targetTime && flag==0){
                                        currentTime=System.currentTimeMillis();
                                       flag= con.getAndroidTransactionStatus(txn);
                                       if(flag==1){
                                          pintemp=con.getPinTemplate(txn);
                                          System.out.println("pintemp"+pintemp);
                                        pintemp=SHA512id(con.getPinTemplate(txn),"54682");
                                       }
                                        System.out.println(currentTime+"    "+targetTime+"     "+(targetTime-currentTime));
                                       Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                                    }
                                    //enter code here to make the current transaction ststus invalid
                                     System.out.println(currentTime+"    "+targetTime+"     "+(targetTime-currentTime)+"    " +flag);
                                   String a=null;
                                     if(flag==1){
                                         a="Authorized";
                                          p[j].println(a);//7
                                          p[j].println(pintemp);//8
                                          System.out.println("just sent pintemplate:  "+pintemp);
                                          // retreive balance from account and atm
                                          int atmcash=con.retreiveATMBal(atmid);
                                          int usercash=con.retreiveUserAccountBal(txn);
                                          p[j].println("sending atm balance and user balance");//9
                                          p[j].println(atmcash);//10
                                          p[j].println(usercash);//11
                                          System.out.println("Reads the type of transaction that is being undergone");
                                           String ttype=br[j].readLine();//12
                                           con.updatetransactiontype(txn, ttype);
                                           if(ttype.equals("withdrawl")){
                                           
                                               con.updateCashBalance(atmid,  Integer.parseInt(br[j].readLine()),txn);  //13  // withdrawl transaction // received amount withdrawn
                                           }
                                           else if(ttype.equals("statement")){
                                               p[j].println("send transaction deails of this user");//13 alt
                                           }
                                     }else{ 
                                         a="Reject";
                                        p[j].println(a);//7 diversion A
                                     }
                                    System.out.println("just sent the code to proceed");
                                    
                                    
                    }
            }
        }
        }
          } catch (IOException ex) {
               System.out.println("Client Disconnected from their end"); 
               sused[j]=false;
              Logger.getLogger(SepiaServerUDP.class.getName()).log(Level.SEVERE, null, ex);
               
            } catch (InterruptedException ex) {
            Logger.getLogger(SepiaServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SepiaServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
