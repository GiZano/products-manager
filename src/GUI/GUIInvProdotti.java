package GUI;

import Manager.ProductsManager;
import Manager.TextFileManager;
import Prodotti.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUIInvProdotti extends JFrame {
    private JPanel pannello;
    private JButton addBtn;
    private JTable productTable;
    private JButton caricaBtn;
    private JButton salvaBtn;
    private JButton modificaBtn;
    private JScrollPane pannelloTableScroll;

    private String pathDati = "data/magazzino.csv";
    private ArrayList<Prodotto> prodotti;
    private final String[] header = {"Tipologia", "Codice", "Nome", "Prezzo", "Quantita", "Data Scadenza/Anni di Garanzia"};


    public GUIInvProdotti() {
        prodotti = new ArrayList<>();
        setContentPane(pannello);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setTitle("Gestione inventario prodotti");
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generaGUIAggiunta();
            }
        });

        salvaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvaDati();
            }
        });

        caricaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caricaDati();
            }
        });

        modificaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generaGUIModifica();
            }
        });
    }

    /**
     * Quando chiamata, verranno aggiunti alla tabella i dati passati come parametro
     * @param dati String[]
     */
    public void aggiungiDati(String[] dati){

        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.addRow(dati);
    }

    /**
     * Generazione della GUI per l'aggiunta di nuovi prodotti
     */
    private void generaGUIAggiunta() {
        GUIAggiuntaProdotti aggiuntaProdotto = new GUIAggiuntaProdotti(prodotti, this);
    }

    private void generaGUIModifica() {
        GUIModificaProdotti modificaProdotti = new GUIModificaProdotti(prodotti, this);
    }

    /**
     * Ricaricamento dei dati partendo dall'arrayList per quando avverrà una modifica nella quantità
     */
    public void ricaricaTabella(){

        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.setRowCount(0);
        String[] dati;
        for(Prodotto p : prodotti){
            dati = p.toString().split(",");
            tableModel.addRow(dati);
        }
    }


    /**
     * Caricamento dei dati dal file presente alla posizione "data/magazzino.csv". <br>
     * Se la tipologia di un prodotto non rientra tra quelle esistenti, viene visualizzato un messaggio di errore,
     * in quanto i dati potrebbero essere corrotti.
     */
    private void caricaDati(){
        try {
            String dati = TextFileManager.readFileAsString(pathDati);
            DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();

            String[] prodottiCaricati = dati.split(";");
            for(String prodotto : prodottiCaricati){
                String[] datiProdotto = prodotto.split(",");
                if(datiProdotto[0].equals("Alimentare")){
                    Prodotto prodottoCaricato = new ProdottoAlimentare(datiProdotto[1], datiProdotto[2], Double.parseDouble(datiProdotto[3]), Integer.parseInt(datiProdotto[4]), datiProdotto[5]);
                    if(ProductsManager.findProductByCode(prodottoCaricato, prodotti) == -1){
                        prodotti.add(prodottoCaricato);
                        tableModel.addRow(datiProdotto);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Prodotto con codice " + datiProdotto[1] + " già presente all'interno della tabella, non verrà caricato!");
                    }
                }
                else if(datiProdotto[0].equals("Elettronica")){
                    Prodotto prodottoCaricato = new ProdottoElettronico(datiProdotto[1], datiProdotto[2], Double.parseDouble(datiProdotto[3]), Integer.parseInt(datiProdotto[4]), Integer.parseInt(datiProdotto[5]));
                    if(ProductsManager.findProductByCode(prodottoCaricato, prodotti) == -1){
                        prodotti.add(prodottoCaricato);
                        tableModel.addRow(datiProdotto);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Prodotto con codice " + datiProdotto[1] + " già presente all'interno della tabella, non verrà caricato!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Dati corrotti all'interno del file!");
                }
            }
            bloccaCarica();
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Errore durante il caricamento!");
        }
    }

    /**
     * Salvataggio dei dati nel file presente alla posizione "data/magazzino.csv".
     */
    private void salvaDati(){
        StringBuilder content = new StringBuilder();
        for(Prodotto p : prodotti){
            content.append(p).append(";");
        }

        if(!content.isEmpty()){
            content.deleteCharAt(content.length()-1);
        }

        try {
            TextFileManager.writeStringToFile(pathDati, content.toString());
        }catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Errore durante il salvataggio!");
        }
    }

    /**
     * Disattivazione del bottone carica per far sì che non si carichi due volte il file, in modo tale che non venga visualizzato
     * il messaggio di errore che indica la presenza di duplicati tra file e tabella per ogni singolo prodotto.
     */
    public void bloccaCarica(){
        caricaBtn.setEnabled(false);
    }

    /**
     * Attivazione del bottone carica quando ci saranno nuovi dati o un cambiamento
     */
    public void sbloccaCarica(){
        caricaBtn.setEnabled(true);
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(null, header){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnCount(6);
        productTable = new JTable(model);


        pannelloTableScroll = new JScrollPane();
        pannelloTableScroll.setViewportView(productTable);

    }
}
