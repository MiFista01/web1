/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import session.PictureFacade;
import session.UnitFacade;
import session.UserFacade;
/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index","/reg",
    "/admin","/add","/mod","/registration","/add_img","/authorize","/unit","/unitForGuest",
    "/page_change_profile","/change_profile","/chronology"})
@MultipartConfig()
public class servlet_prime extends HttpServlet {
    @EJB
    UserFacade userFacade = new UserFacade();
    @EJB
    UnitFacade unitFacade = new UnitFacade();
    @EJB
    PictureFacade pictureFacade = new PictureFacade();
    long id = 1;
    long unit_id;
    int i = 0;
    User user;
    Unit unit;
    Unit state;
    Picture picture;
    List<Unit> items;
    List<Unit> units;
    
    
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
        int role = 3;
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
                units = unitFacade.findByID(id);
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
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/reg":
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/admin":
                units = unitFacade.findByID(id);
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
            case "/add":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                request.getRequestDispatcher("WEB-INF/prime_pages/add.jsp").forward(request, response);
                break;
            case "/chronology":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                List<Unit>units = unitFacade.findAll();
                
                request.getRequestDispatcher("WEB-INF/prime_pages/chronology.jsp").forward(request, response);
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
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
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
                        if(aut_user.getRole()==3){
                            request.getRequestDispatcher("/admin").forward(request, response);
                        }
                        if(aut_user.getRole()==1){
                            request.getRequestDispatcher("/index").forward(request, response);
                        }
                    }else{
                        request.getRequestDispatcher("/index").forward(request, response);
                        }
                }
                break;
            case "/unit":
                request.setAttribute("name", user.getName());
                request.setAttribute("surname", user.getSurname());
                unit_id = Long.parseLong(request.getParameter("unit_id"));
                state = unitFacade.find(unit_id);
                request.setAttribute("state", state);
                request.getRequestDispatcher("WEB-INF/prime_pages/unit.jsp").forward(request, response);
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
