package Prodotti;

public class ProdottoElettronico extends Prodotto {

    private int anniGaranzia;

    public ProdottoElettronico(String codice, String nome, double prezzo, int quantita, int anniGaranzia) {
        super(codice, nome, prezzo, quantita);
        this.anniGaranzia = anniGaranzia;
    }

    @Override
    public String toString() {
        return "Elettronica"+","+super.toString()+","+anniGaranzia;
    }

}
