package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.io.InputStream;
import java.awt.Font;

public record Theme() {
    public static final Color BACKGROUND_COLOR = new Color(167, 170, 225);
    public static final Color BUTTON_COLOR = new Color(105, 111, 199);
    public static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    public static final Color TEXTFIELD_TEXT_COLOR = new Color(51, 60, 184);
    public static final Font GOOGLESANSFLEX_FONT = getFont("ui/GoogleSansFlex.ttf");
    
    /**
     * Loads a font from the specified path within the classpath.
     * @param path - Path of the font file (.ttf or .otf)
     * @return Font object loaded from the specified path, or null if loading fails
    */
    public static final Font getFont(String path) {
        InputStream is = ClassLoader.getSystemResourceAsStream(path);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Applies the Google Sans Flex font to a component and all its child components recursively.
     * @param component The main component to apply the font to
     * @param style The style of the font (e.g., Font.PLAIN, Font.BOLD)
     * @param size  The size of the font
     */
    public static final void applyFontOnFrame(Component component,int style, float size)
    {
        Font font = GOOGLESANSFLEX_FONT.deriveFont(style,size); //We use the same font in whole project
        if (font == null) return;   // Verify if font has been succesfully loaded
        component.setFont(font);
            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    applyFontOnFrame(child, style, size);   // Apply font on itself and its children
                }
            }
    }
}