public class Mangija {

    private String nimi; // Mängija nimi
    private int punktid; // Mängija punktid

    public Mangija(String nimi) {
        this.nimi = nimi;
        this.punktid = 0; // Punktide algväärtus on 0, millele õigete vastuste korral hakatakse punkte lisama
    }
    public void lisaPunkt() {  // Lisab mängijale punkti õige vastuse korral
        this.punktid++;
    }
    public int getPunktid() {  // Kui hiljem vaja punktiseisu kuvada
        return punktid;
    }
    public String getNimi() {
        return nimi;
    }
}
