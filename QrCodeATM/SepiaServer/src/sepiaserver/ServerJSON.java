/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiaserver;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sepiadbcon.*;

/**
 *
 * @author Nikku
 */
public class ServerJSON {
    public static String buildTransactionID(String requestid , String atmid, String timestamp){
        String TransactionID="default value";
        String a=null;
         System.out.println("in transaction builder");
        TransactionID=SHA512id(requestid+atmid+timestamp,"SebaMeenuRose");
       
        return TransactionID;
    }
    public static String SHA512id(String passwordToHash, String   salt)
{   //4a00f9d1a8902ac76e6d0da8b5650e19f513f78be1a16d86af85f3f84459fca376145c0724e1a045d2d1dc6ae1698600b601f87ce791e75a80d5d9a9a42ba1b7
    //9fd7222042df5b2925a48684ffaae6aafe24ab947cc72063b75b36c1ed8cd2c37ccc84428e66086c3c4825b41af1c2c44d7cbdae0c03a6f877076245540e74e9
    String generatedPassword = null;
    try {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes("UTF-8"));
        byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
    } 
    catch (NoSuchAlgorithmException e) 
    {
        e.printStackTrace();
    }   catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    return generatedPassword;
}
    public static void addJSON(String jsonstr){
        JSONParser parser = new JSONParser();
        SepiaDBQuery con=new SepiaDBQuery();
	try {
                                            String name=null,accno=null,ccordb=null,cardno=null,expiry=null,email=null,password=null;
                                            Long pin=null,cvv=null;
		Object obj = parser.parse(jsonstr);

		JSONObject jsonObject = (JSONObject) obj;
                                            System.out.println(jsonObject);
                                            try {
		name = (String) jsonObject.get("name");
                                            }catch(Exception e){
                                                System.out.println("name");
                                            }
                                            try{
                                             accno = (String) jsonObject.get("accno");
                                             }catch(Exception e){
                                                System.out.println("accno");
                                            }
                                            try{
                                             email = (String) jsonObject.get("email");
                                             }catch(Exception e){
                                                System.out.println("email");
                                            }
                                             try{
                                             password = (String) jsonObject.get("password");
                                             }catch(Exception e){
                                                System.out.println("password");
                                            }
                                            try{
                                           cardno = (String) jsonObject.get("cardno");
                                            }catch(Exception e){
                                                System.out.println("cardno");
                                            }
                                            try{
                                            expiry = (String) jsonObject.get("expiry");
                                             }catch(Exception e){
                                                System.out.println("expiry");
                                            }
                                            try{
                                            pin = (Long) jsonObject.get("pin");
                                             }catch(Exception e){
                                                System.out.println("pin");
                                            }
                                            try{
                                            cvv = (Long) jsonObject.get("cvv"); 
                                            }catch(Exception e){
                                                System.out.println("cvv");
                                            }
                                            
                                            int f=con.insertCardholderDetails(name,accno,email,password,cardno,expiry,pin,cvv);
                                            
//more to be written
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void addATMJSON(String jsonstr){
        JSONParser parser = new JSONParser();
        SepiaDBQuery con=new SepiaDBQuery();
	try {
                                            String atmid=null,location=null;
                                            
		Object obj = parser.parse(jsonstr);

		JSONObject jsonObject = (JSONObject) obj;
                                            System.out.println(jsonObject);
                                            try {
		atmid = (String) jsonObject.get("atmid");
                                            }catch(Exception e){
                                                System.out.println("atmid");
                                            }
                                            try{
                                             location = (String) jsonObject.get("location");
                                             }catch(Exception e){
                                                System.out.println("location");
                                            }
                                            
                                            
                                            int f=con.insertATMDetails(atmid,location);
                                            
//more to be written
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static ArrayList returnuserdata(){// useless 
      
            String s;
            ArrayList<Cardholder> Cardholders = new ArrayList<>();
            //   JSONObject obj = new JSONObject();
            SepiaDBQuery con=new SepiaDBQuery();
            ResultSet rs= con.readallaccount();
              try {
                  while(rs.next()){
                Cardholder ch=new Cardholder(Integer.parseInt(rs.getString(0)),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),Integer.parseInt(rs.getString(6)),Integer.parseInt(rs.getString(7)));
                Cardholders.add(ch);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Cardholders;
    }
    
    public static String convertrstoJSON(String accid,String name,String accno,String email,String password,String  cardno,String expiry,String pin,String cvv){
        JSONObject obj = new JSONObject();
       obj.put("accid",accid);
        obj.put("name", name);
       obj.put("accno", accno);  
       obj.put("email", email);
        obj.put("password",password);
       obj.put("cardno", cardno);
       obj.put("expiry",expiry);
       obj.put("pin", pin);
       obj.put("cvv", cvv);
        return obj.toJSONString();
    }
    public static Boolean verifyAndroidUserCredentials(String json){
        JSONParser parser= new JSONParser();
        String email=null,password=null;
        Boolean  b=false;
        SepiaDBQuery con=new SepiaDBQuery();
        try{
            Object obj= parser.parse(json);
            JSONObject jObject=(JSONObject)obj;
            email=(String) jObject.get("email");
            password=(String) jObject.get("pass");
            System.out.println("email:"+email+"---pass:"+password);
            b=con.androidlogin(email, password);
        }catch(Exception e){
            System.out.println(""+e);
        }
        return b;
    }
    static void createQRGenReq(String jsonstr) {
        JSONParser parser = new JSONParser();
        SepiaDBQuery con=new SepiaDBQuery();
        String str=null;
        System.out.println("in create QR json method");
	try {
                                            String reqid=null,atmid=null,time=null;
                                            
		Object obj = parser.parse(jsonstr);
                                    
		JSONObject jsonObject = (JSONObject) obj;
                                            System.out.println(jsonObject);
                                            reqid = (String) jsonObject.get("reqid");
                                            System.out.println(reqid);
                                           
                                            atmid = (String) jsonObject.get("atmid");
                                            
                                            time = (String) jsonObject.get("timestamp");
                                            System.out.println(time);
                                            str= time.replaceAll("[:\\. -]","");
                                            System.out.println(str);
                                           // str=str.replaceAll("-","");
                                            //System.out.println(str);
                                            //str=str.replaceAll(" ","");
                                           String txnid= "T"+reqid+atmid+str;
                                            //we get the req id at id and atm timestamp here   
                                            // calculate a transaction id here// transaction id = atmid + reqid +
                                            // pin template here
                                            
                                            
                                            int f=con.qrEntry(reqid,atmid,time,txnid);// also create a transaction id and enter pin template here and return the transactionn id and pin templae and validity as csv or something to the caller
                                            
//more to be written
        }catch(Exception e){
            System.out.println(e);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
