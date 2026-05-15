package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.exception.DataProcessingException;
import com.example.onlinebookstore.model.Book;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(book);
                transaction.commit();
                return book;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (Exception e) {
            throw new DataProcessingException("Can't save book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Book.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }
}
