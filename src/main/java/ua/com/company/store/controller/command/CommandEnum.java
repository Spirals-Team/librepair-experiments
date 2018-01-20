package ua.com.company.store.controller.command;

import ua.com.company.store.controller.impl.executions.*;
import ua.com.company.store.controller.impl.redirection.*;
import ua.com.company.store.hashing.PasswordHashing;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.service.OrderService;
import ua.com.company.store.service.ProductImageService;
import ua.com.company.store.service.ProductService;
import ua.com.company.store.service.UserService;
import ua.com.company.store.utils.SessionManager;

/**
 * Enum using as map with key - url from client & value = command realisation;
 */
public enum CommandEnum {
    PAGE_NOT_FOUND {
        {
            this.key = "GET:pageNotFound";
            this.command = new CommandPageNotFound();
        }
    },
    HOME {{
        this.key = "GET:/store";
        this.command = new CommandHomePageCommand(UserService.getInstance());
    }
    },
    CHANGE_LOCALE {{

        this.key = "GET:/store/locale";
        this.command = new CommandChangeLocale();
    }
    },
    SORT_PRODUCTS{{

        this.key = "GET:/store/sortingProducts";
        this.command = new CommandSortProducts(ProductImageService.getInstance());
    }
    },

    SEARCH_PRODUCTS{{

        this.key = "GET:/store/searchProduct";
        this.command = new CommandSearchProduct(ProductImageService.getInstance());
    }
    },

    SIGNUP_PAGE {{
        this.key = "GET:/store/signUp";
        this.command = new CommandSignUpPage();

    }},
    DELETE_USER{{
        this.key = "GET:/store/deleteUser";
        this.command = new CommandDeleteUserExecution(UserService.getInstance());

    }},
    MARK_USER_AS_DEFAULTER{{
        this.key = "GET:/store/markAsDefaulter";
        this.command = new CommandMarkUserAsDefaulterExecution(UserService.getInstance());

    }},
    ADMIN_PAGE{{
        this.key = "GET:/store/adminPage";
        this.command = new CommandAdminPage(UserService.getInstance());

    }},
    LOGIN_PAGE {{
        this.key = "GET:/store/login";
        this.command = new CommandLoginPage();

    }},
    LOGIN_FORM {{
        this.key = "POST:/store/loginForm";
        this.command = new CommandLoginFormExecution(UserService.getInstance(),PasswordHashing.getInstance());

    }},
    ADD_NEW_PRODUCT_FORM {{
        this.key = "POST:/store/addNewProduct";
        this.command = new CommandAddNewProductByAdminExecution(ProductImageService.getInstance());

    }},
    LOGOUT_FORM {{
        this.key = "GET:/store/logout";
        this.command = new CommandRemoveSession(SessionManager.getSessionManager());

    }},
    CREATING_NEW_ORDER{{
        this.key = "GET:/store/createOrder";
        this.command = new CommandCreatingNewOrder(OrderService.getInstance(),ProductService.getInstance());

    }},
    DELETE_ORDER {{
        this.key = "GET:/store/deleteOrder";
        this.command = new CommandDeleteOrderExec(OrderService.getInstance(),UserService.getInstance());

    }},
    EXECUTION_ORDER{{
        this.key = "GET:/store/executeOrder";
        this.command = new CommandExecuteOrder(OrderService.getInstance(),UserService.getInstance());

    }},
    SIGNUP_FORM {{
        this.key = "POST:/store/signUpForm";
        this.command = new CommandSignUpFormExecution(UserService.getInstance(), PasswordHashing.getInstance());

    }};

    String key;
    CommandTypical command;

     public String getKey() {
        return key;
    }

    public CommandTypical getCommand() {
        return command;
    }

    public static CommandTypical getCommand(String key) {
        CommandTypical resCommand = null;
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getKey().equals(key)) {
                resCommand = commandEnum.getCommand();
            }
        }
        if (resCommand != null) {
            return resCommand;
        } else {
            return PAGE_NOT_FOUND.getCommand();
        }
    }
}
