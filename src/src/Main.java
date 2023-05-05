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
        Automate automate = null;
        try {
            automate = new Automate("src/files/automate2.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String nomDeFichier = "src/files/testAutomate2.txt";
        BufferedReader br = new BufferedReader(new FileReader(nomDeFichier));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("# mots")) {
                while ( !(line = br.readLine()).startsWith("#")) {
                    System.out.println(line + " "  + automate.analyse(line) + " : resultat de l'analyse lexical et syntaxique\n");
                }
            }
        }

        /*String expression = "((150-24)*72+4)/5 ";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = engine.eval(expression);
        System.out.println("Le résultat de l'expression " + expression + " = " + result);
        double test = ((150-24)*72+4)/5;
        System.out.println(test);*/


    }
}