package com.latorreencantada.abc9.Models;

import java.util.List;

public class Level {

    private int levelNum;
    private String levelName;
    private boolean isChecked;
    private List<Card> cards;

    public Level() {
    }

    public Level(int level_num, String levelName, List<Card> cards) {
        this.levelNum = level_num;
        this.isChecked = false;
        this.cards = cards;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
