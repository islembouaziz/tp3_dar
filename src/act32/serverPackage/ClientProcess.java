package act32.serverPackage;
import java.io.*;
import java.net.*;

class ClientProcess extends Thread {
    private final Socket socket;
    private final int numero;

    ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.flush();
            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                oos.writeObject("Vous êtes le client n°" + numero);
                oos.flush();
                while (true) {
                    Operation o = (Operation) ois.readObject();
                    calculer(o);
                    int c = Server.incrementerCompteur();
                    System.out.println("Client n°" + numero + " : " + o.getOp1() + " " + o.getOperateur()
                            + " " + o.getOp2() + " | compteur global = " + c);
                    oos.writeObject(o);
                    oos.flush();
                    oos.reset();
                }
            }
        } catch (EOFException e) {
            // client déconnecté normalement
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client n°" + numero + " : erreur " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client n°" + numero + " déconnecté");
        }
    }

    private void calculer(Operation o) {
        switch (o.getOperateur()) {
            case '+': o.setResultat(o.getOp1() + o.getOp2()); break;
            case '-': o.setResultat(o.getOp1() - o.getOp2()); break;
            case '*': o.setResultat(o.getOp1() * o.getOp2()); break;
            case '/':
                if (o.getOp2() == 0) o.setErreur("Division par zéro");
                else o.setResultat(o.getOp1() / o.getOp2());
                break;
            default: o.setErreur("Opérateur inconnu");
        }
    }
}
