package ui;

import java.awt.Color;

public record Theme() {
    static final Color bgColor = new Color(167, 170, 225);
    static final Color btnColor = new Color(105, 111, 199);
    static final Color txtColor = Color.WHITE;
    public Color bgColor() {
        return bgColor;
    }
    public Color btnColor() {
        return btnColor;
    }
    public Color txtColor() {
        return txtColor;
    }
}