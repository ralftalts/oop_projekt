import java.util.Scanner;

public class Peaklass {

    public static void main(String[] args) throws Exception {

        System.out.println("Vasta küsimustele, sisestades õige vastusevariandi numbri.");
        System.out.println("Iga õige vastuse korral lisatakse sulle 1 punkt.\n");

        Scanner scanner = new Scanner(System.in); // Kasutaja sisendi lugemiseks
        
        System.out.print("Sisesta oma nimi: ");
        String nimi = scanner.nextLine(); // Scanner loeb kasutaja sisestatud nime
        Mangija mängija = new Mangija(nimi);
        
        Mang mang = new Mang("kysimused.txt");
        
        int kysimusteKogus = mang.getKysimusteArv();
        for (int i = 0; i < kysimusteKogus; i++) { // Tsükkel küsimuste küsimiseks ja vastuste kontrollimiseks

            Kysimus kysimus = mang.juhuslikKysimus();
            
            kysimus.kuva();
            
            System.out.print("\nSisesta õige vastuse number (1-4): ");
            int vastus = scanner.nextInt(); // Loeb kasutaja sisestatud vastusevariandi numbri
            scanner.nextLine(); // puhasta buffer
            
            if (mang.kontrolliVastus(kysimus, vastus)) {  // Kui meetod kontrolliVastus tagastab "true", lisatakse mängijale punkt
                mängija.lisaPunkt();
                System.out.println("Oige vastus! Teenisid uhe punkti.");
            } else {
                System.out.println("Vale vastus! Oige vastus oli: " + kysimus.getOigeVastus()); // Väljastatakse ka õige vastus
            }

            System.out.println("Punktid: " + mängija.getPunktid() + "/" + (i + 1)); // Väljastab igal korral ka hetkese punktiseisu
        }
        
        System.out.println("\nMang sai labi!");
        System.out.println("Sinu punktisumma on: " + mängija.getPunktid() + "/" + kysimusteKogus);
        scanner.close();
    }
}
