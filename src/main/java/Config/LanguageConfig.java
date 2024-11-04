package Config;

import java.util.Locale;

public class LanguageConfig {
    private static LanguageConfig instance;
    private Locale currentLocale = Locale.ENGLISH; // Default language

    // Private constructor for singleton pattern
    private LanguageConfig() {}

    public static LanguageConfig getInstance() {
        if (instance == null) {
            instance = new LanguageConfig();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }
}
