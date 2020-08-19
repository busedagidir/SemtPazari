import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped
public class kategori {
    public String fiyat;
    public String resimYolu;
    public List<Pair<String,String>> products = new ArrayList<>();
    private String servername = "localhost";
    private String server_username = "root";
    private String server_password = "";
    
    public String getFiyat(){
        return fiyat;
    }
    public void setFiyat(String fiyat){
        this.fiyat = fiyat;
    }
    
    public String getResimYolu(){
        return resimYolu;
    }
    public void setResimYolu(String resimYolu){
        this.resimYolu = resimYolu;
    }
    
    public List<Pair<String,String>> getProducts(){
        return products;
    }
    public void setProducts(String s1,String s2){
        Pair p = new Pair(s1,s2);
        this.products.add(p);
    }
    
    public String kategoriSayfa(String altkat)throws SQLException, ClassNotFoundException{
        kategoriSirala(altkat);
        return "urunler.xhtml";
    }
    
    public void kategoriSirala(String altkat) throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM urunler WHERE altKategori='"+altkat+"'");
            String s1 = new String();String s2 = new String();
            products.clear();
            while(dataset.next()){
                s1 = dataset.getString("fiyat");
                s2 = dataset.getString("resim");
                setProducts(s1,s2);
            }
        }
    }
    public void kategoriyeGoreSayiBul(String kategori)throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        int count = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM alicilar WHERE altKategori IN('"+kategori+"')");
            String s1 = new String();String s2 = new String();
            products.clear();
            while(dataset.next()){
                s1 = dataset.getString("fiyat");
                s2 = dataset.getString("resim");
                setProducts(s1,s2);
            }
        }
    }
}
