package Model;

import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;

public class Badge {
    private Image image;
    private int threshold;
    private String description;

    public Badge(String imagePath, int threshold, String description) {
        this.image = new Image(imagePath);
        this.threshold = threshold;
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public int getThreshold() {
        return threshold;
    }

    public String getDescription() {
        return description;
    }
}