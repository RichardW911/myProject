package utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    private static MysqlDataSource dataSource = null;
    public static Connection getConnection() throws SQLException {
        if(dataSource == null) {
            dataSource = new MysqlDataSource();
            //设置连接服务器地址
            /*jdbc:mysql://localhost:3306/*/
            dataSource.setURL("jdbc:mysql://localhost:3306/picture_service?characterEncoding=utf-8&useSSL=false");
            //设置用户名
            dataSource.setUser("root");
            //设置密码
            dataSource.setPassword("11111111");
        }
        return dataSource.getConnection();
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if(connection != null) {
            connection.close();
        }
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        if(resultSet != null) {
            resultSet.close();
        }
    }
}
