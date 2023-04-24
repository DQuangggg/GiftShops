/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Order;

import DAO.DAOCategory;
import DAO.DAOCustomer;
import DAO.DAOOrder;
import Entity.Category;
import Entity.Contact;
import Entity.Customer;
import Entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class managerOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet managerOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet managerOrderController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Category> listCategory = new ArrayList<>();
        DAOCategory cd = new DAOCategory();
        listCategory = cd.getCategory();
        DAOOrder od = new DAOOrder();

        ArrayList<Customer> listCustomer = new ArrayList<>();
        DAOCustomer cu = new DAOCustomer();
        listCustomer = cu.getCustomers();

        int count = od.getTotalOrder();
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        int endPage = count / 15;
        if (count % 15 != 0) {
            endPage++;
        }

        String statusParam = request.getParameter("status");
        int status = (statusParam != null && !statusParam.isEmpty()) ? Integer.parseInt(statusParam) : -1;
        List<Order> listOrder = new ArrayList<>();
        if (status == -1) {
            listOrder = od.pagingOrder(index);
        } else {
            listOrder = od.getOrdersByStatus(status);
        }

        request.setAttribute("listC", listCategory);
        request.setAttribute("listO", listOrder);
        request.setAttribute("listCU", listCustomer);
        request.setAttribute("tag", index);
        request.setAttribute("endP", endPage);
        request.getRequestDispatcher("managerOrder.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oidString = request.getParameter("oid");
        DAOOrder od = new DAOOrder();
        Order o = new Order();
        ArrayList<Order> listOrder = new ArrayList<>();
        listOrder = od.getOrdersById(oidString);

        DAOCategory cd = new DAOCategory();
        ArrayList<Category> listCategory = new ArrayList<>();
        listCategory = cd.getCategory();

        ArrayList<Order> listStatus = new ArrayList<>();
        
        ArrayList<Customer> listCustomer = new ArrayList<>();
        DAOCustomer cud = new DAOCustomer();
        listCustomer = cud.getCustomers();
        
        listOrder.add(o);
        
        request.setAttribute("searchMessage", oidString);
        request.setAttribute("listO", listOrder);
        request.setAttribute("listC", listCategory);
        request.setAttribute("listCU", listCustomer);
        request.getRequestDispatcher("managerOrder.jsp").forward(request, response);
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
