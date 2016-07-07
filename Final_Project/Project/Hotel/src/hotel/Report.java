/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author shreee
 */
class Report {

    int i = 1;

    public Report() {
    }

    public void displayCheckoutReport(int res_id) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/checkout.jrxml");
            HashMap hm = new HashMap();
            hm.put("res_id", res_id);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint,"checkout report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayDetailReport(int res_id, int days) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/details.jrxml");
            HashMap hm = new HashMap();
            hm.put("res_id", res_id);
            hm.put("num_days", days);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint,"detail report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateReservationSummaryReport()
    {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/reservations.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), DbConnection.getConnection());
            storeFile(jasperPrint,"reservation summary");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void generateDailyReportForRooms(String date,int roomcost) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/hotel_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Report for " + date);
            hm.put("totalroomcost", roomcost);
            String sql = "SELECT count(rooms) AS nums, sum(datediff(check_out,check_in)) AS days, room.id, room_category.category,"
                    + " room_category.price, reservation_history.rooms, reservation_history.check_in, reservation_history.check_out,"
                    + " reservation_history.reservation_id FROM room_category INNER JOIN room ON room_category.cat_id = room.cat_id, reservation_history "
                    + " where INSTR(rooms,room.id)>0 and DATE(check_in) = '" + date + "' GROUP BY room.id ORDER BY nums DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "daily report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateMonthlyReportForRooms(int month, String val,int roomcost) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/hotel_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Monthly Report for " + val);
            hm.put("totalroomcost", roomcost);
            String sql = "SELECT count(rooms) AS nums, sum(datediff(check_out,check_in)) AS days, room.id, room_category.category,"
                    + " room_category.price, reservation_history.rooms, reservation_history.check_in, reservation_history.check_out,"
                    + " reservation_history.reservation_id FROM room_category INNER JOIN room ON room_category.cat_id = room.cat_id, reservation_history "
                    + " where INSTR(rooms,room.id)>0 and MONTH(check_in)=" + month + " GROUP BY room.id ORDER BY nums DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "monthly report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateYearlyReportForRooms(int year,int roomcost) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/hotel_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Yearly Report for " + year);
            hm.put("totalroomcost", roomcost);
            String sql = "SELECT count(rooms) AS nums, sum(datediff(check_out,check_in)) AS days, room.id, room_category.category,"
                    + " room_category.price, reservation_history.rooms, reservation_history.check_in, reservation_history.check_out,"
                    + " reservation_history.reservation_id FROM room_category INNER JOIN room ON room_category.cat_id = room.cat_id, reservation_history "
                    + " where INSTR(rooms,room.id)>0 and YEAR(check_in)=" + year + " GROUP BY room.id ORDER BY nums DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "yearly report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //report for foods and beverages
    public void generateDailyFoodsReport(String date) {

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/food_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Report for " + date);
            String sql = "SELECT order_history.item_code,order_history.quantity, item.name, item.price, item_category.category, reservation_history.check_in, sum(order_history.quantity) as total_quantity"
                    + " FROM reservation_history INNER JOIN order_history ON reservation_history.reservation_id = order_history.reservation_id"
                    + " INNER JOIN item ON order_history.item_code = item.item_code INNER JOIN item_category ON item.cat_id = item_category.cat_id"
                    + " where DATE(check_in) = '" + date + "' group by item.item_code ORDER BY total_quantity DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "daily food report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateMonthlyFoodsReport(int month, String val) {

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/food_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Monthly Report for " + val);
            String sql = "SELECT order_history.item_code,order_history.quantity, item.name, item.price, item_category.category, reservation_history.check_in, sum(order_history.quantity) as total_quantity"
                    + " FROM reservation_history INNER JOIN order_history ON reservation_history.reservation_id = order_history.reservation_id"
                    + " INNER JOIN item ON order_history.item_code = item.item_code INNER JOIN item_category ON item.cat_id = item_category.cat_id"
                    + " where MONTH(check_in) = '" + month + "' group by item.item_code ORDER BY total_quantity DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "monthly food report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateYearlyFoodsReport(int year) {

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("report_designs/food_report.jrxml");
            HashMap hm = new HashMap();
            hm.put("reportID", "Yearly Report for " + year);
            String sql = "SELECT order_history.item_code,order_history.quantity, item.name, item.price, item_category.category, reservation_history.check_in, sum(order_history.quantity) as total_quantity"
                    + " FROM reservation_history INNER JOIN order_history ON reservation_history.reservation_id = order_history.reservation_id"
                    + " INNER JOIN item ON order_history.item_code = item.item_code INNER JOIN item_category ON item.cat_id = item_category.cat_id"
                    + " where YEAR(check_in) = '" + year + "' group by item.item_code ORDER BY total_quantity DESC";
            hm.put("sql_input", sql);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DbConnection.getConnection());
            storeFile(jasperPrint, "yearly food report");
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void storeFile(JasperPrint print, String reportType) throws JRException, IOException {
        String filename = getFileName(reportType);
        JasperExportManager.exportReportToPdfFile(print, filename); 
        Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN) || desktop.isSupported(Desktop.Action.EDIT)
                    || desktop.isSupported(Desktop.Action.PRINT)) {
                desktop.open(new File(filename));
            }
    }

    private String getFileName(String reportType) {
        File dir = new File("reports");
        dir.mkdir();
        String fn = "";
        while (true) {
            fn = "reports/"+reportType + i + ".pdf";
            File f = new File(fn);
            if (f.exists()) {
                i++;
            } else {
                return fn;
            }
        }
    }
}
