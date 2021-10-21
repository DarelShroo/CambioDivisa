package dad.cambiodivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class CambioDivisa extends Application implements ControllerCambioDivisa {
    GridPane gridpane;
    ColumnConstraints firstColumn;
    ColumnConstraints secondColumn;
    HBox hBox;
    private Divisa to;
    private Divisa from;
    private ComboBox<Divisa> comboBoxDivisaTo;
    private ComboBox<Divisa> comboBoxDivisaFrom;
    private TextField textFieldDivisaTo;
    private TextField textFieldDivisaFrom;
    private Button buttonCambio;
    private Divisa euro;
    private Divisa libra;
    private Divisa dolar;
    private Divisa yen;
    private Alert alert;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        comboBoxDivisaTo = new ComboBox<>();
        comboBoxDivisaFrom = new ComboBox<>();
        textFieldDivisaTo = new TextField();
        textFieldDivisaFrom = new TextField();
        buttonCambio = new Button("Cambio");
        euro = new Divisa("Euro", 1.0);
        libra = new Divisa("Libra", 0.8873);
        dolar = new Divisa("Dolar", 1.2007);
        yen = new Divisa("Yen", 132.33);
        alert = new Alert(Alert.AlertType.INFORMATION);
        gridpane = new GridPane();
        firstColumn = new ColumnConstraints();
        secondColumn = new ColumnConstraints();
        hBox = new HBox();

        firstColumn.setPercentWidth(50);
        secondColumn.setPercentWidth(50);

        gridpane.getColumnConstraints().addAll(firstColumn, secondColumn);

        gridpane.setVgap(5);
        gridpane.setHgap(5);

        gridpane.setAlignment(Pos.CENTER);
        firstColumn.setHalignment(HPos.RIGHT);
        secondColumn.setHalignment(HPos.LEFT);

        gridpane.add(textFieldDivisaFrom, 0, 0, 1, 1);
        gridpane.add(comboBoxDivisaFrom, 1, 0, 1, 1);
        gridpane.add(textFieldDivisaTo, 0, 1, 1, 1);
        gridpane.add(comboBoxDivisaTo, 1, 1, 1, 1);


        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(buttonCambio);

        gridpane.add(hBox, 0, 2, 2, 1);

        ArrayList<Divisa> listDivisa = new ArrayList<>();
        Collections.addAll(listDivisa, euro, libra, dolar, yen);

        textFieldDivisaTo.setEditable(false);
        comboBoxDivisaTo.getSelectionModel().select(yen);
        comboBoxDivisaFrom.getSelectionModel().select(euro);
        comboBoxDivisaTo.getItems().addAll(listDivisa);
        comboBoxDivisaFrom.getItems().addAll(listDivisa);

        textFieldDivisaTo.setMaxWidth(70);
        textFieldDivisaFrom.setMaxWidth(70);

        buttonCambio.setOnAction(e -> onButtonCambio(e));

        Scene scene = new Scene(gridpane, 250, 180);
        stage.setTitle("CambioDivisa");
        stage.setScene(scene);
        stage.show();
    }

    private void onButtonCambio(ActionEvent e) {
        try {
            from = comboBoxDivisaFrom.getSelectionModel().getSelectedItem();
            to = comboBoxDivisaTo.getSelectionModel().getSelectedItem();

            double origen = Double.parseDouble(textFieldDivisaFrom.getText());
            double cambio = from.toEuro(origen);
            double destino = to.fromEuro(cambio);
            textFieldDivisaTo.setText(destino + "");

        } catch (NumberFormatException exception) {
            alert.setTitle("Algo a ocurrido");
            alert.setContentText("Vuelve a intentarlo");
            alert.setHeaderText("No es un número");
            alert.showAndWait();
        }
    }
}
