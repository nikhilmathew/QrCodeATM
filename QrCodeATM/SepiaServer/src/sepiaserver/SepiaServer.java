/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiaserver;

/**
 *
 * @author Nikku
 */
public class SepiaServer  {
    //static Thread t1=null,t2=null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SepiaServerUDP r1= new SepiaServerUDP();
        SepiaServerUDP r2= new SepiaServerUDP(5555);
        r1.t1=new Thread(r1);
        r1.t2=new Thread(r1);
        r1.t3=new Thread(r1);
        r1.t4=new Thread(r1);
        r2.t5=new Thread(r2);
         r2.t6=new Thread(r2);
        r1.t1.start();
        r1.t2.start();
        r1.t3.start();
        r1.t4.start();
        r2.t5.start();
        r2.t6.start();
       // need paralle execution here (threads)
        
        
// TODO code application logic here
    }

    
                
      
    
    
}
