/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.ImageIcon;
import javax.swing.*;

/**
 *
 * @author Nikku
 */
public class dummmm extends JFrame{
   
   public  dummmm() throws IOException{
       Container c=getContentPane();
       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       System.out.println("enter name of image");
       String s=br.readLine();
     ImageIcon i= new ImageIcon("C:\\Users\\Nikku\\Pictures\\My Start Wallpapers\\"+s+".png");
       JLabel jl=new JLabel(i);
       c.add(jl);
       setVisible(true);
       setSize(700,600);
       s=br.readLine();
       i= new ImageIcon("C:\\Users\\Nikku\\Pictures\\My Start Wallpapers\\"+s+".png");
       remove(jl);
       jl=new JLabel(i);
      add(jl);
      revalidate();
       repaint();
        s=br.readLine();
       i= new ImageIcon("C:\\Users\\Nikku\\Pictures\\My Start Wallpapers\\"+s+".png");
       remove(jl);
        jl=new JLabel(i);
      add(jl);
      revalidate();
       repaint();
        s=br.readLine();
       i= new ImageIcon("C:\\Users\\Nikku\\Pictures\\My Start Wallpapers\\"+s+".png");
       remove(jl);
       jl=new JLabel(i);
      add(jl);
      revalidate();
       repaint();
       
}
   public static void main(String args[]) throws IOException{
       dummmm d= new dummmm();
   } 
   
}
