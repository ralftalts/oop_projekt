public class Kysimus {

    private String   tekst;        // Küsimuse tekst
    private String[] vastused;     // 4 vastusevariant
    private int      oigeIndeks;   // Õige vastuse indeks (1-4)

    public Kysimus(String tekst, String[] vastused, int oigeIndeks) {
        this.tekst      = tekst;
        this.vastused   = vastused;
        this.oigeIndeks = oigeIndeks;
    }

    public String getTekst() {
        return tekst;
    }

    public String[] getVastused() {
        return vastused;
    }

    public int getOigeIndeks() {
        return oigeIndeks;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public void setVastused(String[] vastused) {
        this.vastused = vastused;
    }

    public void setOigeIndeks(int oigeIndeks) {
        this.oigeIndeks = oigeIndeks;
    }

    public boolean onOige(int vastus) {
        return vastus == oigeIndeks;
    }

    public String getOigeVastus() {
        return vastused[oigeIndeks - 1];
    }

    public void kuva() {
        System.out.println("\n  " + tekst);
        System.out.println();
        for (int i = 0; i < vastused.length; i++) {
            System.out.println("  " + (i + 1) + ") " + vastused[i]);
        }
    }
}
