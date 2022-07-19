package controller;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import view.EstoqueView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de estoque
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class EstoqueController {
    //Atributo para manipular a tela de cadastro
    private EstoqueView tela;
    
    //
    private boolean alterar;

    //Construtor Vazio
    public EstoqueController() {
    }

    //Construtor para valorizar o objeto de tela
    public EstoqueController(EstoqueView tela) {
        this.tela = tela;
    }
    
    /*
     * Método para controlar a ação do botão novo
     */
    public void botaoNovo() {
        //
        alterar = false;
        
        //Bloqueando botões de opção
        tela.getBtNovo().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
        tela.getBtExcluir().setEnabled(false);
        tela.getBtSair().setEnabled(false);

        //habilitando os TextFields
        tela.getTfQtdEstoque().setEditable(true);
        tela.getTfQtdMinima().setEditable(true);
        
        //habilitando o combo box
        tela.getCbProduto().setEnabled(true);

        //habilitando os botões
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
    }//fim do método botaoNovo
    
    /*
    * Método responsável por controlar a ação do botão alterar
    */
    public void botaoAlterar() {
        alterar = true;
    }
    
    /*
    * Método responsável por controlar a ação do botão excluir
    */
    public void botaoExcluir() {
        
    }
    
    /*
    * Método responsável por controlar a ação do botão salvar
    */
    public void botaoSalvar() {
        if (alterar) {
            //função alterar
        } else {
            //componente de inclusão de registro
        }
    }
    
    /*
     * Método para controlar a ação do botão cancelar
     */
    public void botaoCancelar() {
        //Desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);

        //desabilitando os TextFields
        tela.getTfQtdEstoque().setEditable(false);
        tela.getTfQtdMinima().setEditable(false);
        
        //desabilitando o combo box
        tela.getCbProduto().setEnabled(false);

        //desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);

        //limpando os campos da tela
        limparTela();
    }//fim do método botaoCancelar
    
    /*
     * Método para controlar a ação do botão sair
     */
    public void botaoSair() throws SQLException {
        tela.dispose();
    }//fim do método botaoSair
    
     /*
     * Método para bloqueio inicial dos objetos
     */
    public void bloqueioInicial() {
        
        //desabilitando os TextFields
        tela.getTfQtdEstoque().setEditable(false);
        tela.getTfQtdMinima().setEditable(false);
        
        //desabilitando o combo box
        tela.getCbProduto().setEnabled(false);
        
        //Desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);
    }
    
    /*
     * Método para limpar os campos da tela
     */
    private void limparTela() {
        tela.getTfQtdEstoque().setText(null);
        tela.getTfQtdMinima().setText(null);
        tela.getCbProduto().setSelectedItem("-Informe Produto/Fornecedor-");
    }//fim do método limparTela
    
     /*
     * Método para mudar a cor do painel Novo
     */
    public void mudaCorBotaoNovo() {
        tela.getjPanelSuperior().setBackground(new Color(100, 190, 90));
        tela.getBtNovo().setForeground(Color.WHITE);
        tela.getBtAlterar().setForeground(Color.WHITE);
        tela.getBtExcluir().setForeground(Color.WHITE);
        tela.getBtSair().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Novo
     */
    public void resetaCorBotaoNovo() {
        tela.getjPanelSuperior().setBackground(tela.getBtSalvar().getBackground());
        tela.getBtNovo().setForeground(Color.BLACK);
        tela.getBtAlterar().setForeground(Color.BLACK);
        tela.getBtExcluir().setForeground(Color.BLACK);
        tela.getBtSair().setForeground(Color.BLACK);
    }
    
    /*
     * Método para mudar a cor do painel Alterar
     */
    public void mudaCorBotaoAlterar() {
        tela.getjPanelSuperior().setBackground(new Color(40, 120, 205));
        tela.getBtNovo().setForeground(Color.WHITE);
        tela.getBtAlterar().setForeground(Color.WHITE);
        tela.getBtExcluir().setForeground(Color.WHITE);
        tela.getBtSair().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Alterar
     */
    public void resetaCorBotaoAlterar() {
        tela.getjPanelSuperior().setBackground(tela.getBtSalvar().getBackground());
        tela.getBtNovo().setForeground(Color.BLACK);
        tela.getBtAlterar().setForeground(Color.BLACK);
        tela.getBtExcluir().setForeground(Color.BLACK);
        tela.getBtSair().setForeground(Color.BLACK);
    }
    
    /*
     * Método para mudar a cor do painel Excluir
     */
    public void mudaCorBotaoExcluir() {
        tela.getjPanelSuperior().setBackground(new Color(235, 140, 10));
        tela.getBtNovo().setForeground(Color.WHITE);
        tela.getBtAlterar().setForeground(Color.WHITE);
        tela.getBtExcluir().setForeground(Color.WHITE);
        tela.getBtSair().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Escluir
     */
    public void resetaCorBotaoExcluir() {
        tela.getjPanelSuperior().setBackground(tela.getBtSalvar().getBackground());
        tela.getBtNovo().setForeground(Color.BLACK);
        tela.getBtAlterar().setForeground(Color.BLACK);
        tela.getBtExcluir().setForeground(Color.BLACK);
        tela.getBtSair().setForeground(Color.BLACK);
    }
    
     /*
     * Método para mudar a cor do painel Sair
     */
    public void mudaCorBotaoSair() {
        tela.getjPanelSuperior().setBackground(new Color(170, 25, 25));
        tela.getBtNovo().setForeground(Color.WHITE);
        tela.getBtAlterar().setForeground(Color.WHITE);
        tela.getBtExcluir().setForeground(Color.WHITE);
        tela.getBtSair().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Sair
     */
    public void resetaCorBotaoSair() {
        tela.getjPanelSuperior().setBackground(tela.getBtSalvar().getBackground());
        tela.getBtNovo().setForeground(Color.BLACK);
        tela.getBtAlterar().setForeground(Color.BLACK);
        tela.getBtExcluir().setForeground(Color.BLACK);
        tela.getBtSair().setForeground(Color.BLACK);
    }
    
    /*
    * Método responsável por bloquear o menu principal
    */
    public void bloquearMenu(MenuView menu) {
        menu.getBtClientes().setEnabled(false);
        menu.getBtFornecedor().setEnabled(false);
        menu.getBtFuncionarios().setEnabled(false);
        menu.getBtProdutos().setEnabled(false);
        menu.getBtVendas().setEnabled(false);
        menu.getBtCompras().setEnabled(false);
        menu.getBtSair().setEnabled(false);
        
        menu.getjMenuCadastro().setEnabled(false);
        menu.getjMenuEstoque().setEnabled(false);
        menu.getjMenuVendas().setEnabled(false);
        menu.getjMenuCompas().setEnabled(false);
        menu.getjMenuContas().setEnabled(false);
        menu.getjMenuSistema().setEnabled(false);
    }
    
    /*
    * Método responsável por desbloquear o menu principal
    */
    public void desBloquearMenu(MenuView menu) {
        menu.getBtClientes().setEnabled(true);
        menu.getBtFornecedor().setEnabled(true);
        menu.getBtFuncionarios().setEnabled(true);
        menu.getBtProdutos().setEnabled(true);
        menu.getBtVendas().setEnabled(true);
        menu.getBtCompras().setEnabled(true);
        menu.getBtSair().setEnabled(true);
        
        menu.getjMenuCadastro().setEnabled(true);
        menu.getjMenuEstoque().setEnabled(true);
        menu.getjMenuVendas().setEnabled(true);
        menu.getjMenuCompas().setEnabled(true);
        menu.getjMenuContas().setEnabled(true);
        menu.getjMenuSistema().setEnabled(true);
    }
    
    /*
    * Método para murdar o ícone da tela
    */
    public void mudaIcone() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/sistema_60.png"));
        tela.setIconImage(icon.getImage());
    }
}
