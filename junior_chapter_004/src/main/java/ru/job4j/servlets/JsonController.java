package ru.job4j.servlets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonController extends HttpServlet {
    private Validate logic = ValidateService.getInstance();
    private static final Map<Integer, SimpleUser> STORE = new ConcurrentHashMap<>();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        MAPPER.writeValue(writer, STORE.values());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String sex = req.getParameter("sex");
        String description = req.getParameter("description");
        String jsonText = String.format("{\"name\":\"%s\", \"surname\":\"%s\", \"sex\":\"%s\", \"description\":\"%s\"}", name, surname, sex, description);
        SimpleUser resault = MAPPER.readValue(jsonText, SimpleUser.class);
        STORE.put(COUNTER.getAndIncrement(), resault);
    }

    public static class SimpleUser {
        @JsonProperty("name")
        private String name;
        @JsonProperty("surname")
        private String surname;
        @JsonProperty("sex")
        private String sex;
        @JsonProperty("description")
        private String description;

        public SimpleUser() {

        }

        public SimpleUser(String name, String surname, String sex, String description) {
            this.name = name;
            this.surname = surname;
            this.sex = sex;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
