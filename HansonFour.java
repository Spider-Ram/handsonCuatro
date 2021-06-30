package agenteclips;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.Hashtable;
import net.sf.clipsrules.jni.*;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Cerezo
 */
public class HansonFour extends Agent {

    // The title of the book to buy
    private String targetBookTitle, rule;
    private List<String> facts = new ArrayList<>();
    private List<String> rules = new ArrayList<>();
    Environment clips;
    private Hashtable catalogue;
    // The GUI by means of which the user can add books in the catalogue
    private BookSellerGui myGui;

    protected void setup() {
        clips = new Environment();

        // Create the catalogue
        catalogue = new Hashtable();

        // Create and show the GUI 
        myGui = new BookSellerGui(this);
        myGui.showGui();
        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("product-selling");
        sd.setName("Amazon-trading");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //addBehaviour(new TellBehaviour());
        //addBehaviour(new AskBehaviour());
    }

    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Close the GUI
        //myGui.dispose();
        // Printout a dismissal message
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }
    //Comportamiento Tell, asignamos tenplates y hecho al KB engine

    public void TellBehaviour() {
        addBehaviour(new OneShotBehaviour(){
        public void action() {
            clips = new Environment();
            System.out.println("Tell is executed");
            clips.eval("(clear)");
            for(String rule : rules){
                String regla = String.format("(defrule %s crlf)", rule);
                clips.build(regla);
            }
            clips.eval("(reset)");
            for (String fact : facts) {

                String SQL = String.format("(%s)", fact);
                clips.assertString(SQL);
            }
            
            //clips.build(SQL);

            clips.eval("(facts)");
            AskBehaviour();

        }
        });
        // END of inner class ...Behaviour
    }

    public void AskBehaviour() {
    addBehaviour(new OneShotBehaviour() {

        public void action() {
            
            clips.run();
            System.out.println("Ask is executed");

        }

    });
}
    //Almacenamos el valor ingresa en la interfaz

    public void Buscar(String nameProduct, String rule) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                if (nameProduct != null) {
                    if (facts.contains(nameProduct)) {
                        System.out.println("Bloqueado");

                    } else {
                        facts.add(nameProduct);
                        rules.add(rule);
                        System.out.println("Agregada");
                        TellBehaviour();
                    }

                }
            }
        });
    }

    //Asiganamos el valor agregado a la clase
    protected String entregar() {
        return targetBookTitle;
    }
}
