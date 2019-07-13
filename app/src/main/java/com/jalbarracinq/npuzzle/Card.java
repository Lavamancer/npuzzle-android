package com.jalbarracinq.npuzzle;

import java.io.Serializable;

public class Card implements Serializable {

    private Integer position;


    public Card(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
