package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        showLotteryStage();

        Parent root = FXMLLoader.load(getClass().getResource("views/TipView.fxml"));
        primaryStage.setTitle("Lottery - Tip");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showLotteryStage() throws Exception
    {
        Stage lotteryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("views/LotteryView.fxml"));
        lotteryStage.setTitle("Lottery");
        lotteryStage.setScene(new Scene(root));
        lotteryStage.setResizable(false);
        lotteryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
