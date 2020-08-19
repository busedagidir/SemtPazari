import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean    // Using ManagedBean annotation  
@SessionScoped
public class arama {
    private String servername = "localhost";
    private String server_username = "root";
    private String server_password = "";
    public String fiyat;
    public String resimYolu;
    public String hedef;
    public String[] bilgiKarsilastirma;
    public List<Pair<String,String>> products = new ArrayList<>();

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getResimYolu() {
        return resimYolu;
    }

    public void setResimYolu(String resimYolu) {
        this.resimYolu = resimYolu;
    }

    public String[] getBilgiKarsilastirma() {
        return bilgiKarsilastirma;
    }

    public void setBilgiKarsilastirma(String bilgiKarsilastirma) {
        this.bilgiKarsilastirma = bilgiKarsilastirma.split("-");
    }

    public List<Pair<String, String>> getProducts() {
        return products;
    }

    public void setProducts(String s1,String s2) {
        Pair p = new Pair(s1,s2);
        this.products.add(p);
    }

    public String getHedef() {
        return hedef;
    }

    public void setHedef(String hedef) {
        this.hedef = hedef;
    }
    
    public double oranBul(double sayi1,double sayi2){
        return (sayi1/sayi2);
    }
    
    public String kisaOlan(String s1,String s2){
        return (s1.length()>s2.length())?s2:s1;
    }
    
    public boolean oranKarsilastir(String s1){
        int count =0;
        String s = kisaOlan(s1,hedef);
        for(int i = 0;i<s.length();i++){
            if(hedef.charAt(i) == s1.charAt(i)){
                count++;
            }
        }
        if(oranBul(count,hedef.length())>0.6){
            return true;
        }
        return false;
    }
    
    public String search() throws SQLException, ClassNotFoundException{
        products.clear();
        int check = 0;
        String className = "com.mysql.jdbc.Driver";
        Class.forName(className);
        ResultSet dataset = null;
        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/website-database?zeroDateTimeBehavior=convertToNull", server_username, server_password)) {
            Statement s = con.createStatement();
            dataset = s.executeQuery("SELECT * FROM urunler");
            String s1 = new String();String s2 = new String();
            while(dataset.next()){
                setBilgiKarsilastirma(dataset.getString("bilgi"));
                if(oranKarsilastir(dataset.getString("isim"))){
                    s1 = dataset.getString("fiyat");
                    s2 = dataset.getString("resim");
                    setProducts(s1,s2);
                    check++;
                }
                else if(oranKarsilastir(dataset.getString("kategori"))){
                    s1 = dataset.getString("fiyat");
                    s2 = dataset.getString("resim");
                    setProducts(s1,s2);
                    check++;
                }
                else if(oranKarsilastir(this.bilgiKarsilastirma[1])){
                    s1 = dataset.getString("fiyat");
                    s2 = dataset.getString("resim");
                    setProducts(s1,s2);
                    check++;
                }
            }
            if(check == 0){
                s1 = "Ürün bulunamadı";
                s2 = "./resources/images/alert.jpg";
                setProducts(s1,s2);
            }
        }
        return "search.xhtml";
    }
}