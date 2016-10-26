/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepiaserver;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Nikku
 */
public class PinTemp {
    public static String generatePinTemplate(){
          int j = 0;
        Integer[] arr = new Integer[8];
         Object[] marr = new Object[8];
        Integer[] pos = new Integer[4];
         Integer[] arr1 = new Integer[4];
         String finals=null;
         char [] charr= new char[8];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=i;
            
        }
        Collections.shuffle(Arrays.asList(arr));
      //  System.out.println(Arrays.toString(arr));
        for(int i=0;i<4;i++)
        {
            arr1[i]=arr[i];
            charr[arr1[i]]=Integer.toString(arr1[i]).charAt(0);
            
            
        }
        //System.out.println(Arrays.toString(arr1));
        for(int i=4;i<8;i++)
        {
            pos[j]=arr[i];
            charr[pos[j]]='*';
            j++;
            
        }
        //System.out.println(Arrays.toString(pos));
       
        
        finals=new String(charr);
        System.out.println(finals);
        return finals;
    }
}
