/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Biography;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import session.BiographyFacade;
import session.Order_userFacade;
import session.PictureFacade;
import session.UnitFacade;
import session.UserFacade;
import tools.Password_protector;
import tools.Send_message;
/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index","/reg",
    "/add","/registration","/add_img","/authorize","/unit","/page_change_profile",
    "/change_profile","/chronology","/style","/style_choice","/change_img","/size",
    "/size_choise","/order","/message","/change_status","/biography","/change_biography"})
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
    @EJB
    BiographyFacade biographyFacade = new BiographyFacade();
    
    long admin_id = 1;
    int unit_id;
    int i = 0;
    User user;
    Unit unit;
    Unit state;
    int role = 0;
    String user_folder;
    String imagesFolder;
    Picture picture;
    String delete_id;
    Unit delete_unit;
    String choice;
    Password_protector password_protector;
    String salt;
    List<Unit>states;
    List<Unit> items;
    List<Unit> units;
    Comparator<Unit> comparator;
    List<String> sizes;
    List<Part> fileParts;
    
    
    
    
    
    
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
            password_protector = new Password_protector();
            salt = password_protector.getSalt();
            System.out.println(salt);
            pers.setSalt(salt);
            int role = 2;
            String password = "12345";
            pers.setLogin("admin");
            pers.setName("Tatjana");
            pers.setSurname("Matskevits");
            pers.setPhone("XXXXXX");
            pers.setEmail("example@gmail.com");
            pers.setPassword(password_protector.getPassword_protector(password, salt));
            pers.setRole(role);
            userFacade.create(pers);
        }
        if(biographyFacade.count() == 0){
            Biography biography = new Biography();
            biographyFacade.create(biography);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path){
            case "/index":
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                try {
                    role = Integer.parseInt(request.getParameter("out"));
                    user = null;
                } catch (Exception e) {
                }
                delete_id = request.getParameter("delete_id");
                if(delete_id != null){
                    delete_unit = unitFacade.findid(Integer.parseInt(delete_id));
                    delete_unit.setStatus(0);
                    unitFacade.edit(delete_unit);
                    try {
                        Picture delete_picture = delete_unit.getPicture();
                        File file = new File(delete_picture.getPathToFile());
                        unitFacade.remove(delete_unit);
                        pictureFacade.remove(delete_picture);
                        file.delete();
                    } catch (Exception e) {
                    }
                }
                units = unitFacade.findNotDeleted();
                items = new ArrayList();
                i = 0;
                while (i<4){
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
                delete_id = null;
                request.setAttribute("items", items);
                request.setAttribute("user", user);
                request.setAttribute("role", role);
                request.setAttribute("admin", userFacade.findByid(1));
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/biography":
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                try {
                    role = Integer.parseInt(request.getParameter("out"));
                    user = null;
                } catch (Exception e) {
                }
                request.setAttribute("biography", biographyFacade.findid(1));
                request.setAttribute("user", user);
                request.setAttribute("role", role);
                request.setAttribute("admin", userFacade.findByid(1));
                request.getRequestDispatcher("WEB-INF/prime_pages/biography.jsp").forward(request, response);
                break;
            case "/change_biography":
                Biography biography = biographyFacade.findid(1);
                fileParts = request.getParts().stream().filter( part -> "image".equals(part.getName())).collect(Collectors.toList());
                user_folder = Long.toString(user.getId()) ;
                imagesFolder = "C:\\uploadFiles\\"+user_folder;
                try {
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
                        if(biography.getPicture() == null){
                            biography.setPicture(picture);
                            pictureFacade.create(picture);
                        }else{
                            File delete_file = new File(biography.getPicture().getPathToFile());
                            delete_file.delete();
                            biography.getPicture().setPathToFile(pathToFile);
                            pictureFacade.edit(biography.getPicture());
                            biographyFacade.edit(biography);
                        }
                    }    
                } catch (Exception e) {
                }
                
                if(!request.getParameter("description").equals("")){
                    biography.setBiography(request.getParameter("description"));
                }
                biographyFacade.edit(biography);
                request.getRequestDispatcher("/biography").forward(request, response);
                break;
            case "/message":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                    if (role == 1){
                        List<Order_user> orders = order_userFacade.findAll();
                        List<Order_user> user_orders = new ArrayList();
                        for (Order_user i:orders){
                            if(i.getUser().getId()==user.getId()){
                                user_orders.add(i);
                            }
                        }
                        request.setAttribute("items", user_orders);
                    }else{
                        request.setAttribute("items", order_userFacade.findAll());
                    }
                } catch (Exception e) {
                    role = 0;                
                }
                state = unitFacade.findid(unit_id);
                request.setAttribute("state", state);
                request.setAttribute("role", role);
                request.getRequestDispatcher("WEB-INF/prime_pages/message.jsp").forward(request, response);
                break;
            case "/message_user":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                request.getRequestDispatcher("WEB-INF/prime_pages/message_user.jsp").forward(request, response);
                break;
            case "/change_status":
                String status = request.getParameter("status");
                System.out.println(Integer.parseInt(request.getParameter("id")));
                Order_user user_order = order_userFacade.findid(Integer.parseInt(request.getParameter("id")));
                user_order.setStatus(status);
                order_userFacade.edit(user_order);
                request.getRequestDispatcher("/message").forward(request, response);
                break;
            case "/reg":
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                request.setAttribute("role", role);
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/registration":
                User pers = new User();
                password_protector = new Password_protector();
                salt = password_protector.getSalt();
                pers.setSalt(salt);
                pers.setLogin(request.getParameter("login"));
                pers.setName(request.getParameter("name"));
                pers.setSurname(request.getParameter("surname"));
                pers.setPhone(request.getParameter("phone"));
                pers.setEmail(request.getParameter("email"));
                pers.setPassword(password_protector.getPassword_protector(request.getParameter("password"), salt) );
                pers.setRole(1);
                if(userFacade.findByEmail(request.getParameter("email"))!= null || 
                    userFacade.findByLogin(request.getParameter("login"))!= null){
                    request.setAttribute("info", "Такой логин или пароль уже есть");
                }else{
                    userFacade.create(pers);
                }
                request.getRequestDispatcher("/reg").forward(request, response);
                break;
            case "/order":
                Unit unit_for_order = unitFacade.findid(Integer.parseInt(request.getParameter("id")));
                User user_for_order = user;
                Order_user order = new Order_user();
                order.setUnit(unit_for_order);
                order.setUser(user_for_order);
                order.setStatus("В ожидании");
                order.setDate(LocalDate.now().toString());
                order_userFacade.create(order);
                Send_message send_message = new Send_message();
                String text ="Название картины: "+ unit_for_order.getArt_name()+
                            "  Имя фамилия: "+user_for_order.getName()+" "+user_for_order.getSurname()+
                            "   Почта: "+user_for_order.getEmail()+
                            "   Телефон: "+user_for_order.getPhone();
                send_message.Send(text);
                request.getRequestDispatcher("/unit").forward(request, response);
                break;
                
            case "/add":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                request.setAttribute("pictures", unitFacade.findNotDeleted());
                request.setAttribute("lenght", unitFacade.findNotDeleted().size());
                request.setAttribute("role", role);
                request.getRequestDispatcher("WEB-INF/prime_pages/add.jsp?#btn").forward(request, response);
                break;
            case "/chronology":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                delete_id = request.getParameter("delete_id");
                if(delete_id != null){
                    delete_unit = unitFacade.findid(Integer.parseInt(delete_id));
                    delete_unit.setStatus(0);
                    unitFacade.edit(delete_unit);
                }
                
                states = unitFacade.findNotDeleted();
                comparator = Comparator.comparing(obj -> obj.getYear());
                System.out.println(states);
                Collections.sort(states, comparator);
                items = states;
                delete_id = null;
                request.setAttribute("role", role);
                request.setAttribute("items", items);
                request.getRequestDispatcher("WEB-INF/prime_pages/chronology.jsp").forward(request, response);
                break;
            case "/style":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }                
                sizes = unitFacade.findSize();
                Collections.sort (sizes);
                request.setAttribute("role", role);
                request.setAttribute("sizes", sizes);
                request.getRequestDispatcher("WEB-INF/prime_pages/style.jsp").forward(request, response);
                break;
            case "/style_choice":
                delete_id = request.getParameter("delete_id");
                if(delete_id != null){
                    delete_unit = unitFacade.findid(Integer.parseInt(delete_id));
                    delete_unit.setStatus(0);
                    unitFacade.edit(delete_unit);
                }
                units = unitFacade.findNotDeleted();
                items = new ArrayList();
                for (Unit unit:units){
                    try {
                        if (request.getParameter("kinds").equals(unit.getKind())){
                            items.add(unit);
                        }
                    } catch (Exception e) {
                    }
                    
                }
                delete_id = null;
                request.setAttribute("items", items);
                request.getRequestDispatcher("/style").forward(request, response);
                request.getRequestDispatcher("/style_choice").forward(request, response);
                break;
            case "/size":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                delete_id = null;
                
                sizes = unitFacade.findSize();
                Collections.sort (sizes);
                request.setAttribute("role", role);
                request.setAttribute("sizes", sizes);
                request.getRequestDispatcher("WEB-INF/prime_pages/size.jsp").forward(request, response);
                break;
            case "/size_choise":
                delete_id = request.getParameter("delete_id");
                if(delete_id != null){
                    delete_unit = unitFacade.findid(Integer.parseInt(delete_id));
                    delete_unit.setStatus(0);
                    unitFacade.edit(delete_unit);
                }
                units = unitFacade.findNotDeleted();
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
                request.getRequestDispatcher("/size").forward(request, response);
                break;
            case "/page_change_profile":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                request.setAttribute("role", role);
                request.setAttribute("old_login", user.getLogin());
                request.setAttribute("old_name", user.getName());
                request.setAttribute("old_surname", user.getSurname());
                request.setAttribute("old_phone", user.getPhone());
                request.setAttribute("old_email", user.getEmail());
                request.setAttribute("old_password", user.getPassword());
                request.getRequestDispatcher("WEB-INF/prime_pages/page_change_profile.jsp").forward(request, response);
                break;
            case "/change_profile":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
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
                try {
                    if(!request.getParameter("kinds").equals("")){
                    unit.setKind(request.getParameter("kinds"));
                }
                } catch (Exception e) {
                }
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
                
                if(!request.getParameter("description").equals("")){
                    unit.setDescription(request.getParameter("description"));
                }
                unitFacade.edit(unit);
                request.getRequestDispatcher("/unit").forward(request, response);
                break;
            case "/add_img":
                unit = new Unit();
                fileParts = request.getParts().stream().filter( part -> "image".equals(part.getName())).collect(Collectors.toList());
                user_folder = Long.toString(user.getId()) ;
                imagesFolder = "C:\\uploadFiles\\"+user_folder;
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
                unit.setStatus(1);
                unitFacade.create(unit);
                request.getRequestDispatcher("/add").forward(request, response);
                break;
            
            case "/authorize":
                String user_aut = request.getParameter("user");
                String password = request.getParameter("password");
                password_protector = new Password_protector();
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
                if(aut_user != null){
                    if(aut_user.getPassword().equals(password_protector.getPassword_protector(password, aut_user.getSalt()))){
                        user = aut_user;
                    }
                }
                
                request.getRequestDispatcher("/index").forward(request, response);
                break;
            case "/unit":
                request.setAttribute("user", user);
                request.setAttribute("admin", userFacade.findByid(1));
                try {
                    unit_id = Integer.parseInt(request.getParameter("unit_id"));
                    request.setAttribute("role", request.getParameter("role"));
                } catch (Exception e) {
                }
                try {
                    role = user.getRole();
                } catch (Exception e) {
                    role = 0;                
                }
                
                state = unitFacade.findid(unit_id);
                request.setAttribute("state", state);
                request.setAttribute("role", role);
                request.getRequestDispatcher("WEB-INF/prime_pages/unit.jsp").forward(request, response);
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
