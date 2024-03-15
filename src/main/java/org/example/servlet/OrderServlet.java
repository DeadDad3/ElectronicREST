package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.OrderDAO;
import org.example.dao.OrderDAOImpl;
import org.example.dao.ProductDAO;
import org.example.dao.ProductDAOImpl;
import org.example.entity.Order;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        // Инициализируйте ProductDAOImpl
        ProductDAO productDAO = new ProductDAOImpl();
        // Предполагаем, что OrderDAOImpl требует ProductDAO в конструкторе
        this.orderDAO = new OrderDAOImpl(productDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("id"); // Получаем ID заказа из параметров запроса

        if (orderIdParam != null) {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderDAO.getOrderById(orderId);
                if (order != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(mapper.writeValueAsString(order));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Заказ с указанным ID не найден.");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Некорректный формат ID.");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Ошибка сервера: " + e.getMessage());
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Необходимо указать ID заказа.");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(req.getReader(), Order.class);

            orderDAO.addOrder(order); // Добавляем заказ в базу данных

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(mapper.writeValueAsString(order));
            resp.setStatus(HttpServletResponse.SC_CREATED); // 201 Created
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            orderDAO.deleteOrder(id); // Удаляем заказ из базы данных

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Некорректный формат ID заказа");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при удалении заказа: " + e.getMessage());
        }
    }
}
