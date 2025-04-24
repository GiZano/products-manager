package Manager;

import Prodotti.Prodotto;

import java.util.ArrayList;
import java.util.Collections;

public class ProductsManager {
    /**
     * Metodo che controlla se il formato della data
     * corrisponde a numero/numero/numero, se i tre numeri sono positivi
     * e infine se i valori di mese e giorno sono realistici
     * @param date String
     * @return boolean
     */
    public static boolean checkDate(String date){

        if(date.contains("/")){
            String[] valoriData = date.split("/");
            if(valoriData.length == 3){
                try {
                    int giorno = Integer.parseInt(valoriData[0]);
                    int mese = Integer.parseInt(valoriData[1]);
                    int anno = Integer.parseInt(valoriData[2]);

                    if(giorno < 0 || giorno > 31 || mese < 0 || mese > 12 || anno < 0){
                        return false;
                    }
                    else{
                        return mese != 2 || giorno <= 29;
                    }

                }catch(NumberFormatException nfe){
                    return false;
                }

            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }

    /**
     * Metodo che controlla se il prodotto è già presente all'interno della lista
     * controllando se il codice è presente tramite ricerca dicotomica.
     * @param p Prodotto
     * @param prodotti ArrayList<Prodotto>
     * @return boolean
     */
    public static int findProductByCode(Prodotto p, ArrayList<Prodotto> prodotti){
        Collections.sort(prodotti);
        int start = 0;
        int end = prodotti.size()-1;

        while(start <= end){
            int mid = start + (end-start)/2;
            if(prodotti.get(mid).compareTo(p) == 0){
                return mid;
            }
            else if(prodotti.get(mid).compareTo(p) < 0){
                start = mid+1;
            }
            else{
                end = mid-1;
            }
        }
        return -1;
    }

}
