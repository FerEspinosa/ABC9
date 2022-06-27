package com.latorreencantada.abc9.Models;

public class Player {

    String name;
    int bestScore;
    int userLevel;
    String profileImage;

    public Player(String name, int bestScore, int level, String profileImage) {
        this.name = name;
        this.bestScore = bestScore;
        this.userLevel = level;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
