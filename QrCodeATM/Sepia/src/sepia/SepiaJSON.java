/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.simple.JSONObject;

/**
 *
 * @author Nikku
 */
public class SepiaJSON {
    public static String QRreqJSON(String reqid,String atmid,String timestamp){
        JSONObject obj = new JSONObject();
       obj.put("reqid",reqid);
        obj.put("atmid", atmid);
       obj.put("timestamp", timestamp);  
       
        return obj.toJSONString();
    }
    public static String SHA512id(String passwordToHash, String   salt)
{  
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
}
