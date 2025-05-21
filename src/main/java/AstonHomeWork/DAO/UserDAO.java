package AstonHomeWork.DAO;

import AstonHomeWork.models.User;

import java.util.List;

public interface UserDAO {
    // Create
    void save(User user);

    // Read
    User findById(Long id);
    List<User> findAll();
    User findByEmail(String email);

    // Update
    void update(User user);

    // Delete
    void delete(User user);
    void deleteById(Long id);
}
