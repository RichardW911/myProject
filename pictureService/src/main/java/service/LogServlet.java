package service;

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

public class LogServlet extends HttpServlet {



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/text");
        //获取前端参数
        String msg = "";
        int state = -1;
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username == null || password == null) {
            msg = "参数异常，请重新登录！";
        }else {
            //业务处理
            UserInfo userInfo = new UserInfo();

            userInfo.setUsername(username);
            userInfo.setPassword(password);

            UserInfoDao userInfoDao = new UserInfoDao();
            try {
                userInfo = userInfoDao.getUserInfo(userInfo);
                //返回后端数据
                if(userInfo.getId() != null) {
                    state = 200;
                    //创建session
                    HttpSession httpSession = req.getSession();
                    //将用户信息存入session中
                    httpSession.setAttribute("userinfo",userInfo);
                }else {
                    state = 100;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        HashMap<String,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("msg",msg);
        ResultJSONUtils.WriteMap(resp,result);
    }
}
