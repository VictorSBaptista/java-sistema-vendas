package controller;

import java.sql.SQLException;
import javax.swing.ImageIcon;
import view.ConfirmaCompraPrazoView;

/**
 * Classe responsável por armazenar os processos de controle de tela de confirma
 * compra prazo
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class ConfirmaCompraPrazoController {

    //Atributo para manipular a tela de cadastro
    private ConfirmaCompraPrazoView tela;

    //Construtor Vazio
    public ConfirmaCompraPrazoController() {
    }

    //Construtor para valorizar o objeto de tela
    public ConfirmaCompraPrazoController(ConfirmaCompraPrazoView tela) {
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
        tela.getTfPagar().setEditable(false);
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
