package AstonHomeWork.DAOTest;

import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.models.User;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
public abstract class UserDAOTest {

    protected static SessionFactory sessionFactory;

    @Container
    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13").
            withDatabaseName("asthomeworkdb").
            withUsername("postgres").
            withPassword("postgres");

    @BeforeAll
     static void Setup() {
        Configuration config = new Configuration().configure();

        config.addAnnotatedClass(User.class);
        config.setProperty("hibernate.connection.url", postgreSQLContainer.getJdbcUrl());
        config.setProperty("hibernate.connection.username", postgreSQLContainer.getUsername());
        config.setProperty("hibernate.connection.password", postgreSQLContainer.getPassword());

        sessionFactory = config.buildSessionFactory();
    }

    @AfterAll
    static void Close() {
        sessionFactory.close();
        postgreSQLContainer.stop();
    }
}

