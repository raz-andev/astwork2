package AstonHomeWork.DAOTest;


import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest extends UserDAOTest {

    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl(sessionFactory);
    }

    @Test
    void saveAndFindByIdTest() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setAge(30);
        user.setCreate(LocalDateTime.now());

        // Проверяем сохранение и чтение
        userDAO.save(user);
        User found = userDAO.findById(user.getId());

        assertNotNull(found);
        assertEquals(user.getName(), found.getName());
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    void updateUser_ShouldModifyExistingUser() {
        User user = new User("Original", "original@test.com", 25, LocalDateTime.now());
        userDAO.save(user);

        user.setName("Updated");
        userDAO.update(user);

        User updated = userDAO.findById(user.getId());
        assertEquals("Updated", updated.getName());
    }

    @Test
    void delete_ShouldRemoveUser() {
        User user = new User("ToDelete", "delete@test.com", 40, LocalDateTime.now());
        userDAO.save(user);

        userDAO.delete(user);

        assertNull(userDAO.findById(user.getId()));
    }
}
