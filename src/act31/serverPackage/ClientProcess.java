package act31.serverPackage;

import java.io.*;
import java.net.*;

public class ClientProcess extends Thread {
    private final Socket socket;
    private final int numero;

    ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("Bienvenue ! Vous êtes le client n°" + numero);
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Client n°" + numero + " : " + msg);
                out.println("Echo : " + msg);
            }
        } catch (IOException e) {
            System.out.println("Client n°" + numero + " : erreur " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client n°" + numero + " déconnecté");
        }
    }
}
