package View;

import javafx.scene.image.ImageView;

public interface BadgeLock {
    void unlockBadge( ImageView imageView);
    void lockBadge(boolean value, ImageView imageView);
}