package tech.spring.structure.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tech.spring.structure.auth.evaluator.StructureSecurityExpressionEvaluator;
import tech.spring.structure.auth.model.Role;
import tech.spring.structure.auth.model.User;
import tech.spring.structure.menu.config.MenuConfig;
import tech.spring.structure.menu.model.MenuItem;

@Service
public class MenuService {

    @Autowired
    private MenuConfig menuConfig;

    @Autowired
    private StructureSecurityExpressionEvaluator securityExpressionEvaluator;

    public List<MenuItem> get(HttpServletRequest request, HttpServletResponse response) {
        List<MenuItem> menu = new ArrayList<MenuItem>();
        menu.addAll(menuConfig.getItems());
        menu.addAll(getItemsPerRole());
        menu.addAll(getItemsPerSecurityExpression(request, response));
        return menu;
    }

    private List<MenuItem> getItemsPerSecurityExpression(HttpServletRequest request, HttpServletResponse response) {
        List<MenuItem> menu = new ArrayList<MenuItem>();
        menuConfig.getItemsPerSecurityExpression().forEach(secuirtyExpressionItem -> {
            if (securityExpressionEvaluator.evaluate(secuirtyExpressionItem.getExpression(), request, response)) {
                menu.add(secuirtyExpressionItem.getItem());
            }
        });
        return menu;
    }

    private List<MenuItem> getItemsPerRole() {
        List<MenuItem> menu = new ArrayList<MenuItem>();
        Optional<User> user = getUser();
        if (user.isPresent()) {
            Optional<Map<Role, List<MenuItem>>> itemsPerRole = Optional.ofNullable(menuConfig.getItemsPerRole());
            if (itemsPerRole.isPresent()) {
                Optional<List<MenuItem>> items = Optional.ofNullable(itemsPerRole.get().get(user.get().getRole()));
                if (items.isPresent()) {
                    menu.addAll(items.get());
                }
            }
        }
        return menu;
    }

    private Optional<User> getUser() {
        Optional<User> user = Optional.empty();
        Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        if (authentication.isPresent()) {
            try {
                user = Optional.of((User) authentication.get().getPrincipal());
            } catch (ClassCastException e) {

            }
        }
        return user;
    }

}
