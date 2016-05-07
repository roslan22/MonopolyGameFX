package com.monopoly.view.guiView;

import com.monopoly.logic.events.Event;
import com.monopoly.view.View;

import java.io.File;
import java.util.List;

public class GuiView extends View
{
    private MonopolBoard monopolBoard = new MonopolBoard();

    public GuiView(MonopolBoard monopolBoard)
    {
        this.monopolBoard = monopolBoard;
    }

    @Override
    public void init()
    {
        monopolBoard.showGameInit();
    }

    @Override
    public String loadExternalXmlPath()
    {
        File externalXML = monopolBoard.getExternalXML();
        return externalXML != null ? externalXML.getAbsolutePath() : null;
    }

    @Override
    public int getHumanPlayersNumber(int maximumAllowed)
    {
        return monopolBoard.getHumanPlayers();
    }

    @Override
    public int getComputerPlayersNumber(int maximumAllowed)
    {
        return monopolBoard.getComputerPlayers();
    }

    @Override
    public List<String> getDistinctHumanPlayerNames(int humanPlayersNumber)
    {
        return monopolBoard.getHumanPlayersNames();
    }

    @Override
    protected void showPlayerMove(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void promptPlayerToBuy(Event event)
    {
      monopolBoard.promptPlayerToBuy(event.getEventMessage(), playerBuyAssetDecision,
                                     event.getEventID());
    }

    @Override
    protected void showGameStartedMsg()
    {
        monopolBoard.showMessageToPlayer("Game Started");

    }

    @Override
    protected void showGameOverMsg()
    {
        monopolBoard.showMessageToPlayer("Game Over");
    }

    @Override
    protected void showDiceRollResult(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
        System.out.println(event.getPlayerName() + " move " + (event.getFirstDiceResult() + event.getSecondDiceResult()));
        monopolBoard.movePlayer(event.getFirstDiceResult() + event.getSecondDiceResult(),
                event.getPlayerName());
    }

    @Override
    protected void showGameWinner(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showAssetBoughtMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showHouseBoughtMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showLandedOnStartSquareMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPassedStartSquareMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPaymentMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPlayerLostMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showPlayerResignMsg(Event event)
    {
         monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showUsedOutOfJailCardMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showSurpriseCardMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showWarrantCardMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showOutOfJailCard(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    @Override
    protected void showGoToJailMsg(Event event)
    {
        monopolBoard.showMessageToPlayer(event.getEventMessage());
    }

    public static Boolean isNewGameRequired()
    {
        return false;
    }

    @Override
    public void setCellsNames(List<String> boardCellsNames) 
    {
        monopolBoard.setCellsNames(boardCellsNames);
    }
}
