package dao;


import models.Image;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao {


    //查询图片
    public int getMyimage(String md5,int u_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String sql = "select count(0) count from imageinfo where md5=? and u_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, md5);
        preparedStatement.setInt(2,u_id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int res = resultSet.getInt("count");
        DBUtils.close(connection, preparedStatement, resultSet);
        return res;
    }

    //插入图片
    public int insert(Image image, int u_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        //image_id u_id image_name size upload_time md5 content_type path
        String sql = "insert into imageinfo values(null,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, u_id);
        preparedStatement.setString(2, image.getImage_name());
        preparedStatement.setLong(3, image.getSize());
        preparedStatement.setString(4, image.getUpload_time());
        preparedStatement.setString(5, image.getMd5());
        preparedStatement.setString(6, image.getContent_type());
        preparedStatement.setString(7, image.getPath());
        int res = preparedStatement.executeUpdate();
        DBUtils.close(connection, preparedStatement, null);
        return res;
    }

    public List<Image> queryAll(int u_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String sql = "select * from imageinfo where u_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, u_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Image> list = new ArrayList<>();
        while (resultSet.next()) {
            Image image = new Image();
            image.setImageId(resultSet.getInt("image_id"));
            image.setU_id(u_id);
            image.setImage_name(resultSet.getString("image_name"));
            image.setSize(resultSet.getLong("size"));
            image.setUpload_time(resultSet.getString("upload_time"));
            image.setContent_type(resultSet.getString("content_type"));
            image.setMd5(resultSet.getString("md5"));
            image.setPath(resultSet.getString("path"));

            list.add(image);
        }
        DBUtils.close(connection, preparedStatement, resultSet);
        return list;
    }

    public Image queryOne(int image_id, int u_id) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String sql = "select * from imageinfo where image_id=? and u_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, image_id);
        preparedStatement.setInt(2, u_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Image image = null;
        while (resultSet.next()) {
            image = new Image();
            image.setImageId(resultSet.getInt("image_id"));
            image.setU_id(u_id);
            image.setSize(resultSet.getLong("size"));
            image.setUpload_time(resultSet.getString("upload_time"));
            image.setContent_type(resultSet.getString("content_type"));
            image.setMd5(resultSet.getString("md5"));
            image.setPath(resultSet.getString("path"));
        }
        DBUtils.close(connection, preparedStatement, resultSet);
        return image;
    }

    public int delete(int image_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);

            String sql = "delete from imageinfo where image_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, image_id);
            int res = preparedStatement.executeUpdate();
            connection.commit();
            return res;
        } catch (Exception e) {

            connection.rollback();
            throw new RuntimeException("删除图片回滚出错:"+image_id, e);
        } finally {
            DBUtils.close(connection, preparedStatement, null);
        }


    }
}
