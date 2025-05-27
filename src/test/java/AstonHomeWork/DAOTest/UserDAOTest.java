package AstonHomeWork.DAOTest;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.testcontainers.containers.PostgreSQLContainer;


public abstract class UserDAOTest {


    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");


}
