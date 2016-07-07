/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author shreee
 */
class DbConnection {
    static Connection conn = null;
    
    public static Connection getConnection()
    {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbheavenhill", "root", "root");
                return conn;
            } catch (SQLException e) {
                //JOptionPane.showMessageDialog(null, "Can't Connect to Database");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Could not load Database");
            }
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbheavenhill", "root", "");
                return conn;
            } catch (SQLException e) {
                //JOptionPane.showMessageDialog(null, "Can't Connect to Database");
            } catch (ClassNotFoundException e) {
                //JOptionPane.showMessageDialog(null, "Could not load Database");
            }
        }
        return conn;
    }
}
