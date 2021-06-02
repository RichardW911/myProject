package service;


import dao.ImageDao;
import lombok.SneakyThrows;
import models.Image;
import models.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/imageShow")
public class ImageShowServlet extends HttpServlet {
    //页面显示图片

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String imageId = req.getParameter("imageId");
        HttpSession httpSession = req.getSession();
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("userinfo");

        ImageDao imageDao = new ImageDao();
        Image image = imageDao.queryOne(Integer.parseInt(imageId),userInfo.getId());

        resp.setContentType(image.getContent_type());

        String path = ImageServlet.IMAGE_DIR + image.getPath();

        FileInputStream fileInputStream = new FileInputStream(path);

        OutputStream outputStream =resp.getOutputStream();
        byte[] bytes = new byte[1024*10];
        int len;
        while(( len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes,0, len);
        }

        outputStream.flush();
        fileInputStream.close();
        outputStream.close();
    }
}
