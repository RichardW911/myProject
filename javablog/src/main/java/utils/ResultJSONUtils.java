package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ResultJSONUtils {
    /**
     * 统一的信息类
     */
    public static void write(HttpServletResponse response,String jsonStr) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/text");
        PrintWriter writer = response.getWriter();
        writer.println(jsonStr);
    }

    public static void writeMap(HttpServletResponse response, HashMap<String,Object> map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/text");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.println(mapper.writeValueAsString(map));
    }
}
