/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiadbcon;

import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikku
 */
public class SepiaDBCon {

         //ResultSet rs;
           // int flag;
            Connection con;
            Statement stmt;
    /**
     * @param args the command line arguments
     */
    public SepiaDBCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sepia", "root","");
            stmt=con.createStatement();
           
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SepiaDBCon.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex+"  fail");
        }
    }
    public int todb(String query){
            int flag= -1;
            try {
                 flag=stmt.executeUpdate(query);
                 
             } catch (SQLException ex) {
                 Logger.getLogger(SepiaDBCon.class.getName()).log(Level.SEVERE, null, ex);
             }
             return flag;
    }
   
     public ResultSet fromdb(String query){
            ResultSet rst=null;
            try {
                 rst=stmt.executeQuery(query);
                 
             } catch (SQLException ex) {
                 Logger.getLogger(SepiaDBCon.class.getName()).log(Level.SEVERE, null, ex);
             }
             return rst;
    }
    /*public static void main(String[] args) {
        //SepiaDBCon obj=new SepiaDBCon();// TODO code application logic here
    }*/
    
}
