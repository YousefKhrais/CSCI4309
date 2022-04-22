import java.util.ArrayList;

public class Blockchain {

    public ArrayList<Transaction> unconfirmedTransactions;
    public ArrayList<Block> chain;
    public int difficulty;

    public Blockchain() {
        this.unconfirmedTransactions = new ArrayList<>();
        this.chain = new ArrayList<>();
        this.difficulty = 2;

        createGenesisBlock();
    }

    public void createGenesisBlock() {
        addTransaction(new Transaction("(GenesisBlock)Basecoin->Yousef->50"));
        mine();
    }

    public Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

    public boolean addBlock(Block block, String proof) {
        String previousHash;

        if (chain.size() == 0)
            previousHash = "0";
        else
            previousHash = lastBlock().getHash();

        if (!previousHash.equals(block.getHeader().getPreviousHash()))
            return false;

        if (!isValidProof(block, proof))
            return false;

        block.setHash(proof);
        chain.add(block);

        return true;
    }

    public boolean isValidProof(Block block, String blockHash) {
        String sDifficulty = new String(new char[block.getHeader().getDifficulty()]).replace('\0', '0');
        return blockHash.substring(0, block.getHeader().getDifficulty()).equals(sDifficulty) && proofOfWork(block).equals(blockHash);
    }

    public String proofOfWork(Block block) {
        block.getHeader().setNonce(0);
        String computedHash = block.computeHash();

        String sDifficulty = new String(new char[block.getHeader().getDifficulty()]).replace('\0', '0');
        while (!computedHash.substring(0, block.getHeader().getDifficulty()).equals(sDifficulty)) {
            block.getHeader().setNonce(block.getHeader().getNonce() + 1);
            computedHash = block.computeHash();
        }
        return computedHash;
    }

    public void addTransaction(Transaction transaction) {
        this.unconfirmedTransactions.add(transaction);
    }

    public boolean mine() {
        if (unconfirmedTransactions.isEmpty())
            return false;

        Block block;
        if (chain.isEmpty()) {
            Header header = new Header(1, "0", 2);
            block = new Block(0, header, unconfirmedTransactions);
        } else {
            Block lastBlock = lastBlock();
            Header header = new Header(1, lastBlock.getHash(), 2);
            block = new Block(lastBlock.getIndex() + 1, header, unconfirmedTransactions);
        }

        String proof = proofOfWork(block);
        addBlock(block, proof);

        unconfirmedTransactions = new ArrayList<>();

        return true;
    }

    @Override
    public String toString() {
        return "Blockchain{" + '\n' +
                "   unconfirmedTransactions=" + unconfirmedTransactions + '\n' +
                "   difficulty=" + difficulty + '\n' +
                "   chain=" + chain + '\n' +
                '}';
    }
}

