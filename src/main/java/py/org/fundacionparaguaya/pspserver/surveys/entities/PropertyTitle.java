package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by rodrigovillalba on 10/9/17.
 */
@Embeddable
@Access(AccessType.FIELD)
public class PropertyTitle implements Serializable {


    @Enumerated(EnumType.STRING)
    private Lang lang;

    private String title;

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public PropertyTitle(String title) {
        this.title = title;
        this.lang = Lang.DEFAULT;
    }


    public PropertyTitle() {}

    public PropertyTitle(Lang lang, String title) {
        this.lang = lang;

        this.title = title;
    }

    public static PropertyTitle of(String value) {
        return new PropertyTitle(Lang.DEFAULT, value);
    }

    public enum Lang {
        DEFAULT("es"), ES("es"), ENG("eng");

        private String value;

        Lang(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static Lang fromValue(String text) {
            for (Lang b : Lang.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }

            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PropertyTitle {\n");

        sb.append("    lang: ").append(toIndentedString(lang)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
