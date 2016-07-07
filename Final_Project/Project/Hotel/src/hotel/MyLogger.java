/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shreee
 */
class MyLogger {

    public static void writeLog(String title, String line) {
        try {
            File f = new File("logs");
            f.mkdir();
            f = new File("logs/logs.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedWriter br = new BufferedWriter(new FileWriter(f,true));
            br.write(new Date().toString()+"  ");
            br.write(title+"  ");
            br.write(line);
            br.newLine();
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
