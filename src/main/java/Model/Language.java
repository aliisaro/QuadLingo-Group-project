package Model;

public class Language {
    //TODO: According to Copilot: "This class would represent a language that a user can learn. It could contain fields such as name, alphabet, vocabulary, grammarRules, etc."

    private String name;

    private String alphabet;

    public Language(String name, String alphabet) {
        this.name = name;
        this.alphabet = alphabet;
    }

    public String getName() {
        return name;
    }

    public String getAlphabet() {
        return alphabet;
    }
}
