package Prodotti;

import Eccezioni.InsufficientStockException;

public abstract class Prodotto implements Comparable<Prodotto> {

    protected String codice;
    protected String nome;
    protected double prezzo;
    protected int quantita;

    public Prodotto(String codice, String nome, double prezzo, int quantita) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public void vendi(int quantita) throws InsufficientStockException {
        if(quantita > this.quantita){
            throw new InsufficientStockException("Quantita insufficiente!");
        }
        else{
            this.quantita -= quantita;
        }
    }

    public void compra(int quantita){
        this.quantita += quantita;
    }



    public String getCodice() {
        return codice;
    }

    public int getQuantita(){
        return quantita;
    }

    @Override
    public String toString() {
        return codice+","+nome+","+prezzo+","+quantita;
    }

    @Override
    public int compareTo(Prodotto o) {
        return this.codice.compareTo(o.codice);
    }


}
