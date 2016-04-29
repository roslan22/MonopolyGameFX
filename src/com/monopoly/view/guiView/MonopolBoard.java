package com.monopoly.view.guiView;

import com.monopoly.controller.Controller;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.view.consoleView.ConsoleView;
import com.monopoly.view.guiView.controllers.BoardSceneController;
import com.monopoly.view.guiView.controllers.GameInitSceneController;
import com.monopoly.view.guiView.controllers.GetNamesSceneController;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MonopolBoard extends Application {
    
    private static final String BOARD_SCENE_FXML_PATH = "../res/scenes/BoardScene.fxml";
    private static final String GAME_INIT_SCENE_FXML_PATH = "../res/scenes/game_init_scene.fxml";
    private static final String GET_NAMES_SCENE_FXML_PATH = "../res/scenes/game_init_get_human_names.fxml";

    private Stage primaryStage;

    private File externalXML;
    private int humanPlayers;
    private int computerPlayers;
    private List<String> humanPlayersNames = new ArrayList<>();

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Monopoly");
        primaryStage.setResizable(false);
        startGame();
    }

    public File getExternalXML()
    {
        return externalXML;
    }

    public int getHumanPlayers()
    {
        return humanPlayers;
    }

    public int getComputerPlayers()
    {
        return computerPlayers;
    }

    public void showGameInit()
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
        Parent root = getRoot(getNamesFXMLLoader);
        GetNamesSceneController getNamesFXMLLoaderController = getNamesFXMLLoader.getController();
        getNamesFXMLLoaderController.setHumanPlayersNumber(humanPlayers);
        humanPlayersNames = getNamesFXMLLoaderController.getNames();
        showNextScene(humanPlayers, root, getNamesFXMLLoaderController);
    }

    private void showNextScene(int humanPlayers, Parent root, GetNamesSceneController getNamesFXMLLoaderController)
    {
        if (humanPlayers != 0)
        {
            primaryStage.setScene(new Scene(root));
            getNamesFXMLLoaderController.setGetNamesEndedListener(() -> endGetNames(getNamesFXMLLoaderController.getNames()));
        }
        else
            endGetNames(getNamesFXMLLoaderController.getNames());
    }

    private void endGetNames(List<String> names)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(BOARD_SCENE_FXML_PATH);
        Parent root = getRoot(getNamesFXMLLoader);
        BoardSceneController boardSceneController = getNamesFXMLLoader.getController();
        
        boardSceneController.setPlayers(names, humanPlayers, computerPlayers);
        primaryStage.setScene(new Scene(root));
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

    public static void main(String[] args)
    {
        launch(args);
    }

    private void startGame()
    {
        Boolean isNewGameRequired = true;
        while(isNewGameRequired)
        {
            playMonopoly();
            isNewGameRequired = ConsoleView.isNewGameRequired();
        }
    }

    private void playMonopoly()
    {
        try
        {
            startController();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            playMonopoly();
        }
    }

    private void startController()
    {
        Controller controller = new Controller(new GuiView(this), new MonopolyEngine());
        controller.play();
    }

    public List<String> getHumanPlayersNames()
    {
        return humanPlayersNames;
    }
}
