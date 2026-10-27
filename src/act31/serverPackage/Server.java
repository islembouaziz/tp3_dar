package act31.serverPackage;

import java.io.*;
import java.net.*;

public class Server {
    private static int nbClients = 0;

    public static void main(String[] args) throws IOException {
        try (ServerSocket ss = new ServerSocket(1234)) {
            System.out.println("Serveur en attente sur le port 1234...");
            while (true) {
                Socket s = ss.accept();
                int numero = ++nbClients;   // un seul thread (main) incrémente
                System.out.println("Client n°" + numero + " connecté : " + s.getRemoteSocketAddress());
                new ClientProcess(s, numero).start();
            }
        }
    }
}

