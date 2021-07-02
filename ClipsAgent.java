/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteclips;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.clipsrules.jni.*;

public class ClipsAgent extends Agent {

    private BookSellerGui myGui;
    private Environment clips;

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hallo! Cutomer-agent " + getAID().getName() + " is ready.");
        myGui = new BookSellerGui(this);
        myGui.showGui();

    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println("Clips-agent " + getAID().getName() + " terminating.");
    }

    public void muestraContenido(String archivo, String clp) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                clips = new Environment();
                System.out.println(clp);
                clips.eval("(clear)");
                //String SQL = String.format("(%s)", archivo);
                clips.load(archivo);
                clips.eval("(reset)");
                clips.run();

            }
        });

    }

    public void buscaFichero() throws IOException {
        muestraContenido("clps/market/persons.clp", "**** Market CLP ****");
        muestraContenido("clps/persons/load-persons.clp", "\n\n**** Persons CLP ****");
       muestraContenido("clps/prodcust/load-prod-cust.clp", "\n\n**** ProdCust CLP ****");
    }

}
