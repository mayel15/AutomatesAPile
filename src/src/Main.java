// Pape THIAM
package src;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException, ScriptException {
        // indique le numero de l'automate à tester: 1 pour le 1, 2 pour le 2
        int numeroAutomate = 2;

        Automate automate = null;
        try {
            automate = new Automate("src/files/automate"+numeroAutomate+".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String nomDeFichier = "src/files/testAutomate"+numeroAutomate+".txt";
        BufferedReader br = new BufferedReader(new FileReader(nomDeFichier));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("# mots")) {
                while (!(line = br.readLine()).startsWith("#")) {
                    System.out.println(line + " " + automate.analyse(line, numeroAutomate) + " : resultat de l'analyse lexical et syntaxique\n");
                }
            }
        }
    }
}