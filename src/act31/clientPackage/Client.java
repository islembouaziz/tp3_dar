package act31.clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";   // IP du serveur en réseau local
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(InetAddress.getByName(host), 1234));
            try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                 Scanner sc = new Scanner(System.in)) {
                    System.out.println(in.readLine());   // numéro d'ordre
                    while (sc.hasNextLine()) {
                        System.out.print("> ");
                        String msg = sc.nextLine();
                        if (msg.equalsIgnoreCase("quit")) break;
                        out.println(msg);
                        System.out.println(in.readLine());
                    }
            }
        }
    }
}
