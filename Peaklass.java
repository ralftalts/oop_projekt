import java.util.Scanner;

public class Peaklass {

    public static void main(String[] args) throws Exception {

        System.out.println("Vasta küsimustele, sisestades õige vastusevariandi numbri.");
        System.out.println("Iga õige vastuse korral lisatakse sulle 1 punkt.\n");

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Sisesta oma nimi: ");
        String nimi = scanner.nextLine();
        Mangija mängija = new Mangija(nimi);
        
        Mang mang = new Mang("kysimused.txt");
        
        int kysimusteKogus = mang.getKysimusteArv();
        for (int i = 0; i < kysimusteKogus; i++) {

            Kysimus kysimus = mang.juhuslikKysimus();
            
            kysimus.kuva();
            
            System.out.print("\nSisesta õige vastuse number (1-4): ");
            int vastus = scanner.nextInt();
            scanner.nextLine(); // puhasta buffer
            
            if (mang.kontrolliVastus(kysimus, vastus)) {
                mängija.lisaPunkt();
                System.out.println("Oige vastus! Teenisid uhe punkti.");
            } else {
                System.out.println("Vale vastus! Oige vastus oli: " + kysimus.getOigeVastus());
            }

            System.out.println("Punktid: " + mängija.getPunktid() + "/" + (i + 1));
        }
        
        System.out.println("\nMang sai labi!");
        System.out.println("Sinu punktisumma on: " + mängija.getPunktid() + "/" + kysimusteKogus);
        scanner.close();
    }
}
