import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Mang {

    private ArrayList<Kysimus> kysimused;    // Kõik laetud küsimused
    private ArrayList<Integer> kasutatud;    // Juba küsitud küsimuste indeksid
    private String             failiTee;     // Küsimuste faili asukoht
    private Random             random;       // Juhusliku küsimuse valimiseks

    public Mang(String failiTee) throws IOException {
        this.failiTee  = failiTee;
        this.kysimused = new ArrayList<>();
        this.kasutatud = new ArrayList<>();
        this.random    = new Random();
        laeFailist();
    }

    public String getFailiTee() {
        return failiTee;
    }

    public int getKysimusteArv() {
        return kysimused.size();
    }

    public int getJarelejaanud() {
        return kysimused.size() - kasutatud.size();
    }

    //standartne failist lugemise meetod
    private void laeFailist() throws IOException {
        BufferedReader lugeja = new BufferedReader(new FileReader(failiTee));
        String rida;
        while ((rida = lugeja.readLine()) != null) {
            rida = rida.trim();
            if (rida.isEmpty()) continue;
            String[] osad = rida.split(";");
            String   tekst      = osad[0];
            String[] vastused   = { osad[1], osad[2], osad[3], osad[4] };
            int      oigeIndeks = Integer.parseInt(osad[5].trim());
            kysimused.add(new Kysimus(tekst, vastused, oigeIndeks));
        }
        lugeja.close();
    }

    //kasutab randomit, et küsida juhuslik küsimus, mida pole juba küsitud
    public Kysimus juhuslikKysimus() {
        if (kasutatud.size() >= kysimused.size()) {
            kasutatud.clear();
            System.out.println("  (Kõik küsimused esitatud — alustame uuesti)");
        }
        int indeks;
        do {
            indeks = random.nextInt(kysimused.size());
        } while (kasutatud.contains(indeks));
        kasutatud.add(indeks);
        return kysimused.get(indeks);
    }

    public boolean kontrolliVastus(Kysimus kysimus, int vastus) {
        return kysimus.onOige(vastus);
    }

}
