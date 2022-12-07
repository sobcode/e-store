package testDAO;

import com.finalVariant.OnlineStore.model.dao.DaoFactory;
import com.finalVariant.OnlineStore.model.dao.UserDao;
import com.finalVariant.OnlineStore.model.dao.impl.ConnectionPoolHolder;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestUserDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final UserDao userDao = daoFactory.createUserDao();


    @Before
    public void dropDB() throws SQLException, FileNotFoundException {
        Connection connection = ConnectionPoolHolder.getDataSource(databaseResourceBundle).getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_PATH));
        scriptRunner.runScript(bufferedReader);
        connection.close();
    }

    @Test
    public void testCreateUser(){
        User user = User.createUser("Test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        Assertions.assertTrue(userDao.findById(user.getId()).isPresent());
    }

    @Test
    public void TestFindByLogin(){
        User user = User.createUser("Test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        Assertions.assertEquals(user.getId(), userDao.findByLogin(user.getLogin()).get().getId());
    }

    @Test
    public void testFindAll(){
        User user1 = User.createUser("Test1", "test", User.Role.User, User.UserStatus.Unblocked);
        User user2 = User.createUser("Test2", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user1);
        userDao.create(user2);
        Assertions.assertEquals(2, userDao.findAll().size());
    }

    @Test
    public void testUpdateUser(){
        User user = User.createUser("Test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        user.setLogin("Updated");
        userDao.update(user);
        Assertions.assertEquals(user.getLogin(), userDao.findById(user.getId()).get().getLogin());
    }

    @Test
    public void testDeleteUser(){
        User user = User.createUser("Test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        userDao.delete(user);
        Assertions.assertFalse(userDao.findById(user.getId()).isPresent());
    }
}
