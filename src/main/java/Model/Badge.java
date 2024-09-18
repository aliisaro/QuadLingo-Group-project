package Model;

import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;

public class Badge {
    private Image image;
    private int threshold;

    public Badge(String imagePath, int threshold) {
        this.image = new Image(imagePath);
        this.threshold = threshold;
    }

    public Image getImage() {
        return image;
    }

    public int getThreshold() {
        return threshold;
    }
}