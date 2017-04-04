import java.sql.*;

public class MysqlConnector {
    private Connection conn = null;
    /**
     * If this doesn't work, do the following (from StackOverflow):
     *
     * right click on
     * project properties --> Java Build Path --> Libraries --> add Jar to your
     * project(which you already downloaded)  This is the MySQL JDBC
     * driver.  This worked for me in NetBeans.
     */

    public MysqlConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/customer?user=root&password=");
        } catch (Exception ex) {
            System.out.println("Error in connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize(){
        closeConnection();
    }

    // using for test mysql connection
    public static void main(String[] args) {
        MysqlConnector m = new MysqlConnector();
        Connection conn = m.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM prefer;");
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        m.closeConnection();
        System.out.println("Success!!");
    }
}
