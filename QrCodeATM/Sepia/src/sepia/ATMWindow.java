/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nikku
 */
public class ATMWindow  {
  static int ATMBAL;
    
    ATMWindow(){}
    public static void ATM(BufferedReader br,PrintWriter pw) throws IOException{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame atm= new JFrame("ATM Transaction Window"); 
        atm.setSize((int)screenSize.getWidth()/2*3,(int)screenSize.getHeight()/2*3);
        int xl = (int) ((screenSize.getWidth()- atm.getWidth()) /2);
        int yl = (int) ((screenSize.getHeight() - atm.getHeight()) / 2);
        atm.setLocation(xl, yl);
        atm.setVisible(true);
        atm.setLayout(new BorderLayout());
        
       
        JPanel jp= new JPanel(null);
        JButton getCash= new JButton("Get Cash");
        JButton statement= new JButton(" my Transactions");
       // String withdrawlamt = null;
        atm.add(jp,BorderLayout.CENTER);
        getCash.setBounds(150,150,170,100);
        statement.setBounds(590,150,170,100);
        jp.add(getCash);
        jp.add(statement);
        System.out.println(br.readLine());//9
        ATMBAL=Integer.parseInt(br.readLine());//10
       int userbalance=Integer.parseInt(br.readLine());//11
        System.out.println(ATMBAL+"    "+userbalance);
        
        getCash.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawlamt=null;
                int a,b,c;
                statement.setEnabled(false);
                withdrawlamt=JOptionPane.showInputDialog("enter the amount to withdraw");
                System.out.println(withdrawlamt);
                if(Integer.parseInt(withdrawlamt)>userbalance){
                    a=JOptionPane.showConfirmDialog(jp,"Your account balance is less than the withdrawl amount, \n do you want to try again with a different amount?");
                    if(a==1){
                            System.exit(0);
                    }
                }else if(Integer.parseInt(withdrawlamt)>ATMBAL){
                   b= JOptionPane.showConfirmDialog(jp,"The Atm Machine cannnot provide you with the amount at the moment, \n do you want to try again with a different amount?");
                    if(b==1){
                            System.exit(0);
                    }
                }else{
                   c= JOptionPane.showConfirmDialog(jp,"Do You Want to Proceed with the Transaction?");
                   if(c==1){
                            System.exit(0);
                    }else if(c==0){
                        pw.println("withdrawl");//12
                        pw.println(withdrawlamt);//13
                       JOptionPane.showMessageDialog(jp,"Transaction Successful");
                       System.exit(0);
                       
                   }
                }
                
            }
        });
        statement.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                        JFrame stmtframe= new JFrame("User Statement");
                        stmtframe.setSize(700,600);
            }
        });
       
       
    }
    public static void main(String[] args){
        //ATM();
        
    }
   
    
}
