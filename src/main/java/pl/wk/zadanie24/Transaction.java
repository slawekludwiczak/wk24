package pl.wk.zadanie24;

public class Transaction {
    private Integer id;
    private TransactionType transactionType;
    private String description;
    private Double amount;
    private String date;

    public Transaction() {
    }

    public Transaction(Integer id, TransactionType transactionType, String description, Double amount, String date) {
        this.id = id;
        this.transactionType = transactionType;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(TransactionType transactionType, String description, Double amount, String date) {
        this.transactionType = transactionType;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return id +
                ") " + transactionType.getDescription() +
                ", " + description +
                ", " + amount +
                "zł, " + date
                ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

