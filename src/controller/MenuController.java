package controller;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import view.ClienteView;
import view.CompraView;
import view.ContasPagarView;
import view.ContasReceberView;
import view.EstoqueView;
import view.FornecedorView;
import view.FuncionarioView;
import view.LoginView;
import view.MenuView;
import view.ProdutoView;
import view.SobreView;
import view.VendaView;

/**
 * Classe responsável por armazenar os processos de controle de tela de Menu
 *
 * @author Victor Baptista
 * @since 22/03/2021
 * @version 1.0
 */
public class MenuController {

    //Atributo para manipular a tela de menu
    private MenuView tela;

    //Construtor vazio
    public MenuController() {
    }

    //Construtor para valorizar o objeto de tela
    public MenuController(MenuView tela) {
        this.tela = tela;
    }

    /*
     * Método para executar a tela de Cliente
     */
    public void iniciaCliente() {
        new ClienteView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Fornecedor
     */
    public void iniciaFornecedor() {
        new FornecedorView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Funcionários
     */
    public void iniciaFuncionario() {
        new FuncionarioView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Produtos
     */
    public void iniciaProduto() {
        new ProdutoView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Estoque
     */
    public void iniciaEstoque() {
        new EstoqueView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Vendas
     */
    public void iniciaVenda() {
        new VendaView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Compras
     */
    public void iniciaCompra() {
        new CompraView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Contas a Receber
     */
    public void iniciaContasReceber() {
        new ContasReceberView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Contas a Pagar
     */
    public void iniciaContasPagar() {
        new ContasPagarView(tela).setVisible(true);
    }

    /*
     * Método para executar a tela de Sair
     */
    public void iniciaSair() {
        new LoginView().setVisible(true);
    }

    /*
     * Método para executar a tela sobre
     */
    public void iniciaSobre() {
        new SobreView(tela).setVisible(true);
    }
    
    /*
     * Método para controlar a ação do botão e item menu sair
     */
    public void botaoSair() throws SQLException {
        int confirm = JOptionPane.showConfirmDialog(null, "Deseja fechar o programa?", "Saindo do programa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//fim do método botaoSair
    
    /*
     * Método para controlar a ação do item menu logout
     */
    public void itemMenuLogout() throws SQLException {
        tela.dispose();
        new LoginView().setVisible(true);
    }//fim do método itemMenuLogout

    /*
     * Método para mudar a cor do painel Clientes
     */
    public void mudaCorBotaoCliente() {
        tela.getjPanelCliente().setBackground(new Color(55, 135, 230));
        tela.getjPanelLateralDireito().setBackground(new Color(55, 135, 230));
        tela.getBtClientes().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Clientes
     */
    public void resetaCorBotaoCliente() {
        tela.getjPanelCliente().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtClientes().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel Fornecedor
     */
    public void mudaCorBotaoFornecedor() {
        tela.getjPanelFornecedor().setBackground(new Color(105, 200, 100));
        tela.getjPanelLateralDireito().setBackground(new Color(105, 200, 100));
        tela.getBtFornecedor().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Fornecedor
     */
    public void resetaCorBotaoFornecedor() {
        tela.getjPanelFornecedor().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtFornecedor().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel Funcionários
     */
    public void mudaCorBotaoFuncionario() {
        tela.getjPanelFuncionario().setBackground(new Color(160, 105, 230));
        tela.getjPanelLateralDireito().setBackground(new Color(160, 105, 230));
        tela.getBtFuncionarios().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Funcionários
     */
    public void resetaCorBotaoFuncionario() {
        tela.getjPanelFuncionario().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtFuncionarios().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel Produto
     */
    public void mudaCorBotaoProduto() {
        tela.getjPanelProduto().setBackground(new Color(200, 110, 60));
        tela.getjPanelLateralDireito().setBackground(new Color(200, 110, 60));
        tela.getBtProdutos().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Produto
     */
    public void resetaCorBotaoProduto() {
        tela.getjPanelProduto().setBackground(tela.getjPanelCliente().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelCliente().getBackground());
        tela.getBtProdutos().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel venda
     */
    public void mudaCorBotaoVenda() {
        tela.getjPanelVenda().setBackground(new Color(3, 190, 72));
        tela.getjPanelLateralDireito().setBackground(new Color(3, 190, 72));
        tela.getBtVendas().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Venda
     */
    public void resetaCorBotaoVenda() {
        tela.getjPanelVenda().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtVendas().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel Compra
     */
    public void mudaCorBotaoCompra() {
        tela.getjPanelCompra().setBackground(new Color(230, 190, 25));
        tela.getjPanelLateralDireito().setBackground(new Color(230, 190, 25));
        tela.getBtCompras().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Compra
     */
    public void resetaCorBotaoCompra() {
        tela.getjPanelCompra().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtCompras().setForeground(Color.BLACK);
    }

    /*
     * Método para mudar a cor do painel Sair
     */
    public void mudaCorBotaoSair() {
        tela.getjPanelSair().setBackground(new Color(170, 25, 25));
        tela.getjPanelLateralDireito().setBackground(new Color(170, 25, 25));
        tela.getBtSair().setForeground(Color.WHITE);
        tela.getjLabelUsuario().setForeground(Color.WHITE);
        tela.getjLabel1().setForeground(Color.WHITE);
        tela.getjLabel3().setForeground(Color.WHITE);
        tela.getjLabel4().setForeground(Color.WHITE);
    }

    /*
     * Método para reseta a cor do painel Sair
     */
    public void resetaCorBotaoSair() {
        tela.getjPanelSair().setBackground(tela.getjPanelProduto().getBackground());
        tela.getjPanelLateralDireito().setBackground(tela.getjPanelProduto().getBackground());
        tela.getBtSair().setForeground(Color.BLACK);
        tela.getjLabelUsuario().setForeground(Color.BLACK);
        tela.getjLabel1().setForeground(Color.BLACK);
        tela.getjLabel3().setForeground(Color.BLACK);
        tela.getjLabel4().setForeground(Color.BLACK);
    }

    /*
     * Método para retornar o horário atual
     */
    public static java.util.Date getDate() {
        java.util.Date date = new java.util.Date();
        return date;
    }

    /*
     * Método para processar a data atual
     */
    public void date() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        tela.getjLabel3().setText(dateString);

    }

    /*
     * Método para processar o horário atual
     */
    public void liveTime() {
        Thread t = new Thread(runnable);
        t.start();
    }
    
    Runnable runnable = new Runnable(){
        
        @Override
        public void run() {
            while (true) {                
                Date date = getDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                String dateString = simpleDateFormat.format(date);
                tela.getjLabel4().setText(dateString);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    
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
    public void carregarUsuario() {
        tela.getjLabelUsuario().setText(LoginController.nomeFuncionario);
    }
}
