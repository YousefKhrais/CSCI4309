import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static Utils INSTANCE = null;
    public ArrayList<Socket> PEERS_LIST = new ArrayList<>();

    private Utils() {
    }

    public static Utils getInstance() {
        if (INSTANCE == null) INSTANCE = new Utils();

        return INSTANCE;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String calculateHash(String stringToHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

            return Utils.bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //server
    public void starSocketServer(int port) {
        new Thread(() -> {
            System.out.println("starSocketServer: " + port);

            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    System.out.println("readObject");
                    Block block = (Block) is.readObject();
                    System.out.println("Peer says: " + block.toString());
                }

            } catch (Exception e) {
                System.out.println("SocketServer Exception: " + e.getMessage());
            }
        }).start();
    }

    //client
    public void starSocketClient(String address, int port) {
        new Thread(() -> {
            System.out.println("Connecting to peer: " + address + ":" + port);

            try {
                Socket socket = new Socket(address, port);
                PEERS_LIST.add(socket);
                System.out.println(PEERS_LIST.toString());

            } catch (Exception e) {
                System.out.println("Can't Connect to peer: " + address + ":" + port + " Error:" + e.getMessage());
            }

        }).start();
    }

    public void broadcastBlock(Block block) {
        System.out.println("Broadcasting Block <" + block.getIndex() + ">");
        System.out.println("PEERS_LIST: " + PEERS_LIST.size());

        for (Socket peer : PEERS_LIST) {
            try {
                sendBlockToPeer(peer, block, 0);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void sendBlockToPeer(Socket socket, Block block, int tCount) throws Exception {
        //Failed to send block to peer after 3 tries...
        if (tCount == 2) {
            throw new Exception("Failed to send Block <" + block.getIndex() + "> to " + socket.getRemoteSocketAddress().toString() + " after 3 tries");
        }

        System.out.println("Sending Block <" + block.getIndex() + "> to: " + socket.getRemoteSocketAddress().toString());
        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(block);
            os.close();
        } catch (Exception e) {
            System.out.println("Failed to send Block <" + block.getIndex() + "> to: " + socket.getRemoteSocketAddress().toString() + " Trying again in 5 sec.");

            TimeUnit.SECONDS.sleep(5);

            sendBlockToPeer(socket, block, tCount + 1);
        }
    }

    public void receiveBlock(Block block) {
        System.out.println("Received Block <" + block.getIndex() + "> from: ");
    }
}