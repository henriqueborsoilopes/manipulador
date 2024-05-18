package br.unipar.manipulador.modelo.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatarUtil {
    
    public static int arredondaParaCima(double valor) {
        return (int) Math.ceil(valor);
    }
    
    public static String valorParaBR(double valor) {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(valor);
    }
    
    public static Double valorParaDouble(String valor) {
        try {
            return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).parse(valor).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(FormatarUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
