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
import org.imgscalr.Scalr;
import session.PictureFacade;
import session.UnitFacade;
import session.UserFacade;

/**
 *
 * @author aleksei
 */
@WebServlet(name = "servlet_prime", loadOnStartup = 1, urlPatterns = {"/index","/reg",
    "/admin","/add","/mod","/registration","/add_img","/authorize"})
@MultipartConfig()
public class servlet_prime extends HttpServlet {
    @EJB
    UserFacade userFacade = new UserFacade();
    @EJB
    UnitFacade unitFacade = new UnitFacade();
    @EJB
    PictureFacade pictureFacade = new PictureFacade();
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
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        User user;
        Unit unit;
        switch (path){
            case "/index":
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/reg":
                request.getRequestDispatcher("WEB-INF/prime_pages/reg.jsp").forward(request, response);
                break;
            case "/admin":
                user = userFacade.find(id);
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
            case "/add_img":
                unit = new Unit();
                List<Part> fileParts = request.getParts().stream().filter( part -> "image".equals(part.getName())).collect(Collectors.toList());
                String imagesFolder = "C:\\uploadFiles\\";
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
                    String description = request.getParameter("description");
                    Picture picture = new Picture();
                    picture.setDescription(description);
                    picture.setPathToFile(pathToFile);
                    pictureFacade.create(picture);
                }    
                unit.setPrice(request.getParameter("price"));
                unit.setDates(request.getParameter("year"));
                unit.setKind(request.getParameter("kinds"));
                unit.setKind(request.getParameter("kinds"));
                unit.setDescription(request.getParameter("description"));
                unitFacade.create(unit);
                request.getRequestDispatcher("/add").forward(request, response);
                break;
            case "/authorize":
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                List<User> array = userFacade.findAll();
                User aut_user = userFacade.findByEmail(email);
                if (aut_user == null){
                    request.getRequestDispatcher("/index").forward(request, response);
                }else{
                    id = aut_user.getId();
                    request.getRequestDispatcher("/admin").forward(request, response);
                }
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
