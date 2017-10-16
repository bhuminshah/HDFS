import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class MaxTemp9293 {

        Connection myConnection;

        public static void main(String args[]) {
                int count = 0;
                int max1 = 0;
                int max2 = 0;

                String year = null;
                String year_sub = null;

                try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/temp?autoReconnect=true&useSSL=false", "root", "itmd521");
                        // here sonoo is database name, root is username and password
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from 9293_sample_txt");
                        while (rs.next()) {
                                count++;
                                String Max = rs.getString(27);
                                year = rs.getString(4);
                                if (Max.substring(0, 1).equals("+")) {
                                        Max = Max.substring(1);
                                        year_sub = year.substring(0, 4);
                                        int temp = Integer.parseInt(Max);
                                        if (year_sub.equals("1992")) {
                                                if (temp > max1 && temp != 9999)
                                                        max1 = temp;}
                                        if (year_sub.equals("1993")) {
                                                if (temp > max2 && temp != 9999)
                                                        max2 = temp;}
                                }
                        }
                        con.close();

                } catch (

                Exception e) {
                        System.out.println(e);
                }
                // System.out.println(count);
                System.out.println("Maximum temperature in 1992 " + ": " + max1);
                System.out.println("Maximum temperature in 1993 " + ": " + max2);

        }

}

