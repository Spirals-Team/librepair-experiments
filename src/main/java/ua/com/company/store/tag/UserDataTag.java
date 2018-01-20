package ua.com.company.store.tag;

import ua.com.company.store.locale.MessageLocale;
import ua.com.company.store.model.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Владислав on 12.12.2017.
 */
public class UserDataTag extends TagSupport {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(showUserData());
        } catch (IOException e) {
            e.printStackTrace();
        }
return SKIP_BODY;
    }
private String showUserData(){
        return new StringBuffer().append(MessageLocale.resourceBundle.getString("store.loggedInAs"))
                .append(user.getEmail()).append("(").append(MessageLocale.resourceBundle.getString(String.valueOf(user.isRole())))
                .append(")").toString();
}
}
