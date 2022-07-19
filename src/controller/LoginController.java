package controller;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Funcionario;
import util.Mensagem;
import util.Valida;
import view.LoginView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de Login
 *
 * @author Victor Baptista
 * @since 22/03/2021
 * @version 1.0
 */
public class LoginController {

    //Atributo para manipular a tela de cadastro
    private LoginView tela;
    
    //
    public static String nomeFuncionario;

    //Construtor vazio
    public LoginController() {
    }

    //Construtor para valorizar o objeto de tela
    public LoginController(LoginView tela) {
        this.tela = tela;
    }

    /*
     * Método para iniciar o menu
     */
    public void iniciaMenu() {

    }

    /*
     * Método para processar as informações da tela de login
     */
    private boolean validarDados() {
        if (Valida.isEmptyOrNull(tela.getTfLogin().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.loginVazio, Mensagem.login, 0);
            tela.getTfLogin().grabFocus();
            return false;
        }

        if (Valida.isEmptyOrNull(tela.getPfSenha().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.senhaVazia, Mensagem.login, 0);
            tela.getPfSenha().grabFocus();
            return false;
        }

        return true;
    }

    /*
     * Método para controlar a ação do botão Confirmar
     */
    public void botaoConfirmar() {
        if (validarDados()) {
            efetuarLogin();
        }
    }

    /*
     * Método para controlar a ação do botão Cancelar
     */
    public void botaoCancelar() {
        int confirm = JOptionPane.showConfirmDialog(null, "Deseja fechar o programa?", "Saindo do programa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /*
     * Método para murdar o ícone da tela
     */
    public void mudaIcone() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/sistema_60.png"));
        tela.setIconImage(icon.getImage());
    }

    /*
     *
     */
    private void efetuarLogin() {
        String nome = tela.getTfLogin().getText();
        String senha = tela.getPfSenha().getText();

        boolean achouLogin = false;
        boolean achouSenha = false;
        
        for (Funcionario funcionario : new FuncionarioController().buscarPorLogin(nome)) {
            achouLogin = true;
            if (funcionario.getSenha().equals(senha)) {
                nomeFuncionario = funcionario.getPessoaFisicaIdPessoaFisica().getNome();
                new MenuView().setVisible(true);
                tela.dispose();
                achouSenha = true;
                break;
            } else {
                achouSenha = false;
            }
        }
        
        if (!achouLogin || !achouSenha) {
            JOptionPane.showMessageDialog(null, Mensagem.credenciaisInvalidas, Mensagem.login, 0);
        }
    }
}
