package util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.ie.IESaoPauloValidator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Classe responsável por armazenar métodos de validação do sistema
 * @author Victor Baptista
 * @since 01/04/2021
 * @version 1.0
 */
public class Valida {

    /*
     *Método para verificar se o campo é diferente de vazio ou nulo
     */
    public static boolean isEmptyOrNull(String args) {
        return (args.trim().equals("") || args == null);
    }//fim do método isEmptyOrNull

    /*
     *Método para verificar se o campo formatado é diferente de vazio ou nulo 
     */
    public static boolean formattedIsEmptyOrNull(String args) {
        String aux = args.trim().replaceAll("[()-./]", "");
        return (aux.trim().equals("") || aux == null);
    }//fim do método isEmptyOrNull

    /*
     * Método para verificar se o CNPJ é válido
     */
    public static boolean isCnpjInvalido(String args) {
        CNPJValidator validator = new CNPJValidator();
        try {
            validator.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /*
     * Método para verificar se o CPF é válido
     */
    public static boolean isCpfInvalido(String args) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
    
    /*
     * Método para verificar se a Incrição Estadual é válida
     */
    public static boolean isInscEstadualInvalido(String args) {
        IESaoPauloValidator validator = new IESaoPauloValidator();
        try {
            validator.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /*
     * Método para verificar se a Data de fundação é válida
     */
    public static boolean isDataInvalida(String args) {
        String formato = "dd/MM/uuuu";
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formato).withResolverStyle(ResolverStyle.STRICT);
        
        try {
            LocalDate date = LocalDate.parse(args, dateTimeFormatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }
    
    /*
     * Método verifica se o número é inteiro
     */
    public static boolean isInteger(String args) {
        try {
            Integer.parseInt(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /*
     * Método verifica se o número é double
     */
    public static boolean isDouble(String args) {
        try {
            Double.parseDouble(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /*
    * Método para verificar a seleção da comboBox
    */
    public static boolean isComboIvalida(int index) {
        return index == 0;
    }
}
