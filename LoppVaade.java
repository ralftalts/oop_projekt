import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

    // Klass LoppVaade – kuvab mängu lõpptulemuse ning logi põhjal tehtud käikude ajaloo.
     
    public class LoppVaade {

        private final Stage     stage;
        private final Mangija   mangija;
        private final int       kysimusteKogus;
        private final ManguLogi logi;

        public LoppVaade(Stage stage, Mangija mangija, int kysimusteKogus, ManguLogi logi) {
            this.stage          = stage;
            this.mangija        = mangija;
            this.kysimusteKogus = kysimusteKogus;
            this.logi           = logi;
        }

        public Scene looScene() {
            VBox juur = new VBox(20);
            juur.setAlignment(Pos.CENTER);
            juur.setPadding(new Insets(36, 40, 36, 40));
            juur.setStyle("-fx-background-color: #1a1a2e;");

            int punktid = mangija.getPunktid();
            // Arvutab õigete vastuste protsendi
            double protsent = kysimusteKogus > 0 ? (double) punktid / kysimusteKogus * 100 : 0;

            // Kogu, mis lõpus ekraanile väljastatakse
            Label loppPealkiri = new Label("Mäng läbi!");
            loppPealkiri.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
            loppPealkiri.setStyle("-fx-text-fill: #e2b96f;");

            Label nimiLabel = new Label(mangija.getNimi() + ", sinu tulemus:");
            nimiLabel.setFont(Font.font("Georgia", 17));
            nimiLabel.setStyle("-fx-text-fill: #a0a8c0;");

            Label skoorLabel = new Label(punktid + " / " + kysimusteKogus
                    + "  (" + String.format("%.0f", protsent) + "%)");
            skoorLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 28));
            skoorLabel.setStyle("-fx-text-fill: "
                    + (protsent >= 70 ? "#6fcf6f" : protsent >= 40 ? "#ffb347" : "#ff6b6b") + ";");

            // Väljastab arvutatud protsendi põhjal tagasiside mängijale
            String hinnang = protsent >= 90 ? "Suurepärane!" :
                    protsent >= 70 ? "Väga hea!"    :
                            protsent >= 50 ? "Päris hasti!" :
                                    "Harjuta edasi!";
            Label hinnangLabel = new Label(hinnang);
            hinnangLabel.setFont(Font.font("Georgia", 18));
            hinnangLabel.setStyle("-fx-text-fill: #c8d0e8;");

            // Logifaili abil sisestatud vastuste info kuvamine
            Label logiPealkiri = new Label("Käikude ajalugu (logifailist):");
            logiPealkiri.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
            logiPealkiri.setStyle("-fx-text-fill: #e2b96f;");

            TextArea logiAla = new TextArea();
            logiAla.setEditable(false);
            logiAla.setStyle(
                    "-fx-background-color: #0d1117; -fx-text-fill: #c0c8e0; " +
                            "-fx-control-inner-background: #0d1117; " +
                            "-fx-border-color: #334; -fx-border-radius: 6; -fx-background-radius: 6; " +
                            "-fx-font-family: monospace; -fx-font-size: 12px;"
            );
            
        
            logiAla.setPrefHeight(160);

            List<ManguLogi.LogiKirje> kirjed = logi.loeKoikKirjed();
            if (kirjed.isEmpty()) {
                logiAla.setText("(Logifail on tühi)");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-6s %-8s %-12s %-12s%n", "K.nr", "Tulemus", "Antud", "Õige"));
                sb.append("-".repeat(42)).append("\n");
                for (ManguLogi.LogiKirje k : kirjed) {
                    sb.append(String.format("%-6d %-8s %-12d %-12d%n",
                            k.kysimiseNr(), k.oliOige() ? "ÕIGE" : "VALE",
                            k.antudVastus(), k.oigeVastus()));
                }
                logiAla.setText(sb.toString());
            }
            
            // Uue mängu alustamise nupule vajutamise tegevused (nupu disain, kui sellele vajutada jne)
            Button uusMangNupp = new Button("Uus mäng");
            uusMangNupp.setStyle(
                    "-fx-background-color: #e2b96f; -fx-text-fill: #1a1a2e; " +
                            "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
                            "-fx-background-radius: 8; -fx-cursor: hand;"
            );
            uusMangNupp.setOnMouseEntered(e ->
                    uusMangNupp.setStyle("-fx-background-color: #f5d08a; -fx-text-fill: #1a1a2e; " +
                            "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
                            "-fx-background-radius: 8; -fx-cursor: hand;")
            );
            uusMangNupp.setOnMouseExited(e ->
                    uusMangNupp.setStyle("-fx-background-color: #e2b96f; -fx-text-fill: #1a1a2e; " +
                            "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
                            "-fx-background-radius: 8; -fx-cursor: hand;")
            );
            uusMangNupp.setOnAction(e -> {
                Peaklass pk = new Peaklass();
                try { pk.start(stage); } catch (Exception ex) { ex.printStackTrace(); }
            });

            juur.getChildren().addAll(loppPealkiri, nimiLabel, skoorLabel, hinnangLabel,
                    new Separator(), logiPealkiri, logiAla, uusMangNupp);

            Scene scene = new Scene(juur, 700, 600);
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) stage.close();
            });
            return scene;
        }
    }

