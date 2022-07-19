package controller;

import dao.CidadeDAO;
import dao.FornecedorDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cidade;
import model.Contato;
import model.Endereco;
import model.Estado;
import model.Fornecedor;
import model.PessoaJuridica;
import util.Mensagem;
import util.Util;
import util.Valida;
import view.FornecedorView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de
 * fornecedor
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class FornecedorController {

    //Atributo para manipular a tela de cadastro
    private FornecedorView tela;

    //
    private ArrayList<Estado> listaEstados;

    //
    private ArrayList<Cidade> listaCidades;

    //
    private ArrayList<Fornecedor> listaFornecedores;
    private Fornecedor fornecedor;
    private PessoaJuridica pessoa;
    private Endereco endereco;
    private Contato contato;

    //
    private boolean alterar;

    //Construtor Vazio
    public FornecedorController() {
    }

    //Construtor para valorizar o objeto de tela
    public FornecedorController(FornecedorView tela) {
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
        tela.getTfCnpj().setEditable(true);
        tela.getTfInscricaoEstadual().setEditable(true);
        tela.getTfRazaoSocial().setEditable(true);
        tela.getTfDataFundacao().setEditable(true);
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfContato().setEditable(true);
        tela.getTfEmail().setEditable(true);

        //habilitando o combo box
        tela.getCbEstados().setEnabled(true);

        //habilitando os botões
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);
    }//fim do método botaoNovo

    /*
     * Método para controlar a ação do botão Alterar
     */
    public void botaoAlterar() {
        alterar = true;
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_fornecedor, Mensagem.cadastro_fornecedor, 0);
        } else {
            fornecedor = listaFornecedores.get(tela.getTabela().getSelectedRow());
            bloqueioAlterar();
            carregarTela();
        }
    }

    /*
     * Método para controlar a ação do botão Excluir
     */
    public void botaoExcluir() {
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_fornecedor, Mensagem.cadastro_fornecedor, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_fornecedor, Mensagem.cadastro_fornecedor, 2);
            if (opcao == JOptionPane.YES_OPTION) {
            fornecedor = listaFornecedores.get(tela.getTabela().getSelectedRow());
            
            new FornecedorDAO().excluir(fornecedor);
            new PessoaJuridicaController().excluir(fornecedor.getPessoaJuridicaIdPessoaJuridica());
            new EnderecoController().excluir(fornecedor.getEnderecoIdEndereco());
            new ContatoController().excluir(fornecedor.getContatoIdContato());
            
            JOptionPane.showMessageDialog(null, Mensagem.fornecedor_excluido, Mensagem.cadastro_fornecedor, 1);
            
            carregarTabela();
            }
        }
    }

    /*
     * Método para controlar a ação do botão Salvar
     */
    public void botaoSalvar() {
        if (validarDados()) {

            if (alterar) {
                //função de alteração
                pessoa = fornecedor.getPessoaJuridicaIdPessoaJuridica();
                endereco = fornecedor.getEnderecoIdEndereco();
                contato = fornecedor.getContatoIdContato();

            } else {
                //componente de inclusão de registro
                fornecedor = new Fornecedor();
                pessoa = new PessoaJuridica();
                endereco = new Endereco();
                contato = new Contato();
            }
            //
            pessoa = getPessoaJuridica();
            endereco = getEndereco();
            contato = getContato();

            //
            new PessoaJuridicaController().salvar(pessoa);
            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            //
            fornecedor.setContato(tela.getTfContato().getText());
            fornecedor.setPessoaJuridicaIdPessoaJuridica(pessoa);
            fornecedor.setEnderecoIdEndereco(endereco);
            fornecedor.setContatoIdContato(contato);

            try {
                new FornecedorDAO().salvar(fornecedor);
                JOptionPane.showMessageDialog(null, Mensagem.fornecedor_salvo, Mensagem.cadastro_fornecedor, 1);
                limparTela();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, Mensagem.fornecedor_erro, Mensagem.cadastro_fornecedor, 0);
            }

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
        tela.getTfCnpj().setEditable(false);
        tela.getTfInscricaoEstadual().setEditable(false);
        tela.getTfRazaoSocial().setEditable(false);
        tela.getTfDataFundacao().setEditable(false);
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfContato().setEditable(false);
        tela.getTfEmail().setEditable(false);

        //desabilitando o combo box
        tela.getCbEstados().setEnabled(false);
        tela.getCbCidades().setEnabled(false);

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
        //Desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);

        //desabilitando os TextFields
        tela.getTfCnpj().setEditable(false);
        tela.getTfInscricaoEstadual().setEditable(false);
        tela.getTfRazaoSocial().setEditable(false);
        tela.getTfDataFundacao().setEditable(false);
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfContato().setEditable(false);
        tela.getTfEmail().setEditable(false);

        //desabilitando o combo box
        tela.getCbEstados().setEnabled(false);
        tela.getCbCidades().setEnabled(false);

        //Desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);
    }

    /*
     * Método para limpar os campos da tela
     */
    private void limparTela() {
        tela.getTfCnpj().setText(null);
        tela.getTfInscricaoEstadual().setText(null);
        tela.getTfRazaoSocial().setText(null);
        tela.getTfDataFundacao().setText(null);
        tela.getTfEndereco().setText(null);
        tela.getTfNumero().setText(null);
        tela.getTfComplemento().setText(null);
        tela.getTfBairro().setText(null);
        tela.getTfCep().setText(null);
        tela.getTfTelefone().setText(null);
        tela.getTfCelular().setText(null);
        tela.getTfContato().setText(null);
        tela.getTfEmail().setText(null);
        tela.getCbEstados().setSelectedIndex(0);
        tela.getCbCidades().setSelectedIndex(0);
    }//fim do método limparTela

    /*
     * Método para carregar a combo de estados
     */
    public void carregarComboEstados() {
        listaEstados = new EstadoController().buscarTodos();
        tela.getCbEstados().addItem("-Selecione Estado-");
        for (Estado estado : listaEstados) {
            tela.getCbEstados().addItem(estado.getNome());
        }
    }

    /*
     * Método para carregar a combo de cidades
     */
    public void carregarComboCidade() {
        int indice = tela.getCbEstados().getSelectedIndex() - 1;
        if (indice >= 0) {
            try {
                listaCidades = new CidadeDAO().buscarPorEstado(listaEstados.get(indice));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar cidades");
                Logger.getLogger(FornecedorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            // removendo todos os dados da combo
            tela.getCbCidades().removeAllItems();
            tela.getCbCidades().addItem("-Selecione Cidade-");
            for (Cidade cidade : listaCidades) {
                tela.getCbCidades().addItem(cidade.getNome());
            }
            tela.getCbCidades().setEnabled(true);
        }
    }

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
     * Método para validar os dados
     */
    private boolean validarDados() {

        // Validando o CNPJ
        if (Valida.formattedIsEmptyOrNull(tela.getTfCnpj().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cnpjVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfCnpj().grabFocus();
            return false;
        } else if (Valida.isCnpjInvalido(tela.getTfCnpj().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cnpjInvalido, Mensagem.cadastro_fornecedor, 0);
            tela.getTfCnpj().grabFocus();
            return false;
        }

        // Validando a Incrição Estadual
        if (Valida.formattedIsEmptyOrNull(tela.getTfInscricaoEstadual().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.inscEstadualVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfInscricaoEstadual().grabFocus();
            return false;
        } else if (Valida.isInscEstadualInvalido(tela.getTfInscricaoEstadual().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.inscEstadualInvalida, Mensagem.cadastro_fornecedor, 0);
            tela.getTfInscricaoEstadual().grabFocus();
            return false;
        }

        // Validando a Razão Social
        if (Valida.isEmptyOrNull(tela.getTfRazaoSocial().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.razaoSocialVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfRazaoSocial().grabFocus();
            return false;
        }

        // Validando a Data de Fundação
        if (Valida.formattedIsEmptyOrNull(tela.getTfDataFundacao().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.dataFundacaoVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfDataFundacao().grabFocus();
            return false;
        } else if (Valida.isDataInvalida(tela.getTfDataFundacao().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.dataFundacaoInvalida, Mensagem.cadastro_fornecedor, 0);
            tela.getTfDataFundacao().grabFocus();
            return false;
        }

        // Validando o Endereço
        if (Valida.isEmptyOrNull(tela.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.enderecoVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfEndereco().grabFocus();
            return false;
        }

        // Validando o Número do endereço
        if (Valida.isEmptyOrNull(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfNumero().grabFocus();
            return false;
        } else if (!Valida.isInteger(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroInvalido, Mensagem.cadastro_fornecedor, 0);
            tela.getTfNumero().grabFocus();
            return false;
        }

        // Validando o Bairro
        if (Valida.isEmptyOrNull(tela.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.bairroVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfBairro().grabFocus();
            return false;
        }

        // Validando a comboBox de Estado
        if (Valida.isComboIvalida(tela.getCbEstados().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.estadoVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getCbEstados().grabFocus();
            return false;
        }

        // Validando a comboBox de Cidade
        if (Valida.isComboIvalida(tela.getCbCidades().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidadeVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getCbCidades().grabFocus();
            return false;
        }

        // Validando o CEP
        if (Valida.formattedIsEmptyOrNull(tela.getTfCep().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cepVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfCep().grabFocus();
            return false;
        }

        // Validando o Celular
        if (Valida.formattedIsEmptyOrNull(tela.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.celularVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfCelular().grabFocus();
            return false;
        }

        // Validando o Email
        if (Valida.isEmptyOrNull(tela.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.emailVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfEmail().grabFocus();
            return false;
        }

        // Validando o Contato
        if (Valida.isEmptyOrNull(tela.getTfContato().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.contatoVazio, Mensagem.cadastro_fornecedor, 0);
            tela.getTfContato().grabFocus();
            return false;
        }

        return true;
    }//fim do método validarDados

    /*
     * Método para retornar um novo objeto
     */
    private PessoaJuridica getPessoaJuridica() {
        //
        pessoa.setCnpj(tela.getTfCnpj().getText());
        pessoa.setInscricaoEstadual(tela.getTfInscricaoEstadual().getText());
        pessoa.setRazaoSocial(tela.getTfRazaoSocial().getText());
        pessoa.setDataFundacao(tela.getTfDataFundacao().getText());

        return pessoa;
    }

    /*
     * Método para retornar um novo objeto
     */
    private Endereco getEndereco() {
        //
        endereco.setNome(tela.getTfEndereco().getText());
        endereco.setNumero(Util.getInteger(tela.getTfNumero().getText()));
        endereco.setComplemento(tela.getTfComplemento().getText());
        endereco.setBairro(tela.getTfBairro().getText());
        endereco.setCep(tela.getTfCep().getText());
        endereco.setCidadeIdCidade(listaCidades.get(tela.getCbCidades().getSelectedIndex() - 1));

        return endereco;
    }

    /*
     * Método para retornar um novo objeto
     */
    private Contato getContato() {
        //
        contato.setTelefone(tela.getTfTelefone().getText());
        contato.setCelular(tela.getTfCelular().getText());
        contato.setEmail(tela.getTfEmail().getText());

        return contato;
    }

    /*
     * Método responsável por chamar o DAO e carregar os fornecedores cadastrados
     */
    public ArrayList<Fornecedor> buscarTodos() {
        try {
            return listaFornecedores = new FornecedorDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.fornecedor_erro_consulta, Mensagem.cadastro_fornecedor, 0);
        }
        return null;
    }// fim do método buscarTodos

    /*
     * Método para carregar a tabela de fornecedor
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        //limpar a tabela
        modelo.setRowCount(0);
        //carregar a tabela
        for (Fornecedor fornecedor : listaFornecedores) {
            modelo.addRow(new String[]{fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial(),
                fornecedor.getContatoIdContato().getTelefone(),
                fornecedor.getContato(),
                fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getNome()});
        }
    }//fim do método carregarTabela

    /*
     * Método para carregar a tela com os dados do fornecedor
     */
    private void carregarTela() {
        tela.getTfCnpj().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getCnpj());
        tela.getTfInscricaoEstadual().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getInscricaoEstadual());
        tela.getTfRazaoSocial().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        tela.getTfDataFundacao().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getDataFundacao());
        tela.getTfEndereco().setText(fornecedor.getEnderecoIdEndereco().getNome());
        tela.getTfNumero().setText(fornecedor.getEnderecoIdEndereco().getNumero() + "");
        tela.getTfComplemento().setText(fornecedor.getEnderecoIdEndereco().getComplemento());
        tela.getTfBairro().setText(fornecedor.getEnderecoIdEndereco().getBairro());
        tela.getCbEstados().setSelectedItem(fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        tela.getCbCidades().setSelectedItem(fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        tela.getTfCep().setText(fornecedor.getEnderecoIdEndereco().getCep());
        tela.getTfTelefone().setText(fornecedor.getContatoIdContato().getTelefone());
        tela.getTfCelular().setText(fornecedor.getContatoIdContato().getCelular());
        tela.getTfEmail().setText(fornecedor.getContatoIdContato().getEmail());
        tela.getTfContato().setText(fornecedor.getContato());
    }//fim do método carregarTela

    /*
     * Método para bloquear os campos da ação alterar
     */
    private void bloqueioAlterar() {
        //Bloqueando botões de opção
        tela.getBtNovo().setEnabled(false);
        tela.getBtAlterar().setEnabled(false);
        tela.getBtExcluir().setEnabled(false);
        tela.getBtSair().setEnabled(false);
        tela.getBtSalvar().setEnabled(true);
        tela.getBtCancelar().setEnabled(true);

        //
        tela.getTfRazaoSocial().setEditable(true);
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfContato().setEditable(true);
        tela.getTfEmail().setEditable(true);
        tela.getCbEstados().setEnabled(true);
    }
}
