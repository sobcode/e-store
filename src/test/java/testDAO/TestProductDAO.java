package testDAO;

import com.finalVariant.OnlineStore.model.dao.*;
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

public class TestProductDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final ProductDao productDao = daoFactory.createProductDao();
    private final CategoryDao categoryDao = daoFactory.createCategoryDao();
    private final SizeDao sizeDao = daoFactory.createSizeDao();
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
    public void testCreateProduct(){
        Product.Category category = Product.Category.createCategory(0, "testCategory");
        Product.Color color = Product.Color.createColor(0, "testColor");
        Product.Size size = Product.Size.createSize(0, "testSize");
        Product product = Product.createProduct(0, "Test", 111, Product.Sex.Unisex, "test.jpg",
                                                category, color, size);

        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);
        productDao.create(product);

        Assertions.assertTrue(productDao.findById(product.getId()).isPresent());
    }

    @Test
    public void testFindAll(){
        Product.Category category1 = Product.Category.createCategory(0, "testCategory1");
        Product.Color color1 = Product.Color.createColor(0, "testColor1");
        Product.Size size1 = Product.Size.createSize(0, "testSize1");
        Product product1 = Product.createProduct(0, "Test1", 111, Product.Sex.Unisex, "test1.jpg",
                category1, color1, size1);

        colorDao.create(color1);
        sizeDao.create(size1);
        categoryDao.create(category1);
        productDao.create(product1);

        Product.Category category2 = Product.Category.createCategory(0, "testCategory2");
        Product.Color color2 = Product.Color.createColor(0, "testColor2");
        Product.Size size2 = Product.Size.createSize(0, "testSize2");
        Product product2 = Product.createProduct(0, "Test2", 111, Product.Sex.Unisex, "test2.jpg",
                category2, color2, size2);

        colorDao.create(color2);
        sizeDao.create(size2);
        categoryDao.create(category2);
        productDao.create(product2);

        Assertions.assertEquals(2, productDao.findAll().size());
    }

    @Test
    public void testUpdateUser(){
        Product.Category category = Product.Category.createCategory(0, "testCategory");
        Product.Color color = Product.Color.createColor(0, "testColor");
        Product.Size size = Product.Size.createSize(0, "testSize");
        Product product = Product.createProduct(0, "Test", 111, Product.Sex.Unisex, "test.jpg",
                category, color, size);

        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);
        productDao.create(product);
        product.setName("Updated");
        productDao.update(product);

        Assertions.assertEquals(product.getName(), productDao.findById(product.getId()).get().getName());
    }

    @Test
    public void testDeleteProduct(){
        Product.Category category = Product.Category.createCategory(0, "testCategory");
        Product.Color color = Product.Color.createColor(0, "testColor");
        Product.Size size = Product.Size.createSize(0, "testSize");
        Product product = Product.createProduct(0, "Test", 111, Product.Sex.Unisex, "test.jpg",
                category, color, size);

        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);
        productDao.create(product);
        productDao.delete(product);

        Assertions.assertFalse(productDao.findById(product.getId()).isPresent());
    }

    @Test
    public void testCountProductsWithFilter(){
        Product.Category category = Product.Category.createCategory(0, "testCategory");
        Product.Color color = Product.Color.createColor(0, "testColor");
        Product.Size size = Product.Size.createSize(0, "testSize");
        Product product = Product.createProduct(0, "Test", 111, Product.Sex.Unisex, "test.jpg",
                category, color, size);

        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);
        productDao.create(product);

        Assertions.assertEquals(1, productDao.findAmountOfProductsWithFilter(new String[]{"color_ID=" + color.getId()}));
    }

    @Test
    public void testFindProductWithSortLimitAndFilter(){
        Product.Category category = Product.Category.createCategory(0, "testCategory");
        Product.Color color = Product.Color.createColor(0, "testColor");
        Product.Size size = Product.Size.createSize(0, "testSize");
        Product product = Product.createProduct(0, "Test", 111, Product.Sex.Unisex, "test.jpg",
                category, color, size);

        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);
        productDao.create(product);

        Assertions.assertEquals(1, productDao.findProductWithSortLimitAndFilter("date", "asc",
                new String[]{"category_ID=" + category.getId(), "color_ID=" + color.getId(), "size_ID=" + size.getId(),
                "sex='Unisex'"}, 1, 1).size());
    }
}
