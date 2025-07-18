package calculator_fx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CylinderMaterials extends Application {

    private Computations computations = new Computations();
    private TextField tf;
    private double firstOperand;
    private String operator;
    private Boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        //main pane
        FlowPane fb = new FlowPane();
        // upper
        HBox hb = new HBox(10);
        hb.setPadding(new Insets(10));
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.setStyle("-fx-background-color: black;");

        hb.prefWidthProperty().bind(Bindings.multiply(fb.widthProperty(), 1));
        hb.prefHeightProperty().bind(Bindings.multiply(fb.heightProperty(), 0.4));

        Text lbl = new Text("Calculator");
        lbl.setFill(Color.WHITE);
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 22));
        tf = new TextField();
        tf.setEditable(false);
        tf.setFont(Font.font(18));
        tf.setAlignment(Pos.CENTER_RIGHT);
        tf.setPrefWidth(300);
        hb.getChildren().addAll(lbl, tf);
        //lower
        HBox hb1 = new HBox();

        hb1.setStyle("-fx-background-color: yellow;");
        hb1.prefWidthProperty().bind(Bindings.multiply(fb.widthProperty(), 1));
        hb1.prefHeightProperty().bind(Bindings.multiply(fb.heightProperty(), 0.6));

        GridPane gp = new GridPane();
        // Make each column take equal space
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25); // 4 columns => 100/4
            gp.getColumnConstraints().add(col);
        }

// Make 5 rows of equal height
        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20); // 5 rows => 100/5
            gp.getRowConstraints().add(row);
        }

        String[][] layOuts = {
            {"AC", "%", "DEL", "/"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"(/)", "0", ".", "="}
        };
        for (int row = 0; row < layOuts.length; row++) {
            for (int col = 0; col < layOuts[row].length; col++) {
                String btnLabel = layOuts[row][col];
                Button button = new Button(layOuts[row][col]);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setFont(Font.font(16));
                button.setOnAction(e -> handleCalc(btnLabel));
                gp.add(button, col, row);
            }
        }

        gp.prefWidthProperty().bind(Bindings.multiply(hb1.widthProperty(), 1));
        gp.prefHeightProperty().bind(Bindings.multiply(hb1.heightProperty(), 0.6));
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setPadding(new Insets(10));
        gp.setStyle("-fx-background-color: black;");

        hb1.getChildren().addAll(gp);

        //Main 
        fb.setStyle("-fx-background-color: black;");
        fb.setAlignment(Pos.TOP_LEFT);
        fb.setPadding(new Insets(0, 0, 0, 0));
        fb.getChildren().addAll(hb, hb1);

        Scene scene = new Scene(fb);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleCalc(String btnLabel) {
        String text = tf.getText();

        switch (btnLabel) {
            case "AC":
                tf.clear();
                firstOperand = 0;
                operator = "";
                startNewNumber = true;
                break;
            case "DEL":
                if (!text.isEmpty()) {
                    tf.setText(text.substring(0, text.length() - 1));
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                if (!text.isEmpty()) {
                    firstOperand = Double.parseDouble(text);
                    operator = btnLabel;
                    tf.setText(text + " " + operator + "");
                    startNewNumber = true;
                }
                break;
            case "=":
                if (!operator.isEmpty() && !text.isEmpty()) {
                    try {
                        // Example: if text = "7 + 5"
                        String[] parts = text.split("[" + "\\" + operator + "]");
                        if (parts.length > 1) {
                            double secondOperand = Double.parseDouble(parts[1].trim());
                            double result = computations.compute(firstOperand, secondOperand, operator);
                            tf.setText(String.valueOf(result));
                            operator = "";
                            startNewNumber = true;
                        }
                    } catch (Exception e) {
                        tf.setText("Error");
                    }
                }
                break;
            case "(/)":
                // Optional: Add functionality later
                break;
            case ".":
                if (startNewNumber) {
                    tf.setText("0.");
                    startNewNumber = false;
                } else if (!text.contains(".")) {
                    tf.appendText(".");
                }
                break;
            default: // Numbers
                if (startNewNumber) {
                    tf.setText(text + "" + btnLabel);
                    startNewNumber = false;
                } else {
                    tf.appendText(btnLabel);
                }
                break;
        }
    }

}
