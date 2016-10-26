/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sepia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Nikku
 */
public class Qr {
    public static void qrgenerate(String str)
    {
        
    
    
        ByteArrayOutputStream out = QRCode.from(str)
                                        .to(ImageType.PNG).stream();
         FileOutputStream fout;
 
        try {
           fout = new FileOutputStream(new File("QR.png"));
 
            fout.write(out.toByteArray());
 
            fout.flush();
            fout.close();
 
        } catch (FileNotFoundException e) {
            // Do Logging
        	e.printStackTrace();
        } catch (IOException e) {
            // Do Logging
        	e.printStackTrace();
        }
        
    }
}
