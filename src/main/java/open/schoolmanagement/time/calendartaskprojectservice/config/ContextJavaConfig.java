package open.schoolmanagement.time.calendartaskprojectservice.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages =
    {"open.schoolmanagement.time.calendartaskprojectservice",
        "open.schoolmanagement.time.calendartaskprojectservice.repository"})
@ServletComponentScan(basePackages =
    {"open.schoolmanagement.time.calendartaskprojectservice",
        "open.schoolmanagement.time.calendartaskprojectservice.repository"})
public class ContextJavaConfig {

}
