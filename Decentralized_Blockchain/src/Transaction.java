import java.io.Serializable;

public class Transaction implements Serializable {

    private String data;
    private String transactionHash;

    public Transaction(String data) {
        this.data = data;
        this.transactionHash = computeHash();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String computeHash() {
        return Utils.calculateHash(data);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "data='" + data + '\'' +
                ", transactionHash='" + transactionHash + '\'' +
                '}';
    }
}

