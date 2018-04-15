package com.isa.instaticketapi.service.dto.projection;

public class SeatDTO {

    private int cordX;
    private int cordY;
    private boolean isSeat;
    private String type;

    public SeatDTO() {

    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public boolean isSeat() {
        return isSeat;
    }

    public void setSeat(boolean seat) {
        isSeat = seat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
