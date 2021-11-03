package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import sample.models.Lottery;
import sample.models.LotteryTip;

import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class TipController {

    private final int SUCCESS_MSG_DURATION = 2000;  // ms

    @FXML
    public TextField tipInput;

    @FXML
    public TextField nameInput;

    @FXML
    public Button tipButton;

    @FXML
    public Label errorMessage;

    @FXML
    public Label successMessage;

    @FXML
    void initialize(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            // allow only numbers and spaces
            if (text.matches("[0-9 ]*")){
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        tipInput.setTextFormatter(textFormatter);
        tipInput.setPromptText("z.B. 32 11 5 19 1 24");
        nameInput.setPromptText("Max Mustermann");

        // remove auto-focus on initial run
        tipInput.setFocusTraversable(false);
        nameInput.setFocusTraversable(false);
    }

    public void handleTipSubmit(ActionEvent actionEvent) {

        // check if inputs are not empty
        if (tipInput.getText().equals("") || nameInput.getText().equals("")){
            return;
        }

        String[] arr = tipInput.getText().split(" ");

        // filter and parse tip numbers
        Set<Integer> temp = Arrays.stream(arr)
                .filter(c -> !c.equals(""))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toSet());

        SortedSet<Integer> tipNumbers = new TreeSet<>(temp);

        if (tipNumbers.size() != Lottery.AMOUNT_OF_NUMBERS) {
            errorMessage.setVisible(true);
            errorMessage.setText("● " + Lottery.AMOUNT_OF_NUMBERS + " Zahlen und keine Duplikate!");
            return;
        }

        LotteryTip lotteryParticipant = new LotteryTip(tipNumbers, nameInput.getText());
        Lottery lottery = Lottery.getInstance();
        lottery.addParticipant(lotteryParticipant);
        errorMessage.setVisible(false);

        // run the operations to show success message in separate thread so the whole UI does not sleep while waiting
        // for the success message to disappear
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                successMessage.setVisible(true);
                try
                {
                    Thread.sleep(SUCCESS_MSG_DURATION);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                successMessage.setVisible(false);
            }
        }).start();

        System.out.println(lottery);

        // clear the input
        tipInput.setText("");
        nameInput.setText("");
    }
}
