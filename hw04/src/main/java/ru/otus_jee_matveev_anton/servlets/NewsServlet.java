package ru.otus_jee_matveev_anton.servlets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NewsServlet", urlPatterns = "/news")
public class NewsServlet extends HttpServlet {

    public static final String HTTPS_WWW_RBC_RU = "https://www.rbc.ru/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Document doc;
        response.setContentType("text/html; charset=UTF8");
        try (PrintWriter pw = response.getWriter()) {
//            doc = Jsoup.connect("http://google.com/").get();
            doc = Jsoup.connect(HTTPS_WWW_RBC_RU).get();
            Elements mainCont = doc.getElementsByClass("main-feed");
            if(!mainCont.isEmpty()){
                Elements news = mainCont.get(0).children();
                news.forEach(pw::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
