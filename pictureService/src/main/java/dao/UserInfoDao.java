package dao;

import models.UserInfo;
import utils.DBUtils;

import java.sql.*;

public class UserInfoDao {


    public boolean add(UserInfo userInfo) throws SQLException {
        boolean result = false;
        if(userInfo.getUsername() != null && userInfo.getPassword() != null) {
            Connection connection = DBUtils.getConnection();
            String sql = "insert into userinfo(username,password) values (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userInfo.getUsername());
            preparedStatement.setString(2,userInfo.getPassword());

            result = preparedStatement.executeUpdate() >= 1;

            //关闭连接
            DBUtils.close(connection,preparedStatement,null);
        }
        return result;
    }

/*    public boolean isLogin(UserInfo userInfo) throws SQLException {
        boolean result = false;
        if(userInfo.getUsername() != null && userInfo.getPassword() != null &&
                !userInfo.getUsername().equals("") && !userInfo.getPassword().equals("")) {
            Connection connection = DBUtils.getConnection();
            String sql = "select * from userinfo where username=? and password=? and state=1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userInfo.getUsername());
            preparedStatement.setString(2,userInfo.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                result = true;
            }

            //关闭连接
            DBUtils.close(connection,preparedStatement,resultSet);
        }

        return result;
    }*/

    public UserInfo getUserInfo(UserInfo userInfo) throws SQLException {
        UserInfo user = new UserInfo();
        //非空效验
        if(userInfo.getUsername() != null && userInfo.getPassword() != null &&
        !userInfo.getUsername().equals("") && !userInfo.getPassword().equals("")) {
            Connection connection = DBUtils.getConnection();
            String sql = "select * from userinfo where username=? and password=? and state=1";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userInfo.getUsername());
            statement.setString(2, userInfo.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatetime(resultSet.getDate("createtime"));
                user.setUpdatetime(resultSet.getDate("updatetime"));
                user.setState(resultSet.getInt("state"));
            }
            //关闭连接
            DBUtils.close(connection, statement, resultSet);
        }
        return user;
    }
}
