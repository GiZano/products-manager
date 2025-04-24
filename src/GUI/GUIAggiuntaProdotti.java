package GUI;

import Eccezioni.InvalidProductDataException;
import Manager.ProductsManager;
import Prodotti.Prodotto;
import Prodotti.ProdottoAlimentare;
import Prodotti.ProdottoElettronico;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIAggiuntaProdotti extends JFrame{


    private JPanel pannello;
    private JPanel pannelloRadio;
    private JPanel pannelloDati;
    private JRadioButton radioAlimentare;
    private JRadioButton radioElettronica;
    private JTextField codiceTxt;
    private JTextField nomeTxt;
    private JTextField prezzoTxt;
    private JTextField extraTxt;
    private JButton chiudiBtn;
    private JButton addBtn;
    private JLabel extraLbl;
    private JTextField quantitaTxt;

    private ArrayList<Prodotto> prodotti;

    public GUIAggiuntaProdotti(ArrayList<Prodotto> prodotti, GUIInvProdotti guiPadre){

        this.prodotti = prodotti;

        setContentPane(pannello);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(600,600);
        setLocationRelativeTo(null);
        setResizable(false);

        radioAlimentare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioElettronica.setSelected(false);
                extraLbl.setText("Data di scadenza");
            }
        });

        radioElettronica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radioAlimentare.setSelected(false);
                extraLbl.setText("Anni di garanzia");
            }
        });

        chiudiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] dati = new String[6];

                if(radioAlimentare.isSelected()){
                    dati[0] = "Alimentare";
                    dati[1] = codiceTxt.getText();
                    dati[2] = nomeTxt.getText();
                    dati[3] = prezzoTxt.getText();
                    dati[4] = quantitaTxt.getText();
                    dati[5] = extraTxt.getText(); // data di scadenza

                    try {
                        if(!ProductsManager.checkDate(dati[5])){
                            throw new InvalidProductDataException("La data deve esistere e seguire il formato GG/MM/AAAA !");
                        }
                        Prodotto nuovoProdotto = new ProdottoAlimentare(dati[1], dati[2], Double.parseDouble(dati[3]), Integer.parseInt(dati[4]), dati[5]);
                        if(ProductsManager.findProductByCode(nuovoProdotto, prodotti) == -1){
                            prodotti.add(nuovoProdotto);
                            guiPadre.aggiungiDati(dati);
                            guiPadre.sbloccaCarica();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Prodotto con codice \"" + dati[1] + "\" già esistente !");
                        }

                    }catch(NumberFormatException nfe){
                        JOptionPane.showMessageDialog(null, "Prezzo e Quantità devono essere numerici!");
                    }catch(InvalidProductDataException ipde){
                        JOptionPane.showMessageDialog(null, ipde.getMessage());
                    }
                }
                else if(radioElettronica.isSelected()){

                    dati[0] = "Elettronica";
                    dati[1] = codiceTxt.getText();
                    dati[2] = nomeTxt.getText();
                    dati[3] = prezzoTxt.getText();
                    dati[4] = quantitaTxt.getText();
                    dati[5] = extraTxt.getText();

                    try {
                        Prodotto nuovoProdotto = (new ProdottoElettronico(dati[1], dati[2], Double.parseDouble(dati[3]), Integer.parseInt(dati[4]), Integer.parseInt(dati[5])));
                        if(ProductsManager.findProductByCode(nuovoProdotto, prodotti) != -1){
                            prodotti.add(nuovoProdotto);
                            guiPadre.aggiungiDati(dati);
                            guiPadre.sbloccaCarica();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Prodotto con codice \"" + dati[1] + "\" già esistente !");
                        }

                    }catch(NumberFormatException nfe){
                        JOptionPane.showMessageDialog(null, "Prezzo, Quantità  e Anni di Garanzia devono essere numerici!");
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "Inserire tipologia prodotto!");
                }

            }
        });
    }
}
