package ru.otus_jee_matveev_anton.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = "/currencyratings")
public class CurrencyServlet extends HttpServlet {
    private static final String CURRENCY_RATINGS_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static final String CURRENCY_TO_HTML_XSL = "/currency_cbr.xsl";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StreamSource stylesource = new StreamSource(getServletContext().getResourceAsStream(CURRENCY_TO_HTML_XSL));
        StreamSource xmlsource = getCurrencyRateXMLAsStreamSource();
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter pw = resp.getWriter()) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            transformer.transform(xmlsource, new StreamResult(pw));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private StreamSource getCurrencyRateXMLAsStreamSource() throws IOException {
        URL url = new URL(CURRENCY_RATINGS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return new StreamSource(connection.getInputStream());
    }
}




