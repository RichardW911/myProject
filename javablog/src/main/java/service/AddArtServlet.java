package service;

import dao.ArticleInfoDao;
import dao.UserInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class AddArtServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg ="";
        //1.先从前端获取参数
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        //非空效验
        if(title != null && content != null &&
        !title.equals("") && !content.equals("")) {
            //获取uid
            HttpSession session = request.getSession(false);
            if(session !=null && session.getAttribute("userinfo") != null) {
                //登录状态
                UserInfo userInfo = (UserInfo)session.getAttribute("userinfo");
                //2.执行数据库添加操作
                ArticleInfoDao articleInfoDao = new ArticleInfoDao();
                try {
                    succ = articleInfoDao.add(title,content,userInfo.getId());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                msg = "非法请求，非登录状态";
            }

        }else {
            //非法请求，参数不完整
            msg = "非法请求，参数不完整!";
        }

        //3.将结果返回给前端
        HashMap<String,Object> result = new HashMap<>();
        result.put("succ",succ);
        result.put("msg",msg);
        ResultJSONUtils.writeMap(response,result);
    }
}
