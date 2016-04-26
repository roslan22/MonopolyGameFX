package monopolyBoard.scenes.init;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ruslan
 */
public class BoardSceneController implements Initializable {
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private GridPane gridPaneMain;
    
    @FXML
    private void showMessageOnBoard(ActionEvent event) {
        System.out.println("You clicked me!");
        messageLabel.setText("Hello World! sad sad sad asdasd ads asd sad");
        messageLabel.setTextAlignment(TextAlignment.CENTER);
    }
    
    public final static int FIRST_ROW = 0;
    public final static int LAST_ROW = 9;
    public final static int FIRST_COLUMN = 0;
    public final static int LAST_COLUMN = 9;
    public final static int MAX_PLAYERS_NUM = 6;
    
    private boolean isErrorMessageShown = false;
    private SimpleBooleanProperty finishedInit;
    private ArrayList<Pane> boardCells = new ArrayList<>();
    
    /* private BoardManager boardManager;

    public void setPlayersManager(BoardManager playersManager) {
        this.BoardManager = playersManager;
    }
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        finishedInit = new SimpleBooleanProperty(false);
        initLinesOfCells();
        boardCells.get(0).getChildren().add(new Label("START"));
        addPlayerToBoard(MAX_PLAYERS_NUM);
    }   
    
    public void addPlayerToBoard(int playersNumber)
    {
        for(int i=1; i <= playersNumber; i++)
        {
           boardCells.get(i).getChildren().add(createPlayerIcon(i));
        }
    }

    private ImageView createPlayerIcon(int playerIndex) {
        ImageView playerIcon = new ImageView();
        playerIcon.setId("player" + playerIndex);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }
    
    public SimpleBooleanProperty finishedInit() {
        return finishedInit;
    }

    private void initLinesOfCells() 
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
        if(isCornerCell(currentCellNumber))
        {
           currentPane.setId("cornerCell");
        }
        else
        {
           currentPane.setId("cell");
        }
        boardCells.add(currentCellNumber, currentPane);
        gridPaneMain.add(currentPane, from, to);
        currentPane.getChildren().add(new Label(String.valueOf(currentCellNumber)));
        currentCellNumber++;
        
        return currentCellNumber;
    }

    private static boolean isCornerCell(int currentCellNumber) {
        return currentCellNumber == 0 || currentCellNumber == 9 || currentCellNumber == 18 || currentCellNumber == 27;
    }
    
}
