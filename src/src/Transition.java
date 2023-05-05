// Pape THIAM
package src;
public class Transition {
    protected String etatI;
    protected String symbole;
    protected String sommetPileLu;
    protected String symboleAEmpiler;
    protected String etatF;

    public Transition(String etatI, String symboleLu, String sommetPileLu, String symboleAEmpiler, String etatF) {
        this.etatI = etatI;
        this.symbole = symboleLu;
        this.sommetPileLu = sommetPileLu;
        this.symboleAEmpiler = symboleAEmpiler;
        this.etatF = etatF;
    }

    public String getEtatI() {
        return etatI;
    }

    public void setEtatI(String etatI) {
        this.etatI = etatI;
    }

    public String getEtatF() {
        return etatF;
    }

    public void setEtatF(String etatF) {
        this.etatF = etatF;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public String getSommetPileLu() {
        return sommetPileLu;
    }

    public void setSommetPileLu(String sommetPileLu) {
        this.sommetPileLu = sommetPileLu;
    }

    public String getSymboleAEmpiler() {
        return symboleAEmpiler;
    }

    public void setSymboleAEmpiler(String symboleAEmpiler) {
        this.symboleAEmpiler = symboleAEmpiler;
    }

    public String toString(){
        return etatI + " " + symbole + " " + sommetPileLu + " " + symboleAEmpiler + " " + etatF ;
    }
}

