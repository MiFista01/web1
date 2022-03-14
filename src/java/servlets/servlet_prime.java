/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Unit;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.UnitFacade;
import session.UserFacade;

/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index","/reg",
    "/admin","/add","/mod","/registration","/authorize"})
public class servlet_prime extends HttpServlet {
    @EJB
    UserFacade userFacade = new UserFacade();
    UnitFacade unitFacade = new UnitFacade();
    long id = 0;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    public void init()
            throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        if(userFacade.count() == 0){
        User pers = new User();
        pers.setName("Tatjana");
        pers.setSurname("Matskevits");
        pers.setPhone("XXXXXX");
        pers.setEmail("example@gmail.com");
        pers.setPassword("12345");
        userFacade.create(pers);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();
        User user;
        List<Unit> items;
        switch (path){
            case "/index":
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/reg":
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/admin":
                user = userFacade.find(id);
                items = unitFacade.findAll();
                request.setAttribute("items", items);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/admin.jsp").forward(request, response);
                break;
            case "/add":
                user = userFacade.find(id);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/add.jsp").forward(request, response);
                break;
            case "/mod":
                user = userFacade.find(id);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/mod.jsp").forward(request, response);
                break;
            case "/registration":
                User pers = new User();
                pers.setName(request.getParameter("name"));
                pers.setSurname(request.getParameter("surname"));
                pers.setPhone(request.getParameter("phone"));
                pers.setEmail(request.getParameter("email"));
                pers.setPassword(request.getParameter("password"));
                userFacade.create(pers);
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/authorize":
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                List<User> array = userFacade.findAll();
                for(User i : array){
                    if (i.getEmail().equals(email) && i.getPassword().equals(password)){
                        request.setAttribute("name", i.getName());
                        request.setAttribute("surname", i.getSurname());
                        id = i.getId();
                        request.getRequestDispatcher("WEB-INF/prime_pages/admin.jsp").forward(request, response);
                        break;
                    }
                }
                if(id == 0){
                    request.getRequestDispatcher("/index").forward(request, response);   
                }
                break;
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
        processRequest(request, response);
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
