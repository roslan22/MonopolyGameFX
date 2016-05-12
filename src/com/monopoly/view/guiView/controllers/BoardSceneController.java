package com.monopoly.view.guiView.controllers;

import com.monopoly.view.playerDescisions.PlayerBuyAssetDecision;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class BoardSceneController implements Initializable {

    public static final String CELL_RIGHT_FXML = "cell_right.fxml";
    public static final String CELL_LEFT_FXML = "cell_left.fxml";
    public static final String CELL_FXML = "cell.fxml";
    public static final String CELL_CORNER_FXML = "cell_corner.fxml";
    public static final int INIT_MONEY = 1500;
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
    public final static int MAX_BOARD_CELLS = 36;
    public static final ObservableList allGamePlayers = FXCollections.observableArrayList();

    private SimpleBooleanProperty finishedInit;
    private List<Pane> boardCells = new ArrayList<>();
    private Map<String, PlayerPosition> playersPlaceOnBoard = new TreeMap<>();
    private Map<String, String> playerNamesAndIds = new HashMap<>();
    private Map<String, Integer> playerNameToMoney = new HashMap<>();
    private List<String> humanPlayerNames;
    private int computerPlayers = 0;
    private int nextPlayerPlaceIndex = 1;
    private PlayerBuyAssetDecision playerBuyAssetDecision;
    private int                          waitingForAnswerEventId = 0;
    private Timeline                     timeline                = new Timeline();
    private SequentialTransition         seqTransition           = new SequentialTransition();
    private boolean                      isAnimationsFinished    = true;
    private Queue<PlayerPosition>        playersMoves            = new LinkedList<>();
    private List<CellController> cellControllers         = new ArrayList<>();

    @FXML
    private void onYesClicked()
    {
        hidePromptPane();
        playerBuyAssetDecision.onAnswer(waitingForAnswerEventId, true);
    }
    
    @FXML
    private void onNoClicked()
    {
        hidePromptPane();
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
        initBoardCells();
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

    private void createRightTopPlayersMenu()
    {
        vboxPlayers.getChildren().clear();
        for(String name : playersPlaceOnBoard.keySet())
        {
            HBox hbox = createPlayerHbox(name);
            vboxPlayers.getChildren().add(hbox);
        }
    }

    private HBox createPlayerHbox(String name) 
    {
        HBox hbox = new HBox();
        hbox.getChildren().add(setPropertiesToPlayerIcon(playerNamesAndIds.get(name)));
        hbox.getChildren().add(new Label(name));
        hbox.getChildren().add(new Label(" has ₪" + playerNameToMoney.get(name)));
        return hbox;
    }

    private ImageView setPropertiesToPlayerIcon(String playerId) 
    {
        ImageView playerIcon;
        playerIcon = new ImageView();
        playerIcon.setId(playerId);
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
        String playerID; 
        playerID = "player" + nextPlayerPlaceIndex;
        
        playersPlaceOnBoard.put(playerName, createPlayerPosition(placeIndex, playerID));
        playerNamesAndIds.put(playerName, playerID);
        playerNameToMoney.put(playerName, INIT_MONEY);
        Node playerIcon = playersPlaceOnBoard.get(playerName).getPlayerIcon();
        boardCells.get(placeIndex).getChildren().add(playerIcon);
        nextPlayerPlaceIndex++;
    }

    private PlayerPosition createPlayerPosition(int placeIndex, String playerID) {
        return new PlayerPosition(placeIndex, createPlayerIcon(playerID));
    }

    private ImageView createPlayerIcon(String playerID) 
    {
        ImageView playerIcon = new ImageView();
        playerIcon.setId(playerID);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }
    
    public SimpleBooleanProperty finishedInit() {
        return finishedInit;
    }

    private void initBoardCells()
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
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);

        setCellId(currentCellNumber, flowPane);
        boardCells.add(currentCellNumber, flowPane);
        gridPaneMain.add(flowPane, from, to);

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

    public void movePlayer(int cell, String PlayerName)
    {   
        PlayerPosition playerPos = playersPlaceOnBoard.get(PlayerName);
        Node playerIcon = playerPos.getPlayerIcon();
        int currentCell = playerPos.getCell();
        int cellToMove;
        
        if(playerIcon != null)
        {   
            cellToMove = calculateCellToMove(cell,currentCell);
            addPlayerIconToBoard(cellToMove, playerIcon);
            setPlayerPosition(playerPos, cellToMove, playerIcon);
        }      
        
        System.out.println("currently moved player " + PlayerName + "to cell: " + cell
        + "in scene thread: " + Thread.currentThread().getId());
    }

    private void setPlayerPosition(PlayerPosition playerPos, int cellToMove, Node playerIcon) {
        playerPos.setCell(cellToMove);
        playerPos.setPlayerIcon(playerIcon);
    }

    private void addPlayerIconToBoard(int cell, Node playerIcon) 
    {    
        removePlayerIconFromBoard(playerIcon);   
        if(!boardCells.get(cell).getChildren().contains(playerIcon))
        {
            playersMoves.add(new PlayerPosition(cell, playerIcon));
        }

    }

    private FadeTransition createIconsFadeTransition(Node playerIcon) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), playerIcon);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        return ft;
    }

    private void removePlayerIconFromBoard(Node playerIcon) {
        cellControllers.stream().forEach(c -> c.removePlayer(playerIcon));
    }
    
    public void showMessage(String message)
    {
      String prevText = msgTextArea.getText();
      msgTextArea.setText(prevText + "\n" + message);
    }
    
    public void promptPlayer(String text, PlayerBuyAssetDecision playersDecision, int eventID)
    {
        this.playerBuyAssetDecision = playersDecision;
        textAreaPromt.setText(text);
        this.waitingForAnswerEventId = eventID;
        repaint();
        startFadeAnimations();
    }

    private void startFadeAnimations() {
        for(PlayerPosition playerMovePosition : playersMoves)
        {
            FadeTransition ft = createIconsFadeTransition(playerMovePosition.getPlayerIcon());
            seqTransition.getChildren().add(ft);
            ft.setOnFinished((ActionEvent actionEvent) -> {
                cellControllers.get(playerMovePosition.getCell()).addPlayer(playerMovePosition.getPlayerIcon());
                playerMovePosition.getPlayerIcon().setOpacity(1.0);
            });
        }
        
        playersMoves.clear();
        seqTransition.setCycleCount(1);

        if(isAnimationsFinished)
        {
            seqTransition.play();
            isAnimationsFinished = false;
        }
        
        seqTransition.onFinishedProperty().set((ActionEvent actionEvent) ->
        {
              seqTransition = new SequentialTransition();
              System.out.println("ok goood - seq animations finished");
              isAnimationsFinished = true;
              showPromptPane();
        });
    }

    private void hidePromptPane()
    {
      promtPane.setVisible(false);
    }
    
    private void showPromptPane()
    {
      promtPane.setVisible(true);
    }

    public void initCellsNames(List<? extends DrawableProperty> drawableCells)
    {
        for(int i=0; i < drawableCells.size(); i++)
        {
            DrawableProperty drawableProperty = drawableCells.get(i);
            if (i % 9 == 0)
            {
                addCellFxml(i, drawableProperty, CELL_CORNER_FXML);
            }
            else if ((0 <= i && i <= 9) || (18 <= i && i <= 27))
            {
                addCellFxml(i, drawableProperty, CELL_FXML);
            }
            else if (9 <= i && i <= 18)
            {
                addCellFxml(i, drawableProperty, CELL_LEFT_FXML);
            }
            else
            {
                addCellFxml(i, drawableProperty, CELL_RIGHT_FXML);
            }
            repaint();
        }
    }

    private void addCellFxml(int cellIndex, DrawableProperty drawableProperty, String cellFxml)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(cellFxml);
        Parent root = getRoot(getNamesFXMLLoader);
        ((CellController) getNamesFXMLLoader.getController()).setDrawableProperty(drawableProperty);
        cellControllers.add(getNamesFXMLLoader.getController());
        boardCells.get(cellIndex).getChildren().add(root);
    }

    private void repaint()
    {
        cellControllers.forEach(CellController::paint);
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

    private int calculateCellToMove(int cell, int currentCell) 
    {
        if(cell + currentCell >= MAX_BOARD_CELLS)
        {
            return (cell + currentCell - MAX_BOARD_CELLS);
        }

        return cell + currentCell;
    }

    public void updateMoney(String fromPlayerName, String toPlayerName, int paymentAmount)
    {
        playerNameToMoney.computeIfPresent(fromPlayerName, (k, v) -> v - paymentAmount);
        playerNameToMoney.computeIfPresent(toPlayerName, (k, v) -> v + paymentAmount);
        createRightTopPlayersMenu();
    }
}
