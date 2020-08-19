import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped
public class user {
    /*----- Database connection için variable -----*/
    private String servername = "localhost";
    private String server_username = "root";
    private String dbname = "websitetest";
    private int portnumber = 3306;
    private String server_password = "";
    /*---------------------------------------------*/
    private int id;
    private String name;
    private String password;
    private String email;
    public int sepetim;
    public String adet;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {  
        return name;  
    }  
    public void setName(String name){
        this.name = name;
    }

    public String getAdet() {
        return adet;
    }

    public void setAdet(String adet) {
        this.adet = adet;
    }
    
    public int getSepetim() throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        ResultSet dataset = null;
        setSepetim(0);
        if(email==null){
            setEmail("misafir");
        }
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM sepet WHERE email='"+this.email+"'");
            while(dataset.next()){
                this.sepetim++;
            }
        }
        return sepetim; 
    } 
    public void setSepetim(int spt){
        this.sepetim = spt;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword() {  
        return password;  
    }
    
    public String signUp() throws ClassNotFoundException {
        try {
            setDatabase();
        } catch (SQLException ex) {//Exception for sql connection
            Logger.getLogger(user.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.xhtml";
    }
    
    public void setDatabase() throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            PreparedStatement ps = con.prepareStatement("insert into users values (?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
        }
    }
    
    public String Uyegirisi()throws SQLException, ClassNotFoundException{
        boolean kontrol;
        kontrol = karsilastir();
        if(!kontrol)return "login.xhtml";
        return "index.xhtml";
    }
    
    public boolean karsilastir()throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        String pass = new String();
        String mail = new String();
        String ad = new String();
        Class.forName(className);
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM users WHERE email='"+email+"'");
            while(dataset.next()){
                ad = dataset.getString("Username");
                pass = dataset.getString("password");
                mail = dataset.getString("email");
            }
        }
        if(!pass.equals(password) || !mail.equals(email)){
            return false;
        }
        name =ad;
        return true;
    }
    
    public void sepetArti(String resimyolu,String isim,String[] bilgi,String fiyat)throws SQLException, ClassNotFoundException{
        
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        String s = bilgi[0]+" "+bilgi[1]+" "+bilgi[2]+" "+bilgi[3]+" "+bilgi[4]+" "+bilgi[5]+" "+bilgi[6]+" "+bilgi[7]+" "+bilgi[8]+" "+bilgi[9];
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            PreparedStatement ps = con.prepareStatement("insert into sepet values (?,?,?,?,?)");
            ps.setString(1, email);
            ps.setString(2, isim);
            ps.setString(3, resimyolu);
            ps.setString(4, s);
            ps.setString(5, fiyat);
            ps.executeUpdate();
        }
        this.sepetim++;
    }
        public void usersirala(String user)throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        int count = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM alicilar WHERE altKategori IN('"+user+"')");
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                name = dataset.getString("isim");
                adet = dataset.getString("toplam");
            }
        }
    }
}  