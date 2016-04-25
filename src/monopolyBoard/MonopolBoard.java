package monopolyBoard;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopolyBoard.scenes.init.GameInitSceneController;
import monopolyBoard.scenes.init.GetNamesSceneController;


public class MonopolBoard extends Application {
    
    private static final String BOARD_SCENE_FXML_PATH = "scenes/init/BoardScene.fxml";
    private static final String GAME_INIT_SCENE_FXML_PATH = "scenes/init/game_init_scene.fxml";
    private static final String GET_NAMES_SCENE_FXML_PATH = "scenes/init/game_init_get_human_names.fxml";

    private Stage primaryStage;

    private File externalXML;
    private int humanPlayers;
    private int computerPlayers;
    
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Monopoly Board");
        primaryStage.setResizable(false);

        showGameInit();
    }

    private void showGameInit()
    {
        FXMLLoader gameInitXMLLoader = getFXMLLoader(GAME_INIT_SCENE_FXML_PATH);
        primaryStage.setScene(new Scene(getRoot(gameInitXMLLoader)));

        GameInitSceneController gameInitController = gameInitXMLLoader.getController();
        gameInitController.setXmlValidator(xml -> false);
        gameInitController.setNextListener(() -> endGameInit(gameInitController));
        primaryStage.show();
    }

    private void endGameInit(GameInitSceneController gameInitController)
    {
        externalXML = gameInitController.getXMLFile();
        humanPlayers = gameInitController.getHumanPlayers();
        computerPlayers = gameInitController.getComputerPlayers();
        askForHumanPlayersNames(gameInitController.getHumanPlayers());
    }

    private void askForHumanPlayersNames(int humanPlayers)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(GET_NAMES_SCENE_FXML_PATH);
        primaryStage.setScene(new Scene(getRoot(getNamesFXMLLoader)));
        GetNamesSceneController getNamesFXMLLoaderController = getNamesFXMLLoader.getController();
        getNamesFXMLLoaderController.setHumanPlayersNumber(humanPlayers);
        getNamesFXMLLoaderController.setGetNamesEndedListener(() -> endGetNames(getNamesFXMLLoaderController.getNames()));
    }

    private void endGetNames(List<String> names)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(BOARD_SCENE_FXML_PATH);
        primaryStage.setScene(new Scene(getRoot(getNamesFXMLLoader)));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private FXMLLoader getFXMLLoader(String fxmlPath)
    {
        return new FXMLLoader(getClass().getResource(fxmlPath));
    }

    private Parent getRoot(FXMLLoader fxmlLoader) {
        try
        {
            return (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalPathStateException(e.getMessage());
        }
    }

}
