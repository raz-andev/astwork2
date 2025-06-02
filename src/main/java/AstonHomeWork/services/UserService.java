package AstonHomeWork.services;

import AstonHomeWork.DAO.UserDAO;
import AstonHomeWork.DAO.UserDAOImpl;
import AstonHomeWork.exceptions.InvalidDataException;
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

    public UserService(UserDAO userDao) {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
        this.userDao = userDao;
    }

    public void createUser(String name, String email, int age) {
        logger.info("Запуск метода создания пользователя");
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.save(user);
            logger.info("Пользователь создан!");
        } catch (ConstraintViolationException ve) {
            logger.error("Ошибка валидации при создании пользователя {}", ve.getMessage());
            throw new InvalidDataException("Неверно заполнены поля");
        } catch (NullPointerException npe) {
            logger.error("Поля не должны быть пустыми {}", npe.getMessage());
            throw new InvalidDataException("Поля не должны быть пустыми");
        }
    }

    public User getUserById(Long id) {
        logger.info("Запуск метода поиска пользователя");
        User user = userDao.findById(id);  // Сохраняем результат в переменную
        if (user == null) {
            logger.error("Пользователь не найден");
        } else logger.info("Пользователь найден");
        return user;
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);

    }

    public List<User> getAllUsers() {
        logger.info("Запуск метода поиска всех пользователей");
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
            logger.error("Ошибка валидации при обновлении пользователя {} ", ve.getMessage());
            throw new InvalidDataException("Неверно заполнены поля");
        } catch (NullPointerException npe) {
            logger.error("Поля не должны быть пустыми {}", npe.getMessage());
            throw new InvalidDataException("Поля не должны быть пустые");
        }
    }

    public void deleteUser(Long id) {
        logger.info("Запуск метода по удалению пользователя");
        if (userDao.findById(id) != null) {
            userDao.deleteById(id);
            logger.info("Пользователь удален!");
        } else {
            logger.error("Пользователя с id {} не существует!", id);
        }
    }

    public void close() {
        sessionFactory.close();
    }
}
