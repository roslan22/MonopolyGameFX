package com.monopoly.view.guiView.controllers;

import javafx.scene.Node;

/**
 *
 * @author Ruslan
 */
public class PlayersPosition {
    private int cell;
    private Node playerIcon;

    public Node getPlayerIcon() {
        return playerIcon;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }
    
    public PlayersPosition(int cell, Node playerIcon) {
        this.cell = cell;
        this.playerIcon = playerIcon;
    }

    void setPlayerIcon(Node playerIcon) {
    this.playerIcon = playerIcon;    
    }
}
