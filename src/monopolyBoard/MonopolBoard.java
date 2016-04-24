package monopolyBoard;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monopolyBoard.scenes.init.BoardSceneController;


public class MonopolBoard extends Application {
    
    private static final String BOARD_SCENE_FXML_PATH = "scenes/init/BoardScene.fxml";
    //private BoardManager boardManager;

    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        
        FXMLLoader fxmlLoader = getBoardFXMLLoader();
        Parent boardRoot = getBoardRoot(fxmlLoader);
        BoardSceneController boardController = getBoardSceneController(fxmlLoader, primaryStage);

        Scene scene = new Scene(boardRoot, 896, 560);

        primaryStage.setTitle("Monopoly Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private FXMLLoader getBoardFXMLLoader() 
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(BOARD_SCENE_FXML_PATH);
        fxmlLoader.setLocation(url);
        return fxmlLoader;
    }

    private Parent getBoardRoot(FXMLLoader fxmlLoader) throws IOException {
        return (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());
    }

    private BoardSceneController getBoardSceneController(FXMLLoader fxmlLoader, Stage primaryStage) {
        BoardSceneController boardController = (BoardSceneController) fxmlLoader.getController();
        /* boardController.setBoardManager(boardManager);
           boardController.finishedInit().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                final GameScene gameScene = new GameScene(playersManager);
                primaryStage.setScene(gameScene);
            }
        }); */
        return boardController;    
    }
}
