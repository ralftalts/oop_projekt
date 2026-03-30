public class Mängija {

    private String nimi;
    private int punktid;

    public Mängija(String nimi) {
        this.nimi = nimi;
        this.punktid = 0;
    }
    public void lisaPunkt() {  // lisab mängijale punkti
        this.punktid++;
    }
    public int getPunktid() {  // kui pärast vaja punkte näha
        return punktid;
    }
    public String getNimi() {
        return nimi;
    }
}
