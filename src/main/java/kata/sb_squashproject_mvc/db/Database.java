/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kata.sb_squashproject_mvc.db;

import java.util.List;
import javax.persistence.Query;
import kata.sb_squashproject_mvc.model.Court;
import kata.sb_squashproject_mvc.model.Match;
import kata.sb_squashproject_mvc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author Katalin
 */
public class Database {

    private final SessionFactory sessionFactory;

    public Database() {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings from
                // hibernate.cfg.xml
                .build();

        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public User userExists(String name, String pw) {

        User currentUser = null;

        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();

        Query query = session.createQuery("SELECT u FROM User u ");
        List<User> users = query.getResultList();

        for (int i = 0; i < users.size(); i++) {

            if ((name.equals(users.get(i).getName())) && (pw.equals(users.get(i).getPassword()))) {

                currentUser = users.get(i);
            }
        }

        tr.commit();
        session.close();

        return currentUser;
    }

    public User getUserById(int id) {
        User user = null;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        user = session.get(User.class, id);

        tx.commit();
        session.close();

        return user;
    }

    public Court getCourtById(int id) {
        Court court = null;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        court = session.get(Court.class, id);

        tx.commit();
        session.close();

        return court;
    }

    public void closeDb() {

        sessionFactory.close();
    }

    public List<Match> getAllMatches() {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query q = session.createQuery("SELECT m FROM Match m ");
        List<Match> matchList = q.getResultList();
        
        for (int index = 0; index < matchList.size(); index++) {

            int player1Id = matchList.get(index).getPlayer1Id();
            int player2Id = matchList.get(index).getPlayer2Id();
            int courtId = matchList.get(index).getCourtId();
            
            Match currentMatch = matchList.get(index);

            currentMatch.setPlayer1(getUserById(player1Id));
            currentMatch.setPlayer2(getUserById(player2Id));
            currentMatch.setCourt(getCourtById(courtId));

        }

        transaction.commit();
        session.close();

        return matchList;
    }

}
