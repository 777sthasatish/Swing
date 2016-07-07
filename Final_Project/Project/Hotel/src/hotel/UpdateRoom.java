/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UpdateRoom.java
 *
 * 
 */
package hotel;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author shreee
 */
public class UpdateRoom extends javax.swing.JDialog implements ActionListener {

    /** Creates new form UpdateRoom */
    String oldCat, room_id;
    int floor;

    public UpdateRoom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadCategories();
        btnReset.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnExit.addActionListener(this);
        setTitle("Update Room");
        setLocationRelativeTo(null);
    }

    public void setValues(String roomid, int floor, String cat) {
        oldCat = cat;
        this.floor = floor;
        room_id = roomid;
        txtFloor.setText(floor + "");
        cmbCategory.setSelectedItem(cat);
    }

    private void loadCategories() {
        try {
            Statement stat = DbConnection.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("select * from room_category");
            while (rs.next()) {
                cmbCategory.addItem(rs.getString("category"));
            }
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFloor = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel2.setText("Category");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel5.setText("Floor");

        txtFloor.setColumns(10);
        txtFloor.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnUpdate.setText("Update");

        btnReset.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnReset.setText("Reset");

        cmbCategory.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnExit.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnExit.setText("Exit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCategory, 0, 139, Short.MAX_VALUE)
                            .addComponent(txtFloor, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))))
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset)
                    .addComponent(btnExit))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtFloor;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReset) {
            txtFloor.setText(floor + "");
            cmbCategory.setSelectedItem(oldCat);
        } else if (e.getSource() == btnUpdate) {
            if (oldCat.equals((String)cmbCategory.getSelectedItem()) && txtFloor.getText().equals(floor+""))
            {
                JOptionPane.showMessageDialog(null, "Nothing to update");
                return;
            }
                if (txtFloor.getText().trim().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please Enter Floor number first");
                txtFloor.requestFocus();
                return;
            }
            if (!txtFloor.getText().matches("[0-9]+")) {
                JOptionPane.showMessageDialog(null, "Invalid format (number only)");
                txtFloor.requestFocus();
                return;
            }
            try {
                int flr = Integer.parseInt(txtFloor.getText());
                if (flr < 0 || flr >=10) {
                    JOptionPane.showMessageDialog(null, "Invalid floor number");
                    return;
                } else if (flr > 5 && flr <10) {
                    int ans = JOptionPane.showConfirmDialog(null, "Are you sure " + txtFloor.getText() + " is valid floor number?","Room",JOptionPane.OK_CANCEL_OPTION);
                    if (ans == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                    PreparedStatement stat = DbConnection.getConnection().prepareStatement("update room set floor=?,cat_id=? where id=?");
                    stat.setInt(1, flr);
                    Room r = new Room();
                    int cat_id = r.getCategoryID((String)cmbCategory.getSelectedItem());
                    stat.setInt(2, cat_id);
                    stat.setString(3, room_id);
                    int res = stat.executeUpdate();
                    if (res >=0)
                    {
                        JOptionPane.showMessageDialog(null, "Room updated successfully!!!");
                        MyLogger.writeLog("update room","ID : "+room_id+ " category : "+(String)cmbCategory.getSelectedItem()+ " floor :"+txtFloor.getText());
                        this.dispose();
                    }
            }catch (Exception ee){}
        }
        else if (e.getSource() == btnExit)
        {
            this.dispose();
        }
    }
}