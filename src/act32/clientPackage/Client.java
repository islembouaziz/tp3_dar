package act32.clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import act32.serverPackage.Operation;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(InetAddress.getByName(host), 1234));
            try (ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream())) {
                oos.flush();
                try (ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                     Scanner sc = new Scanner(System.in)) {
                    System.out.println((String) ois.readObject());
                    while (sc.hasNextLine()) {
                        System.out.print("Opération (ex: 3 + 4) ou 'quit' : ");
                        String ligne = sc.nextLine().trim();
                        if (ligne.equalsIgnoreCase("quit")) break;
                        String[] t = ligne.split("\\s+");
                        if (t.length != 3 || t[1].length() != 1) {
                            System.out.println("Format invalide. Exemple : 3 + 4");
                            continue;
                        }

                        double op1;
                        double op2;
                        try {
                            op1 = Double.parseDouble(t[0]);
                            op2 = Double.parseDouble(t[2]);
                        } catch (NumberFormatException e) {
                            System.out.println("Les deux opérandes doivent être des nombres.");
                            continue;
                        }

                        oos.writeObject(new Operation(op1, t[1].charAt(0), op2));
                        oos.flush();
                        Operation r = (Operation) ois.readObject();
                        System.out.println(r.getErreur() != null
                                ? "Erreur : " + r.getErreur()
                                : "Résultat = " + r.getResultat());
                    }
                }
            }
        }
    }
}
