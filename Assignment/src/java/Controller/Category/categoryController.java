/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Category;

import DAO.DAOCategory;
import DAO.DAOProduct;
import Entity.Cart;
import Entity.Category;
import Entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class categoryController extends HttpServlet {

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
            out.println("<title>Servlet categoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet categoryController at " + request.getContextPath() + "</h1>");
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
        String cidString = request.getParameter("cid");
        int cid = Integer.parseInt(cidString);

        ArrayList<Category> listCategory = new ArrayList<>();
        DAOCategory cd = new DAOCategory();
        listCategory = cd.getCategory();

        boolean iscategory = true;
        DAOProduct pd = new DAOProduct();

        int count = pd.getTotalProductByCategory(cid);
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        int endPage = count / 16;
        if (count % 16 != 0) {
            endPage++;
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("listcart") != null) {
            ArrayList<Cart> listCart = (ArrayList<Cart>) session.getAttribute("listcart");
            int numProducts = 0;
            for (Cart cart : listCart) {
                numProducts += cart.getAmount();
            }
            request.setAttribute("numProducts", numProducts);
        } else {
            request.setAttribute("numProducts", 0);
        }

        List<Product> products = new ArrayList<>();
        products = pd.pagingProdctByCategory(cid, index);

        request.setAttribute("tag", index);
        request.setAttribute("endP", endPage);
        request.setAttribute("iscategory", iscategory);
        request.setAttribute("cid", cid);
        request.setAttribute("listP", products);
        request.setAttribute("listC", listCategory);
        request.getRequestDispatcher("shop.jsp").forward(request, response);
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
