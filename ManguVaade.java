import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;

    // Klass ManguVaade kuvab küsimused ja vastused JavaFX liidese näol kasutajale
    // Haldab mängu kulgu, punktiseisu ning kirjutab logifaili.

    public class ManguVaade {

        private final Stage stage;
        private final Mangija mangija;
        private final Mang mang;

        private int kysimuseNr = 0;
        private int kysimusteKogus;
        private Kysimus praeguneKysimus;
        private ManguLogi logi;

        // Vajalikud isendiväljad kasutaja vaate jaoks
        private Label kysimusLabel;
        private ToggleGroup vastusGrupp;
        private RadioButton[] vastusNupud = new RadioButton[4]; // Vastusevariantide nupud
        private Label tagasisideLabel;
        private Label punktidLabel;
        private Label progressLabel;
        private Button edasiNupp;
        private Button tagasiNupp;
        private ProgressBar progressBar;

        public ManguVaade(Stage stage, Mangija mangija, Mang mang) {
            this.stage = stage;
            this.mangija = mangija;
            this.mang = mang;
            this.kysimusteKogus = mang.getKysimusteArv();
            this.logi = new ManguLogi(mangija.getNimi()); // Igal mängijal oma logi
        }

        // Mängu stseen
        public Scene looScene() {
            BorderPane juur = new BorderPane();
            juur.setStyle("-fx-background-color: #1a1a2e;");

            // Ülemine äär
            HBox ylemine = new HBox(20);
            ylemine.setAlignment(Pos.CENTER_LEFT);
            ylemine.setPadding(new Insets(16, 24, 12, 24));
            ylemine.setStyle("-fx-background-color: #16213e; -fx-border-color: #e2b96f; -fx-border-width: 0 0 2 0;");

            // Seab sisestatud mängija nimele vastava fondi ja suuruse
            Label mangijanimi = new Label("Mängija: " + mangija.getNimi());
            mangijanimi.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
            mangijanimi.setStyle("-fx-text-fill: #e2b96f;");

            // Seab esialgsetele punktidele fondi ja suuruse
            punktidLabel = new Label("Punktid: 0 / 0");
            punktidLabel.setFont(Font.font("Georgia", 14));
            punktidLabel.setStyle("-fx-text-fill: #a0a8c0;");

            // Seab mängu käiku kirjeldava teksti fondi ja suuruse
            progressLabel = new Label("Küsimus 1 / " + kysimusteKogus);
            progressLabel.setFont(Font.font("Georgia", 14));
            progressLabel.setStyle("-fx-text-fill: #a0a8c0;");

            progressBar = new ProgressBar(0);
            progressBar.setPrefWidth(180);
            progressBar.setStyle("-fx-accent: #e2b96f;");

            Region vahe = new Region();
            HBox.setHgrow(vahe, Priority.ALWAYS);
            ylemine.getChildren().addAll(mangijanimi, vahe, progressLabel, progressBar, punktidLabel);
            juur.setTop(ylemine);

            // Keskmine osa küsimuse ja vastustega
            VBox keskosa = new VBox(18);
            keskosa.setPadding(new Insets(32, 40, 20, 40));
            keskosa.setAlignment(Pos.TOP_LEFT);

            kysimusLabel = new Label();
            kysimusLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
            kysimusLabel.setStyle("-fx-text-fill: #e8e8f0;");
            kysimusLabel.setWrapText(true);
            // Küsimuse laius kohandatakse vastavalt akna laiusele
            kysimusLabel.prefWidthProperty().bind(keskosa.widthProperty().subtract(20));

            vastusGrupp = new ToggleGroup();
            VBox vastusePanel = new VBox(10);


            // Valikvastustega küsimuste nupud
            for (int i = 0; i < 4; i++) {
                RadioButton rb = new RadioButton();
                rb.setToggleGroup(vastusGrupp);
                rb.setFont(Font.font("Georgia", 15));
                rb.setStyle("-fx-text-fill: #d0d8f0; -fx-cursor: hand;");
                rb.setWrapText(true);
                // Hiire hover efekt
                rb.setOnMouseEntered(e -> rb.setStyle("-fx-text-fill: #e2b96f; -fx-cursor: hand;"));
                rb.setOnMouseExited(e -> {
                    if (!rb.isSelected()) rb.setStyle("-fx-text-fill: #d0d8f0; -fx-cursor: hand;");
                });
                rb.selectedProperty().addListener((obs, vana, uus) -> {
                    if (uus) rb.setStyle("-fx-text-fill: #e2b96f; -fx-cursor: hand;");
                    else     rb.setStyle("-fx-text-fill: #d0d8f0; -fx-cursor: hand;");
                });
                vastusNupud[i] = rb;
                vastusePanel.getChildren().add(rb);
            }

            // Tagasiside väljundi tegemine
            tagasisideLabel = new Label();
            tagasisideLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
            tagasisideLabel.setWrapText(true);

            keskosa.getChildren().addAll(kysimusLabel, vastusePanel, tagasisideLabel);
            VBox.setVgrow(vastusePanel, Priority.ALWAYS);
            juur.setCenter(keskosa);

            // Alumised nupud: tagasi ja vasta
            HBox alumine = new HBox(14);
            alumine.setAlignment(Pos.CENTER_RIGHT);
            alumine.setPadding(new Insets(14, 24, 20, 24));
            alumine.setStyle("-fx-background-color: #16213e; -fx-border-color: #e2b96f; -fx-border-width: 2 0 0 0;");

            tagasiNupp = new Button("Tagasi");
            tagasiNupp.setStyle(nupuStiil("#445"));
            tagasiNupp.setDisable(true);

            edasiNupp = new Button("Vasta");
            edasiNupp.setStyle(nupuStiil("#e2b96f"));

            seaNupuHover(tagasiNupp, "#667", "#556");
            seaNupuHover(edasiNupp,  "#f5d08a", "#e2b96f");

            edasiNupp.setOnAction(e  -> kasitleVastamine());  // Hiire s?ndmus: nupp
            tagasiNupp.setOnAction(e -> kasitleTagasivotmine()); // Hiire s?ndmus: nupp

            alumine.getChildren().addAll(tagasiNupp, edasiNupp);
            juur.setBottom(alumine);

            // Klaviatuuri sündmused
            Scene scene = new Scene(juur, 700, 520);
            scene.setOnKeyPressed(e -> {
                // Numbriklahvid 1-4 valivad vastusevariandi
                if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) vastusNupud[0].setSelected(true);
                else if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) vastusNupud[1].setSelected(true);
                else if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) vastusNupud[2].setSelected(true);
                else if (e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) vastusNupud[3].setSelected(true);
                else if (e.getCode() == KeyCode.ENTER) kasitleVastamine(); // Enter = vasta
                else if (e.getCode() == KeyCode.BACK_SPACE  // Backspace = tagasi
                        && !tagasiNupp.isDisabled()) kasitleTagasivotmine();
            });

            laadijaKysimus();
            return scene;
        }

        // Meetod valib juhusliku küsimuse tekstifailist ja uuendab mängija vaadet uue küsimuse ja variantidega
        private void laadijaKysimus() {
            praeguneKysimus = mang.juhuslikKysimus();
            kysimusLabel.setText((kysimuseNr + 1) + ". " + praeguneKysimus.getTekst());

            // Vastusevariandid
            String[] vastused = praeguneKysimus.getVastused();
            for (int i = 0; i < 4; i++) {
                vastusNupud[i].setText((i + 1) + ")  " + vastused[i]);
                vastusNupud[i].setSelected(false);
                vastusNupud[i].setDisable(false);
                vastusNupud[i].setStyle("-fx-text-fill: #d0d8f0; -fx-cursor: hand;");
            }
            tagasisideLabel.setText("");
            edasiNupp.setText(kysimuseNr < kysimusteKogus - 1 ? "Vasta" : "Lõpeta mäng");
            edasiNupp.setDisable(false);

            progressLabel.setText("Küsimus " + (kysimuseNr + 1) + " / " + kysimusteKogus);
            progressBar.setProgress((double) kysimuseNr / kysimusteKogus);
            tagasiNupp.setDisable(!logi.onTagasivotmineVoimalik());
        }


         // Käsitleb vastuse sisestamist.
         // Erindite käsitlemine: kasutaja ei valinud vastust.

        private void kasitleVastamine() {
            RadioButton valitud = (RadioButton) vastusGrupp.getSelectedToggle();
            if (valitud == null) {
                // Vigane sisestus - kasutaja ei valinud vastust
                tagasisideLabel.setText("Palun vali vastus (1-4) enne jätkamist!"); // Kui kasutaja ei vastanud, siis tagasiside seatakse vastavaks
                tagasisideLabel.setStyle("-fx-text-fill: #ffb347;");
                raputaAnimatsioon(tagasisideLabel);
                return;
            }

            int vastusIndeks = -1;
            for (int i = 0; i < 4; i++) {
                if (vastusNupud[i] == valitud) {
                    vastusIndeks = i + 1; break;
                }
            }

            // Punktide lisamise loogika
            // Kui vastus õige ei olnud, siis väljastab õige vastuse, aga punkti ei lisa
            boolean oige = mang.kontrolliVastus(praeguneKysimus, vastusIndeks);
            if (oige) {
                mangija.lisaPunkt();
                tagasisideLabel.setText("Õige vastus!");
                tagasisideLabel.setStyle("-fx-text-fill: #6fcf6f;");
            } else {
                tagasisideLabel.setText("Vale! Õige vastus: " + praeguneKysimus.getOigeVastus());
                tagasisideLabel.setStyle("-fx-text-fill: #ff6b6b;");
            }

            punktidLabel.setText("Punktid: " + mangija.getPunktid() + " / " + (kysimuseNr + 1));

            // Mängija ei saa vastust muuta peale vastamist
            for (RadioButton rb : vastusNupud) rb.setDisable(true);
            edasiNupp.setDisable(true);

            // Salvestab käigud logifaili, et hiljem tulemust väljastada
            logi.salvesta(kysimuseNr + 1, praeguneKysimus.getTekst(), vastusIndeks,
                    praeguneKysimus.getOigeIndeks(), mangija.getPunktid());

            kysimuseNr++;

            // Enne järgmise küsimuse juurde liikumist on väike paus
            PauseTransition paus = new PauseTransition(Duration.millis(3000));
            if (kysimuseNr < kysimusteKogus) {
                paus.setOnFinished(ev -> laadijaKysimus());
            } else {
                paus.setOnFinished(ev -> kuvaLoppTulemus());
            }
            paus.play();
        }

        // Kasutab logifaili, et küsimustes tagasi liikuda
        private void kasitleTagasivotmine() {
            ManguLogi.LogiKirje viimane = logi.votaTagasi();
            if (viimane == null) { tagasiNupp.setDisable(true); return; }
            if (viimane.oliOige()) mangija.eemaldaPunkt(); // Vajadusel eemaldab punkti, kui viimane vastus õige oli
            kysimuseNr--;
            punktidLabel.setText("Punktid: " + mangija.getPunktid() + " / " + kysimuseNr);
            laadijaKysimus();
        }

        // Kuvab mängu lõpptulemuse
        private void kuvaLoppTulemus() {
            LoppVaade loppVaade = new LoppVaade(stage, mangija, kysimusteKogus, logi);
            stage.setScene(loppVaade.looScene());
        }

        // Abimeetodid disaini jaoks

        private String nupuStiil(String varv) {
            return "-fx-background-color: " + varv + "; -fx-text-fill: #1a1a2e; " +
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 9 22; " +
                    "-fx-background-radius: 8; -fx-cursor: hand;";
        }

        private void seaNupuHover(Button nupp, String hoverVarv, String normalVarv) {
            nupp.setOnMouseEntered(e -> nupp.setStyle(nupuStiil(hoverVarv)));
            nupp.setOnMouseExited(e  -> nupp.setStyle(nupuStiil(normalVarv)));
        }

        // Nime tekstikasti raputamine, kui kasutaja ei sisesta nime või ei vali vastust
        private void raputaAnimatsioon(javafx.scene.Node solm) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(60), solm);
            tt.setFromX(0); tt.setByX(10); tt.setCycleCount(4); tt.setAutoReverse(true);
            tt.play();
        }
    }

