package com.latorreencantada.abc9;

public class Card {
    String word;
    String syl1;
    String syl2;
    String syl3;
    String syl4;
    int level;

    public Card(String word, String syl1, String syl2, String syl3, String syl4, int level) {
        this.word = word;
        this.syl1 = syl1;
        this.syl2 = syl2;
        this.syl3 = syl3;
        this.syl4 = syl4;
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSyl(int syl) {

            switch (syl){
                case 1:
                    return syl1;
                case 2:
                    return syl2;
                case 3:
                    return syl3;
                case 4:
                    return syl4;
                default:
                    return null;
            }
    }


    public void setSyl1(String syl1) {
        this.syl1 = syl1;
    }


    public void setSyl2(String syl2) {
        this.syl2 = syl2;
    }


    public void setSyl3(String syl3) {
        this.syl3 = syl3;
    }


    public void setSyl4(String syl4) {
        this.syl4 = syl4;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
