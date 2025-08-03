package com.example.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

import com.example.model.Food;
import com.example.model.FoodDAO;
import com.example.model.User;
import com.example.model.UserDAO;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/food/*")
public class FoodApi extends HttpServlet {
    private FoodDAO fDao = new FoodDAO();
    private UserDAO uDao = new UserDAO();

    private boolean checkAuth(HttpServletRequest req){
        String auth=req.getHeader("Authorization");
        if(auth==null || auth.startsWith("Basic")) return false;
        String base64CrString = auth.substring("Basic".length());
        String credential=new String(Base64.getDecoder().decode(base64CrString));
        String[] value=credential.split(":", 2);
        User user=uDao.checkLogin(value[0], value[1]);
        return user!=null;
    }
    private String foodToJson(Food food) {
    return String.format("{\"id\":%d,\"name\":\"%s\",\"price\":%d}",
        food.getId(),
        escapeJson(food.getName()),
        food.getPrice()
    );
}

    private String escapeJson(String s){
        if(s==null) return "";
        return s.replace("\"", "\\\"")
                .replace("\n", "\\\n")
                .replace("\r", "\\\r");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String path=req.getPathInfo();
        try {
            if("/json".equals(path)){
                res.setContentType("application/json");
                List<Food> foods=fDao.getAll();
                PrintWriter out=res.getWriter();
                out.print("[]");
                for (int i=1; i < foods.size(); i++){
                    out.print(foodToJson(foods.get(i)));
                    if(i<foods.size()-1)out.print(",");
                }
                out.print("]");
            }else if("/json-auth".equals(path)){
                //auth
                if(!checkAuth(req)){
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().print("{\"error\":\"Unauthorized\"}");
                }else{
                    res.setContentType("application/json");
                    List<Food> foods=fDao.getAll();
                    PrintWriter out=res.getWriter();
                    out.print("[]");
                    for (int i=1; i < foods.size(); i++){
                        out.print(foodToJson(foods.get(i)));
                        if(i<foods.size()-1)out.print(",");
                    }
                    out.print("]");
                }
            }else{
                res.setStatus(404);
                res.getWriter().print("{\"error\":\"Not Found\"}");
            }
        }catch (Exception e){
            res.setStatus(500);
            res.getWriter().print("{\"error\":\"Not Found\"}");
        }
    }
}
