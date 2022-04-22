import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {

    private int index;
    private Header header;
    private int transactionsCount;
    private ArrayList<Transaction> transactions;
    private String hash;
    private ArrayList<String> merkleTree;

    public Block(int index, Header header, ArrayList<Transaction> transactions) {
        this.index = index;
        this.header = header;
        this.transactionsCount = transactions.size();
        this.transactions = transactions;
        this.hash = computeHash();
    }

    public void MerkleTree() {
        merkleTree = new ArrayList<>();

        for (Transaction transaction : transactions) {
            merkleTree.add(transaction.getTransactionHash());
        }

        ArrayList<String> tempMerkleTree = new ArrayList<>(merkleTree);
        ArrayList<String> tempArrayList = new ArrayList<>();

        int i = 0;
        while (tempMerkleTree.size() != 1) {
            int tempSize = tempMerkleTree.size();
            String newHash;

            if (!tempMerkleTree.isEmpty()) {
                String newString;
                if (i + 1 < tempSize) {
                    newString = tempMerkleTree.get(i) + tempMerkleTree.get(i + 1);
                } else {
                    newString = tempMerkleTree.get(i) + tempMerkleTree.get(i);
                }
                newHash = Utils.calculateHash(newString);
            } else {
                break;
            }

            merkleTree.add(newHash);
            tempArrayList.add(newHash);
            if (tempSize <= i + 2) {
                tempMerkleTree = (ArrayList<String>) tempArrayList.clone();
                tempArrayList.clear();
                i = 0;
            } else {
                i += 2;
            }
        }
    }

    public String computeHash() {
        return Utils.calculateHash(this.header.toString());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public int getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(int transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ArrayList<String> getMerkleTree() {
        return merkleTree;
    }

    public void setMerkleTree(ArrayList<String> merkleTree) {
        this.merkleTree = merkleTree;
    }

    public void setMerkleRoot() {
        MerkleTree();
        this.header.setMerkleRoot(merkleTree.get(merkleTree.size() - 1));
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", header=" + header +
                ", transactionsCount=" + transactionsCount +
                ", transactions=" + transactions +
                ", hash='" + hash + '\'' +
                ", merkleTree=" + merkleTree +
                '}';
    }
}
