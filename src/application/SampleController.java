package application;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.List;

/*Main module, which implements the drawing system.*/

public class SampleController {
    @FXML
    private Button eraseButton;

    @FXML
    private AnchorPane functionPanel;

    @FXML
    private Button drawButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField functionGetter;

    StringProperty function = new SimpleStringProperty();

    public void initialize() {
        functionPanel.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        functionGetter.textProperty().bindBidirectional(function);

    }
    /*Main class*/
    class DoIt extends Thread {
        public void run() {
            String gotFunction = function.get();
            List<String> listOfX = Arrays.asList(gotFunction.split(" "));
            /*System based on regular expressions collects informations from the user.*/
            int X = 0;
            double Xprevious = 0;
            double Yprevious = 0;
            for (; X < 600; X++) {
                double Y = 0;
                int Xused = X - 300;
                for (String s : listOfX) {
                    double Power = 1;
                    double timeing = 1;
                    double Xin = 0;
                    boolean negative = false;
                    boolean noX = true;
                    char[] list = s.toCharArray();
                    for (int i = 0; i < list.length; i++) {
                        //looking for special chars
                        if (list[i] == '-') {
                            negative = true;
                        }
                        if (list[i] == 'x') {
                            Xin = Xused;
                            noX = false;
                        }
                        if (list[i] == '*') {
                            String s1 = "";
                            for (int lol = 1; lol <= i - 1; lol++) {
                                s1 += list[lol];
                            }
                            timeing = Double.valueOf(s1);
                        }
                        if (list[i] == '(') {
                            String lol = String.valueOf(list[i + 1]);
                            Power = Double.valueOf(lol);
                        }

                    }
                    if (negative == true) {
                        if (noX == true) {
                            s = s.replace("\\D", "");
                            double got = Double.valueOf(s);
                            Y = Y + got;
                        }
                    }
                    if (negative == false) {
                        if (noX == true) {
                            s = s.replace("\\D", "");
                            double got = Double.valueOf(s);
                            Y = Y + got;
                        }
                    }
                    if (negative == true) {
                        if (noX == false) {
                            Y -= (Math.pow(Xin, Power) * timeing);
                        }
                    }
                    if (negative == false) {
                        if (noX == false) {
                            Y += (Math.pow(Xin, Power)) * timeing;
                        }
                    }

                }
                //drawing point after counting
                Y = -Y + 300;
                if ((Y > 0) && (Y < 600)) {
                    functionPanel.getChildren().add(new Line(X, Y, X, Y));
                    if ((X > 1) && (Yprevious != 0)) {
                        functionPanel.getChildren().add(new Line(Xprevious, Yprevious, X, Y));
                    }
                    if (X > 0) {
                        Xprevious = X;
                        Yprevious = Y;
                    }
                }
            }
        }
    }

    @FXML
    void draw(ActionEvent event) {
        DoIt doIt = new DoIt();
        doIt.run();
    }

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void getFunction(ActionEvent event) {

    }

    @FXML
    void options(ActionEvent event) {

    }

    @FXML
    void erase(ActionEvent e) {
        functionPanel.getChildren().removeAll(functionPanel.getChildren());
    }

}
