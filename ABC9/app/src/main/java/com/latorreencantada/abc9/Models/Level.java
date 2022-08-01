package com.latorreencantada.abc9.Models;

import java.util.List;

public class Level {

    private int levelNum;
    private String levelName;
    private boolean isExpanded;
    private List<Card> cards; // nested list

    public Level() {
    }

    public Level(int level_num, String levelName, List<Card> cards) {
        this.levelNum = level_num;
        this.levelName = levelName;
        this.cards = cards;
        this.isExpanded = false;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
