
package projekpbo.helper;

import java.sql.*;
import java.util.*;
import projekpbo.models.KataModel;

public class DBhelper {
    
   private static final String dbUrl = "jdbc:mysql://localhost:3306/tebak_kata";
   private static final String user = "root";
   private static final String pass = "";

   public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, pass);
   } 
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String query;
    
    public DBhelper(){
        try{
            conn = DriverManager.getConnection(dbUrl,user,pass);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //read
    public List<KataModel> getAllData(){
        List<KataModel> data = new ArrayList<>();
        query = "SELECT * FROM daftar_kata";
        try {
            stmt = conn. createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                KataModel kata = new KataModel();
                kata.setId(rs.getInt("id"));
                kata.setKata(rs.getString("kata"));
                data.add(kata);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    //write
    public boolean addNewKata(String kata){
        boolean value = false;
        query = "INSERT INTO daftar_kata (kata) VALUES ('" + kata + "')";
        
        try {
            stmt= conn.createStatement();
            if(stmt.executeUpdate(query)>0){
                value= true;
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return value;
    }
    
    //delete
    public boolean removeKata(int id){
        boolean value = false;
        query = "DELETE FROM daftar_kata WHERE id = '" + id + "'";
        
        try {
            stmt= conn.createStatement();
            if(stmt.executeUpdate(query)>0){
                value= true;
            }
            stmt.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return value;
    }
    
    
    public boolean updateKata(int id,String kata){
        boolean value = false;
        query = "UPDATE daftar_kata SET kata = '" + kata + "' WHERE id = '" + id + "'";
        
        try {
            stmt= conn.createStatement();
            if(stmt.executeUpdate(query)>0){
                value= true;
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return value;
    }
}
