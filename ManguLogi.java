import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * ManguLogi – kirjutab iga käigu CSV-faili ja võimaldab käike tagasi võtta.
 * Nõue: "programm peab mingid andmed kirjutama faili ja neid failist ka lugema".
 */
public class ManguLogi {

    private static final String LOGI_KAUST = "logid";
    private final String        failiTee;
    private final List<LogiKirje> malu;   // Käigud mälus (tagasivõtmiseks)

    public ManguLogi(String mangijanimi) {
        // Loo logikaust kui veel pole
        new File(LOGI_KAUST).mkdirs();

        // Failinimi: mangijanimi + ajatempel
        String ajatempel = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        this.failiTee = LOGI_KAUST + File.separator
                + mangijanimi.replaceAll("[^a-zA-Z0-9]", "_")
                + "_" + ajatempel + ".csv";
        this.malu = new ArrayList<>();

        // Kirjuta päiserida
        kirjutaRida("kysimuseNr;antudVastus;oigeVastus;oliOige;punktid;aeg");
    }

    /**
     * Salvestab ühe käigu logifaili ja mällu.
     */
    public void salvesta(int kysimuseNr, String tekst, int antudVastus,
                         int oigeVastus, int punktid) {
        boolean oige = (antudVastus == oigeVastus);
        String aeg = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String puhasTekst = tekst.replace(";", ",");
        String rida = kysimuseNr + ";" + puhasTekst + ";" + antudVastus + ";"
                    + oigeVastus + ";" + (oige ? "JAH" : "EI") + ";"
                    + punktid + ";" + aeg;
        kirjutaRida(rida);
        malu.add(new LogiKirje(kysimuseNr, antudVastus, oigeVastus, oige));
    }

    /** Tagastab true kui on vähemalt üks käik mälus. */
    public boolean onTagasivotmineVoimalik() {
        return !malu.isEmpty();
    }

    /**
     * Võtab viimase käigu tagasi mälust ja märgib logifailis.
     */
    public LogiKirje votaTagasi() {
        if (malu.isEmpty()) return null;
        LogiKirje viimane = malu.remove(malu.size() - 1);
        String aeg = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        kirjutaRida("TAGASI;" + viimane.kysimiseNr() + ";;;;;" + aeg);
        return viimane;
    }

    /**
     * Loeb kõik kirjed logifailist (peale päise).
     */
    public List<LogiKirje> loeKoikKirjed() {
        List<LogiKirje> kirjed = new ArrayList<>();
        try (BufferedReader lugeja = new BufferedReader(new FileReader(failiTee))) {
            String rida;
            boolean esimene = true;
            while ((rida = lugeja.readLine()) != null) {
                if (esimene) { esimene = false; continue; } // Jäta päis vahele
                if (rida.startsWith("TAGASI")) continue;
                String[] osad = rida.split(";");
                if (osad.length < 6) continue;
                try {
                    int nr     = Integer.parseInt(osad[0]);
                    int antud  = Integer.parseInt(osad[2]);
                    int oige   = Integer.parseInt(osad[3]);
                    boolean ok = "JAH".equals(osad[4]);
                    kirjed.add(new LogiKirje(nr, antud, oige, ok));
                } catch (NumberFormatException e) {
                    // Rikutud rida – jätame vahele
                }
            }
        } catch (IOException e) {
            // Faili lugemisel probleem – tagasta tühi nimekiri
        }
        return kirjed;
    }

    /** Kirjutab ühe rea logifaili (lisamisrežiim). */
    private void kirjutaRida(String rida) {
        try (PrintWriter kirjutaja = new PrintWriter(new FileWriter(failiTee, true))) {
            kirjutaja.println(rida);
        } catch (IOException e) {
            System.err.println("Hoiatus: logifaili kirjutamine ebaonnestus: " + e.getMessage());
        }
    }

    /** Lihtne kirje-klass ühe käigu andmete hoidmiseks. */
    public record LogiKirje(int kysimiseNr, int antudVastus, int oigeVastus, boolean oliOige) {}
}
