package AstonHomeWork.serviceTests;

import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.exceptions.InvalidDataException;
import AstonHomeWork.models.User;
import AstonHomeWork.services.UserService;
import ch.qos.logback.core.Appender;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserDAOImpl userDAO;

    @Mock
    Logger log;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userDAO = Mockito.mock(UserDAOImpl.class);
        userService = new UserService(userDAO);
    }

    @Test
    void createUserTest() {
        userService.createUser("Test","test@test.com",25);

        verify(userDAO,times(1)).save(any(User.class));
    }

    @Test
    void createInvalidMailThrowsExceptionTest() {
        doThrow(new ConstraintViolationException("Invalid lines", Set.of())).
                when(userDAO).save(any(User.class));
        InvalidDataException ide = assertThrows(InvalidDataException.class,
                () -> userService.createUser("Test","test-test.com",25));

        assertEquals("Неверно заполнены поля", ide.getMessage());
        verify(userDAO,times(1)).save(any(User.class));
    }

    @Test
    void createUserNullNameTest() {
        doThrow(new NullPointerException("Null lines")).
                when(userDAO).save(any(User.class));
        InvalidDataException ide = assertThrows(InvalidDataException.class,
                () ->userService.createUser(null,"test@test.com",25));

        assertEquals("Поля не должны быть пустыми",ide.getMessage());
        Mockito.verify(userDAO,Mockito.times(1)).save(any(User.class));
    }

    @Test
    void finaByIdTest() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setName("Test User");

        when(userDAO.findById(userId)).thenReturn(expectedUser);

        User actualUser = userService.getUserById(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
        verify(userDAO, times(1)).findById(userId);
    }

    @Test
    void updateUserTest() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");
        existingUser.setAge(25);

        when(userDAO.findById(userId)).thenReturn(existingUser);
        userService.updateUser(userId, "New Name", "new@example.com", 30);
        verify(userDAO).findById(userId);
        verify(userDAO).update(existingUser);

        assertEquals("New Name", existingUser.getName());
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals(30, existingUser.getAge());

    }

    @Test
    void updateUserNotFoundTest() {
        Long nonExistentId = 999L;
        when(userDAO.findById(nonExistentId)).thenReturn(null);

        userService.updateUser(nonExistentId, "Name", "email@example.com", 30);
        verify(userDAO, never()).update(any());
    }

    @Test
    void updateInvalidTest() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userDAO.findById(userId)).thenReturn(user);
        doThrow(new ConstraintViolationException("Invalid data", Set.of())).
                when(userDAO).update(any(User.class));

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> userService.updateUser(userId, "Name", "invalid-email", 30));

        assertEquals("Неверно заполнены поля", exception.getMessage());
    }

    @Test
    void updateUserNullTest() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userDAO.findById(userId)).thenReturn(user);
        doThrow(new NullPointerException("Null data")).
                when(userDAO).update(any(User.class));

        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> userService.updateUser(userId, null, "invalid@email.com", 30));

        assertEquals("Поля не должны быть пустые", exception.getMessage());
    }

    @Test
    void deleteUserTest () {
            Long userId = 1L;
            userService.deleteUser(userId);
    }
}
