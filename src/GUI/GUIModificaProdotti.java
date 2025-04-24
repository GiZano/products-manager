package GUI;

import Eccezioni.InsufficientStockException;
import Prodotti.Prodotto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIModificaProdotti extends JFrame {
    private JPanel pannello;
    private JButton vendiBtn;
    private JButton compraBtn;
    private JComboBox<String> prodottiCombo;
    private JButton chiudiBtn;
    private JLabel quantLbl;

    ArrayList<Prodotto> prodotti;


    public GUIModificaProdotti(ArrayList<Prodotto> prodotti, GUIInvProdotti guiPadre) {
        this.prodotti = prodotti;
        setContentPane(pannello);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            quantLbl.setText("Quantita disponibile: " + prodotti.get(prodottiCombo.getSelectedIndex()).getQuantita());
        }catch(NullPointerException | IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Nessun prodotto da modificare!");
            dispose();
        }

        // TODO: listener per bottoni vendi, compra e chiudi


        chiudiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        prodottiCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantLbl.setText("Quantita disponibile: " + prodotti.get(prodottiCombo.getSelectedIndex()).getQuantita());
            }
        });


        vendiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int quantita = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserire quantità da vendere"));
                    prodotti.get(prodottiCombo.getSelectedIndex()).vendi(quantita);
                    quantLbl.setText("Quantita disponibile: " + prodotti.get(prodottiCombo.getSelectedIndex()).getQuantita());
                    guiPadre.ricaricaTabella();
                }catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Inserire valori numerici interi!");
                }catch(InsufficientStockException ise){
                    JOptionPane.showMessageDialog(null, "Quantità disponibile insufficiente!");
                }

            }
        });

        compraBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int quantita = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserire quantità comprata"));
                    prodotti.get(prodottiCombo.getSelectedIndex()).compra(quantita);
                    quantLbl.setText("Quantita disponibile: " + prodotti.get(prodottiCombo.getSelectedIndex()).getQuantita());
                    guiPadre.ricaricaTabella();
                }catch(NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Inserire valori numerici interi!");
                }
            }
        });

    }

    private void createUIComponents() {
        // TODO: ComboBox con i codici dei prodotti
        String[] codiciProdotti = new String[prodotti.size()];
        for (int i = 0; i < prodotti.size(); i++) {
            codiciProdotti[i] = prodotti.get(i).getCodice();
        }
        prodottiCombo = new JComboBox<>(codiciProdotti);
    }
}
