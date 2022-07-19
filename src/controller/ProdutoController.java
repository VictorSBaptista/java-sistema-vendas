package controller;

import dao.ProdutoDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fornecedor;
import model.Produto;
import util.Mensagem;
import util.Util;
import util.Valida;
import view.MenuView;
import view.ProdutoView;

/**
 * Classe responsável por armazenar os processos de controle de tela de Produto
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class ProdutoController {

    //Atributo para manipular a tela de cadastro

    private ProdutoView tela;

    //
    private ArrayList<Fornecedor> listaFornecedores;
    private ArrayList<Produto> listaProdutos;
    private Produto produto;

    //
    private boolean alterar;

    //Contrutor Vazio
    public ProdutoController() {
    }

    //Construtor para valorizar o objeto de tela
    public ProdutoController(ProdutoView tela) {
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
        tela.getTfDescricao().setEditable(true);
        tela.getTfValorCusto().setEditable(true);
        tela.getTfValorVenda().setEditable(true);

        //habilitando o combo box
        tela.getCbFornecedor().setEnabled(true);

        //habilitando os botões
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
    }//fim do método botaoNovo

    /*
     * Método responsável por controlar a ação do botão alterar
     */
    public void botaoAlterar() {
        alterar = true;
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_produto, Mensagem.cadastro_produto, 0);
        } else {
            produto = listaProdutos.get(tela.getTabela().getSelectedRow());
            bloqueioAlterar();
            carregarTela();
        }
    }//fim do método botaoAlterar

    /*
     * Método responsável por controlar a ação do botão excluir
     */
    public void botaoExcluir() {
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_produto, Mensagem.cadastro_produto, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_produto, Mensagem.cadastro_produto, 2);
            if (opcao == JOptionPane.YES_OPTION) {
                produto = listaProdutos.get(tela.getTabela().getSelectedRow());

                new ProdutoDAO().excluir(produto);
                
                JOptionPane.showMessageDialog(null, Mensagem.produto_excluido, Mensagem.cadastro_produto, 1);
                carregarTabela();
            }
        }
    }//fim do método botaoExcluir

    /*
     * Método responsável por controlar a ação do botão salvar
     */
    public void botaoSalvar() {
        //
        if (validarDados()) {
            if (!alterar) {
                //componente de inclusão de registro
                produto = new Produto();
            }
            
            //
            produto = getProduto();

            try {
                new ProdutoDAO().salvar(produto);
                JOptionPane.showMessageDialog(null, Mensagem.produto_salvo, Mensagem.cadastro_produto, 1);
                limparTela();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, Mensagem.produto_erro, Mensagem.cadastro_produto, 0);
            }
        }
    }//fim do método botaoSalvar

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
        tela.getTfDescricao().setEditable(false);
        tela.getTfValorCusto().setEditable(false);
        tela.getTfValorVenda().setEditable(false);

        //desabilitando o combo box
        tela.getCbFornecedor().setEnabled(false);

        //desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);

        //limpando os campos da tela
        limparTela();
    }//fim do método botaoCancelar

    /*
     * Método para bloqueio inicial dos objetos
     */
    public void bloqueioInicial() {
        //Desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);

        //desabilitando os TextFields
        tela.getTfDescricao().setEditable(false);
        tela.getTfValorCusto().setEditable(false);
        tela.getTfValorVenda().setEditable(false);

        //desabilitando o combo box
        tela.getCbFornecedor().setEnabled(false);

        //Desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);
    }

    /*
     * Método para controlar a ação do botão sair
     */
    public void botaoSair() throws SQLException {
        tela.dispose();
    }//fim do método botaoSair

    /*
     * Método para limpar os campos da tela
     */
    private void limparTela() {
        tela.getTfDescricao().setText(null);
        tela.getTfValorCusto().setText(null);
        tela.getTfValorVenda().setText(null);
        tela.getCbFornecedor().setSelectedIndex(0);
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

    /*
     * Método pata carregar a combo de Fornecedores
     */
    public void carregarFornecedores() {
        listaFornecedores = new FornecedorController().buscarTodos();
        tela.getCbFornecedor().addItem("-Selecione Fornecedor-");
        for (Fornecedor fornecedor : listaFornecedores) {
            tela.getCbFornecedor().addItem(fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        }
    }//fim do método carregarFornecedores

    /*
     * Método para validar os dados da inclusão
     */
    private boolean validarDados() {

        // Validando a Descrição
        if (Valida.isEmptyOrNull(tela.getTfDescricao().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.descricaoVazio, Mensagem.cadastro_produto, 0);
            tela.getTfDescricao().grabFocus();
            return false;
        }

        // Validando a comboBox de Fornecedores
        if (Valida.isComboIvalida(tela.getCbFornecedor().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.fornecedorVazio, Mensagem.cadastro_produto, 0);
            tela.getCbFornecedor().grabFocus();
            return false;
        }
        
        // Validando o valor de custo
        if (Valida.isEmptyOrNull(tela.getTfValorCusto().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.valorCustoVazio, Mensagem.cadastro_produto, 0);
            tela.getTfValorCusto().grabFocus();
            return false;
        } else if (!Valida.isDouble(tela.getTfValorCusto().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.valorCustoInvalido, Mensagem.cadastro_produto, 0);
            tela.getTfValorCusto().grabFocus();
            return false;
        }
        
        // Validando o valor de venda
        if (Valida.isEmptyOrNull(tela.getTfValorVenda().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.valorVendaVazio, Mensagem.cadastro_produto, 0);
            tela.getTfValorVenda().grabFocus();
            return false;
        } else if (!Valida.isDouble(tela.getTfValorVenda().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.valorVendaInvalido, Mensagem.cadastro_produto, 0);
            tela.getTfValorVenda().grabFocus();
            return false;
        }

        return true;
    }//fim do método validarDados

    /*
     * Método para retornar um novo objeto
     */
    private Produto getProduto() {
        //
        produto.setDescricao(tela.getTfDescricao().getText());
        produto.setFornecedorIdFornecedor(listaFornecedores.get(tela.getCbFornecedor().getSelectedIndex() - 1));
        produto.setValorCusto(Util.getDouble(tela.getTfValorCusto().getText()));
        produto.setValorVenda(Util.getDouble(tela.getTfValorVenda().getText()));

        return produto;
    }

    /*
     * Método responsável por chamar o DAO e carregar os produtos cadastrados
     */
    public ArrayList<Produto> buscarTodos() {
        try {
            return listaProdutos = new ProdutoDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.produto_erro_consulta, Mensagem.cadastro_produto, 0);
        }
        return null;
    }

    /*
     * Método para carregar a tabela de produtos
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        //limpar a tabela
        modelo.setRowCount(0);
        //carregar a tabela
        for (Produto produto : listaProdutos) {
            modelo.addRow(new String[]{produto.getDescricao(),
                produto.getFornecedorIdFornecedor().getPessoaJuridicaIdPessoaJuridica().getRazaoSocial(),
                produto.getValorCusto() + "",
                produto.getValorVenda() + ""});
        }
    }//fim do método carregarTabela

    /*
     * Método para bloquear os campos da ação alterar
     */
    private void bloqueioAlterar() {
        //Bloqueando botões de opção
        tela.getBtNovo().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
        tela.getBtExcluir().setEnabled(false);
        tela.getBtSair().setEnabled(false);

        //habilitando os TextFields
        tela.getTfValorCusto().setEditable(true);
        tela.getTfValorVenda().setEditable(true);

        //habilitando o combo box
        tela.getCbFornecedor().setEnabled(true);

        //habilitando os botões
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
    }

    /*
     * Método para carregar a tela com os dados do produto
     */
    private void carregarTela() {
        tela.getTfDescricao().setText(produto.getDescricao());
        tela.getCbFornecedor().setSelectedItem(produto.getFornecedorIdFornecedor().getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        tela.getTfValorCusto().setText(produto.getValorCusto() + "");
        tela.getTfValorVenda().setText(produto.getValorVenda() + "");
    }//fim do método carregar tela
}
