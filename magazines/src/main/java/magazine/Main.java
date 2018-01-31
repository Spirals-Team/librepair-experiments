package magazine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** @author danis.tazeev@gmail.com */
final class Main {

    public static void main(String... args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-app-ctx.xml");
        ExamplesRepository repo = new ExamplesRepositoryImpl();
        repo.exampleInKeyword();
    }
}
