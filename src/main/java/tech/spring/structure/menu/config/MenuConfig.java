package tech.spring.structure.menu.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import tech.spring.structure.auth.model.Role;
import tech.spring.structure.menu.model.MenuItem;

@Configuration
@ConfigurationProperties(prefix = "structure.menu")
public class MenuConfig {

    private List<MenuItem> items;

    private Map<Role, List<MenuItem>> itemsPerRole;

    private List<SecurityExpressionMenuItem> itemsPerSecurityExpression;

    public MenuConfig() {
        this.items = new ArrayList<MenuItem>();
        this.itemsPerRole = new HashMap<Role, List<MenuItem>>();
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public Map<Role, List<MenuItem>> getItemsPerRole() {
        return itemsPerRole;
    }

    public void setItemsPerRole(Map<Role, List<MenuItem>> itemsPerRole) {
        this.itemsPerRole = itemsPerRole;
    }

    public List<SecurityExpressionMenuItem> getItemsPerSecurityExpression() {
        return itemsPerSecurityExpression;
    }

    public void setItemsPerSecurityExpression(List<SecurityExpressionMenuItem> itemsPerSecurityExpression) {
        this.itemsPerSecurityExpression = itemsPerSecurityExpression;
    }

    public static class SecurityExpressionMenuItem {

        private String expression;

        private MenuItem item;

        public SecurityExpressionMenuItem() {

        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public MenuItem getItem() {
            return item;
        }

        public void setItem(MenuItem item) {
            this.item = item;
        }

    }

}
