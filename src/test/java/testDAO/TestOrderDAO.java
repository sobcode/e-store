package testDAO;

import com.finalVariant.OnlineStore.model.dao.*;
import com.finalVariant.OnlineStore.model.dao.impl.ConnectionPoolHolder;
import com.finalVariant.OnlineStore.model.dao.impl.JDBCDaoFactory;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestOrderDAO {
    private static final  String DROP_TEST_PATH = "D:\\EPAM Java course\\_FinalProject\\e-store\\db\\DB_DropTest.sql";
    private static final String databaseResourceBundle = "testDB";
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, databaseResourceBundle);
    private final ProductDao productDao = daoFactory.createProductDao();
    private final CategoryDao categoryDao = daoFactory.createCategoryDao();
    private final SizeDao sizeDao = daoFactory.createSizeDao();
    private final ColorDao colorDao = daoFactory.createColorDao();
    private final UserDao userDao = daoFactory.createUserDao();
    private final OrderDao orderDao = daoFactory.createOrderDao();

    @Before
    public void dropDB() throws SQLException, FileNotFoundException {
        Connection connection = ConnectionPoolHolder.getDataSource(databaseResourceBundle).getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_PATH));
        scriptRunner.runScript(bufferedReader);
        connection.close();
    }

    private Order createOrder(OrderStatus status){
        Product.Color color = Product.Color.createColor(0, "TestColor");
        Product.Size size = Product.Size.createSize(0, "TestSize");
        Product.Category category = Product.Category.createCategory(0, "TestCategory");
        colorDao.create(color);
        sizeDao.create(size);
        categoryDao.create(category);

        Product product = Product.createProduct(0, "TestProduct", 222, Product.Sex.Unisex, "product.",
                                                category, color, size);
        productDao.create(product);

        User user = User.createUser("TestUser", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);

        return Order.createOrder(user, product, 1, status);
    }

    private Order createOrder(OrderStatus status, User user){
        Order order = createOrder(status);
        order.setUser(user);
        return order;
    }

    @Test
    public void testCreateOrder(){
        Order order = createOrder(OrderStatus.Unregistered);

        orderDao.create(order);

        Assertions.assertTrue(orderDao.findById(order.getId()).isPresent());
    }

    @Test
    public void testFindAll(){
        Order order1 = createOrder(OrderStatus.Unregistered);
        Order order2 = createOrder(OrderStatus.Registered);

        orderDao.create(order1);
        orderDao.create(order2);

        Assertions.assertEquals(2, orderDao.findAll().size());
    }

    @Test
    public void testDeleteOrder(){
        Order order = createOrder(OrderStatus.Unregistered);

        orderDao.create(order);
        orderDao.delete(order);

        Assertions.assertFalse(orderDao.findById(order.getId()).isPresent());
    }

    @Test
    public void testUpdateOrder(){
        Order order = createOrder(OrderStatus.Unregistered);

        orderDao.create(order);
        order.setQuantity(5);
        orderDao.update(order);

        Assertions.assertEquals(order.getQuantity(), orderDao.findById(order.getId()).get().getQuantity());
    }

    @Test
    public void testFindOrdersForUserByOrderStatus(){
        User user = User.createUser("TestUser", "test", User.Role.User, User.UserStatus.Unblocked);
        Order order1 = createOrder(OrderStatus.Unregistered, user);
        Order order2 = createOrder(OrderStatus.Paid, user);

        userDao.create(user);
        orderDao.create(order1);
        orderDao.create(order2);

        Assertions.assertEquals(1, orderDao.findOrdersForUserByOrderStatus(user, OrderStatus.Paid).size());
    }

    @Test
    public void testFindAllOrdersForUser(){
        User user = User.createUser("TestUser", "test", User.Role.User, User.UserStatus.Unblocked);
        Order order1 = createOrder(OrderStatus.Unregistered, user);
        Order order2 = createOrder(OrderStatus.Paid, user);

        userDao.create(user);
        orderDao.create(order1);
        orderDao.create(order2);

        Assertions.assertEquals(2, orderDao.findAllOrdersForUser(user).size());
    }

    @Test
    public void testFindOrderForUserByOrderStatusAndProduct(){
        User user = User.createUser("TestUser", "test", User.Role.User, User.UserStatus.Unblocked);
        Order order = createOrder(OrderStatus.Unregistered, user);

        userDao.create(user);
        orderDao.create(order);
        Order result = orderDao.findOrderForUserByOrderStatusAndProduct(user, order.getProduct(), OrderStatus.Unregistered).get();

        Assertions.assertEquals(order.getId(), result.getId());
    }
}
