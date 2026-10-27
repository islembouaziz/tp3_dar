package act32.serverPackage;

import java.io.*;
import java.net.*;

public class Server {
    private static int nbClients = 0;
    private static int compteurGlobal = 0;                 // ressource critique
    private static final Object LOCK = new Object();

    // Accès synchronisé : incrément + lecture atomiques
    static int incrementerCompteur() {
        synchronized (LOCK) {
            return ++compteurGlobal;
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket ss = new ServerSocket(1234)) {
            System.out.println("Serveur calculatrice en attente sur le port 1234...");
            while (true) {
                Socket s = ss.accept();
                int numero = ++nbClients;
                System.out.println("Client n°" + numero + " connecté : " + s.getRemoteSocketAddress());
                new ClientProcess(s, numero).start();
            }
        }
    }
}

