package act32.serverPackage;

import java.io.Serializable;

public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    private double op1, op2;
    private char operateur;
    private double resultat;
    private String erreur;

    public Operation(double op1, char operateur, double op2) {
        this.op1 = op1; this.operateur = operateur; this.op2 = op2;
    }
    public double getOp1() { return op1; }
    public double getOp2() { return op2; }
    public char getOperateur() { return operateur; }
    public double getResultat() { return resultat; }
    public void setResultat(double r) { this.resultat = r; }
    public String getErreur() { return erreur; }
    public void setErreur(String e) { this.erreur = e; }
}
