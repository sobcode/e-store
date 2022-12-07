package testDAO;

import com.finalVariant.OnlineStore.model.dao.CategoryDao;
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

public class TestCategoryDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final CategoryDao categoryDao = daoFactory.createCategoryDao();

    @Before
    public void dropDB() throws SQLException, FileNotFoundException {
        Connection connection = ConnectionPoolHolder.getDataSource(databaseResourceBundle).getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_PATH));
        scriptRunner.runScript(bufferedReader);
        connection.close();
    }

    @Test
    public void testCreateCategory(){
        Product.Category category = Product.Category.createCategory(0, "Test");

        categoryDao.create(category);

        Assertions.assertTrue(categoryDao.findById(category.getId()).isPresent());
    }

    @Test
    public void testFindAll(){
        Product.Category category1 = Product.Category.createCategory(0, "Test1");
        Product.Category category2 = Product.Category.createCategory(0, "Test2");

        categoryDao.create(category1);
        categoryDao.create(category2);

        Assertions.assertEquals(2, categoryDao.findAll().size());
    }

    @Test
    public void testUpdateCategory(){
        Product.Category category = Product.Category.createCategory(0, "Test");

        categoryDao.create(category);
        category.setName("Updated");
        categoryDao.update(category);

        Assertions.assertEquals(category.getName(), categoryDao.findById(category.getId()).get().getName());
    }

    @Test
    public void testDeleteCategory(){
        Product.Category category = Product.Category.createCategory(0, "Test");

        categoryDao.create(category);
        categoryDao.delete(category);

        Assertions.assertFalse(categoryDao.findById(category.getId()).isPresent());
    }
}
