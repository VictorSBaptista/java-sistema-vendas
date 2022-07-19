package controller;

import dao.PessoaFisicaDAO;
import javax.swing.JOptionPane;
import model.PessoaFisica;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de base de dados
 * @author Victor Baptista
 * @since 06/04/2021
 * @version 1.0
 */
public class PessoaFisicaController {

    /*
     * Método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(PessoaFisica pessoa) {
        try {
            new PessoaFisicaDAO().salvar(pessoa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, Mensagem.pessoa_fisica_erro, Mensagem.cadastro_pessoa_fisica, 0);
        }
    }
    
    /*
    * Método para excluir um objeto
    */
    public void excluir(PessoaFisica pessoa) {
        try {
            new PessoaFisicaDAO().excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.erro_excluir_pessoa_fisica, Mensagem.cadastro_funcionario, 0);
        }
    }//fim do método excluir
}
