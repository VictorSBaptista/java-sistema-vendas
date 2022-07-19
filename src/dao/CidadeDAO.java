package dao;

import java.util.ArrayList;
import model.Cidade;
import model.Estado;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos para acesso ao banco de dados
 * @author Diego Munhoz
 * @since 24/03/2021
 * @version 1.0
 */
public class CidadeDAO extends GenericDAO{
    
    /*
    * método para consultar os cidades por estado gravados na tabela
    */
    public ArrayList<Cidade> buscarPorEstado(Estado estado) throws Exception{
        // lista auxiliar para retornar no método
        ArrayList<Cidade> retorno = new ArrayList<>();
        // classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        // classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Cidade.class);
        // adicionando a restrição de consulta = WHERE
        criteria.add(Restrictions.eq("estadoIdEstado", estado));
        // adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idCidade"));
        // valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Cidade>) criteria.list();
        // encerrando a conexão com o banco de dados
        sessao.close();
        // retornando a lista preenchida
        return retorno;
    }// fim do método buscarTodos
    
}// fim da classe
