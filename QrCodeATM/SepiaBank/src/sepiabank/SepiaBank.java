/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiabank;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.MaskFormatter;
import static sepiabank.SepiaBankUDP.receiveuserdata;
import static sepiabank.SepiaJSONBank.returnjsonelement;

/**
 *
 * @author Nikku
 */
public class SepiaBank extends JFrame implements WindowListener ,ActionListener{
    Container mainContainer= getContentPane();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * @param args the command line arguments
     */
    public SepiaBank(){
         
          
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setExtendedState(JFrame.MAXIMIZED_BOTH);
          setSize(screenSize.getSize());
          setResizable(false);
          setVisible(true);
          mainContainer.add(mainWindow());
    }
    
    
    public JPanel mainWindow(){// need to beautify
        JPanel mainWindow=new JPanel();
        JButton adduser=new JButton("Add a new Cardholder");
        JButton remuser= new JButton("Remove a user");
        JButton moduser= new JButton("Update  a cardholder details");
        JButton viewusers=new JButton("List all Cardholder Details");
        JButton addatm=new JButton("Add an ATM Machine to the System");
        JButton viewatm=new JButton("Display details of ATM Machines");
        
        mainWindow.setLayout(new FlowLayout());
        mainWindow.add(adduser);
        mainWindow.add(moduser);
        mainWindow.add(remuser);
        mainWindow.add(viewusers);
        mainWindow.add(addatm);
        mainWindow.add(viewatm);/*
        viewatm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   viewATMWindow();
                } catch (ParseException ex) {
                    Logger.getLogger(SepiaBank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                });
        
        remuser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    remUserWindow();
                } catch (ParseException ex) {
                    Logger.getLogger(SepiaBank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                });*/
        moduser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                modUserWindow();
            }
                });
        addatm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    addATMWindow();
               
            }
                });
        adduser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addUserWindow();
                } catch (ParseException ex) {
                    Logger.getLogger(SepiaBank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                });
        return mainWindow;
        
    }
    public void modUserWindow(){
       // sepiadbcon.SepiaDBQuery con= new SepiaDBQuery();
        JFrame moduserwindow= new JFrame();
        JPanel  jp1= new JPanel();
        JPanel jp2=new JPanel();      
        moduserwindow.setLayout(new BorderLayout()) ;
       
        JLabel quote=new JLabel("select a user to update details for");
        String json[]=receiveuserdata();
        
        Object rowData[][] =  new Object [json.length][9];  
        String columnNames[]={"accid","name","accno","email","password","cardno","expiry","pin","cvv"};
        for(int i=0;i<json.length;i++){
          
            for(int j=0;j<9;j++)
                rowData[i][j]=returnjsonelement(columnNames[j],json[i]);
        } 
        jp1.setLayout(new GridLayout(json.length+1, 9,1,2));
        jp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        System.out.println(rowData);
        //String columnNames[]={"name","accno","cord","cardno","expiry","pin","cvv"};
        JTable t=new JTable(rowData, columnNames);
        
        for(int i=0;i<9;i++){
           JLabel ta= new JLabel(columnNames[i]);
            Dimension d= new Dimension(120,30);
            ta.setMaximumSize(d);
            ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jp1.add(t);
        }
         for(int i=0;i<json.length;i++){
          
            for(int j=0;j<9;j++){
                JLabel ta= new JLabel((String) rowData[i][j]);
            ta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jp1.add( t);
            }
        } 
        JScrollPane jsp= new JScrollPane(/*jp1*/t,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        
        moduserwindow.getContentPane().add(jsp,BorderLayout.CENTER);
        //moduserwindow.add(jp1,BorderLayout.NORTH);
        JFormattedTextField user= new JFormattedTextField();
        JFormattedTextField location= new JFormattedTextField();
        JButton submit= new JButton("Submit");
        moduserwindow.setVisible(true);
        //moduserwindow.add(new JLabel("select a user to update"));
       // moduserwindow.add(new JLabel("Location of Machine"));
       // moduserwindow.add(location);
       // moduserwindow.add(submit);
        int x=(int)screenSize.getWidth()/2;
        int y=(int)screenSize.getHeight()/2;  
        moduserwindow.setSize(x, y);
        int xl = (int) ((screenSize.getWidth()- moduserwindow.getWidth()) /2);
        int yl = (int) ((screenSize.getHeight() - moduserwindow.getHeight()) / 2);
        moduserwindow.setLocation(xl, yl);
    }
    public void addATMWindow() { //working
        
         JFrame addatmwindow= new JFrame();
         addatmwindow.setLayout(new GridLayout(4,4,10,10));//need to change
        JFormattedTextField atmid= new JFormattedTextField();
        JFormattedTextField location= new JFormattedTextField();
        JButton submit= new JButton("Submit");
        addatmwindow.setVisible(true);
        addatmwindow.add(new JLabel("ATM id of machine"));
        addatmwindow.add(atmid);
        addatmwindow.add(new JLabel("Location of Machine"));
        addatmwindow.add(location);
        addatmwindow.add(submit);
        
        //frame size and location code
        int x=(int)screenSize.getWidth()/2;
        int y=(int)screenSize.getHeight()/2;  
        addatmwindow.setSize(x, y);
        int xl = (int) ((screenSize.getWidth()- addatmwindow.getWidth()) /2);
        int yl = (int) ((screenSize.getHeight() - addatmwindow.getHeight()) / 2);
         addatmwindow.setLocation(xl, yl);
         
         submit.addActionListener(
                new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                         // this is old code int f=con.insertATMDetails(atmid.getText(),location.getText());
                          //System.out.println(f);
                         String json= SepiaJSONBank.addATMJSON(atmid.getText(),location.getText());//System.out.println(f);
                          SepiaBankUDP.sendaddATMdata(json);
                          //JOptionPane.showMessageDialog(null,"Inserted Successfully");
                                                     int f= JOptionPane.showConfirmDialog(null,"Inserted Succesfully,add another user??" ,"add another ",JOptionPane.YES_NO_OPTION);
                          if(f==1){
                              JOptionPane.showMessageDialog(null,"Inserted Successfully");
                          }
                          else{
                              JOptionPane.showMessageDialog(null,"Oops! The Server did not like what you wrote");
                          }
                          }                        });
        
    }
    public void addUserWindow() throws ParseException{//  working
       // sepiadbcon.SepiaDBQuery con= new SepiaDBQuery();
        JFrame adduserwindow= new JFrame();
        // initialization code
        adduserwindow.setLayout(new GridLayout(4,4,10,10));//need to change
        JFormattedTextField name= new JFormattedTextField();
        JFormattedTextField accno= new JFormattedTextField();
        JComboBox ccordb= new JComboBox();
        adduserwindow.setVisible(true);
        ccordb.addItem("C");
        ccordb.addItem("D");
        MaskFormatter ccmask=new  MaskFormatter("####-####-####-####");
       // ccmask.setPlaceholderCharacter('-');
        JFormattedTextField cardno= new JFormattedTextField(ccmask);
        JTextField email=new JTextField();// changes here nikhil
        JTextField password= new JTextField();
        //Date expiry =new Date(WIDTH);
        JFormattedTextField expiry= new JFormattedTextField(new MaskFormatter("##/####"));
        MaskFormatter pinmask=new  MaskFormatter("####");
        JFormattedTextField pin= new JFormattedTextField(pinmask);
        JFormattedTextField cvv= new JFormattedTextField(new MaskFormatter("###"));
        JButton submit= new JButton("Submit");
        //add to screen code
        adduserwindow.add(new JLabel("Account Holder's Name"));
        adduserwindow.add(name);
        adduserwindow.add(new JLabel("Account number"));
        adduserwindow.add(accno);
        adduserwindow.add(new JLabel("Registered email id"));// changes here nikhil
        adduserwindow.add(email);// changes here nikhil
        adduserwindow.add(new JLabel("Initail Password"));// changes here nikhil
        adduserwindow.add(password);// changes here nikhil
        //adduserwindow.add(new JLabel("Type of Card"));// changes here nikhil
      //  adduserwindow.add(ccordb);// changes here nikhil
        adduserwindow.add(new JLabel("Card Number"));
        adduserwindow.add(cardno);
        adduserwindow.add(new JLabel("Expiry Date "));
        adduserwindow.add(expiry);
        adduserwindow.add(new JLabel("Initial ATM Pin"));
        adduserwindow.add(pin);
        adduserwindow.add(new JLabel("CVV number"));
        adduserwindow.add(cvv);
        adduserwindow.add(submit);
        
        //frame size and location code
        int x=(int)screenSize.getWidth()/2;
        int y=(int)screenSize.getHeight()/2;  
        adduserwindow.setSize(x, y);
        int xl = (int) ((screenSize.getWidth()- adduserwindow.getWidth()) /2);
        int yl = (int) ((screenSize.getHeight() - adduserwindow.getHeight()) / 2);
         adduserwindow.setLocation(xl, yl);
         
         submit.addActionListener(
                new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            //sepiadbcon.SepiaDBQuery con= new SepiaDBQuery();
                          //int f=con.insertCardholderDetails(name.getText(),accno.getText(),ccordb.getSelectedItem().toString(),cardno.getText(),/*(java.sql.Date)expiry.getModel().getValue()*/expiry.getText(), Integer.parseInt(pin.getText()),Integer.parseInt(cvv.getText()));
                          String json= SepiaJSONBank.addJSON(name.getText(),accno.getText(),email.getText(),password.getText(),/*ccordb.getSelectedItem().toString(),*/cardno.getText().replaceAll("-",""),expiry.getText(), Integer.parseInt(pin.getText()),Integer.parseInt(cvv.getText()));//System.out.println(f);
                          SepiaBankUDP.sendadddata(json);
                          //JOptionPane.showMessageDialog(null,"Inserted Successfully");
                                                     int f= JOptionPane.showConfirmDialog(null,"Inserted Succesfully,add another user??" ,"add another ",JOptionPane.YES_NO_OPTION);

                         if(f==0){
                                name.setText("");
                                accno.setText("");
                                cardno.setText("");
                                expiry.setText("");
                                pin.setText("");    
                                cvv.setText("");
                          }
                          else{
                             adduserwindow.dispose();
                             
                          }
                          }                        });
        
        
    }
   
    public static void main(String[] args) {
        SepiaBank sb;
        sb = new SepiaBank();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
