package com.monopoly.view;

import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventType;
import com.monopoly.view.playerDescisions.PlayerBuyAssetDecision;
import com.monopoly.view.playerDescisions.PlayerBuyHouseDecision;
import com.monopoly.view.playerDescisions.PlayerResign;

import java.util.Arrays;
import java.util.List;

public abstract class View
{
    protected PlayerBuyHouseDecision playerBuyHouseDecision;
    protected PlayerBuyAssetDecision playerBuyAssetDecision;
    protected PlayerResign           playerResign;

    public void setPlayerResign(PlayerResign playerResign)
    {
        this.playerResign = playerResign;
    }

    public void setPlayerBuyHouseDecision(PlayerBuyHouseDecision playerBuyHouseDecision)
    {
        this.playerBuyHouseDecision = playerBuyHouseDecision;
    }

    public void setPlayerBuyAssetDecision(PlayerBuyAssetDecision playerBuyAssetDecision)
    {
        this.playerBuyAssetDecision = playerBuyAssetDecision;
    }

    public abstract String loadExternalXmlPath();

    public abstract int getHumanPlayersNumber(int maximumAllowed);

    public abstract int getComputerPlayersNumber(int maximumAllowed);

    public abstract List<String> getDistinctHumanPlayerNames(int humanPlayersNumber);

    public void init()
    {
    }

    public void showEvents(Event[] events)
    {
        Arrays.stream(events).forEach((event) -> this.showEvent(event));
    }

    private void showEvent(Event event)
    {
        EventType eventType = event.getEventType();

        switch (eventType)
        {
            case GAME_START:
                showGameStartedMsg();
                break;
            case GAME_OVER:
                showGameOverMsg();
                break;
            case MOVE:
                showPlayerMove(event);
                break;
            case DICE_ROLL:
                showDiceRollResult(event);
                break;
            case GAME_WINNER:
                showGameWinner(event);
                break;
            case PROMPT_PLAYER_TO_BUY_ASSET:
                promptPlayerToBuy(event);
                break;
            case PROMPT_PLAYER_TO_BUY_HOUSE:
                promptPlayerToBuy(event);
                break;
            case GET_OUT_OF_JAIL_CARD:
                showOutOfJailCard(event);
                break;
            case GO_TO_JAIL:
                showGoToJailMsg(event);
                break;
            case ASSET_BOUGHT_MESSAGE:
                showAssetBoughtMsg(event);
                break;
            case HOUSE_BOUGHT_MESSAGE:
                showHouseBoughtMsg(event);
                break;
            case LANDED_ON_START_SQUARE:
                showLandedOnStartSquareMsg(event);
                break;
            case PASSED_START_SQUARE:
                showPassedStartSquareMsg(event);
                break;
            case PAYMENT:
                showPaymentMsg(event);
                break;
            case PLAYER_LOST:
                showPlayerLostMsg(event);
                break;
            case PLAYER_RESIGNED:
                showPlayerResignMsg(event);
                break;
            case PLAYER_USED_OUT_OF_JAIL_CARD:
                showUsedOutOfJailCardMsg(event);
                break;
            case SURPRISE_CARD:
                showSurpriseCardMsg(event);
                break;
            case WARRANT_CARD:
                showWarrantCardMsg(event);
                break;
            default:
                unknownEvent();
        }
    }

    protected abstract void showPlayerMove(Event event);

    protected abstract void promptPlayerToBuy(Event event);

    protected abstract void showGameStartedMsg();

    protected abstract void showGameOverMsg();

    protected abstract void showDiceRollResult(Event event);

    protected abstract void showGameWinner(Event event);

    protected abstract void showAssetBoughtMsg(Event event);

    protected abstract void showHouseBoughtMsg(Event event);

    protected abstract void showLandedOnStartSquareMsg(Event event);

    protected abstract void showPassedStartSquareMsg(Event event);

    protected abstract void showPaymentMsg(Event event);

    protected abstract void showPlayerLostMsg(Event event);

    protected abstract void showPlayerResignMsg(Event event);

    protected abstract void showUsedOutOfJailCardMsg(Event event);

    protected abstract void showSurpriseCardMsg(Event event);

    protected abstract void showWarrantCardMsg(Event event);

    protected abstract void showOutOfJailCard(Event event);

    protected abstract void showGoToJailMsg(Event event);

    public abstract void setCellsNames(List<String> boardCellsNames);

    private void unknownEvent()
    {
    }

}
