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

    }

    @Override
    protected void promptPlayerToBuy(Event event)
    {

    }

    @Override
    protected void showGameStartedMsg()
    {

    }

    @Override
    protected void showGameOverMsg()
    {

    }

    @Override
    protected void showDiceRollResult(Event event)
    {

    }

    @Override
    protected void showGameWinner(Event event)
    {

    }

    @Override
    protected void showAssetBoughtMsg(Event event)
    {

    }

    @Override
    protected void showHouseBoughtMsg(Event event)
    {

    }

    @Override
    protected void showLandedOnStartSquareMsg(Event event)
    {

    }

    @Override
    protected void showPassedStartSquareMsg(Event event)
    {

    }

    @Override
    protected void showPaymentMsg(Event event)
    {

    }

    @Override
    protected void showPlayerLostMsg(Event event)
    {

    }

    @Override
    protected void showPlayerResignMsg(Event event)
    {

    }

    @Override
    protected void showUsedOutOfJailCardMsg(Event event)
    {

    }

    @Override
    protected void showSurpriseCardMsg(Event event)
    {

    }

    @Override
    protected void showWarrantCardMsg(Event event)
    {

    }

    @Override
    protected void showOutOfJailCard(Event event)
    {

    }

    @Override
    protected void showGoToJailMsg(Event event)
    {

    }

    public static Boolean isNewGameRequired()
    {
        return false;
    }
}
