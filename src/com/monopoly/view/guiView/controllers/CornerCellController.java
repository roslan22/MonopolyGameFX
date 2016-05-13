package com.monopoly.view.guiView.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CornerCellController extends CellController
{
    @FXML
    Label     cellNameLabel;
    @FXML
    ImageView backImg;

    @Override
    public void setDrawableProperty(DrawableProperty drawableProperty)
    {
        setBackground(drawableProperty);
    }

    public void setBackground(DrawableProperty drawableProperty)
    {
        if (backImg.getImage() != null)
            return;

        try
        {
            backImg.setImage(new Image(getClass().getResourceAsStream("boardImages/" + drawableProperty.getPropertyName() + ".png")));
        }catch (Exception ignored){}
    }
}
