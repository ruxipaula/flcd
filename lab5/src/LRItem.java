import java.util.List;
import java.util.Objects;

public class LRItem {
    private String nonTerminal;
    private List<String> production;
    private int dotIndex;

    public LRItem(String nonTerminal, List<String> production, int dotIndex) {
        this.nonTerminal = nonTerminal;
        this.production = production;
        this.dotIndex = dotIndex;
    }

    public LRItem(String nonTerminal, List<String> production) {
        this.nonTerminal = nonTerminal;
        this.production = production;
        this.dotIndex = 0;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public List<String> getProduction() {
        return production;
    }

    public int getDotIndex() {
        return dotIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append(nonTerminal);
        sb.append(" -> ");
        for (int i=0; i<production.size() ; i++) {
            if (dotIndex == i) {
                sb.append(".");
            }
            sb.append(production.get(i));
        }
        if (dotIndex == production.size()) {
            sb.append(".");
        }

        sb.append(" ] ");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LRItem lrItem = (LRItem) o;
        return dotIndex == lrItem.dotIndex &&
                nonTerminal.equals(lrItem.nonTerminal) &&
                production.equals(lrItem.production);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonTerminal, production, dotIndex);
    }
}
