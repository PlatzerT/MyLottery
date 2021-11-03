package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.models.Lottery;
import sample.models.LotteryTip;

import java.util.HashSet;
import java.util.Set;

public class LotteryController
{

    Lottery lottery = Lottery.getInstance();

    @FXML
    public Button evaluateButton;

    @FXML
    public Button generateButton;

    @FXML
    public Label lotteryNumbersLabel;

    @FXML
    public TableView<LotteryTip> tipsTable;

    @FXML
    public TableColumn<LotteryTip, Integer> pId;

    @FXML
    public TableColumn<LotteryTip, String> pName;

    @FXML
    public TableColumn<LotteryTip, Set<Integer>> pTip;

    @FXML
    public TableView<LotteryTip> winnersTable;

    @FXML
    public TableColumn<LotteryTip, Integer> wId;

    @FXML
    public TableColumn<LotteryTip, String> wName;

    @FXML
    public TableColumn<LotteryTip, Set<Integer>> wTip;

    @FXML
    public TableColumn<LotteryTip, Integer> wCorrectGuesses;

    @FXML
    void initialize() {
        // participants table columns
        pId.setCellValueFactory(new PropertyValueFactory<LotteryTip, Integer>("id"));
        pName.setCellValueFactory(new PropertyValueFactory<LotteryTip, String>("name"));
        pTip.setCellValueFactory(new PropertyValueFactory<LotteryTip, Set<Integer>>("tipNumbers"));

        // winners table columns
        wId.setCellValueFactory(new PropertyValueFactory<LotteryTip, Integer>("id"));
        wName.setCellValueFactory(new PropertyValueFactory<LotteryTip, String>("name"));
        wTip.setCellValueFactory(new PropertyValueFactory<LotteryTip, Set<Integer>>("tipNumbers"));
        wCorrectGuesses.setCellValueFactory(new PropertyValueFactory<LotteryTip, Integer>("correctGuesses"));

        // bind table data to observables
        tipsTable.setItems(lottery.getParticipants());
        winnersTable.setItems(lottery.getWinners());
    }

    public void handleGenerateNumbers(ActionEvent actionEvent)
    {
        lottery.clearNumbers();
        // lottery.generateTestNumbers();
        lottery.generateNumbers();
        Set<Integer> numbers = lottery.getNumbers();
        lotteryNumbersLabel.setText(numbers.toString());
        evaluateButton.setVisible(true);
        generateButton.setVisible(false);
    }

    public void handleEvaluateWinners(ActionEvent actionEvent)
    {
        lottery.evaluateWinners();
        generateButton.setVisible(true);
        evaluateButton.setVisible(false);
    }

    public void handleReset(ActionEvent actionEvent) {
        lottery.reset();
        lotteryNumbersLabel.setText("");
    }
}
