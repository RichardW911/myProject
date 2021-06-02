package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ResultJSONUtils {

    public static void write(HttpServletResponse response,String jsonStr) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/text");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);

        printWriter.close();
    }

    public static void WriteMap(HttpServletResponse response, HashMap<String,Object> map) throws IOException {

        PrintWriter printWriter = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        printWriter.println(mapper.writeValueAsString(map));

        printWriter.close();
    }

    public static void WriteJson(HttpServletResponse response, Object object) throws IOException {

        PrintWriter printWriter = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        printWriter.println(mapper.writeValueAsString(object));
    }

    public static void WriteJsonMap(HttpServletResponse response, HashMap<String,Object> map) throws IOException {

        PrintWriter printWriter = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        printWriter.println(mapper.writeValueAsString(map));
    }
}
