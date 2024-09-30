package View;

import javafx.scene.image.ImageView;

public interface BadgeLock {
    void unlockBadge(boolean value, ImageView imageView);
    void lockBadge(boolean value, ImageView imageView);
}