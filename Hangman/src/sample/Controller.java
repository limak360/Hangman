package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    static int i;
    static int okOdp, nieokOdp = 0;
    static int wygrane = 0, przegrane = 0;

    @FXML
    Label label;
    @FXML
    Label odp;
    @FXML
    Label labelScore;
    @FXML
    Label labelHappy;
    @FXML
    Label labelSad;
    @FXML
    ImageView imgHappy;
    @FXML
    ImageView imgSad;
    @FXML
    private ArrayList<Button> buttons;
    @FXML
    private ArrayList<Label> labels;
    @FXML
    private ArrayList<ImageView> fails;
    @FXML
    Button newWord;

    @FXML
    public void onButtonClicked() {
        Platform.exit();
    }

    public String scaner() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("I:\\Java projekty\\Hangman\\src\\sample\\zdania.txt"));
        String sc = "";
        for (int k = 0; k < i + 1; k++)
            if (scanner.hasNextLine()) sc = scanner.nextLine();

        return sc;
    }

    @FXML
    public void onButtonClicked1(ActionEvent event) throws FileNotFoundException {

        String zdanie = scaner();
        boolean flaga1;

        if (event.getSource().equals(newWord)) {
            odp.setVisible(false);
            newWord.setVisible(false);
            okOdp = 0;
            nieokOdp = 0;
            i++;
            labelHappy.setVisible(true);
            labelSad.setVisible(true);
            labelScore.setVisible(true);
            imgHappy.setVisible(true);
            imgSad.setVisible(true);
            for (int i = 0; i < zdanie.length(); i++) labels.get(i).setVisible(false);
            for (int i = 0; i < fails.size(); i++) fails.get(i).setVisible(false);

            zdanie = scaner();
            System.out.println(zdanie);
            String[] podlogi = {"_ _ _", "_ _ _ _", "_ _ _ _ _", "_ _ _ _ _ _", "_ _ _ _ _ _ _", "_ _ _ _ _ _ _ _", "_ _ _ _ _ _ _ _ _", "_ _ _ _ _ _ _ _ _ _"};
            label.setText(podlogi[zdanie.length() - 3]);
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setVisible(true);
                buttons.get(i).setDisable(false);
            }
        } else {
            String[] zdanieArray = zdanie.split("");
            flaga1 = false;
            for (int i = 0; i < zdanie.length(); i++) {
                for (int j = 0; j < buttons.size(); j++) {
                    if (event.getSource().equals(buttons.get(j))) {
                        if (zdanieArray[i].equals(buttons.get(j).getText())) {
                            flaga1 = true;
                            labels.get(i).setVisible(true);
                            labels.get(i).setText(buttons.get(j).getText());
                            buttons.get(j).setDisable(true);
                            System.out.println(buttons.get(j).getText());
                        } else {
                            buttons.get(j).setDisable(true);
                        }
                    }
                }
            }

            if (flaga1 == true) okOdp++;
            else {
                fails.get(nieokOdp).setVisible(true);
                nieokOdp++;
            }

            if ((okOdp == zdanie.length()) && (nieokOdp < 10)) {
                wygrane++;
                labelHappy.setText("" + wygrane);
                // System.out.println("wygrywasz!");
                for (int i = 0; i < buttons.size(); i++) buttons.get(i).setDisable(true);
                newWord.setVisible(true);
            }
            if ((okOdp < zdanie.length()) && (nieokOdp >= 10)) {
                przegrane++;
                labelSad.setText("" + przegrane);
                // System.out.println("przygrywasz!");
                for (int i = 0; i < buttons.size(); i++) buttons.get(i).setDisable(true);
                newWord.setVisible(true);
                odp.setVisible(true);
                odp.setText("Odpowiedz to: " + zdanie);
            }
        }
    }
}