package com.monopoly.view.guiView.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;

public class PropertyCellController extends CellController implements Initializable
{
    @FXML
    Label ownerLabel, groupNameLabel, propertyNameLabel;
    @FXML
    Pane  propertyGroupColor;
    @FXML
    ImageView houseOneImg, houseTwoImg, houseThreeImg, backImg;

    private DrawableProperty drawableProperty;
    private List<ImageView>  houses;

    @Override
    public void setDrawableProperty(DrawableProperty drawableProperty)
    {
        this.drawableProperty = drawableProperty;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        houses = Arrays.asList(houseOneImg, houseTwoImg, houseThreeImg);
        clearHouses();
    }

    private void clearHouses()
    {
        houses.forEach(h -> h.setVisible(false));
    }

    @Override
    public void paint()
    {
        paintGroupName(drawableProperty);
        propertyNameLabel.setText(drawableProperty.getPropertyName());
        ownerLabel.setText(drawableProperty.getOwnerName());
        paintHouses(drawableProperty);
        setTooltip(drawableProperty.getPropertySummary());
        setBackground();
    }

    private void setBackground()
    {
        try
        {
            backImg.setImage(new Image(getClass().getResourceAsStream("boardImages/" + drawableProperty.getGroupName() + ".png")));
            if (!drawableProperty.getPropertyName().equals(""))
                backImg.setOpacity(0.3);
        }catch (Exception ignored){}
    }

    private void paintGroupName(DrawableProperty cell)
    {
        groupNameLabel.setText(cell.getGroupName());
        propertyGroupColor.setBackground(new Background(new BackgroundFill(cell.getGroupColor(), null, null)));
    }

    private void paintHouses(DrawableProperty cell)
    {
        clearHouses();
        IntStream.range(0, cell.getHousesOwned()).forEach(i -> houses.get(i).setVisible(true));
    }


}
