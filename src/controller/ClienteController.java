package controller;

import dao.CidadeDAO;
import dao.ClienteDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cidade;
import model.Cliente;
import model.Contato;
import model.Endereco;
import model.Estado;
import model.PessoaFisica;
import model.PessoaJuridica;
import util.Mensagem;
import util.Pessoa;
import util.Util;
import util.Valida;
import view.ClienteView;
import view.MenuView;

/**
 * Classe responsável por armazenar os processos de controle de tela de Cadastro
 * de Cliente
 *
 * @author Victor Baptista
 * @since 23/03/2021
 * @version 1.0
 */
public class ClienteController {

    //Atributo para manipular a tela de cadastro
    private ClienteView tela;

    //Lista de clientes para preencher tabela, alterar e excluir
    private ArrayList<Cliente> listaClientes;

    //
    private ArrayList<Estado> listaEstados;

    //
    private ArrayList<Cidade> listaCidades;

    //Objetos para incluir ou alterar
    private Cliente cliente;
    private PessoaFisica pessoaFisica;
    private PessoaJuridica pessoaJuridica;
    private Endereco endereco;
    private Contato contato;

    //
    private boolean alterar;

    //construtor vazio
    public ClienteController() {
    }

    //Construtor para valorizar o objeto de tela
    public ClienteController(ClienteView tela) {
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
        //pessoaFísica
        tela.getTfNome().setEditable(true);
        tela.getTfCpf().setEditable(true);
        tela.getTfRg().setEditable(true);
        tela.getTfDataNascimento().setEditable(true);
        //pessoa jurídica
        tela.getTfRazaoSocial().setEditable(true);
        tela.getTfCnpj().setEditable(true);
        tela.getTfInscricaoEstadual().setEditable(true);
        tela.getTfDataFundacao().setEditable(true);
        //
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfEmail().setEditable(true);
        tela.getCbEstados().setEnabled(true);
        tela.getRbFisico().setSelected(true);
        tipoPessoaFisico();
        tela.getRbFisico().setEnabled(true);
        tela.getRbJuridico().setEnabled(true);

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
            JOptionPane.showMessageDialog(null, Mensagem.selecione_cliente, Mensagem.cadastro_cliente, 0);
        } else {
            cliente = listaClientes.get(tela.getTabela().getSelectedRow());
            bloqueioAlterar();
            carregarTela();
        }
    }

    /*
     * Método responsável por controlar a ação do botão excluir
     */
    public void botaoExcluir() {
        if (tela.getTabela().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, Mensagem.selecione_cliente, Mensagem.cadastro_cliente, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, Mensagem.excluir_cliente, Mensagem.cadastro_cliente, 2);
            if (opcao == JOptionPane.YES_OPTION) {
                cliente = listaClientes.get(tela.getTabela().getSelectedRow());

                new ClienteDAO().excluir(cliente);
                new EnderecoController().excluir(cliente.getEnderecoIdEndereco());
                new ContatoController().excluir(cliente.getContatoIdContato());

                if (cliente.getTipoPessoa().equals(Pessoa.FISICA.getTipo())) {
                    new PessoaFisicaController().excluir(cliente.getPessoaFisicaIdPessoaFisica());
                } else {
                    new PessoaJuridicaController().excluir(cliente.getPessoaJuridicaIdPessoaJuridica());

                }

                JOptionPane.showMessageDialog(null, Mensagem.cliente_excluido, Mensagem.cadastro_cliente, 1);
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
                //procedimento de alteração
                endereco = cliente.getEnderecoIdEndereco();
                contato = cliente.getContatoIdContato();

                if (tela.getRbFisico().isSelected()) {
                    pessoaFisica = cliente.getPessoaFisicaIdPessoaFisica();
                    pessoaFisica = getPessoaFisica();
                } else {
                    pessoaJuridica = cliente.getPessoaJuridicaIdPessoaJuridica();
                    pessoaJuridica = getPessoaJuridica();
                }
            } else {
                //procedimento de inclusão
                cliente = new Cliente();
                endereco = new Endereco();
                contato = new Contato();
                if (tela.getRbFisico().isSelected()) {
                    pessoaFisica = new PessoaFisica();
                    pessoaFisica = getPessoaFisica();
                } else {
                    pessoaJuridica = new PessoaJuridica();
                    pessoaJuridica = getPessoaJuridica();
                }
            }
            endereco = getEndereco();
            contato = getContato();
            cliente = getCliente();

            if (tela.getRbFisico().isSelected()) {
                new PessoaFisicaController().salvar(pessoaFisica);
            } else {
                new PessoaJuridicaController().salvar(pessoaJuridica);
            }

            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            try {
                new ClienteDAO().salvar(cliente);
                JOptionPane.showMessageDialog(null, Mensagem.cliente_salvo, Mensagem.cadastro_cliente, 1);
                limparTela();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, Mensagem.cliente_erro, Mensagem.cadastro_cliente, 0);
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

        //Desabilitando os TextFields
        //pessoaFísica
        tela.getTfNome().setEditable(false);
        tela.getTfCpf().setEditable(false);
        tela.getTfRg().setEditable(false);
        tela.getTfDataNascimento().setEditable(false);
        //pessoa jurídica
        tela.getTfRazaoSocial().setEditable(false);
        tela.getTfInscricaoEstadual().setEditable(false);
        tela.getTfCnpj().setEditable(false);
        tela.getTfDataFundacao().setEditable(false);
        //
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfEmail().setEditable(false);
        tela.getCbEstados().setEnabled(false);
        tela.getCbCidades().setEnabled(false);
        tela.getRbFisico().setEnabled(false);
        tela.getRbJuridico().setEnabled(false);

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
        escondeCampos();

        //Desbloqueando botões de opção
        tela.getBtNovo().setEnabled(true);
        tela.getBtAlterar().setEnabled(true);
        tela.getBtExcluir().setEnabled(true);
        tela.getBtSair().setEnabled(true);

        //Desabilitando os TextFields
        //pessoa física
        tela.getTfNome().setEditable(false);
        tela.getTfCpf().setEditable(false);
        tela.getTfRg().setEditable(false);
        tela.getTfDataNascimento().setEditable(false);
        //pessoa jurídica
        tela.getTfRazaoSocial().setEditable(false);
        tela.getTfInscricaoEstadual().setEditable(false);
        tela.getTfCnpj().setEditable(false);
        tela.getTfDataFundacao().setEditable(false);
        //
        tela.getTfEndereco().setEditable(false);
        tela.getTfNumero().setEditable(false);
        tela.getTfComplemento().setEditable(false);
        tela.getTfBairro().setEditable(false);
        tela.getTfCep().setEditable(false);
        tela.getTfTelefone().setEditable(false);
        tela.getTfCelular().setEditable(false);
        tela.getTfEmail().setEditable(false);
        tela.getCbEstados().setEnabled(false);
        tela.getCbCidades().setEnabled(false);
        tela.getRbFisico().setEnabled(false);
        tela.getRbJuridico().setEnabled(false);

        //Desabilitando os botões
        tela.getBtSalvar().setEnabled(false);
        tela.getBtCancelar().setEnabled(false);
    }//fim do método bloqueioInicial

    /*
     * Método para limpar os campos da tela
     */
    private void limparTela() {
        escondeCampos();
        tela.getTfCpf().setText(null);
        tela.getTfRg().setText(null);
        tela.getTfNome().setText(null);
        tela.getTfDataNascimento().setText(null);
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
        tela.getTfEmail().setText(null);
        tela.getGrpTipoPessoa().clearSelection();
        tela.getCbEstados().setSelectedIndex(0);
        tela.getCbCidades().setSelectedIndex(0);
        limparPessoaFisica();
        limparPessoaJuridica();
    }//fim do método limparTela

    /*
     * Método para limpar os campos da pessoaJuridica
     */
    private void limparPessoaJuridica() {
        tela.getTfCnpj().setText(null);
        tela.getTfInscricaoEstadual().setText(null);
        tela.getTfRazaoSocial().setText(null);
        tela.getTfDataFundacao().setText(null);
    }//fim do método limparPessoaJuridica

    /*
     * Método para limpar os campos da pessoaFisica
     */
    private void limparPessoaFisica() {
        tela.getTfCpf().setText(null);
        tela.getTfRg().setText(null);
        tela.getTfNome().setText(null);
        tela.getTfDataNascimento().setText(null);
    }//fim do métodod limparPessoaFisica

    /*
     * Método para selecionar o tipo de pessoa no cadastro de cliente
     */
    public void tipoPessoaFisico() {
        //
        limparPessoaJuridica();

        //
        tela.getjLabelNome().setVisible(true);
        tela.getjLabelCpf().setVisible(true);
        tela.getjLabelRg().setVisible(true);
        tela.getjLabelDataNascimento().setVisible(true);
        tela.getTfNome().setVisible(true);
        tela.getTfNome().grabFocus();
        tela.getTfCpf().setVisible(true);
        tela.getTfRg().setVisible(true);
        tela.getTfDataNascimento().setVisible(true);

        tela.getjLabelRazaoSocial().setVisible(false);
        tela.getjLabelCnpj().setVisible(false);
        tela.getjLabelInscEstadual().setVisible(false);
        tela.getjLabelDataFundacao().setVisible(false);
        tela.getTfRazaoSocial().setVisible(false);
        tela.getTfCnpj().setVisible(false);
        tela.getTfInscricaoEstadual().setVisible(false);
        tela.getTfDataFundacao().setVisible(false);
    }

    /*
     * Método para selecionar o tipo de pessoa no cadastro de cliente
     */
    public void tipoPessoaJuridico() {
        //
        limparPessoaFisica();

        //
        tela.getjLabelNome().setVisible(false);
        tela.getjLabelCpf().setVisible(false);
        tela.getjLabelRg().setVisible(false);
        tela.getjLabelDataNascimento().setVisible(false);
        tela.getTfNome().setVisible(false);
        tela.getTfCpf().setVisible(false);
        tela.getTfRg().setVisible(false);
        tela.getTfDataNascimento().setVisible(false);

        tela.getjLabelRazaoSocial().setVisible(true);
        tela.getjLabelCnpj().setVisible(true);
        tela.getjLabelInscEstadual().setVisible(true);
        tela.getjLabelDataFundacao().setVisible(true);
        tela.getTfRazaoSocial().setVisible(true);
        tela.getTfRazaoSocial().grabFocus();
        tela.getTfCnpj().setVisible(true);
        tela.getTfInscricaoEstadual().setVisible(true);
        tela.getTfDataFundacao().setVisible(true);
    }

    /*
     * Método para deixar ambos tipos de pessoa sem campos disponíveis
     */
    public void escondeCampos() {
        //
        tela.getjLabelNome().setVisible(false);
        tela.getjLabelCpf().setVisible(false);
        tela.getjLabelRg().setVisible(false);
        tela.getjLabelDataNascimento().setVisible(false);
        tela.getTfNome().setVisible(false);
        tela.getTfCpf().setVisible(false);
        tela.getTfRg().setVisible(false);
        tela.getTfDataNascimento().setVisible(false);

        //
        tela.getjLabelRazaoSocial().setVisible(false);
        tela.getjLabelCnpj().setVisible(false);
        tela.getjLabelInscEstadual().setVisible(false);
        tela.getjLabelDataFundacao().setVisible(false);
        tela.getTfRazaoSocial().setVisible(false);
        tela.getTfCnpj().setVisible(false);
        tela.getTfInscricaoEstadual().setVisible(false);
        tela.getTfDataFundacao().setVisible(false);
    }

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
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
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
     * Método responsável por chamar o DAO e carregar os clientes cadastrados
     */
    public ArrayList<Cliente> buscarTodos() {
        try {
            return listaClientes = new ClienteDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.cliente_erro_consulta, Mensagem.cadastro_cliente, 0);
        }
        return null;
    }// fim do método buscarTodos

    /*
     * Método para carregar a tabela de cliente
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) tela.getTabela().getModel();
        //limpar a tabela
        modelo.setRowCount(0);
        //carregar a tabela
        for (Cliente cliente : listaClientes) {
            String nome = "";
            if (cliente.getTipoPessoa().equals(Pessoa.FISICA.getTipo())) {
                nome = cliente.getPessoaFisicaIdPessoaFisica().getNome();
            } else {
                nome = cliente.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial();
            }
            modelo.addRow(new String[]{nome, cliente.getEnderecoIdEndereco().getCidadeIdCidade().getNome(),
                cliente.getContatoIdContato().getCelular(), cliente.getContatoIdContato().getEmail()});
        }
    }//fim do método carregarTabela

    /*
     * Método para validar os dados da tela
     */
    private boolean validarDados() {
        //verificando tipo de pessoa
        if (tela.getRbFisico().isSelected()) {
            //validar dados físico
            // Validando o Nome
            if (Valida.isEmptyOrNull(tela.getTfNome().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.nomeVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfNome().grabFocus();
                return false;
            }

            // Validando o CPF
            if (Valida.formattedIsEmptyOrNull(tela.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cpfVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfCpf().grabFocus();
                return false;
            } else if (Valida.isCpfInvalido(tela.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cpfInvalido, Mensagem.cadastro_cliente, 0);
                tela.getTfCpf().grabFocus();
                return false;
            }

            // Validando RG
            if (Valida.formattedIsEmptyOrNull(tela.getTfRg().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.rgVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfRg().grabFocus();
                return false;
            }

            // Validando a Data de Nacimento
            if (Valida.formattedIsEmptyOrNull(tela.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataNascimentoVazia, Mensagem.cadastro_cliente, 0);
                tela.getTfDataNascimento().grabFocus();
                return false;
            } else if (Valida.isDataInvalida(tela.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataNascimentoInvalida, Mensagem.cadastro_cliente, 0);
                tela.getTfDataNascimento().grabFocus();
                return false;
            }
        } else {
            //validar dados jurídico
            // Validando a Razão Social
            if (Valida.isEmptyOrNull(tela.getTfRazaoSocial().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.razaoSocialVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfRazaoSocial().grabFocus();
                return false;
            }

            // Validando o CNPJ
            if (Valida.formattedIsEmptyOrNull(tela.getTfCnpj().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cnpjVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfCnpj().grabFocus();
                return false;
            } else if (Valida.isCnpjInvalido(tela.getTfCnpj().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cnpjInvalido, Mensagem.cadastro_cliente, 0);
                tela.getTfCnpj().grabFocus();
                return false;
            }

            // Validando a Incrição Estadual
            if (Valida.formattedIsEmptyOrNull(tela.getTfInscricaoEstadual().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.inscEstadualVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfInscricaoEstadual().grabFocus();
                return false;
            } else if (Valida.isInscEstadualInvalido(tela.getTfInscricaoEstadual().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.inscEstadualInvalida, Mensagem.cadastro_cliente, 0);
                tela.getTfInscricaoEstadual().grabFocus();
                return false;
            }

            // Validando a Data de Fundação
            if (Valida.formattedIsEmptyOrNull(tela.getTfDataFundacao().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataFundacaoVazio, Mensagem.cadastro_cliente, 0);
                tela.getTfDataFundacao().grabFocus();
                return false;
            } else if (Valida.isDataInvalida(tela.getTfDataFundacao().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataFundacaoInvalida, Mensagem.cadastro_cliente, 0);
                tela.getTfDataFundacao().grabFocus();
                return false;
            }
        }

        // Validando o Endereço
        if (Valida.isEmptyOrNull(tela.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.enderecoVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfEndereco().grabFocus();
            return false;
        }

        // Validando o Número do endereço
        if (Valida.isEmptyOrNull(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfNumero().grabFocus();
            return false;
        } else if (!Valida.isInteger(tela.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroInvalido, Mensagem.cadastro_cliente, 0);
            tela.getTfNumero().grabFocus();
            return false;
        }

        // Validando o Bairro
        if (Valida.isEmptyOrNull(tela.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.bairroVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfBairro().grabFocus();
            return false;
        }

        // Validando o CEP
        if (Valida.formattedIsEmptyOrNull(tela.getTfCep().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cepVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfCep().grabFocus();
            return false;
        }

        // Validando a comboBox de Estado
        if (Valida.isComboIvalida(tela.getCbEstados().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.estadoVazio, Mensagem.cadastro_cliente, 0);
            tela.getCbEstados().grabFocus();
            return false;
        }

        // Validando a comboBox de Cidade
        if (Valida.isComboIvalida(tela.getCbCidades().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidadeVazio, Mensagem.cadastro_cliente, 0);
            tela.getCbCidades().grabFocus();
            return false;
        }

        // Validando o Celular
        if (Valida.formattedIsEmptyOrNull(tela.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.celularVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfCelular().grabFocus();
            return false;
        }

        // Validando o Email
        if (Valida.isEmptyOrNull(tela.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.emailVazio, Mensagem.cadastro_cliente, 0);
            tela.getTfEmail().grabFocus();
            return false;
        }

        return true;
    }//fim do método validarDados

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
     * Método para retornar um novo objeto
     */
    private PessoaFisica getPessoaFisica() {
        //
        pessoaFisica.setCpf(tela.getTfCpf().getText());
        pessoaFisica.setRg(tela.getTfRg().getText());
        pessoaFisica.setNome(tela.getTfNome().getText());
        pessoaFisica.setDataNascimento(tela.getTfDataNascimento().getText());

        return pessoaFisica;
    }

    /*
     * Método para retornar um novo objeto
     */
    private PessoaJuridica getPessoaJuridica() {
        //
        pessoaJuridica.setCnpj(tela.getTfCnpj().getText());
        pessoaJuridica.setInscricaoEstadual(tela.getTfInscricaoEstadual().getText());
        pessoaJuridica.setRazaoSocial(tela.getTfRazaoSocial().getText());
        pessoaJuridica.setDataFundacao(tela.getTfDataFundacao().getText());

        return pessoaJuridica;
    }

    /*
     * Método para retornar um objeto
     */
    private Cliente getCliente() {
        if (tela.getRbFisico().isSelected()) {
            cliente.setTipoPessoa(Pessoa.FISICA.getTipo());
            cliente.setPessoaFisicaIdPessoaFisica(pessoaFisica);
        } else {
            cliente.setTipoPessoa(Pessoa.JURIDICA.getTipo());
            cliente.setPessoaJuridicaIdPessoaJuridica(pessoaJuridica);
        }
        cliente.setEnderecoIdEndereco(endereco);
        cliente.setContatoIdContato(contato);

        return cliente;
    }

    /*
     * Método para carregar a tela com os dados do cliente
     */
    private void carregarTela() {
        if (cliente.getTipoPessoa().equals(Pessoa.FISICA.getTipo())) {
            tela.getRbFisico().setSelected(true);
            tipoPessoaFisico();
            tela.getTfNome().setText(cliente.getPessoaFisicaIdPessoaFisica().getNome());
            tela.getTfCpf().setText(cliente.getPessoaFisicaIdPessoaFisica().getCpf());
            tela.getTfRg().setText(cliente.getPessoaFisicaIdPessoaFisica().getRg());
            tela.getTfDataNascimento().setText(cliente.getPessoaFisicaIdPessoaFisica().getDataNascimento());
        } else {
            tela.getRbJuridico().setSelected(true);
            tipoPessoaJuridico();
            tela.getTfRazaoSocial().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
            tela.getTfCnpj().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getCnpj());
            tela.getTfInscricaoEstadual().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getInscricaoEstadual());
            tela.getTfDataFundacao().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getDataFundacao());
        }
        tela.getTfEndereco().setText(cliente.getEnderecoIdEndereco().getNome());
        tela.getTfNumero().setText(cliente.getEnderecoIdEndereco().getNumero() + "");
        tela.getTfComplemento().setText(cliente.getEnderecoIdEndereco().getComplemento());
        tela.getTfBairro().setText(cliente.getEnderecoIdEndereco().getBairro());
        tela.getCbEstados().setSelectedItem(cliente.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        tela.getCbCidades().setSelectedItem(cliente.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        tela.getTfCep().setText(cliente.getEnderecoIdEndereco().getCep());
        tela.getTfTelefone().setText(cliente.getContatoIdContato().getTelefone());
        tela.getTfCelular().setText(cliente.getContatoIdContato().getCelular());
        tela.getTfEmail().setText(cliente.getContatoIdContato().getEmail());
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
        if (cliente.getTipoPessoa().equals(Pessoa.FISICA.getTipo())) {
            tipoPessoaFisico();
            tela.getTfNome().setEditable(true);
        } else {
            tipoPessoaJuridico();
            tela.getTfRazaoSocial().setEditable(true);
        }
        tela.getTfEndereco().setEditable(true);
        tela.getTfNumero().setEditable(true);
        tela.getTfComplemento().setEditable(true);
        tela.getTfBairro().setEditable(true);
        tela.getTfCep().setEditable(true);
        tela.getTfTelefone().setEditable(true);
        tela.getTfCelular().setEditable(true);
        tela.getTfEmail().setEditable(true);
        tela.getCbEstados().setEnabled(true);
    }
}
