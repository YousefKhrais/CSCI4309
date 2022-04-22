import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static Blockchain blockchain = new Blockchain();
    public static Blockchain BLOCKCHAIN;

    public static void main(String[] args) {
       Utils.getInstance().starSocketServer(6666);
       Utils.getInstance().starSocketClient("localhost", 7777);

       //A 2nd node/peer
        //Utils.getInstance().starSocketServer(7777);
        //Utils.getInstance().starSocketClient("localhost", 6666);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }

        blockchain.addTransaction(new Transaction("Basecoin->Yousef->10"));
        blockchain.addTransaction(new Transaction("Yousef->Ali->10"));
        blockchain.addTransaction(new Transaction("Yousef->Ahmed->10"));
        blockchain.mine();

        blockchain.addTransaction(new Transaction("Basecoin->Yousef->20"));
        blockchain.addTransaction(new Transaction("Yousef->Ali->10"));
        blockchain.mine();

//        blocksExplorer();
//        storeBlockChain();
    }

    private static void blocksExplorer() {
        System.out.println(blockchain);
    }

    private static void storeBlockChain() {
        try {
            FileWriter myWriter = new FileWriter("Blockchain.txt");
            myWriter.write(blockchain.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

