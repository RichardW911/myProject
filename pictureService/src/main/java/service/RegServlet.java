package service;

import dao.UserInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class RegServlet extends HttpServlet {


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/text");
        int state = -1;

        //获取前端参数
        String msg= "";
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //PrintWriter writer = resp.getWriter();

        if(username == null || password == null) {
            msg = "参数异常,请重新注册!";
        }else {
            //操作数据库进行插入操作
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            //调用操作数据库的方法
            UserInfoDao userInfoDao = new UserInfoDao();

            try {
                boolean res = false;
                res = userInfoDao.add(userInfo);
                if(res) {
                    state = 200;
                }else {
                    state = 100;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            HashMap<String,Object> result = new HashMap<>();
            result.put("state",state);
            result.put("msg",msg);
            //ObjectMapper mapper = new ObjectMapper();

            ResultJSONUtils.WriteMap(resp,result);
        }
    }
}
