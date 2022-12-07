package testDAO;

import com.finalVariant.OnlineStore.model.dao.DaoFactory;
import com.finalVariant.OnlineStore.model.dao.SizeDao;
import com.finalVariant.OnlineStore.model.dao.impl.ConnectionPoolHolder;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestSizeDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final SizeDao sizeDao = daoFactory.createSizeDao();

    @Before
    public void dropDB() throws SQLException, FileNotFoundException {
        Connection connection = ConnectionPoolHolder.getDataSource(databaseResourceBundle).getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_PATH));
        scriptRunner.runScript(bufferedReader);
        connection.close();
    }

    @Test
    public void testCreateSize(){
        Product.Size size = Product.Size.createSize(0, "Test");

        sizeDao.create(size);

        Assertions.assertTrue(sizeDao.findById(size.getId()).isPresent());
    }

    @Test
    public void testFindAll(){
        Product.Size size1 = Product.Size.createSize(0, "Test1");
        Product.Size size2 = Product.Size.createSize(0, "Test2");

        sizeDao.create(size1);
        sizeDao.create(size2);

        Assertions.assertEquals(2, sizeDao.findAll().size());
    }

    @Test
    public void testDeleteSize(){
        Product.Size size = Product.Size.createSize(0, "Test");

        sizeDao.create(size);
        sizeDao.delete(size);

        Assertions.assertFalse(sizeDao.findById(size.getId()).isPresent());
    }

    @Test
    public void testUpdateSize(){
        Product.Size size = Product.Size.createSize(0, "Test");

        sizeDao.create(size);
        size.setSize("Updated");
        sizeDao.update(size);

        Assertions.assertEquals(size.getSize(), sizeDao.findById(size.getId()).get().getSize());
    }
}
