package controller;

import java.sql.SQLException;
import javax.swing.ImageIcon;
import view.ConfirmaVendaPrazoView;

/**
 * Classe responsável por armazenar os processos de controle de tela de confirma
 * venda prazo
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class ConfirmaVendaPrazoController {

    //Atributo para manipular a tela de cadastro
    private ConfirmaVendaPrazoView tela;

    //Construtor Vazio
    public ConfirmaVendaPrazoController() {
    }

    //Construtor para valorizar o objeto de tela
    public ConfirmaVendaPrazoController(ConfirmaVendaPrazoView tela) {
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
        tela.getTfReceber().setEditable(false);
        tela.getTfVencimento().setEditable(false);
        
        //Desabilitando os radio button
        tela.getRbVencidoSim().setEnabled(false);
        tela.getRbVencidoNao().setEnabled(false);
        tela.getRbPagoSim().setEnabled(false);
        tela.getRbPagoNao().setEnabled(false);
    }
    
    /*
    * Método para murdar o ícone da tela
    */
    public void mudaIcone() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/sistema_60.png"));
        tela.setIconImage(icon.getImage());
    }
}
