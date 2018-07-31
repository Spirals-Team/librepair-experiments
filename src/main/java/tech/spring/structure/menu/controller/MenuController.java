package tech.spring.structure.menu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.spring.structure.menu.model.MenuItem;
import tech.spring.structure.menu.service.MenuService;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public List<MenuItem> menu(HttpServletRequest request, HttpServletResponse response) {
        return menuService.get(request, response);
    }

}
