package message_game;

import java.io.Serializable;
import java.util.Objects;

public class MessageColors implements Serializable {

    private int state;
    private double count_guess;

   

	private String color1, color2, color3, color4;

     public MessageColors(){
        this.state = 0;
        this.color1 = "";
        this.color2 = "";
        this.color3 = "";
        this.color4 = "";
        this.count_guess=0;
    }
     
    public MessageColors(int state){
        this.state = state;
        this.color1 = "";
        this.color2 = "";
        this.color3 = "";
        this.color4 = "";
        this.count_guess=0;
        
    }
    
    public MessageColors(int state, String color1, String color2, String color3, String color4, double count_guess) {
        this.state = state;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.count_guess=count_guess;
    }
    
    public double getCount_guess() {
		return count_guess;
	}

	public void setCount_guess(int count_guess) {
		this.count_guess = count_guess;
	}
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getColor4() {
        return color4;
    }

    public void setColor4(String color4) {
        this.color4 = color4;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.state);
        hash = 79 * hash + Objects.hashCode(this.color1);
        hash = 79 * hash + Objects.hashCode(this.color2);
        hash = 79 * hash + Objects.hashCode(this.color3);
        hash = 79 * hash + Objects.hashCode(this.color4);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MessageColors other = (MessageColors) obj;
        if (!Objects.equals(this.color1, other.color1)) {
            return false;
        }
        if (!Objects.equals(this.color2, other.color2)) {
            return false;
        }
        if (!Objects.equals(this.color3, other.color3)) {
            return false;
        }
        if (!Objects.equals(this.color4, other.color4)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }
}
