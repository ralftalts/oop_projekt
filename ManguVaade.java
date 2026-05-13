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

/**
 * ManguVaade ? kuvab k?simused ja vastused JavaFX liideses.
 * Haldab m?ngu kulgu, punktiseisu ning kirjutab logi faili.
 */
public class ManguVaade {

    private final Stage   stage;
    private final Mangija mangija;
    private final Mang    mang;

    private int          kysimuseNr      = 0;
    private int          kysimusteKogus;
    private Kysimus      praeguneKysimus;
    private ManguLogi    logi;

    // UI komponendid
    private Label        kysimusLabel;
    private ToggleGroup  vastusGrupp;
    private RadioButton[] vastusNupud    = new RadioButton[4];
    private Label        tagasisideLabel;
    private Label        punktidLabel;
    private Label        progressLabel;
    private Button       edasiNupp;
    private Button       tagasiNupp;
    private ProgressBar  progressBar;

    public ManguVaade(Stage stage, Mangija mangija, Mang mang) {
        this.stage           = stage;
        this.mangija         = mangija;
        this.mang            = mang;
        this.kysimusteKogus  = mang.getKysimusteArv();
        this.logi            = new ManguLogi(mangija.getNimi());
    }

    /** Koostab ja tagastab m?ngu stseeni. */
    public Scene looScene() {
        BorderPane juur = new BorderPane();
        juur.setStyle("-fx-background-color: #1a1a2e;");

        // --- ?LEMINE PANEEL ---
        HBox ylemine = new HBox(20);
        ylemine.setAlignment(Pos.CENTER_LEFT);
        ylemine.setPadding(new Insets(16, 24, 12, 24));
        ylemine.setStyle("-fx-background-color: #16213e; -fx-border-color: #e2b96f; -fx-border-width: 0 0 2 0;");

        Label mangijanimi = new Label("Mangija: " + mangija.getNimi());
        mangijanimi.setFont(Font.font("Georgia", FontWeight.BOLD, 15));
        mangijanimi.setStyle("-fx-text-fill: #e2b96f;");

        punktidLabel = new Label("Punktid: 0 / 0");
        punktidLabel.setFont(Font.font("Georgia", 14));
        punktidLabel.setStyle("-fx-text-fill: #a0a8c0;");

        progressLabel = new Label("Kysimus 1 / " + kysimusteKogus);
        progressLabel.setFont(Font.font("Georgia", 14));
        progressLabel.setStyle("-fx-text-fill: #a0a8c0;");

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(180);
        progressBar.setStyle("-fx-accent: #e2b96f;");

        Region vahe = new Region();
        HBox.setHgrow(vahe, Priority.ALWAYS);
        ylemine.getChildren().addAll(mangijanimi, vahe, progressLabel, progressBar, punktidLabel);
        juur.setTop(ylemine);

        // --- KESKOSA: k?simus ja vastusevariandid ---
        VBox keskosa = new VBox(18);
        keskosa.setPadding(new Insets(32, 40, 20, 40));
        keskosa.setAlignment(Pos.TOP_LEFT);

        kysimusLabel = new Label();
        kysimusLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        kysimusLabel.setStyle("-fx-text-fill: #e8e8f0;");
        kysimusLabel.setWrapText(true);
        // K?simuse laius seotakse akna laiusega ? skaleeritavus
        kysimusLabel.prefWidthProperty().bind(keskosa.widthProperty().subtract(20));

        vastusGrupp = new ToggleGroup();
        VBox vastusePanel = new VBox(10);

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

        tagasisideLabel = new Label();
        tagasisideLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        tagasisideLabel.setWrapText(true);

        keskosa.getChildren().addAll(kysimusLabel, vastusePanel, tagasisideLabel);
        VBox.setVgrow(vastusePanel, Priority.ALWAYS);
        juur.setCenter(keskosa);

        // --- ALUMINE PANEEL: nupud ---
        HBox alumine = new HBox(14);
        alumine.setAlignment(Pos.CENTER_RIGHT);
        alumine.setPadding(new Insets(14, 24, 20, 24));
        alumine.setStyle("-fx-background-color: #16213e; -fx-border-color: #e2b96f; -fx-border-width: 2 0 0 0;");

        tagasiNupp = new Button("Tagasi");
        tagasiNupp.setStyle(nubuStiil("#445"));
        tagasiNupp.setDisable(true);

        edasiNupp = new Button("Vasta");
        edasiNupp.setStyle(nubuStiil("#e2b96f"));

        seaNubuHover(tagasiNupp, "#667", "#556");
        seaNubuHover(edasiNupp,  "#f5d08a", "#e2b96f");

        edasiNupp.setOnAction(e  -> kasitleVastatmine());  // Hiire s?ndmus: nupp
        tagasiNupp.setOnAction(e -> kasitleTagasivotmine()); // Hiire s?ndmus: nupp

        alumine.getChildren().addAll(tagasiNupp, edasiNupp);
        juur.setBottom(alumine);

        // --- KLAVIATUURI S?NDMUSED ---
        Scene scene = new Scene(juur, 700, 520);
        scene.setOnKeyPressed(e -> {
            // Numbriklahvid 1-4 valivad vastusevariandi
            if      (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) vastusNupud[0].setSelected(true);
            else if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) vastusNupud[1].setSelected(true);
            else if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) vastusNupud[2].setSelected(true);
            else if (e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) vastusNupud[3].setSelected(true);
            else if (e.getCode() == KeyCode.ENTER)      kasitleVastatmine();           // Enter = vasta
            else if (e.getCode() == KeyCode.BACK_SPACE  // Backspace = tagasi
                  && !tagasiNupp.isDisabled())           kasitleTagasivotmine();
        });

        laadiaKysimus();
        return scene;
    }

    /** Laadib j?rgmise k?simuse ja uuendab UI. */
    private void laadiaKysimus() {
        praeguneKysimus = mang.juhuslikKysimus();
        kysimusLabel.setText((kysimuseNr + 1) + ". " + praeguneKysimus.getTekst());

        String[] vastused = praeguneKysimus.getVastused();
        for (int i = 0; i < 4; i++) {
            vastusNupud[i].setText((i + 1) + ")  " + vastused[i]);
            vastusNupud[i].setSelected(false);
            vastusNupud[i].setDisable(false);
            vastusNupud[i].setStyle("-fx-text-fill: #d0d8f0; -fx-cursor: hand;");
        }
        tagasisideLabel.setText("");
        edasiNupp.setText(kysimuseNr < kysimusteKogus - 1 ? "Vasta" : "Lopeta mang");
        edasiNupp.setDisable(false);

        progressLabel.setText("Kysimus " + (kysimuseNr + 1) + " / " + kysimusteKogus);
        progressBar.setProgress((double) kysimuseNr / kysimusteKogus);
        tagasiNupp.setDisable(!logi.onTagasivotmineVoimalik());
    }

    /**
     * K?sitleb vastuse esitamist.
     * Erandite k?sitlemine: kasutaja ei valinud vastust.
     */
    private void kasitleVastatmine() {
        RadioButton valitud = (RadioButton) vastusGrupp.getSelectedToggle();
        if (valitud == null) {
            // Vigane sisestus ? kasutaja ei valinud vastust
            tagasisideLabel.setText("Palun vali vastus (1-4) enne jatkamist!");
            tagasisideLabel.setStyle("-fx-text-fill: #ffb347;");
            raputaAnimatsioon(tagasisideLabel);
            return;
        }

        int vastusIndeks = -1;
        for (int i = 0; i < 4; i++) {
            if (vastusNupud[i] == valitud) { vastusIndeks = i + 1; break; }
        }

        boolean oige = mang.kontrolliVastus(praeguneKysimus, vastusIndeks);
        if (oige) {
            mangija.lisaPunkt();
            tagasisideLabel.setText("Oige vastus!");
            tagasisideLabel.setStyle("-fx-text-fill: #6fcf6f;");
        } else {
            tagasisideLabel.setText("Vale! Oige vastus: " + praeguneKysimus.getOigeVastus());
            tagasisideLabel.setStyle("-fx-text-fill: #ff6b6b;");
        }

        punktidLabel.setText("Punktid: " + mangija.getPunktid() + " / " + (kysimuseNr + 1));

        // Keela vastusevariandid p?rast vastamist
        for (RadioButton rb : vastusNupud) rb.setDisable(true);
        edasiNupp.setDisable(true);

        // Salvesta k?ik logifaili
        logi.salvesta(kysimuseNr + 1, praeguneKysimus.getTekst(), vastusIndeks,
                      praeguneKysimus.getOigeIndeks(), mangija.getPunktid());

        kysimuseNr++;

        // Liiku edasi l?hikese viivitusega
        PauseTransition paus = new PauseTransition(Duration.millis(3000));
        if (kysimuseNr < kysimusteKogus) {
            paus.setOnFinished(ev -> laadiaKysimus());
        } else {
            paus.setOnFinished(ev -> kuvaLoppTulemus());
        }
        paus.play();
    }

    /** V?tab viimase k?igu tagasi logi p?hjal. */
    private void kasitleTagasivotmine() {
        ManguLogi.LogiKirje viimane = logi.votaTagasi();
        if (viimane == null) { tagasiNupp.setDisable(true); return; }
        if (viimane.oliOige()) mangija.eemaldaPunkt(); // Vajadusel eemalda punkt
        kysimuseNr--;
        punktidLabel.setText("Punktid: " + mangija.getPunktid() + " / " + kysimuseNr);
        laadiaKysimus();
    }

    /** Kuvab m?ngu l?pptulemuse. */
    private void kuvaLoppTulemus() {
        LoppVaade loppVaade = new LoppVaade(stage, mangija, kysimusteKogus, logi);
        stage.setScene(loppVaade.looScene());
    }

    // --- Abimeetodid ---

    private String nubuStiil(String varv) {
        return "-fx-background-color: " + varv + "; -fx-text-fill: #1a1a2e; " +
               "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 9 22; " +
               "-fx-background-radius: 8; -fx-cursor: hand;";
    }

    private void seaNubuHover(Button nupp, String hoverVarv, String normalVarv) {
        nupp.setOnMouseEntered(e -> nupp.setStyle(nubuStiil(hoverVarv)));
        nupp.setOnMouseExited(e  -> nupp.setStyle(nubuStiil(normalVarv)));
    }

    private void raputaAnimatsioon(javafx.scene.Node solm) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), solm);
        tt.setFromX(0); tt.setByX(10); tt.setCycleCount(4); tt.setAutoReverse(true);
        tt.play();
    }
}
