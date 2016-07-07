/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shreee
 */
public class Item {
    Connection conn;
    public Item()
    {
        conn = DbConnection.getConnection();
    }
    public int getCategoryID(String cat)
    {
        try {
            PreparedStatement stat = conn.prepareStatement("select cat_id from item_category where category = ?");
            stat.setString(1, cat);
            ResultSet rs = stat.executeQuery();
            rs.next();
            int id = rs.getInt("cat_id");
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getItemCode(String cat,String name) {
         try {
            PreparedStatement stat = conn.prepareStatement("select item_code from item,item_category where item_category.category=? and item.name=? and item_category.cat_id=item.cat_id");
            stat.setString(1, cat);
            stat.setString(2, name);
            
            ResultSet rs = stat.executeQuery();
            rs.next();
            int code = rs.getInt("item_code");
            return code;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public String getCategory(int catid)
    {
         try {
            PreparedStatement stat = conn.prepareStatement("select category from item_category where cat_id=?");
            stat.setInt(1, catid);
            ResultSet rs = stat.executeQuery();
            rs.next();
            String cat = rs.getString("category");
            return cat;
        } catch (SQLException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
