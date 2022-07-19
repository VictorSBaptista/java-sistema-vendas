package controller;

import dao.CidadeDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Cidade;
import model.Estado;

/**
 * Classe responsável por controlar os métodos de acesso a base de dados
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class CidadeController {
    
    /*
    * método para retornar os estados gravados na tabela
    */
    public ArrayList<Cidade> buscarPorEstado(Estado estado) {
        //lista auxiliar para retornar no método
        ArrayList<Cidade> retorno = null;
        
        try {
            retorno = new CidadeDAO().buscarPorEstado(estado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar estados");
            Logger.getLogger(CidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
}
