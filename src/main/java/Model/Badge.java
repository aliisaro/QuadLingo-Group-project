package Model;

import javafx.scene.image.Image;

public class Badge {
  private final Image image;
  private final int threshold;
  private final String description;
  private final String checker;

  public Badge(String imagePath, int threshold, String description, String checker) {
    this.image = new Image(imagePath);
    this.threshold = threshold;
    this.description = description;
    this.checker = checker;
  }

  public String getChecker() {
    return checker;
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