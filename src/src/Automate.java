package src;
import src.Transition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Automate {
    private Stack<String> pile;

    protected Alphabet alphabet;
    protected ArrayList<String> etats;
    protected String etatI;
    protected ArrayList<String> etatF;
    protected ArrayList<Transition> transitions;

    public Automate() {
        pile = new Stack<String>();
        pile.push("Z");
    }



    public Automate(String nomDeFichier) throws IOException {
        pile = new Stack<String>();
        pile.push("Z");

        BufferedReader br = new BufferedReader(new FileReader(nomDeFichier));
        String line;
        ArrayList<String> alphabetSymboles = new ArrayList<>();
        ArrayList<String> etats = new ArrayList<>();
        String etatInitial = "";
        ArrayList<String> etatsFinaux = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.equals("# alphabet")) {
                while (!(line = br.readLine()).startsWith("#")) {
                    alphabetSymboles.add(line);
                }
            }
            if (line.equals("# etats")) {
                while (!(line = br.readLine()).startsWith("#")) {
                    etats.add(line);
                }
            }
            if (line.equals("# etat initial")) {
                etatInitial = br.readLine();
            }
            if (line.equals("# etats finaux")) {
                while (!(line = br.readLine()).startsWith("#")) {
                    etatsFinaux.add(line);
                }
            }
            if (line.equals("# transitions")) {
                while ((line = br.readLine()) != null && !line.startsWith("#")) {
                    String[] transitionData = line.split(" ");
                    Transition transition = new Transition(transitionData[0], transitionData[1], transitionData[2], transitionData[3], transitionData[4]);
                    transitions.add(transition);
                }
            }
        }

        Alphabet alphabet = new Alphabet(alphabetSymboles);
        this.alphabet = alphabet;
        this.etats = etats;
        this.etatI = etatInitial;
        this.etatF = etatsFinaux;
        this.transitions = transitions;

        br.close();
    }

    // permet de faire le split un string et d'empiler les caracteres de la chaine dans une pile
    public void empilerCharStr(String str){
        for (int i = 0; i < str.length(); i++) {
            String symboleLu = Character.toString(str.charAt(i));
            pile.push(symboleLu);
        }
    }

    public boolean analyse(String mot) {
        // Initialisation de l'état courant avec l'état initial de l'automate
        String etatCourant = etatI;

        // Parcours du mot avec chaque caractère
        for (int i = 0; i < mot.length(); i++) {
            String symboleLu = Character.toString(mot.charAt(i));

            // Vérification que le symbole lu appartient à l'alphabet de l'automate
            if (!alphabet.getSymboles().contains(symboleLu)) {
                System.out.println("L'expression mathematique n'a pas été acceptée.");
                System.out.println("Le symbole " + symboleLu + " n'appartient pas à l'alphabet de l'automate.");

                // Vider la pile
                while(!pile.peek().equals("Z")) pile.pop();

                return false;
            }

            // Recherche de la transition correspondant à l'état courant et au symbole lu
            Transition transition = null;
            for (Transition t : transitions) {
                //System.out.println(t);
                //System.out.println(pile);
                //System.out.println(t.sommetPileLu);
                if (t.getEtatI().equals(etatCourant) && t.getSymbole().equals(symboleLu) && pile.peek().equals(t.sommetPileLu)) {
                    pile.pop();
                    if(!(t.symboleAEmpiler.equals("eps"))){
                        empilerCharStr(t.symboleAEmpiler);
                    }
                    transition = t;

                    break;
                }
            }

            // Vérification qu'une transition a bien été trouvée
            if (transition == null) {
                System.out.println("L'expression mathematique n'a pas été acceptée.");
                System.out.println("Il n'y a pas de transition à partir de l'état " + etatCourant + " avec le symbole " + symboleLu + ".");

                // Vider la pile
                while(!pile.peek().equals("Z")) pile.pop();

                return false;
            }

            // Passage à l'état suivant
            etatCourant = transition.getEtatF();
        }

        // Vérification que l'état courant est bien un état final
        if (!etatF.contains(etatCourant)) {
            System.out.println("L'expression mathematique n'a pas été acceptée.");
            System.out.println("Le mot a été entièrement lu mais l'état courant (" + etatCourant + ") n'est pas un état final.");

            // Vider la pile
            while(!pile.peek().equals("Z")) pile.pop();

            return false;
        }

        // Verification que la pile est vide
        if(!pile.peek().equals("Z")){
            System.out.println("L'expression mathematique n'a pas été acceptée.");
            System.out.println("Le mot a été entièrement lu mais la pile (" + pile + ") n'est pas vide");

            // Vider la pile
            while(!pile.peek().equals("Z")) pile.pop();

            return false;
        }

        // Si on arrive ici, le mot a été reconnu
        System.out.println("L'expression mathematique a été acceptée.");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = null;
        try {
            result = engine.eval(mot);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Le résultat de l'expression : " + mot + " = " + result);
        return true;
    }
}

