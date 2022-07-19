package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Classe responsável por efetuar a conexão com o banco de dados
 *
 * @author Victor Baptista
 * @since 24/03/2021
 * @version 1.0
 */
public class HibernateUtil {

    //atributo para armazenar a sessão de comunicação
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /*
     * Método responsável por criar um seção de conexão com o banco de dados
     */
    private static SessionFactory buildSessionFactory() {
        return new AnnotationConfiguration().configure().buildSessionFactory();
    }

    //método para acessar o atributo de conexão e seção com o banco de dados
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
