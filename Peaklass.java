import javafx.application.Application;
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
import java.io.IOException;

/**
 * Peaklass – JavaFX graafilise kasutajaliidese käivitaja.
 * Asendab varasema konsooli-põhise suhtluse visuaalse liidesega.
 */
public class Peaklass extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Viktoriin");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        primaryStage.setScene(looNimiScene(primaryStage));
        primaryStage.show();
    }

    /**
     * Loob avaakna, kus kasutaja sisestab oma nime.
     * Töötleb nii hiire (nupuvajutus) kui klaviatuuri (Enter) sündmusi.
     */
    public Scene looNimiScene(Stage stage) {
        VBox juur = new VBox(20);
        juur.setAlignment(Pos.CENTER);
        juur.setPadding(new Insets(40));
        juur.setStyle("-fx-background-color: #1a1a2e;");

        Label pealkiri = new Label("VIKTORIIN");
        pealkiri.setFont(Font.font("Georgia", FontWeight.BOLD, 36));
        pealkiri.setStyle("-fx-text-fill: #e2b96f;");

        Label juhend = new Label("Sisesta oma nimi, et mang alustada");
        juhend.setFont(Font.font("Georgia", 16));
        juhend.setStyle("-fx-text-fill: #a0a8c0;");

        TextField nimiVali = new TextField();
        nimiVali.setPromptText("Sinu nimi...");
        nimiVali.setMaxWidth(300);
        nimiVali.setStyle(
            "-fx-background-color: #16213e; -fx-text-fill: #e0e0e0; " +
            "-fx-border-color: #e2b96f; -fx-border-radius: 6; " +
            "-fx-background-radius: 6; -fx-font-size: 15px; -fx-padding: 10;"
        );

        Label vigaLabel = new Label();
        vigaLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 13px;");

        Button alustaNupp = new Button("Alusta mangu");
        alustaNupp.setStyle(
            "-fx-background-color: #e2b96f; -fx-text-fill: #1a1a2e; " +
            "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
            "-fx-background-radius: 8; -fx-cursor: hand;"
        );
        // Hiire hover efekt nupul
        alustaNupp.setOnMouseEntered(e ->
            alustaNupp.setStyle("-fx-background-color: #f5d08a; -fx-text-fill: #1a1a2e; " +
                "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
                "-fx-background-radius: 8; -fx-cursor: hand;")
        );
        alustaNupp.setOnMouseExited(e ->
            alustaNupp.setStyle("-fx-background-color: #e2b96f; -fx-text-fill: #1a1a2e; " +
                "-fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 28; " +
                "-fx-background-radius: 8; -fx-cursor: hand;")
        );

        // Toimingu loogika: kontrollib nime ja käivitab mängu
        Runnable alustaMang = () -> {
            String nimi = nimiVali.getText().trim();
            // Erandite ja vigaste sisestuste käsitlemine
            if (nimi.isEmpty()) {
                vigaLabel.setText("Palun sisesta oma nimi!");
                raputaAnimatsioon(nimiVali);
                return;
            }
            if (nimi.length() > 30) {
                vigaLabel.setText("Nimi on liiga pikk (max 30 tahemärki)");
                return;
            }
            try {
                Mangija mangija = new Mangija(nimi);
                Mang mang = new Mang("kysimused.txt");
                ManguVaade manguVaade = new ManguVaade(stage, mangija, mang);
                stage.setScene(manguVaade.looScene());
            } catch (IOException e) {
                // Faili lugemisel tekkinud erand – teavita kasutajat
                vigaLabel.setText("Kysimuste faili ei leitud: kysimused.txt");
            }
        };

        // Hiire sündmus – nupu klikk
        alustaNupp.setOnAction(e -> alustaMang.run());
        // Klaviatuuri sündmus – Enter käivitab sama loogika
        nimiVali.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) alustaMang.run();
        });

        juur.getChildren().addAll(pealkiri, juhend, nimiVali, vigaLabel, alustaNupp);

        Scene scene = new Scene(juur, 600, 450);
        // Escape sulgeb akna – globaalne klaviatuuri kuulaja
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) stage.close();
        });
        return scene;
    }

    private void raputaAnimatsioon(javafx.scene.Node solm) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), solm);
        tt.setFromX(0); tt.setByX(10); tt.setCycleCount(4); tt.setAutoReverse(true);
        tt.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
