package ru.otus_jee_matveev_anton.servlets;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.otus_jee_matveev_anton.repository.IDBService;
import ru.otus_jee_matveev_anton.repository.JPAService;
import ru.otus_jee_matveev_anton.repository.model.Employee;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

@WebServlet(name = "MarshalXMLServlet", urlPatterns = "/xml")
public class MarshalXMLServlet extends HttpServlet {

    private static final String EMPLOYEES_SALARY_XPATH = String.format("//%s/@salary", Employee.TAG_NAME);
    private static final String EMPLOYEES_WITH_SALARY_GT = String.format("//%s[@%s>%%d]", Employee.TAG_NAME, Employee.SALARY_TAG);
    private static final Path MARSHAL_JSON_TO = Paths.get("employee.json");
    private static final Path MARSHAL_XML_TO = Paths.get("employee.xml");
    private static final String EMPLOYEE_ROOT_NAME = "listOfEmployees";
    private static final String EMPLOYEES_LIST_WRAP_NAME = "employees";

    private final IDBService service = new JPAService();
    private final XPath xPath = XPathFactory.newInstance().newXPath();
    private XPathExpression xPathSalary;

    @Override
    public void init() throws ServletException {
        try {
            xPathSalary = xPath.compile(EMPLOYEES_SALARY_XPATH);
        } catch (XPathExpressionException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter responseWriter = response.getWriter();
        try {
            marshalEmployeesInXml(responseWriter);
            outEmployeesWithSalaryGtAvg(responseWriter);
            convertEmployeesXmlToJson(responseWriter);
            unmarshalEmployeesFromJson(responseWriter);
        } catch (Exception e) {
            e.printStackTrace(responseWriter);
        }
    }

    private void unmarshalEmployeesFromJson(PrintWriter responseWriter) throws IOException {
        responseWriter.println("unmarshalling employees from json:");
        Jsonb jsonb = JsonbBuilder.create();
        ListOfEmployees listOfEmployees = jsonb.fromJson(Files.newBufferedReader(MARSHAL_JSON_TO), ListOfEmployees.class);
        List<Employee> employees = listOfEmployees.employees;
        IntStream.range(0, employees.size()).filter(i -> i % 2 == 0).mapToObj(employees::get).forEach(responseWriter::println);
    }

    private void convertEmployeesXmlToJson(PrintWriter responseWriter) throws IOException {
        JSONObject xmlJSONObj = XML.toJSONObject(new String(Files.readAllBytes(MARSHAL_XML_TO)));
        String jsonStr = ((JSONObject) xmlJSONObj.get(EMPLOYEE_ROOT_NAME)).get(EMPLOYEES_LIST_WRAP_NAME).toString();
        Files.write(MARSHAL_JSON_TO, jsonStr.getBytes());
        responseWriter.println("json from xml saved to:" + MARSHAL_JSON_TO.toAbsolutePath());
        responseWriter.println("json from xml :");
        responseWriter.println(jsonStr);
    }

    private void marshalEmployeesInXml(PrintWriter responseWriter) throws IOException, JAXBException {
        ListOfEmployees employees = new ListOfEmployees(service.getAllEmployees());
        JAXBContext jaxbContext = JAXBContext.newInstance(ListOfEmployees.class, Employee.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(employees, Files.newOutputStream(MARSHAL_XML_TO));
        responseWriter.println("employees marshal to " + MARSHAL_XML_TO.toAbsolutePath());
    }

    private void outEmployeesWithSalaryGtAvg(PrintWriter responseWriter) throws IOException, XPathExpressionException, TransformerException {
        InputSource is = new InputSource(Files.newInputStream(MARSHAL_XML_TO));
        NodeList salaries = (NodeList) xPathSalary.evaluate(is, XPathConstants.NODESET);

        if (salaries.getLength() > 0) {
            int sum = IntStream.range(0, salaries.getLength()).map(i -> Integer.parseInt(salaries.item(i).getNodeValue())).sum();
            int avgSalary = sum / salaries.getLength();
            responseWriter.println("avg salary: " + avgSalary);

            XPathExpression empWithSalaryGtAvg = xPath.compile(String.format(EMPLOYEES_WITH_SALARY_GT, avgSalary));
            is = new InputSource(Files.newInputStream(MARSHAL_XML_TO));
            NodeList emps = (NodeList) empWithSalaryGtAvg.evaluate(is, XPathConstants.NODESET);
            responseWriter.println(nodeListToString(emps));
        } else {
            responseWriter.println("salaries not found");
        }
    }

    private static String nodeListToString(NodeList nodes) throws TransformerException {
        DOMSource source = new DOMSource();
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        for (int i = 0; i < nodes.getLength(); ++i) {
            source.setNode(nodes.item(i));
            transformer.transform(source, result);
        }

        return writer.toString();
    }

    @XmlRootElement(name = EMPLOYEE_ROOT_NAME)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ListOfEmployees {
        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }

        @XmlElementWrapper(name = EMPLOYEES_LIST_WRAP_NAME)
        private List<Employee> employees;

        public ListOfEmployees() {

        }

        public ListOfEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }
}
