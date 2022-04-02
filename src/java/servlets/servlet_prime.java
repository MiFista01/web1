/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Order_user;
import entity.Picture;
import entity.Unit;
import entity.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import session.Order_userFacade;
import session.PictureFacade;
import session.UnitFacade;
import session.UserFacade;
/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index","/reg",
    "/admin","/add","/mod","/registration","/add_img","/authorize","/unit","/unitForGuest",
    "/page_change_profile","/change_profile","/chronology","/style","/style_choice",
    "/chronology_for_guest","/style_for_guest","/user","/chronology_for_admin","/style_for_admin",
    "/unit_admin","/change_img","/size_for_admin","/size_for_guest","/size","/size_choise","/order",
    "/message_admin","/change_status","/message_user"})
@MultipartConfig()
public class servlet_prime extends HttpServlet {
    @EJB
    UserFacade userFacade = new UserFacade();
    @EJB
    UnitFacade unitFacade = new UnitFacade();
    @EJB
    PictureFacade pictureFacade = new PictureFacade();
    @EJB
    Order_userFacade order_userFacade = new Order_userFacade();
    
    long admin_id = 1;
    long unit_id;
    int i = 0;
    User user;
    Unit unit;
    Unit state;
    List<Unit>states;
    Picture picture;
    List<Unit> items;
    List<Unit> units;
    Comparator<Unit> comparator;
    List<String> sizes;
    
    
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
        int role = 2;
        pers.setLogin("admin");
        pers.setName("Tatjana");
        pers.setSurname("Matskevits");
        pers.setPhone("XXXXXX");
        pers.setEmail("example@gmail.com");
        pers.setPassword("12345");
        pers.setRole(role);
        userFacade.create(pers);
        
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path){
            case "/index":
                user = null;
                units = unitFacade.findByID(admin_id);
                items = new ArrayList();
                int id = 1;
                i = 0;
                while (i<3){
                    try {
                        Unit state = units.get(units.size()-i-1);
                        try {
                            String description = state.getDescription().substring(0,150)+"...";
                            state.setDescription(description);
                        } catch (Exception e) {
                        }
                        items.add(state);
                        i++;
                    } catch (Exception e) {
                        break;
                    }
                }
                request.setAttribute("items", items);
                request.setAttribute("name", userFacade.findByid(1).getName());
                request.setAttribute("surname", userFacade.findByid(1).getSurname());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/message_admin":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.setAttribute("items", order_userFacade.findAll());
                request.getRequestDispatcher("WEB-INF/prime_pages/message.jsp").forward(request, response);
                break;
            case "/message_user":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                List<Order_user> orders = order_userFacade.findAll();
                List<Order_user> user_orders = new ArrayList();
                for (Order_user i:orders){
                    if(i.getUser().getId()==user.getId()){
                        user_orders.add(i);
                    }
                }
                request.setAttribute("items", user_orders);
                request.getRequestDispatcher("WEB-INF/prime_pages/message_user.jsp").forward(request, response);
                break;
            case "/change_status":
                String status = request.getParameter("status");
                System.out.println(Integer.parseInt(request.getParameter("id")));
                Order_user user_order = order_userFacade.findid(Integer.parseInt(request.getParameter("id")));
                user_order.setStatus(status);
                order_userFacade.edit(user_order);
                request.getRequestDispatcher("/message_admin").forward(request, response);
                break;
            case "/reg":
                request.setAttribute("name", userFacade.findByid(1).getName());
                request.setAttribute("surname", userFacade.findByid(1).getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/admin":
                units = unitFacade.findByID(admin_id);
                items = new ArrayList();
                i = 0;
                while (i<3){
                    try {
                        Unit state = units.get(units.size()-i-1);
                        try {
                            String description = state.getDescription().substring(0,150)+"...";
                            state.setDescription(description);
                        } catch (Exception e) {
                        }
                        items.add(state);
                        i++;
                    } catch (Exception e) {
                        break;
                    }
                }
                request.setAttribute("items", items);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/admin.jsp").forward(request, response);
                break;
            case "/user":
                units = unitFacade.findByID(admin_id);
                items = new ArrayList();
                i = 0;
                while (i<3){
                    try {
                        Unit state = units.get(units.size()-i-1);
                        try {
                            String description = state.getDescription().substring(0,150)+"...";
                            state.setDescription(description);
                        } catch (Exception e) {
                        }
                        items.add(state);
                        i++;
                    } catch (Exception e) {
                        break;
                    }
                }
                request.setAttribute("items", items);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/user.jsp").forward(request, response);
                break;
            case "/order":
                Unit unit_for_order = unitFacade.findid(Integer.parseInt(request.getParameter("id")));
                User user_for_order = user;
                Order_user order = new Order_user();
                order.setUnit(unit_for_order);
                order.setUser(user_for_order);
                order.setStatus("Заказ");
                order_userFacade.create(order);
                if (user == null){
                    request.getRequestDispatcher("/unitForGuest").forward(request, response);
                }else{
                    if(user.getRole()==1){
                        request.getRequestDispatcher("/unit").forward(request, response);
                    }else{
                        request.getRequestDispatcher("/unit_admin").forward(request, response);
                    }
                }
                break;
                
            case "/add":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/add.jsp").forward(request, response);
                break;
            case "/chronology":
                try {
                    request.setAttribute("name", user.getName());
                    request.setAttribute("surname", user.getSurname());
                } catch (Exception e) {
                }
                states = unitFacade.findAll();
                comparator = Comparator.comparing(obj -> obj.getYear());
                System.out.println(states);
                Collections.sort(states, comparator);
                items = states;
                request.setAttribute("items", items);
                request.getRequestDispatcher("WEB-INF/prime_pages/chronology.jsp").forward(request, response);
                break;
            case "/chronology_for_admin":
                try {
                    request.setAttribute("name", user.getName());
                    request.setAttribute("surname", user.getSurname());
                } catch (Exception e) {
                }
                states = unitFacade.findAll();
                comparator = Comparator.comparing(obj -> obj.getYear());
                System.out.println(states);
                Collections.sort(states, comparator);
                items = states;
                request.setAttribute("items", items);
                request.getRequestDispatcher("WEB-INF/prime_pages/chronology_for_admin.jsp").forward(request, response);
                break;
            case "/chronology_for_guest":
                try {
                    request.setAttribute("name", user.getName());
                    request.setAttribute("surname", user.getSurname());
                } catch (Exception e) {
                }
                states = unitFacade.findAll();
                comparator = Comparator.comparing(obj -> obj.getYear());
                System.out.println(states);
                Collections.sort(states, comparator);
                items = states;
                request.setAttribute("items", items);
                request.getRequestDispatcher("WEB-INF/prime_pages/chronology_for_guest.jsp").forward(request, response);
                break;
            case "/style":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/style.jsp").forward(request, response);
                break;
            case "/style_for_guest":
                request.getRequestDispatcher("WEB-INF/prime_pages/style_for_guest.jsp").forward(request, response);
                break;
            case "/style_for_admin":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/style_for_admin.jsp").forward(request, response);
                break;
            case "/style_choice":
                units = unitFacade.findByID(admin_id);
                items = new ArrayList();
                for (Unit unit:units){
                    try {
                        if (request.getParameter("kinds").equals(unit.getKind())){
                        items.add(unit);
                    }
                    } catch (Exception e) {
                    }
                    
                }
                request.setAttribute("items", items);
                if (user == null){
                    request.getRequestDispatcher("/style_for_guest").forward(request, response);
                }else{
                    if(user.getRole()==1){
                        request.getRequestDispatcher("/style").forward(request, response);
                    }else{
                        request.getRequestDispatcher("/style_for_admin").forward(request, response);
                    }
                }
                
                break;
            case "/size":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                sizes = unitFacade.findSize();
                Collections.sort (sizes);
                request.setAttribute("sizes", sizes);
                request.getRequestDispatcher("WEB-INF/prime_pages/size.jsp").forward(request, response);
                break;
            case "/size_for_guest":
                request.setAttribute("name", userFacade.findByid(1).getName());
                request.setAttribute("surname", userFacade.findByid(1).getSurname());
                sizes = unitFacade.findSize();
                Collections.sort (sizes);
                request.setAttribute("sizes", sizes);
                request.getRequestDispatcher("WEB-INF/prime_pages/size_for_guest.jsp").forward(request, response);
                break;
            case "/size_for_admin":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                sizes = unitFacade.findSize();
                Collections.sort (sizes);
                request.setAttribute("sizes", sizes);
                request.getRequestDispatcher("WEB-INF/prime_pages/size_for_admin.jsp").forward(request, response);
                break;
            case "/size_choise":
                units = unitFacade.findByID(admin_id);
                items = new ArrayList();
                for (Unit unit:units){
                    try {
                        if (request.getParameter("size").equals(unit.getSize())){
                        items.add(unit);
                    }
                    } catch (Exception e) {
                    }
                    
                }
                request.setAttribute("items", items);
                if (user == null){
                    request.getRequestDispatcher("/size_for_guest").forward(request, response);
                }else{
                    if(user.getRole()==1){
                        request.getRequestDispatcher("/size").forward(request, response);
                    }else{
                        request.getRequestDispatcher("/size_for_admin").forward(request, response);
                    }
                    
                }
                
                break;
            case "/page_change_profile":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/page_change_profile.jsp").forward(request, response);
                break;
            case "/change_profile":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                if(!request.getParameter("login").equals("")){
                    user.setLogin(request.getParameter("login"));
                }
                if(!request.getParameter("name").equals("")){
                    user.setName(request.getParameter("name"));
                }
                if(!request.getParameter("surname").equals("")){
                    user.setSurname(request.getParameter("surname"));
                }
                if(!request.getParameter("phone").equals("")){
                    user.setPhone(request.getParameter("phone"));
                }
                if(!request.getParameter("email").equals("")){
                    user.setEmail(request.getParameter("email"));
                }
                if(!request.getParameter("password").equals("")){
                    user.setPassword(request.getParameter("password"));
                }
                userFacade.edit(user);
                request.getRequestDispatcher("/page_change_profile").forward(request, response);
                break;
            case "/change_img":
                Unit unit = unitFacade.find(Long.parseLong(request.getParameter("id")));
                if(!request.getParameter("art_name").equals("")){
                    unit.setArt_name(request.getParameter("art_name"));
                }
                if(!request.getParameter("size").equals("")){
                    unit.setSize(request.getParameter("size"));
                }
                if(!request.getParameter("price").equals("")){
                    unit.setPrice(request.getParameter("price"));
                }
                if(!request.getParameter("year").equals("")){
                    unit.setYear(request.getParameter("year"));
                }
                if(!request.getParameter("kinds").equals("")){
                    unit.setKind(request.getParameter("kinds"));
                }
                if(!request.getParameter("description").equals("")){
                    unit.setDescription(request.getParameter("description"));
                }
                unitFacade.edit(unit);
                request.getRequestDispatcher("/unit_admin").forward(request, response);
                break;
            case "/add_img":
                unit = new Unit();
                List<Part> fileParts = request.getParts().stream().filter( part -> "image".equals(part.getName())).collect(Collectors.toList());
                String user_folder = user.getName()+"_"+user.getSurname();
                String imagesFolder = "C:\\uploadFiles\\"+user_folder;
                for(Part filePart : fileParts){
                    String pathToFile = imagesFolder + File.separatorChar
                                    +getFileName(filePart);
                    
                    File tempFile = new File(imagesFolder+File.separatorChar+"tmp"+File.separatorChar+getFileName(filePart));
                    tempFile.mkdirs();
                    System.out.println(tempFile.getName());
                    try(InputStream fileContent = filePart.getInputStream()){
                       Files.copy(
                               fileContent,tempFile.toPath(), 
                               StandardCopyOption.REPLACE_EXISTING
                       );
                       writeToFile(resize(tempFile),pathToFile);
                       tempFile.delete();
                    }
                    picture = new Picture();
                    picture.setPathToFile(pathToFile);
                    
                    pictureFacade.create(picture);
                }    
                unit.setUser(user);
                unit.setPicture(picture);
                unit.setArt_name(request.getParameter("art_name"));
                unit.setSize(request.getParameter("size"));
                unit.setPrice(request.getParameter("price"));
                unit.setYear(request.getParameter("year"));
                unit.setKind(request.getParameter("kinds"));
                unit.setDescription(request.getParameter("description"));
                unitFacade.create(unit);
                request.getRequestDispatcher("/add").forward(request, response);
                break;
            case "/registration":
                User pers = new User();
                pers.setLogin(request.getParameter("login"));
                pers.setName(request.getParameter("name"));
                pers.setSurname(request.getParameter("surname"));
                pers.setPhone(request.getParameter("phone"));
                pers.setEmail(request.getParameter("email"));
                pers.setPassword(request.getParameter("password"));
                pers.setRole(1);
                userFacade.create(pers);
                request.getRequestDispatcher("/reg").forward(request, response);
                break;
            case "/authorize":
                String user_aut = request.getParameter("user");
                String password = request.getParameter("password");
                User aut_user = new User();
                if(userFacade.findByEmail(user_aut) != null){
                    aut_user = userFacade.findByEmail(user_aut);
                }else{
                    if(userFacade.findByLogin(user_aut) != null){
                        aut_user = userFacade.findByLogin(user_aut);
                    }else{
                        aut_user = null;
                    }
                }
                if (aut_user == null){
                    request.getRequestDispatcher("/index").forward(request, response);
                }else{
                    if(aut_user.getPassword().equals(password)){
                    user = aut_user;
                        if(aut_user.getRole()==2){
                            request.getRequestDispatcher("/admin").forward(request, response);
                        }
                        if(aut_user.getRole()==1){
                            request.getRequestDispatcher("/user").forward(request, response);
                        }
                    }else{
                        request.getRequestDispatcher("/index").forward(request, response);
                        }
                }
                break;
            case "/unit":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                try {
                    unit_id = Long.parseLong(request.getParameter("unit_id"));
                } catch (Exception e) {
                }
                
                state = unitFacade.find(unit_id);
                request.setAttribute("state", state);
                request.getRequestDispatcher("WEB-INF/prime_pages/unit.jsp").forward(request, response);
                break;
            case "/unit_admin":
                try {
                    unit_id = Long.parseLong(request.getParameter("unit_id"));
                } catch (Exception e) {
                }
                
                state = unitFacade.find(unit_id);
                request.setAttribute("state", state);
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/unit_admin.jsp").forward(request, response);
                break;
                
            case "/unitForGuest":
                unit_id = Long.parseLong(request.getParameter("unit_id"));
                state = unitFacade.find(unit_id);
                request.setAttribute("state", state);
                request.getRequestDispatcher("WEB-INF/prime_pages/unitForGuest.jsp").forward(request, response);
                break;
        }
    }

    
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
    }
    public void writeToFile(byte[] data, String fileName) throws IOException{
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(data);
        }
    }
    public byte[] resize(File icon) {
        try {
           BufferedImage originalImage = ImageIO.read(icon);
           byte[] imageInByte;
            try ( //To save with original ratio uncomment next line and comment the above.
            //originalImage= Scalr.resize(originalImage, 153, 128);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                String name = icon.getName();
                if ("jpg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "jpg", baos);
                }
                if ("png".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "png", baos);
                }
                if ("svg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "svg", baos);
                }
                if ("jpeg".equals(name.substring(name.indexOf(".")+1))){
                    ImageIO.write(originalImage, "jpeg", baos);
                }
                baos.flush();
                imageInByte = baos.toByteArray();
            }
            return imageInByte;
        } catch (Exception e) {
            return null;
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
