import java.io.Serializable;

public class Header implements Serializable {

    private int version;
    private String previousHash;
    private String merkleRoot;
    private long timeStamp;
    private int difficulty;
    private int nonce;

    public Header(int version, String previousHash, int difficulty) {
        this.version = version;
        this.previousHash = previousHash;
        this.merkleRoot = null;
        this.timeStamp = System.currentTimeMillis();
        this.difficulty = difficulty;
        this.nonce = 0;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Header{" +
                "version=" + version +
                ", previousHash='" + previousHash + '\'' +
                ", merkleRoot='" + merkleRoot + '\'' +
                ", timeStamp=" + timeStamp +
                ", difficulty=" + difficulty +
                ", nonce=" + nonce +
                '}';
    }
}

