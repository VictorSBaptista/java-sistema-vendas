package controller;

import dao.EnderecoDAO;
import javax.swing.JOptionPane;
import model.Endereco;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de base de dados
 * @author Victor Baptista
 * @since 01/04/2021
 * @version 1.0
 */
public class EnderecoController {

    /*
     * Método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(Endereco endereco) {
        try {
            new EnderecoDAO().salvar(endereco);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, Mensagem.endereco_erro, Mensagem.cadastro_endereco, 0);
        }
    }
    
    /*
    * Método para excluir um objeto
    */
    public void excluir(Endereco endereco) {
        try {
            new EnderecoDAO().excluir(endereco);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.erro_excluir_endereco, Mensagem.cadastro_fornecedor, 0);
        }
    }//fim do método excluir
}
