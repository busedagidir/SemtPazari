import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped

public class sepet {
    public List<Pair<String,String>> sepettekiler = new ArrayList<>();
    public int fiyat;
    private String servername = "localhost";
    private String server_username = "root";
    private String server_password = "";
    public List<Pair<String,String>> compare;
    public String ad;
    public String email;
    
    public List<Pair<String,String>> getCompare(){
        return compare;
    }
    public void setCompare(String s1,String s2){
        Pair p = new Pair(s1,s2);
        this.compare.add(p);
    }
    
    public int getFiyat(){
        return fiyat;
    }
    public void setFiyat(int fiyat){
        this.fiyat = fiyat;
    }
    
    public List<Pair<String,String>> getSepettekiler(){
        return sepettekiler;
    }
    
    public void setSepettekiler(String s1,String s2){
        Pair p1 = new Pair(s1,s2);
        this.sepettekiler.add(p1);
    }
    
    public String sepetGit(String email)throws SQLException, ClassNotFoundException{
        createTable(email);
        return "sepetim.xhtml";
    }
    
    public void createTable(String email)throws SQLException, ClassNotFoundException{
        
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        String sonuc = new String();
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM sepet WHERE email='"+email+"'");
            sepettekiler.clear();
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                s1 = dataset.getString("fiyat");
                s2 = dataset.getString("urunResmi");
                setSepettekiler(s2,s1);
            }
        }
    }
    
    public int toplamFiyat(String email)throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        String[] sonuc;
        int sum = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM sepet WHERE email='"+email+"'");
            this.fiyat=0;
            while(dataset.next()){
                sonuc = dataset.getString("fiyat").split(" ");
                sum += Integer.parseInt(sonuc[0]);
                sonuc = null;
            }
        }
        this.fiyat=sum;
        return sum;
    }
    
    public void kaldir(String resim,String email)throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        if(email == ""){email="misafir";}
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            PreparedStatement ps = con.prepareStatement("delete from sepet where urunResmi = ? and email = ?");
            ps.setString(1, resim);
            ps.setString(2, email);
            ps.executeUpdate();
        }
        sepetGit(email);
    }
    
    public void musteriFiyat(String musteri) throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        int count = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM sepet WHERE altKategori IN('"+musteri+"')");
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                ad = dataset.getString("urunAdi");
                email = dataset.getString("email");
            }
        }
    }
}