package com.monopoly.view.guiView.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CornerCellController extends CellController
{
    @FXML
    Label cellNameLabel;

    @Override
    public void setDrawableProperty(DrawableProperty drawableProperty)
    {
        cellNameLabel.setText(drawableProperty.getPropertyName());
    }
}
