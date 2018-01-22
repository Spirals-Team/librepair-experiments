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
public class EmbeddablePropertyTitle implements Serializable {

    private String lang;

    @Column(name = "title")
    private String title;

    public Lang getLang() {
        return Lang.fromValue(lang);
    }

    public void setLang(Lang lang) {
        if (lang == null) {
            this.lang = null;
        } else {
            this.lang = lang.toString();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EmbeddablePropertyTitle(String title) {
        this.title = title;
        this.lang = Lang.DEFAULT.toString();
    }

    public EmbeddablePropertyTitle() {
    }

    public EmbeddablePropertyTitle(String lang, String title) {
        this.lang = lang;

        this.title = title;
    }

    public static EmbeddablePropertyTitle of(String value) {
        return new EmbeddablePropertyTitle(Lang.DEFAULT.toString(), value);
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

            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EmbeddablePropertyTitle {\n");
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
