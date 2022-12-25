package com.finalVariant.OnlineStore.controller.util;

import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;
import com.finalVariant.OnlineStore.model.service.impl.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);
    public static boolean checkUserIsLogged(HttpSession session, User user){
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>)session.getServletContext().getAttribute("loggedUsers");
        if(loggedUsers == null){
            loggedUsers = new HashSet<>();
        }
        if(loggedUsers.stream().anyMatch(userLogin::equals)){
            return true;
        }
        loggedUsers.add(userLogin);
        session.getServletContext().setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static void deleteUserFromLogged(HttpSession session){
        User user = (User)session.getAttribute("user");
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>)session.getServletContext().getAttribute("loggedUsers");
        if(loggedUsers == null){
            loggedUsers = new HashSet<>();
        }
        loggedUsers.removeIf(ul -> ul.equals(userLogin));
        session.getServletContext().setAttribute("loggedUsers", loggedUsers);
        session.removeAttribute("user");
    }

    public static void addProductToCartForUnloggedUser(HttpSession session, Product product, int quantity) {
        List<Order> cart = getCart(session);

        if (cart.stream().anyMatch((c) -> c.getProduct().equals(product))) {
            cart.forEach(order -> {
                if (order.getQuantity() + quantity > 0 && order.getProduct().equals(product)) {
                    order.setQuantity(order.getQuantity() + quantity);
                }
            });
        } else {
            cart.add(Order.createOrder(null, product, quantity, OrderStatus.Unregistered));
        }

        session.setAttribute("cart", cart);
    }

    public static void removeProductFromCartForUnloggedUser(HttpSession session, Product product){
        List<Order> cart = getCart(session);

        cart = cart.stream()
                .filter(c -> !c.getProduct().equals(product))
                .collect(Collectors.toList());

        session.setAttribute("cart", cart);
    }

    private static List<Order> getCart(HttpSession session){
        if(session.getAttribute("cart") == null){
            session.setAttribute("cart", new ArrayList<Order>());
        }
        return (List<Order>)session.getAttribute("cart");
    }

    /**
     * This method is used to extract product from the form which the admin fills in while adding products.
     * The Apache Commons FileUpload is used here.
     *
     * @param request This is a request with uploaded items for a new product.
     * @param userService With the help of this parameter we can get the required elements from the DB by id.
     * @return the new product wrapped in an {@link Optional}
     */
    public static Optional<Product> extractProductFromForm(HttpServletRequest request, UserService userService){
        final int MAX_MEMORY_SIZE = 1024 * 1024 * 6;  // 6 MB
        final int MAX_REQUEST_SIZE = 1024 * 1024 * 3;  // 3 MB

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MAX_MEMORY_SIZE); // maximum size that will be stored in memory

        String uploadFolder = request.getServletContext().getRealPath("").replaceAll("e-store.*", "") +
                "e-store\\src\\main\\webapp\\product-image";

        ServletFileUpload upload = new ServletFileUpload(factory); // create a new file upload handler
        upload.setSizeMax(MAX_REQUEST_SIZE); // maximum file size to be uploaded

        Product product;

        try{
            List<FileItem> items = upload.parseRequest(request); // parse the request to get file items

            int categoryId = Integer.parseInt(items.get(0).getString());
            int colorId = Integer.parseInt(items.get(1).getString());
            int sizeId = Integer.parseInt(items.get(2).getString());
            Product.Sex sex = Product.Sex.valueOf(items.get(3).getString());
            int price = Integer.parseInt(items.get(4).getString());
            String name = Streams.asString(items.get(5).getInputStream(), "UTF-8");
            String image = items.get(6).getName();

            Product.Category category = userService.getCategoryById(categoryId);
            Product.Color color = userService.getColorByID(colorId);
            Product.Size size = userService.getSizeByID(sizeId);

            product = Product.createProduct(0, name, price, sex, image, category, color, size);

            loadFilesToDirectory(uploadFolder, items);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(product);
    }

    /**
     * This method is used to load the product image to "product-image" folder.
     * Firstly find not form field item, then loads it to the necessary folder.
     *
     * @param uploadFolder The folder where the image should be uploaded.
     * @param items All items parsed from the sent request.
     */
    private static void loadFilesToDirectory(String uploadFolder, List<FileItem> items) throws Exception{
        for(FileItem item : items){
            if(!item.isFormField()){
                String fileName = new File(item.getName()).getName();
                String filePath = uploadFolder + File.separator + fileName;
                File uploadedFile = new File(filePath);
                item.write(uploadedFile);
            }
        }
    }
}
