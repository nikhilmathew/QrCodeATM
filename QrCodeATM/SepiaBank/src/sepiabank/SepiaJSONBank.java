/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiabank;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sepiadbcon.SepiaDBQuery;

/**
 *
 * @author Nikku
 */
public class SepiaJSONBank {
    
    public static String addJSON(String name,String accno,String email,String password,String  cardno,String expiry, int pin,int cvv){
        JSONObject obj = new JSONObject();
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
    public static String addATMJSON(String atmid,String loc){
        JSONObject obj = new JSONObject();
       obj.put("atmid", atmid);
       obj.put("location", loc);  
       System.out.println("json created");
        return obj.toJSONString();
    }
    public static ResultSet returnholderdata(String jsonstr){
            JSONParser parser = new JSONParser();
            ResultSet rs=null;
        //SepiaDBQuery con=new SepiaDBQuery();
	try {
                                           
                                            
		Object obj = parser.parse(jsonstr);

		JSONObject jsonObject = (JSONObject) obj;
                                            rs= (ResultSet)jsonObject.get("rs");
                
    }catch(Exception e){
        System.out.println("returnholderdata "+e+"\n "+rs);
    }
        return rs;
}
    public static String returnjsonelement(String name,String jsonstr){
        
            JSONParser parser = new JSONParser();
            //SepiaDBQuery con=new SepiaDBQuery();//this was causing lots of exceptions
            String ret=null;
            try {
            Object obj = parser.parse(jsonstr);
            JSONObject jsonObject = (JSONObject) obj;
            //System.out.println(jsonObject);
            ret = (String) jsonObject.get(name);
                                                   
            
        } catch (ParseException ex) {
            Logger.getLogger(SepiaJSONBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
