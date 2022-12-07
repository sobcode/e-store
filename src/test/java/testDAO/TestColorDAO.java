package testDAO;

import com.finalVariant.OnlineStore.model.dao.ColorDao;
import com.finalVariant.OnlineStore.model.dao.DaoFactory;
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

public class TestColorDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final ColorDao colorDao = daoFactory.createColorDao();

    @Before
    public void dropDB() throws SQLException, FileNotFoundException {
        Connection connection = ConnectionPoolHolder.getDataSource(databaseResourceBundle).getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_PATH));
        scriptRunner.runScript(bufferedReader);
        connection.close();
    }

    @Test
    public void testCreateColor(){
        Product.Color color = Product.Color.createColor(0, "Test");

        colorDao.create(color);

        Assertions.assertTrue(colorDao.findById(color.getId()).isPresent());
    }

    @Test
    public void testFindAll(){
        Product.Color color1 = Product.Color.createColor(0, "Test1");
        Product.Color color2 = Product.Color.createColor(0, "Test2");

        colorDao.create(color1);
        colorDao.create(color2);

        Assertions.assertEquals(2, colorDao.findAll().size());
    }

    @Test
    public void testDeleteColor(){
        Product.Color color = Product.Color.createColor(0, "Test");

        colorDao.create(color);
        colorDao.delete(color);

        Assertions.assertFalse(colorDao.findById(color.getId()).isPresent());
    }

    @Test
    public void testUpdateColor(){
        Product.Color color = Product.Color.createColor(0, "Test");

        colorDao.create(color);
        color.setColor("Updated");
        colorDao.update(color);

        Assertions.assertEquals(color.getColor(), colorDao.findById(color.getId()).get().getColor());
    }
}
