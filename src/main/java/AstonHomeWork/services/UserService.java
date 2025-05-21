package AstonHomeWork.services;

import AstonHomeWork.DAO.UserDAO;
import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.models.User;
import jakarta.validation.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private final UserDAO userDao;
    private final SessionFactory sessionFactory;
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.userDao = new UserDAOImpl(sessionFactory);
    }

    public void createUser(String name, String email, int age) {
        logger.info("Запуск метода создания пользователя");
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.save(user);
        } catch (ConstraintViolationException ve) {
            logger.error("Ошибка валидации при создании пользователя");
            ve.getConstraintViolations().
                            forEach(v -> {
                                System.out.println(v.getMessage());
                                System.out.println(v.getPropertyPath().toString());
                                System.out.println(v.getInvalidValue());});
            throw new IllegalArgumentException("Ошибка валидации " + ve.getMessage());
        }
    }

    public User getUserById(Long id) {
        if (userDao.findById(id) == null) {
            logger.error("Пользователь не найден");
        } else {
            return userDao.findById(id);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);

    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(Long id, String newName, String newEmail, int newAge) {
        logger.info("Запуск метода обновления пользователя");
        User user = userDao.findById(id);
        try {
            if (user != null) {
                user.setName(newName);
                user.setEmail(newEmail);
                user.setAge(newAge);
                userDao.update(user);
                logger.info("Пользователь обновлен!");
            }
        } catch (ConstraintViolationException ve) {
            logger.error("Ошибка валидации при обновлении пользователя ");
            ve.getConstraintViolations().
                    forEach(v -> {
                        System.out.println(v.getMessage());
                        System.out.println(v.getPropertyPath().toString());
                        System.out.println(v.getInvalidValue());});
            throw new IllegalArgumentException("Ошибка валидации " + ve.getMessage());
        }
    }

    public void deleteUser(Long id) {
        logger.info("Запуск метода по удалению пользователя");
        if (userDao.findById(id) != null) {
            userDao.deleteById(id);
            logger.info("Пользователь удален!");
        } else {
            logger.error("Пользователя с таким айди не существует!");
        }
    }

    public void close() {
        sessionFactory.close();
    }
}
