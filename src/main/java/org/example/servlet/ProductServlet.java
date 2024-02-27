package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.ProductDAO;
import org.example.dao.ProductDAOImpl;
import org.example.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAOImpl(); // Инициализация DAO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Product> products = productDAO.getAllProducts();
            ObjectMapper mapper = new ObjectMapper();
            String productsJson = mapper.writeValueAsString(products);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(productsJson);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получаем данные о новом товаре из запроса
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price")); // Возможно возникновение NumberFormatException
            String category = req.getParameter("category");

            // Создаем новый объект товара
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setDescription(description);
            newProduct.setPrice(price);
            newProduct.setCategory(category);

            // Добавляем товар в базу данных
            productDAO.addProduct(newProduct);

            // Отправляем код 201 (Created) в ответе
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NumberFormatException e) {
            // Отправляем сообщение об ошибке клиенту
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Некорректное значение цены товара");
        } catch (Exception e) {
            // Отправляем сообщение об ошибке клиенту
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при выполнении операции: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получаем идентификатор товара из запроса
            int productId = Integer.parseInt(req.getParameter("id"));

            // Получаем данные о товаре из запроса
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            String category = req.getParameter("category");

            // Создаем объект товара с обновленными данными
            Product updatedProduct = new Product();
            updatedProduct.setId(productId);
            updatedProduct.setName(name);
            updatedProduct.setDescription(description);
            updatedProduct.setPrice(price);
            updatedProduct.setCategory(category);

            // Обновляем информацию о товаре в базе данных
            productDAO.updateProduct(updatedProduct);

            // Отправляем код 200 (OK) в ответе
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            // Отправляем сообщение об ошибке клиенту
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Некорректное значение цены товара");
        } catch (Exception e) {
            // Отправляем сообщение об ошибке клиенту
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при обновлении информации о товаре: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получаем идентификатор товара из запроса
            int productId = Integer.parseInt(req.getParameter("id"));

            // Удаляем товар из базы данных
            productDAO.deleteProduct(productId);

            // Отправляем код 204 (No Content) в ответе
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            // Отправляем сообщение об ошибке клиенту
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при удалении товара: " + e.getMessage());
        }
    }
}