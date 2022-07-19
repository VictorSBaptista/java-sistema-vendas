package controller;

import java.sql.SQLException;
import javax.swing.ImageIcon;
import view.ContasPagarView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de contas a
 * pagar
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class ContasPagarController {
    //Atributo para manipular a tela de cadastro
    private ContasPagarView tela;

    //Construtor Vazio
    public ContasPagarController() {
    }

    //Construtor para valorizar o objeto de tela
    public ContasPagarController(ContasPagarView tela) {
        this.tela = tela;
    }
    
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
        tela.getTfDataPagamento().setEditable(false);
        tela.getTfDataVencimento().setEditable(false);
        
        //desabilitando o combo box
        tela.getRbPago().setEnabled(false);
        tela.getRbNPago().setEnabled(false);
        
        //Desabilitando os botões
        tela.getBtSalvarProduto().setEnabled(false);
        tela.getBtCancelarProduto().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
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
