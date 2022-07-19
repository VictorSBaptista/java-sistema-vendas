package controller;

import dao.CidadeDAO;
import dao.ContatoDAO;
import dao.EnderecoDAO;
import dao.FuncionarioDAO;
import dao.PessoaFisicaDAO;
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
import model.Funcionario;
import model.PessoaFisica;
import util.Mensagem;
import util.Util;
import util.Valida;
import view.FuncionarioView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de
 * funcionário
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class FuncionarioController {

    //Atributo para manipular a tela de cadastro

    private FuncionarioView tela;

    //
    private ArrayList<Funcionario> listaFuncionarios;
    private Funcionario funcionario;
    private PessoaFisica pessoa;
    private Endereco endereco;
    private Contato contato;

    //
    private ArrayList<Estado> listaEstados;

    //
    private ArrayList<Cidade> listaCidades;

    //
    private boolean alterar;

    //Construtor vazio
    public FuncionarioController() {
    }

    //Construtor para valorizar o objeto de tela
    public FuncionarioController(FuncionarioView tela) {
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
        tela.getTfCpf().setEditable(true);
        tela.getTfRg().setEditable(true);
        tela.getTfNome().setEditable(true);
        tela.getTfDataNascimento().setEditable(true);
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfEmail().setEditable(true);
        tela.getTfLogin().setEditable(true);
        tela.getTfSenha().setEditable(true);

        //habilitando o combo box
        tela.getCbEstados().setEnabled(true);

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
            JOptionPane.showMessageDialog(null, Mensagem.selecione_funcionario, Mensagem.cadastro_funcionario, 0);
        } else {
            funcionario = listaFuncionarios.get(tela.getTabela().getSelectedRow());
            bloqueioAlterar();
            carregarTela();
        }
    }

    /*
     * Método responsável por controlar a ação do botão excluir
     */
    public void botaoExcluir() {
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_funcionario, Mensagem.cadastro_funcionario, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_funcionario, Mensagem.cadastro_funcionario, 2);
            if (opcao == JOptionPane.YES_OPTION) {
                funcionario = listaFuncionarios.get(tela.getTabela().getSelectedRow());

                new FuncionarioDAO().excluir(funcionario);
                new PessoaFisicaDAO().excluir(funcionario.getPessoaFisicaIdPessoaFisica());
                new EnderecoDAO().excluir(funcionario.getEnderecoIdEndereco());
                new ContatoDAO().excluir(funcionario.getContatoIdContato());

                JOptionPane.showMessageDialog(null, Mensagem.funcionario_excluido, Mensagem.cadastro_funcionario, 1);
                carregarTabela();
            }
        }
    }

    /*
     * Método responsável por controlar a ação do botão salvar
     */
    public void botaoSalvar() {
        if (validarDados()) {
            if (alterar) {
                //função alterar
                pessoa = funcionario.getPessoaFisicaIdPessoaFisica();
                endereco = funcionario.getEnderecoIdEndereco();
                contato = funcionario.getContatoIdContato();

            } else {
                //componente de inclusão de registro 
                funcionario = new Funcionario();
                pessoa = new PessoaFisica();
                endereco = new Endereco();
                contato = new Contato();
            }
            //
            pessoa = getPessoaFisica();
            endereco = getEndereco();
            contato = getContato();

            //
            new PessoaFisicaController().salvar(pessoa);
            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            //
            funcionario.setLogin(tela.getTfLogin().getText());
            funcionario.setSenha(tela.getTfSenha().getText());
            funcionario.setPessoaFisicaIdPessoaFisica(pessoa);
            funcionario.setEnderecoIdEndereco(endereco);
            funcionario.setContatoIdContato(contato);

            try {
                new FuncionarioDAO().salvar(funcionario);
                JOptionPane.showMessageDialog(null, Mensagem.funcionario_salvo, Mensagem.cadastro_funcionario, 1);
                limparTela();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, Mensagem.funcionario_erro, Mensagem.cadastro_funcionario, 0);
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
        tela.getTfCpf().setEditable(false);
        tela.getTfRg().setEditable(false);
        tela.getTfNome().setEditable(false);
        tela.getTfDataNascimento().setEditable(false);
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfEmail().setEditable(false);
        tela.getTfLogin().setEditable(false);
        tela.getTfSenha().setEditable(false);

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
     * Método para bloqueio inicial dos objetos
     */
    public void bloqueioInicial() {
        //Desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);

        //desabilitando os TextFields
        tela.getTfCpf().setEditable(false);
        tela.getTfRg().setEditable(false);
        tela.getTfNome().setEditable(false);
        tela.getTfDataNascimento().setEditable(false);
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfEmail().setEditable(false);
        tela.getTfLogin().setEditable(false);
        tela.getTfSenha().setEditable(false);

        //desabilitando o combo box
        tela.getCbEstados().setEnabled(false);
        tela.getCbCidades().setEnabled(false);

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
        tela.getTfCpf().setText(null);
        tela.getTfRg().setText(null);
        tela.getTfNome().setText(null);
        tela.getTfDataNascimento().setText(null);
        tela.getTfEndereco().setText(null);
        tela.getTfNumero().setText(null);
        tela.getTfComplemento().setText(null);
        tela.getTfBairro().setText(null);
        tela.getTfCep().setText(null);
        tela.getTfTelefone().setText(null);
        tela.getTfCelular().setText(null);
        tela.getTfEmail().setText(null);
        tela.getTfLogin().setText(null);
        tela.getTfSenha().setText(null);
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
                Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
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
     * Método para validar os dados da inclusão
     */
    private boolean validarDados() {
        // Validando o CPF
        if (Valida.formattedIsEmptyOrNull(tela.getTfCpf().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cpfVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfCpf().grabFocus();
            return false;
        } else if (Valida.isCpfInvalido(tela.getTfCpf().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cpfInvalido, Mensagem.cadastro_funcionario, 0);
            tela.getTfCpf().grabFocus();
            return false;
        }

        // Validando RG
        if (Valida.formattedIsEmptyOrNull(tela.getTfRg().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.rgVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfRg().grabFocus();
            return false;
        }

        // Validando o Nome
        if (Valida.isEmptyOrNull(tela.getTfNome().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.nomeVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfNome().grabFocus();
            return false;
        }

        // Validando a Data de Nacimento
        if (Valida.formattedIsEmptyOrNull(tela.getTfDataNascimento().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.dataNascimentoVazia, Mensagem.cadastro_funcionario, 0);
            tela.getTfDataNascimento().grabFocus();
            return false;
        } else if (Valida.isDataInvalida(tela.getTfDataNascimento().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.dataNascimentoInvalida, Mensagem.cadastro_funcionario, 0);
            tela.getTfDataNascimento().grabFocus();
            return false;
        }

        // Validando o Endereço
        if (Valida.isEmptyOrNull(tela.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.enderecoVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfEndereco().grabFocus();
            return false;
        }

        // Validando o Número do endereço
        if (Valida.isEmptyOrNull(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfNumero().grabFocus();
            return false;
        } else if (!Valida.isInteger(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroInvalido, Mensagem.cadastro_funcionario, 0);
            tela.getTfNumero().grabFocus();
            return false;
        }

        // Validando o Bairro
        if (Valida.isEmptyOrNull(tela.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.bairroVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfBairro().grabFocus();
            return false;
        }

        // Validando a comboBox de Estado
        if (Valida.isComboIvalida(tela.getCbEstados().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.estadoVazio, Mensagem.cadastro_funcionario, 0);
            tela.getCbEstados().grabFocus();
            return false;
        }

        // Validando a comboBox de Estado
        if (Valida.isComboIvalida(tela.getCbCidades().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidadeVazio, Mensagem.cadastro_funcionario, 0);
            tela.getCbCidades().grabFocus();
            return false;
        }

        // Validando o CEP
        if (Valida.formattedIsEmptyOrNull(tela.getTfCep().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cepVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfCep().grabFocus();
            return false;
        }

        // Validando o Celular
        if (Valida.formattedIsEmptyOrNull(tela.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.celularVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfCelular().grabFocus();
            return false;
        }

        // Validando o Email
        if (Valida.isEmptyOrNull(tela.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.emailVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfEmail().grabFocus();
            return false;
        }

        // Validando o Login
        if (Valida.isEmptyOrNull(tela.getTfLogin().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.loginVazio, Mensagem.cadastro_funcionario, 0);
            tela.getTfLogin().grabFocus();
            return false;
        }

        // Validando a Senha
        if (Valida.isEmptyOrNull(tela.getTfSenha().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.senhaVazia, Mensagem.cadastro_funcionario, 0);
            tela.getTfSenha().grabFocus();
            return false;
        }

        return true;
    }//fim do método validarDados

    /*
     * Método para retornar um novo objeto
     */
    private PessoaFisica getPessoaFisica() {
        //
        pessoa.setCpf(tela.getTfCpf().getText());
        pessoa.setRg(tela.getTfRg().getText());
        pessoa.setNome(tela.getTfNome().getText());
        pessoa.setDataNascimento(tela.getTfDataNascimento().getText());

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
     * Método responsável por chamar o DAO e carregar os funcionários cadastrados
     */
    public ArrayList<Funcionario> buscarTodos() {
        try {
            return listaFuncionarios = new FuncionarioDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.funcionario_erro_consulta, Mensagem.cadastro_funcionario, 0);
        }
        return null;
    }// fim do método buscarTodos

    /*
     * Método para carregar a tabela de funcionario
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        //limpar a tabela
        modelo.setRowCount(0);
        //carregar a tabela
        for (Funcionario funcionario : listaFuncionarios) {
            modelo.addRow(new String[]{funcionario.getPessoaFisicaIdPessoaFisica().getNome(),
                funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getNome(),
                funcionario.getContatoIdContato().getTelefone(),
                funcionario.getContatoIdContato().getCelular()});
        }
    }//fim do método carregarTabela

    /*
     * Método para carregar a tela com os dados do funcionário
     */
    private void carregarTela() {
        tela.getTfCpf().setText(funcionario.getPessoaFisicaIdPessoaFisica().getCpf());
        tela.getTfRg().setText(funcionario.getPessoaFisicaIdPessoaFisica().getRg());
        tela.getTfNome().setText(funcionario.getPessoaFisicaIdPessoaFisica().getNome());
        tela.getTfDataNascimento().setText(funcionario.getPessoaFisicaIdPessoaFisica().getDataNascimento());
        tela.getTfEndereco().setText(funcionario.getEnderecoIdEndereco().getNome());
        tela.getTfNumero().setText(funcionario.getEnderecoIdEndereco().getNumero() + "");
        tela.getTfComplemento().setText(funcionario.getEnderecoIdEndereco().getComplemento());
        tela.getTfBairro().setText(funcionario.getEnderecoIdEndereco().getBairro());
        tela.getCbEstados().setSelectedItem(funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        tela.getCbCidades().setSelectedItem(funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        tela.getTfCep().setText(funcionario.getEnderecoIdEndereco().getCep());
        tela.getTfTelefone().setText(funcionario.getContatoIdContato().getTelefone());
        tela.getTfCelular().setText(funcionario.getContatoIdContato().getCelular());
        tela.getTfEmail().setText(funcionario.getContatoIdContato().getEmail());
        tela.getTfLogin().setText(funcionario.getLogin());
        tela.getTfSenha().setText(funcionario.getSenha());
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
        tela.getTfNome().setEditable(true);
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfEmail().setEditable(true);
        tela.getTfLogin().setEditable(true);
        tela.getTfSenha().setEditable(true);
        tela.getCbEstados().setEnabled(true);
    }
    
    /*
    * 
    */
    public ArrayList<Funcionario> buscarPorLogin(String login) {
        try {
            return new FuncionarioDAO().buscarPorLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.funcionario_erro_consulta, Mensagem.login, 0);
        }
        return null;
    } 
}
