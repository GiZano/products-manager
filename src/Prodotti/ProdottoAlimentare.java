package Prodotti;

public class ProdottoAlimentare extends Prodotto {

    private String dataScadenza;

    public ProdottoAlimentare(String codice, String nome, double prezzo, int quantita, String dataScadenza) {
        super(codice, nome, prezzo, quantita);
        this.dataScadenza = dataScadenza;
    }

    @Override
    public String toString() {
        return "Alimentare"+","+super.toString()+","+dataScadenza;
    }

}
