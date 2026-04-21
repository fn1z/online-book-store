package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.exception.DataProcessingException;
import com.example.onlinebookstore.model.Book;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save book: " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
