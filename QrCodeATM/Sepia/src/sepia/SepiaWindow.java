/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import com.google.zxing.WriterException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
 import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
/**
 *
 * @author Nikku
 *///to do set relooping of this class , make code efficient
public class SepiaWindow extends JFrame implements ActionListener,WindowListener {
    ImageIcon  imgp;
    JLabel qr;
    Thread rt=null;
    static int x,y;
    SepiaWindow sw;
       String response,pintemp;
       int Deathflag=1;
    Container initial= getContentPane();
   PrintWriter pw;
   BufferedReader br;
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   JPanel second= new JPanel();
   JPanel first= new JPanel();
   JPanel third= new JPanel();
   Socket s=null;
   SepiaWindow(){
    }
    public void SepiaATMClient(int port) throws IOException{
        
        
        
        
        try {
           
            s= new Socket("localhost",port);
            System.out.println("Socket connected at port"+port);
            br= new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw= new PrintWriter(s.getOutputStream(),true);
            initial.setBackground(new Color(1, 1,1, (float) 0.45));
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setResizable(false);
            setSize(screenSize.getSize());
            x = (int) ((screenSize.getWidth()) /2);
            y = (int) ((screenSize.getHeight()) / 2);
            setVisible(true);
            
/// first frame
            JButton click= new JButton(new ImageIcon("Avengers-movie-shield-logo.png"));
            first.setLayout(null);
            first.setSize(screenSize);
            first.setBackground(Color.WHITE);
            JButton exittter= new JButton("Exit me");
            exittter.setSize(10,15);
            click.setSize(480,480);
            first.add(exittter);
            exittter.setBounds(10,10,20,20);
            click.setBounds(x-240,y-240,480,480);
           
 // end of first frame
            
            
            
           
           // start of panel 2
            imgp= new ImageIcon("C:\\Users\\Nikku\\Pictures\\My Start Wallpapers\\elements.png");
            qr= new JLabel(imgp);//second
            JButton exiter= new JButton("exit me on page 2");
           JButton tocode=new JButton("Redirect me to pin input");
            JLabel qrread= new JLabel("Please Scan this qr code");
            JLabel third1=new JLabel("Enter your ATM Pin here");
            JPasswordField atmpin=new JPasswordField(null, WIDTH);
            exiter.setSize(10,15);
            second.setLayout(null); 
            second.add(qrread);
            second.add(exiter);
            second.add(tocode);
            // end of panel 2
            
            // start of panel 3
            JButton exit3= new JButton("exit me on page 3");
            JButton aaa= new JButton(" click me ");
           third.setLayout(null);
            aaa.setBounds(500, 500, 300, 100);
            third1.setBounds(900,500,120,40);
            exit3.setBounds(10,10,50,50);
            atmpin.setBounds(950, 650, 100, 50);
            tocode.setBounds(30, 1000, 300, 40);
            qrread.setBounds(760, 280, 400, 80);
            qr.setBounds(760,340,400,400);
            third.add(third1);
            third.add(aaa);
            third.add(atmpin);
            third.add(exit3);
            // end of panel 3
            
            // panel 1
             first.add(click); 
             initial.add(first);
           // panel 1
            
           
           
           // listener for big button on first panel
            click.addActionListener(
                    new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            
                            Random ran = new Random();//qr generation code from here
                            int reqid = ran.nextInt(999999) ;
                            Date  date= new java.util.Date();
                            Timestamp t= new Timestamp(date.getTime());
                            System.out.println(reqid +"               "+new Timestamp(date.getTime()));
                            
                            System.out.println(Sepia.atmid);//checking if this class can read atmid
                            // send atmid and requestid to server
                            pw.println(Sepia.atmid);//1
                            pw.println(reqid);//2
                            pw.println(t);//3
                            System.out.println("got past the frist three write requests");
                            
                            try {
                            
                                System.out.println(br.readLine());//4got the message sending transaction id next
                                System.out.println("got past the next  receive");
                                String txn=br.readLine();//5
                                System.out.println(txn);
                                GenerateQRCode.createQRImage(txn);
                           
                            
                            initial.remove(first);
                            second.remove(qr);
                            imgp= new ImageIcon("QR.png");
                            qr= new JLabel(imgp);
                            second.add(qr);
                            qr.setBounds(760,340,400,400);
                           
                                        initial.add(second);
                                        initial.revalidate();
                                        initial.repaint();
                                        
                                        
                                        pw.println("Showed QR here");//6
                                         System.out.println("after repaint to show QR");
                                       
                                        SwingUtilities.invokeLater(new Runnable(){
                                        public void run(){
                                            try{
                                            response= br.readLine();//7
                                            System.out.println(response);
                                                  if(response.equals("Authorized")){
                                                            initial.remove(second);
                                                            initial.add(third);
                                                            initial.revalidate();
                                                            initial.repaint();
                                                            pintemp=br.readLine();//8
                                                            System.out.println(pintemp);
                                              
                                             }else{
                                                System.out.println("Transaction Dropped due to timeout");
                                                System.exit(0);
                                               
                                            }
                                            }catch(IOException ex){
                                                ex.printStackTrace();
                                            }
                                            }});
                                        System.out.println("this line is after receiving Auth from server");
                                        
                                       
                                      
         
                                       
                             } catch (IOException ex) {
                                Logger.getLogger(SepiaWindow.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (WriterException ex) {
                                Logger.getLogger(SepiaWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }                       });
          
                                       
                                       
                                      
            
            exit3.addActionListener(
                    new ActionListener()
                    {
                        
                        public void actionPerformed(ActionEvent e)
                        { System.exit(0);
                        }
                        
                        
                    });
            exit3.addActionListener(
                    new ActionListener()
                    {
                        
                        public void actionPerformed(ActionEvent e)
                        { System.exit(0);
                        }
                        
                        
                    });
            aaa.addActionListener(
                    new ActionListener()
                    {
                        
                        public void actionPerformed(ActionEvent e)
                        { try {
                            System.out.println("just clciked button on third panel");
                            String pinatm=atmpin.getText();
                            System.out.println(pintemp);
                            pinatm=SepiaJSON.SHA512id(pinatm.trim(), "54682");
                            System.out.println(pinatm);
                            if(pinatm.equals(pintemp)){
                                
                                    System.out.println(" eureka");
                                    ATMWindow.ATM(br,pw);
                               
                            }else{
                                System.out.println("damn");
                                // show error message
                            } } catch (IOException ex) {
                                    Logger.getLogger(SepiaWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        
                        
                    });
            tocode.addActionListener(
                    new ActionListener()
                    {
                        
                        public void actionPerformed(ActionEvent e)
                        {
                            initial.remove(second);
                            initial.add(third);
                            initial.revalidate();
                            initial.repaint();
                        }
                        
                        
                    });
        } catch (IOException ex) {
            Logger.getLogger(SepiaWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
       /* while(true){
          try{  System.out.println("hiiii" +Deathflag);
                                           if (Deathflag==0){
                                               s.close();
                                               return 0;
                                           }
                                           if(Deathflag==-1)
                                               break;
                                            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
          }catch(Exception e){e.printStackTrace();}
                                       }   */
       // s.close();// this causes infinite looping in server
     //  s.close();
       //System.out.println("near the end of code");
        
}
/*public void run() {
    System.out.println("Successfully started thread");
   try{
       if(Thread.currentThread()==rt){
        response= br.readLine();
         System.out.println(response);
         if(response.equals("Authorised")){
                                              initial.remove(second);
                                              initial.add(third);
                                              initial.revalidate();
                                              initial.repaint();
         }
    }}catch(Exception e){
        e.printStackTrace();
    }
}   
*/
    @Override
    public void actionPerformed(ActionEvent e) {
       
         
                    }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
       System.exit(0);
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    

    
}
