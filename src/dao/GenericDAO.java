package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos genéricos de incluir, alterar e
 * excluir
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public abstract class GenericDAO {

    /*
     * Método genérico para incluir e alterar um objeto
     */
    public void salvar(Object obj) {
        //classe auxiliar para armazenar uma sessão com o banco de dados
        Session sessao = null;

        //classe auxiliar para efetuar transações com o banco de dados
        Transaction transacao = null;

        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();

            //incluindo ou alterando um objeto
            sessao.saveOrUpdate(obj);
            transacao.commit();
        } catch (Exception e) {
            transacao.rollback();
        } finally {
            sessao.close();
        }

    }//fim do método salvar

    /*
    * Método genérico para excluir um registro
    */
    public void excluir(Object obj) {
        //classe auxiliar para armazenar uma sessão com o banco de dados
        Session sessao = null;

        //classe auxiliar para efetuar transações com o banco de dados
        Transaction transacao = null;

        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();

            //incluindo ou alterando um objeto
            sessao.delete(obj);
            transacao.commit();
        } catch (Exception e) {
            transacao.rollback();
        } finally {
            sessao.close();
        }
    }//fim do método excluir
}//fim da classe
