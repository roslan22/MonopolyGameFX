package com.monopoly.controller;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.view.View;

import java.util.List;

public class Controller
{
    private View   view;
    private Engine engine;
    private int lastEvent = 0;

    public static       String GAME_NAME            = "Monopoly";
    public static final int    MAXIMUM_GAME_PLAYERS = 6;
    public static       int    DUMMY_PLAYER_ID      = 1;
    public static       String DEFAULT_XML_PATH  = "configs/monopoly_config.xml";
    private Event[] events;

    public Controller(View view, MonopolyEngine engine)
    {
        this.view = view;
        this.engine = engine;
        initView(view);
    }

    private void initView(View view)
    {
        view.setPlayerBuyHouseDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerBuyAssetDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerResign(() -> resign(DUMMY_PLAYER_ID));
    }

    public void play()
    {
        initGame();
        runEventsLoop();
    }
    
    public void continueGameAfterPromt()
    {
        runEventsLoop();
    }
    
    private void runEventsLoop() 
    {
        events = engine.getEvents(DUMMY_PLAYER_ID, lastEvent);
        while (events.length != 0)
        {
            lastEvent = events[events.length - 1].getEventID();
            //NEXT TWO LINES FOR EX. 3
            // events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player));
            // lastReceivedEventIds.replace(player, events[events.length-1].getEventID());
            view.showEvents(events);
            events = engine.getEvents(DUMMY_PLAYER_ID, lastEvent);
        }
    }
        
    private void initGame()
    {
        initBoard();
        createPlayers();
    }

    private void initBoard()
    {
        String xmlPath;
        xmlPath = view.loadExternalXmlPath();
        
        if(xmlPath == null || xmlPath.isEmpty())
        {
            xmlPath = DEFAULT_XML_PATH;
        }      
        
        tryToLoadBoardFromXML(xmlPath);
    }

    private void tryToLoadBoardFromXML(String xmlPath) {
        try
        {
            engine.initializeBoard(new XmlMonopolyInitReader(xmlPath));
            view.setCellsNames(engine.getBoardCells());
            System.out.println("Configurations XML loaded from: " + xmlPath);
        } catch (CouldNotReadMonopolyInitReader couldNotReadMonopolyInitReader)
        {
            System.out.println(couldNotReadMonopolyInitReader.getMessage());
            initBoard();
        }
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber(MAXIMUM_GAME_PLAYERS);
        int computerPlayersNumber = view.getComputerPlayersNumber(MAXIMUM_GAME_PLAYERS - humanPlayersNumber);

        engine.createGame(GAME_NAME, computerPlayersNumber, humanPlayersNumber);
        addHumanPlayersNames(view.getDistinctHumanPlayerNames(humanPlayersNumber));
    }

    private void addHumanPlayersNames(List<String> humanPlayersNames)
    {
        humanPlayersNames.forEach(p -> engine.joinGame(GAME_NAME, p));
    }

    private void buy(int playerID, int eventID, boolean answer)
    {
        engine.buy(playerID, eventID, answer);
        continueGameAfterPromt();
    }

    private void resign(int playerID)
    {
        engine.resign(playerID);
    }

}
