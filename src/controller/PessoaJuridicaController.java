package controller;

import dao.PessoaJuridicaDAO;
import javax.swing.JOptionPane;
import model.PessoaJuridica;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de base de dados
 * @author Victor Baptista
 * @since 01/04/2021
 * @version 1.0
 */
public class PessoaJuridicaController {

    /*
     * Método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(PessoaJuridica pessoa) {
        try {
            new PessoaJuridicaDAO().salvar(pessoa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, Mensagem.pessoa_juridica_erro, Mensagem.cadastro_pessoa_juridica, 0);
        }
    }
    
    /*
    * Método para excluir um objeto
    */
    public void excluir(PessoaJuridica pessoa) {
        try {
            new PessoaJuridicaDAO().excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.erro_excluir_pessoa_juridica, Mensagem.cadastro_fornecedor, 0);
        }
    }//fim do método excluir
}
