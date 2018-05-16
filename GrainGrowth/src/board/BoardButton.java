package board;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class BoardButton extends Button
{

    public static final int STATE_INACTIVE = 0;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_COLORED = 2;
    private int state;

    private String styleClassDefault;   //css domyślnie

    private String styleClassActive;    //css dla aktywnego
    private String colorStyle;
    private String colorStyleGreen = "green";
    private String colorStyleRed = "red";
    private String colorStyleLightblue = "lightblue";
    private String colorStyleViolet = "violet";
    private String colorStylePink = "pink";
    private String colorStyleYellow = "yellow";
    private String colorStyleOrange = "orange";

    private ArrayList<String> buttonColors = new ArrayList<>();


    enum colors{
       GREEN,
       RED,
       VIOLET,
       PINK,
       ORANGE,
       YELLOW,
        LIGHTBLUE
}

    public BoardButton()
    {
        super();
        this.setStyleClassDefault("btn-board");
        this.setStyleClassActive("active");
        this.setState(STATE_INACTIVE);
    }

    public BoardButton(String styleClassDefault, String styleClassActive)
    {
        super();
        this.setStyleClassDefault(styleClassDefault);
        this.setStyleClassActive(styleClassActive);
        this.setState(STATE_INACTIVE);
    }

    public void refreshStyleClass()
    {
        ObservableList<String> styleClass = this.getStyleClass();
        styleClass.remove("button");

        switch (this.getState()) {
            case STATE_ACTIVE:
                styleClass.addAll(this.getStyleClassDefault(), this.getStyleClassActive());
                break;
            case STATE_COLORED:
                styleClass.addAll(this.getStyleClassDefault(), this.getColorStyle());
                break;
            case STATE_INACTIVE:
            default:
                styleClass.add(this.getStyleClassDefault());
                styleClass.remove(this.getStyleClassActive());
                break;
        }
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        if (state < STATE_INACTIVE || state > STATE_COLORED) {
            throw new IllegalArgumentException("unknown state");
        }

        this.state = state;
        //System.out.println(this.state);
        this.refreshStyleClass();
    }

    public boolean isActive()
    {
        return this.state == STATE_ACTIVE;
    }


    public String getStyleClassDefault()
    {
        return styleClassDefault;
    }

    public String getColorStyle(){ return this.colorStyle; }

    public void  setColors(){
        this.buttonColors.add(colorStyleGreen);
        this.buttonColors.add(colorStyleRed);
        this.buttonColors.add(colorStyleYellow);
        this.buttonColors.add(colorStyleLightblue);
        this.buttonColors.add(colorStylePink);
        this.buttonColors.add(colorStyleViolet);
        this.buttonColors.add(colorStyleOrange);

    }

    public void setColorStyle(int color)
    {
        this.colorStyle = this.buttonColors.get(color);
       // System.out.println(this.colorStyle);

    }

    public void setStyleClassDefault(String styleClassDefault)
    {
        this.styleClassDefault = styleClassDefault;
    }


    public String getStyleClassActive()
    {
        return styleClassActive;
    }

    public void setStyleClassActive(String styleClassActive)
    {
        this.styleClassActive = styleClassActive;
    }
}
