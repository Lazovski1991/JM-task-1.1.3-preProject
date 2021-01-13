package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(50) not NULL, " +
                " last_name VARCHAR (50) not NULL, " +
                " age INTEGER not NULL, " +
                " PRIMARY KEY (id));";
        session.createSQLQuery(sql).executeUpdate();
        tx1.commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        tx1.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tx1.commit();
    }


    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE User WHERE id = :id")
                .setParameter("id", id).executeUpdate();
        tx1.commit();
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) Util.getSessionFactory().openSession().createQuery("From User").list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("DELETE User").executeUpdate();
        tx1.commit();
    }
}
