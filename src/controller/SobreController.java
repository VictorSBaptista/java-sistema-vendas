package controller;

import java.sql.SQLException;
import javax.swing.ImageIcon;
import view.MenuView;
import view.SobreView;

/**
 * Classe responsável por armazenar os processos de controle de tela de Sobre
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class SobreController {
    //Atributo para manipular a tela de cadastro
    private SobreView tela;

    //Construtor Vazio
    public SobreController() {
    }

    //Construtor para valorizar o objeto de tela
    public SobreController(SobreView tela) {
        this.tela = tela;
    }
    
    /*
     * Método para controlar a ação do botão voltar
     */
    public void botaoVoltar() throws SQLException {
        tela.dispose();
    }//fim do método botaoVoltar
    
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
