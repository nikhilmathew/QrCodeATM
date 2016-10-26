/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiadbcon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikku
 */
public class SepiaDBQuery  { 
    SepiaDBCon con= new SepiaDBCon();
    public void updatetransactiontype(String txn,String type){
         int f1= con.todb("update txn set txntype='"+type+"',status=3 where txnid='"+txn+"'");
    }
    public void updateCashBalance(String atmid,int cash,String txnid){
        int accid=0;
            ResultSet rr=con.fromdb("select accno from txn where txnid='"+txnid+"'");
            ResultSet rs;
            try {
           if(rr.next())
                accid=rr.getInt(1);
        int f1= con.todb("update atm set cash=cash-"+cash+" where atmid='"+atmid+"'");
         int f2= con.todb("update account set balance=balance-"+cash+" where accid='"+accid+"'");
                }catch(Exception e){}
    }
    public int retreiveATMBal(String atmid){
         int cash=-1;
            ResultSet rs=con.fromdb("select cash from atm where atmid='"+atmid+"'");
            try {
           
            if(rs.next())
                cash=rs.getInt(1);
           
        } catch (SQLException ex) {
            Logger.getLogger(SepiaDBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
            return cash;
    }
    
    public int retreiveUserAccountBal(String txnid){
         int balance=-1;
         int accid=0;
            ResultSet rr=con.fromdb("select accno from txn where txnid='"+txnid+"'");
            ResultSet rs;
            try {
           if(rr.next())
                accid=rr.getInt(1);
           rs=con.fromdb("select balance from account where accid='"+accid+"'");
            if(rs.next())
               balance=rs.getInt(1);
           
        } catch (SQLException ex) {
            Logger.getLogger(SepiaDBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
            return balance;
    }
    public String getPinTemplate(String txn) throws SQLException{
        String pintemp="",pintemplate=null;
        String pin=null;
        ResultSet rs=con.fromdb("select a.pin,t.pintemplate from txn t,account a where a.accid=t.accno and t.txnid='"+txn+"'");
        if(rs.next()){
            pintemplate=rs.getString(2);
            pin= rs.getString(1);
        }
            int j=0,i=0;
            System.out.println(pintemplate+"   "+ pin);
             while (i<8 || j<4)
        {
            if(pintemplate.charAt(i)=='*')
            {
                
                
                pintemp +=pin.charAt(j);
                i++;j++;
                
            }else{
                
                 
                  pintemp+=pintemplate.charAt(i);
                  i++;
            }
        }
            System.out.println(pintemp);
        return pintemp.trim();
    }
public int getAndroidTransactionStatus(String txn){
       int f=0; 
       try {
            ResultSet rs=con.fromdb("select status from txn where txnid='"+txn+"'");
            if(rs.next()){
                f=rs.getInt(1);
                if(f>1)// to prevent already completed transcations from being used
                    f=0;
                
            } 
        } catch (SQLException ex) {
            Logger.getLogger(SepiaDBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
}
    public void initiateTranactionphase(String txn,String atmid,String timestamp,String pintemplate){
        int f= con.todb("INSERT INTO `txn`(`txnid`, `atmid`, `timestamp`, `status`,`pintemplate`) VALUES ('"+txn+"','"+atmid+"','"+timestamp+"',"+0+",'"+pintemplate+"')");
    }
    public boolean androidlogin(String email,String password){
        Boolean res=false;
       try{
        ResultSet rs=con.fromdb("select count(*) from user where email='"+email+"' and password='"+password+"'");
        if(rs.next()){
                int a=Integer.parseInt(rs.getString(1));
                if(a==1){res=true;}
                else res=false;
        }
       }catch(Exception e){
           System.out.println(" some error happend while verifying password of android user");
       }
        return res;
    }
    public  ResultSet readallusernames(String name){
        return con.fromdb("Select name from account ");
    }
    public  ResultSet readallaccount(){
        return con.fromdb("Select account.accid,name,accno,email,password,cardno,expiry,pin,cvv from account,user where account.accid=user.accid");
    }
    public int insertCardholderDetails(String name,String accno,String email,String password,String cardno,String expiry,long pin,long cvv){
       int f=0,accid=-1;
       try{
       f+= con.todb("INSERT INTO `account`( `name`, `accno`,  `cardno`, `expiry`, `pin`, `cvv`) VALUES ('"+name+"',"+accno+",'"+cardno+"','"+expiry+"',"+pin+","+cvv+")");
        ResultSet acc=con.fromdb("SELECT `accid` FROM `account` WHERE `name`='"+name+"' and `accno`='"+accno+"'");
        if(acc.next())
                    accid=acc.getInt(1);
        System.out.println(accid);
        f+=con.todb("INSERT INTO `user`(`email`, `password`, `account`, `accid`) VALUES ('"+email+"','"+password+"','"+accno+"','"+accid+"'");
       }catch(SQLException sqe){
           System.out.println(sqe+" in insert cardholder details ");
       }
        return f;
    }
    public int insertCardholderDetails(String name,String accno,String ccordb,String cardno,String expiry,long pin,long cvv){
        return con.todb("INSERT INTO `account`( `name`, `accno`, `cord`, `cardno`, `expiry`, `pin`, `cvv`) VALUES ('"+name+"',"+accno+",'"+ccordb+"','"+cardno+"','"+expiry+"',"+pin+","+cvv+")");
    }
     public int insertATMDetails(String atmid,String loc){
        return con.todb("INSERT INTO `atm`( `atmid`, `location`,`cash`,`validity`) VALUES ('"+atmid+"','"+loc+"',0,30)");
    }
      public int qrEntry(String reqid,String atmid,String t,String txnid){
        return con.todb("INSERT INTO `qrid`( `reqid`, `atmid`, `timestamp`,`txnid`) VALUES ('"+reqid+"','"+atmid+"','"+t+"','"+txnid+"')");
    }
    public int ATMverify(String atmid){
         int i=0;
         try   {
             System.out.println(atmid+".   in db sent by atm");
            ResultSet rs=con.fromdb("select count(*) from atm where atmid='"+atmid+"'");
           if(rs.next())
                i=Integer.parseInt(rs.getString(1));
          System.out.println(i+"    in db");
        } catch (SQLException ex) {
            Logger.getLogger(SepiaDBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
 public static void main(String args[]){
        SepiaDBQuery q1= new SepiaDBQuery();
       ResultSet r=null;
       try{
           Date d= new java.util.Date();
           Timestamp t=new Timestamp(d.getTime());
          // int i=q1.qrEntry("12471832","ATM00000001",t.toString());
       // r=q1.readallaccount();
       /*while(r.next()){
            System.out.println("   "+r.getString(2)+"  "+r.getString("cardno"));
        }*/
     //  System.out.println(i);
        }catch(Exception e){
        Logger.getLogger(SepiaDBCon.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
