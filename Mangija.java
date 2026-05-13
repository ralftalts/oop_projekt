public class Mangija {

    private String nimi; // Mangija nimi
    private int punktid; // Mangija punktid

    public Mangija(String nimi) {
        this.nimi = nimi;
        this.punktid = 0; // Punktide algväärtus on 0, millele õigete vastuste korral hakatakse punkte lisama
    }
    public void lisaPunkt() {  // Lisab mangijale punkti õige vastuse korral
        this.punktid++;
    }
    public int getPunktid() {  // Kui hiljem vaja punktiseisu kuvada
        return punktid;
    }
    public void eemaldaPunkt() {  // Eemaldab punkti käigu tagasivõtmisel
        if (this.punktid > 0) this.punktid--;
    }
    public String getNimi() {
        return nimi;
    }
}
