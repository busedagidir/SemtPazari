import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped

public class satis {
    public String isim,soyad,adres;
    private String servername = "localhost";
    private String server_username = "root";
    private String server_password = "";
    public String fiyat;
    public String ad;

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
    
    public String getIsim() {
        return isim;
    }
    public void setIsim(String isim) {
        this.isim = isim;
    }
    public String getSoyad() {
        return soyad;
    }
    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
    public String getAdres() {
        return adres;
    }
    public void setAdres(String adres) {
        this.adres = adres;
    }
    
    public String sat(String email)throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        String satilanTemp ; int adetI = 0, i = 0;
        Class.forName(className);
        ResultSet satilan = null;
        ResultSet dataset = null;
        if(email == ""){email="misafir";}
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            PreparedStatement ps = con.prepareStatement("insert into satis values (?,?,?)");
            ps.setString(1, isim);
            ps.setString(2, soyad);
            ps.setString(3, adres);
            ps.executeUpdate();
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement(),  n = con.createStatement();
            satilan = s.executeQuery("SELECT * FROM sepet WHERE email='"+email+"'");
            while(satilan.next()){
                satilanTemp = satilan.getString("urunResmi");
                dataset = n.executeQuery("SELECT * FROM sales WHERE satilan='"+satilanTemp+"'");
                if (dataset.next()){
                    do{
                        adetI = Integer.parseInt(dataset.getString("adet"));
                        adetI++;
                        PreparedStatement ps = con.prepareStatement("update sales set adet = '"+adetI+"'where satilan='"+satilanTemp+"'");
                        ps.executeUpdate();
                        adetI = 0;
                    }while(dataset.next());
                }
                else{
                    PreparedStatement ps = con.prepareStatement("insert into sales values (?,?)");
                    ps.setString(1, satilanTemp);
                    ps.setString(2, Integer.toString(1));
                    ps.executeUpdate();
                }
            }
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            PreparedStatement ps = con.prepareStatement("delete from sepet where email = ?");
            ps.setString(1, email);
            ps.executeUpdate();
        }
        
        return "index.xhtml";
    }
    
        public void satilanSayisi(String hedef) throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        int count = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM indirim WHERE altKategori IN('"+hedef+"')");
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                ad = dataset.getString("isim");
                fiyat = dataset.getString("fiyat");
            }
        }
    }
}


