package com.monopoly.view.guiView.controllers;

import com.monopoly.view.playerDescisions.PlayerBuyAssetDecision;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BoardSceneController implements Initializable {
    
    @FXML
    private GridPane gridPaneMain;
    
    @FXML 
    private VBox vboxPlayers;
    
    @FXML
    private TextArea msgTextArea;
    
    @FXML 
    private Pane promtPane;
    
    @FXML
    private TextArea textAreaPromt;
    
    public final static int FIRST_ROW = 0;
    public final static int LAST_ROW = 9;
    public final static int FIRST_COLUMN = 0;
    public final static int LAST_COLUMN = 9;
    public final static int MAX_PLAYERS_NUM = 6;
    public final static int START_PLACE = 0;
    
    private SimpleBooleanProperty finishedInit;
    private ArrayList<Pane> boardCells = new ArrayList<>();
    private HashMap<String, Node> playersPlaceOnBoard = new HashMap<>();
    private List<String> humanPlayerNames;
    private int computerPlayers = 0;
    private int nextPlayerPlaceIndex = 1;
    public static final ObservableList allGamePlayers = 
        FXCollections.observableArrayList();
    private PlayerBuyAssetDecision playerBuyAssetDecision;
    private int waitingForAnswerEventId = 0;
    
    @FXML
    private void onYesClicked()
    {
        hidePromtPane();
        playerBuyAssetDecision.onAnswer(waitingForAnswerEventId, true);
    }
    
    @FXML
    private void onNoClicked()
    {
        hidePromtPane();
        playerBuyAssetDecision.onAnswer(waitingForAnswerEventId, false);
    }
    
    @FXML
    private void onResignClicked()
    {
        //TODO: Implement
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        finishedInit = new SimpleBooleanProperty(false);
        inintBoardCells();
        boardCells.get(0).getChildren().add(new Label("START"));
    }   
    
    public void setPlayers(List<String> playerNames, int humanPlayers, int computerPlayers)
    {
         this.humanPlayerNames = playerNames;
         this.computerPlayers = computerPlayers;
         initPlayersOnBoard(humanPlayers + computerPlayers);
    }
    
    public void initPlayersOnBoard(int playersNumber)
    {
        addHumanPlayers();     
        addComputerPlayers();   
        createRightTopPlayersMenu();
    }

    private void createRightTopPlayersMenu() {
        int playerId=1;
        
        for(String name : playersPlaceOnBoard.keySet())
        {
            HBox hbox = createPlayerHbox(playerId, name);
            vboxPlayers.getChildren().add(hbox);
            playerId++;
        }
    }

    private HBox createPlayerHbox(int playerId, String name) {
        ImageView playerIcon;
        HBox hbox = new HBox();
        playerIcon = setPropertiesToPlayerIcon(playerId);
        hbox.getChildren().add(playerIcon);
        hbox.getChildren().add(new Label(name));
        return hbox;
    }

    private ImageView setPropertiesToPlayerIcon(int playerId) {
        ImageView playerIcon;
        playerIcon = new ImageView();
        playerIcon.setId("player" + playerId);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }

    private void addHumanPlayers() 
    {   
        for(String name :this.humanPlayerNames)
        {
            allGamePlayers.add(name);
            placePlayerOnBoard(name, START_PLACE);
        }
    }
    
    
    private void addComputerPlayers() 
    {
        for(int i=1; i <= computerPlayers; i++)
        {
            String name = "Computer" + i;
            allGamePlayers.add(name);
            placePlayerOnBoard(name, START_PLACE);
        }
    }
    
    private void placePlayerOnBoard(String playerName, int placeIndex)
    {
        playersPlaceOnBoard.put(playerName, createPlayerIcon(nextPlayerPlaceIndex));
        boardCells.get(placeIndex).getChildren().add(playersPlaceOnBoard.get(playerName));
        nextPlayerPlaceIndex++;
    }

    private ImageView createPlayerIcon(int playerIndex) 
    {
        ImageView playerIcon = new ImageView();
        playerIcon.setId("player" + playerIndex);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }
    
    public SimpleBooleanProperty finishedInit() {
        return finishedInit;
    }

    private void inintBoardCells() 
    {
       int currentCellNumber = 0;
       currentCellNumber = initBottomLineCells(currentCellNumber);
       currentCellNumber = initLeftLineCells(currentCellNumber);
       currentCellNumber = initTopLineCells(currentCellNumber);
       initRightLineCells(currentCellNumber);
    }

    private void initRightLineCells(int currentCellNumber) 
    {
        for(int row = FIRST_ROW; row < LAST_ROW; row++ )
        {   
            currentCellNumber = addNewPane(currentCellNumber, LAST_COLUMN, row);
        }
    }

    private int initTopLineCells(int currentCellNumber) 
    {
        for(int column = FIRST_COLUMN; column < LAST_COLUMN; column++ )
        {
             currentCellNumber = addNewPane(currentCellNumber, column, FIRST_ROW);
        }
        return currentCellNumber;
    }
    
    
    private int initLeftLineCells(int currentCellNumber) {        
        for(int row = LAST_ROW - 1; row > 0; row-- )
        {
            currentCellNumber = addNewPane(currentCellNumber, FIRST_COLUMN, row);

        }
        return currentCellNumber;
    }

    private int initBottomLineCells(int currentCellNumber) {

        for(int column = LAST_COLUMN; column>=0; column-- )
        {
            currentCellNumber = addNewPane(currentCellNumber, column, LAST_ROW);            
        }
        
        return currentCellNumber;
    }
    
    private int addNewPane(int currentCellNumber, int from, int to) 
    {
        Pane currentPane;
        currentPane = new Pane();
        
        setCellId(currentCellNumber, currentPane);
        boardCells.add(currentCellNumber, currentPane);
        gridPaneMain.add(currentPane, from, to);
        currentPane.getChildren().add(new Label(String.valueOf(currentCellNumber)));
        currentCellNumber++;
        
        return currentCellNumber;
    }

    private void setCellId(int currentCellNumber, Pane currentPane) 
    {
        if(isCornerCell(currentCellNumber))
            currentPane.setId("cornerCell");
        else
            currentPane.setId("cell");
    }

    private static boolean isCornerCell(int currentCellNumber) {
        return currentCellNumber == 0 || currentCellNumber == 9 || currentCellNumber == 18 || currentCellNumber == 27;
    }

    public void movePlayerIcon(int cell, String PlayerName)
    {
        Node playerIcon = playersPlaceOnBoard.get(PlayerName);
        
        if(playerIcon != null)
        {            
            addPlayerIconToBoard(cell, playerIcon);
        }
    }

    private void addPlayerIconToBoard(int cell, Node playerIcon) {
        if(!boardCells.get(cell).getChildren().contains(playerIcon))
        {
            boardCells.get(cell).getChildren().add(playerIcon);
        }
    }
    
    public void showMessage(String message)
    {
      String prevText = msgTextArea.getText();
      msgTextArea.setText(prevText + "\n" + message);
    }
    
    public void promtPlayer(String text, PlayerBuyAssetDecision playersDecision, int eventID)
    {
        this.playerBuyAssetDecision = playersDecision;
        textAreaPromt.setText(text);
        this.waitingForAnswerEventId = eventID;
        showPromtPane();
    }

    private void hidePromtPane() 
    {
      promtPane.setVisible(false);
    }
    
    private void showPromtPane()
    {
      promtPane.setVisible(true);
    }
}
