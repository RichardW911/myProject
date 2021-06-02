package service;

import dao.ArticleInfoDao;
import models.vo.ArticleInfoVO;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg ="";
        List<ArticleInfoVO> list = null;
        //1.从前端获取参数（如果没有参数，忽略此步骤）
        //当前页码
        int cpage = Integer.parseInt(request.getParameter("cpage"));
        //每页显示条数
        int psize = Integer.parseInt(request.getParameter("psize"));
        //2.操作数据，执行相应的业务逻辑
        ArticleInfoDao dao = new ArticleInfoDao();
        try {
            // 查询数据库得到列表
            list = dao.getListByPage(cpage,psize);
            succ = 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //3.返回结果给前端
        HashMap<String,Object> result = new HashMap<>();
        result.put("succ",succ);
        result.put("msg",msg);
        result.put("list",list);
        ResultJSONUtils.writeMap(response,result);
    }
}
