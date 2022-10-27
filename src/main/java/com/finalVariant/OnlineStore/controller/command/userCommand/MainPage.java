package com.finalVariant.OnlineStore.controller.command.userCommand;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPage implements Command {
    private final Logger logger = LogManager.getLogger(MainPage.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");

        if(session.getAttribute("sortBy") == null || request.getParameter("sortBy") != null){
            session.setAttribute("sortBy", request.getParameter("sortBy") == null ? "date" : request.getParameter("sortBy"));
        }

        if(session.getAttribute("order") == null || request.getParameter("order") != null){
            session.setAttribute("order", request.getParameter("order") == null ? "asc" : request.getParameter("order"));
        }

        if(session.getAttribute("filterParam") == null || request.getParameterValues("filterParam") != null){
            session.setAttribute("filterParam", request.getParameterValues("filterParam"));
        }

        if(user == null || user.getRole() == User.Role.User){
            List<Product> products;

            try{
                String[] filterParams = session.getAttribute("filterParam") == null ? new String[0]
                                      : (String[]) session.getAttribute("filterParam");

                int amountOfProducts = adminService.getAmountOfProducts(filterParams);
                String sortBy = (String) session.getAttribute("sortBy");
                String order = (String) session.getAttribute("order");

                int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
                int from = (page - 1) * 8 + 1;
                int to = amountOfProducts - from > 7 ? from + 7 : amountOfProducts;

                List<Integer> pages = new ArrayList<>();

                if(amountOfProducts > 8){
                    for(int i = 1; i <= (amountOfProducts % 8 != 0 ? (amountOfProducts / 8) + 1 : amountOfProducts / 8); i++){
                        pages.add(i);
                    }
                }

                request.setAttribute("pages", pages);
                products = adminService.getProductWithSortAndLimit(sortBy, order, filterParams, from, to);
            }catch (Exception e){
                logger.warn(e.getMessage(), e);
                String role = user == null ? "guest" : user.getRole().toString().toLowerCase(Locale.ROOT);
                return "redirect:/app/" + role + "/mainPage";
            }
            request.setAttribute("colors", userService.getAllColors());
            request.setAttribute("categories", userService.getAllCategories());
            request.setAttribute("sizes", userService.getAllSizes());
            request.setAttribute("products", products);
            return JSPPageConstants.MAIN_PAGE;
        }
        return "/app/admin/productsManagePage";
    }
}
