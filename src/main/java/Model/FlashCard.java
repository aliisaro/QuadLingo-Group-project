package Model;

public class FlashCard {

    public String term;

    public String definition;

    public boolean isMastered;

    public String group;

    public FlashCard(String term, String definition, boolean isMastered, String group) {
        this.term = term;
        this.definition = definition;
        this.isMastered = isMastered;
        this.group = group;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public boolean getIsMastered() {
        return isMastered;
    }

    public String getGroup() {
        return group;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setIsMastered(boolean isMastered) {
        this.isMastered = isMastered;
    }
}
