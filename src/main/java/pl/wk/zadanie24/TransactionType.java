package pl.wk.zadanie24;

public enum TransactionType {
    IN("Przychodząca"), OUT("Wychodząca");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
