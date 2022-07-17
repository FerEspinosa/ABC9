package com.latorreencantada.abc9.Models;

public class Level {

    private String level_num;
    private boolean isChecked;

    public Level() {
    }

    public Level(String level_num, boolean isChecked) {
        this.level_num = level_num;
        this.isChecked = isChecked;
    }

    public String getLevel_num() {
        return level_num;
    }

    public void setLevel_num(String level_num) {
        this.level_num = level_num;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
