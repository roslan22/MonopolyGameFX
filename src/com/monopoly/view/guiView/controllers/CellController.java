package com.monopoly.view.guiView.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

public class CellController implements Initializable
{
    @FXML
    FlowPane playersPane;

    public void addPlayer(Node player)
    {
        playersPane.getChildren().add(player);
    }

    public void removePlayer(Node player)
    {
        playersPane.getChildren().remove(player);
    }

    public void paint()
    {
    }

    public void setDrawableProperty(DrawableProperty drawableProperty)
    {}

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
