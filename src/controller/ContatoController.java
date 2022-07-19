package controller;

import dao.ContatoDAO;
import javax.swing.JOptionPane;
import model.Contato;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de base de dados
 * @author Victor Baptista
 * @since 01/04/2021
 * @version 1.0
 */
public class ContatoController {

    /*
     * Método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(Contato contato) {
        try {
            new ContatoDAO().salvar(contato);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, Mensagem.contato_erro, Mensagem.cadastro_contato, 0);
        }
    }
    
    /*
    * Método para excluir um objeto
    */
    public void excluir(Contato contato) {
        try {
            new ContatoDAO().excluir(contato);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.erro_excluir_contato, Mensagem.cadastro_fornecedor, 0);
        }
    }//fim do método excluir
}
