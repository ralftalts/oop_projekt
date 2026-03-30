import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Peaklass {

    public static void main(String[] args) throws Exception {

        System.out.println("Vasta küsimustele, sisestades õige vastusevariandi numbri. Iga õige vastuse korral lisatakse sulle 1 punkt.");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Sisesta oma nimi: ");
        String nimi = scanner.nextLine();

        Mängija mängija = new Mängija(nimi);

        List<Kysimus> kysimused = Mäng.loeKysimused("kysimused.txt");
        for (Kysimus kysimus : kysimused) {
            System.out.println(kysimus.getKysimus());
            System.out.println("1: " + kysimus.getVariant1());
            System.out.println("2: " + kysimus.getVariant2());
            System.out.println("3: " + kysimus.getVariant3());
            System.out.println("4: " + kysimus.getVariant4());

            System.out.print("Sisesta õige vastuse number (1-4): ");
            int vastus = scanner.nextInt();

            if (vastus == kysimus.getVastus()) {
                mängija.lisaPunkt();
                System.out.println("Õige vastus! Teenisid ühe punkti lisaks");
        } else {
                System.out.println("Vale vastus! Õige vastus on: " + kysimus.getVastus());
            }

        }
        System.out.println("Mäng sai läbi!");
        System.out.println("Sinu punktisumma on: " + mängija.getPunktid());
    }
}
