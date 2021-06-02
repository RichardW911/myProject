package service;

import dao.ImageDao;
import lombok.SneakyThrows;
import models.Image;
import models.UserInfo;
import org.apache.commons.codec.digest.DigestUtils;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@WebServlet("/image")
@MultipartConfig
public class ImageServlet extends HttpServlet {

    public static final String IMAGE_DIR = "/root/soft/project/picture_service";

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String state = "";
        String msg = "";
        HashMap<String, Object> map = new HashMap<>();

        HttpSession httpSession = req.getSession(false);
        try {
            UserInfo userInfo = (UserInfo) httpSession.getAttribute("userinfo");//获取登录用户信息
            Part part = req.getPart("uploadImage");
            long size = part.getSize();//获取上传的文件大小
            String contentType = part.getContentType();//获取每个part的数据格式
            String name = part.getSubmittedFileName();

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadTime = dateFormat.format(date);


            //获取part（上传图片文件）的输入流
            InputStream inputStream = part.getInputStream();//获取上传文件的输入流（数据）
            //根据输入流转md5校验码
            String md5 = DigestUtils.md5Hex(inputStream);


            ImageDao imageDao = new ImageDao();


            //TODO:根据MD5和用户id联合查询（文件名写入时加上用户id）
            int num = imageDao.getMyimage(md5,userInfo.getId());
            if (num >= 1) {
                throw new RuntimeException("上传图片重复");
            }

            //根据请求数据完成业务处理
            //保存上传图片为服务端本地文件
            part.write(IMAGE_DIR+"/" + userInfo.getId() + md5);


            //图片信息保存在数据库中
            Image image = new Image();
            image.setU_id(userInfo.getId());
            image.setImage_name(name);
            image.setSize(size);
            image.setUpload_time(uploadTime);
            image.setMd5(md5);
            image.setContent_type(contentType);
            image.setPath("/" + userInfo.getId() + md5);

            imageDao.insert(image, userInfo.getId());
            map.put("ok", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("ok", false);
            if (e instanceof RuntimeException) {
                map.put("msg", e.getMessage());
            } else {
                map.put("msg", "未知错误，请联系管理员");
            }
        }


        ResultJSONUtils.WriteJsonMap(resp, map);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String state = "";
        String msg = "";
        HashMap<String, Object> map = new HashMap<>();
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("userinfo") == null) {
            state = "no";
            msg = "当前用户未登录";
            map.put(state, msg);
            ResultJSONUtils.WriteJsonMap(resp, map);
        } else {
            String imageId = req.getParameter("imageId");
            Object object = null;
            ImageDao imageDao = new ImageDao();
            try {
                UserInfo userInfo = (UserInfo) httpSession.getAttribute("userinfo");
                if (imageId == null) {

                    object = imageDao.queryAll(userInfo.getId());

                } else {
                    object = imageDao.queryOne(Integer.parseInt(imageId), userInfo.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResultJSONUtils.WriteJson(resp, object);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String imageId = req.getParameter("imageId");

        HttpSession httpSession = req.getSession(false);
        UserInfo userInfo = (UserInfo) httpSession.getAttribute("userinfo");
        ImageDao imageDao = new ImageDao();
        try {
            Image image = imageDao.queryOne(Integer.parseInt(imageId), userInfo.getId());

            int res = imageDao.delete(Integer.parseInt(imageId));

            String path = IMAGE_DIR+image.getPath();
            File file = new File(path);
            file.delete();
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("{\"ok\":true}");
            printWriter.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
