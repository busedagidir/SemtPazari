import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped
public class urunler {
    public String kategori;
    public String isim;
    public String fiyat;
    public String resimYolu;
    public String userisim;
    public String useremail;
    public String[] bilgi;
    private String servername = "localhost";
    private String server_username = "root";
    private String server_password = "";
    
    public String getKategori(){
        return kategori;
    }
    public void setKategori(String kategori){
        this.kategori = kategori;
    }
    
    public String getIsim(){
        return isim;
    }
    public void setIsim(String isim){
        this.isim = isim;
    }
    
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
    
    public String[] getBilgi(){
        return bilgi;
    }
    public void setBilgi(String bilgi){
        this.bilgi = bilgi.split("-");
    }
    
    public String urunSayfa(String resim)throws SQLException, ClassNotFoundException{
        if(resim == "./resources/images/alert.jpg")
            return "index.xhtml";
        urunYerlestir(resim);
        return "urun.xhtml";
    }
    
    public void urunYerlestir(String resim) throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM urunler WHERE resim='"+resim+"'");
            while(dataset.next()){
                setKategori(dataset.getString("kategori"));
                setIsim(dataset.getString("isim"));
                setFiyat(dataset.getString("fiyat"));
                setResimYolu(dataset.getString("resim"));
                setBilgi(dataset.getString("bilgi"));
            }
        }
    }
    
    public void userAra(String arama) throws SQLException, ClassNotFoundException{
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        int count = 0;
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM users WHERE altKategori IN('"+arama+"')");
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                userisim = dataset.getString("isim");
                useremail = dataset.getString("email");
            }
        }
    }
}
