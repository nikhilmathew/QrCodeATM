/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiaserver;

import java.io.Serializable;

/**
 *
 * @author Nikku
 */
public class Cardholder implements Serializable{
    int accid,pin,cvv;
    String name, accno,cord, cardno,expiry;
    public Cardholder(int accid, String name,String accno,String cord,String cardno, String expiry, int pin,int cvv){
            this.accid=accid;
            this.name=name;
            this.accno=accno;
            this.cord=cord;
            this.cardno=cardno;
            this.expiry=expiry;
            this.pin=pin;
            this.cvv=cvv;
            
    }
}


