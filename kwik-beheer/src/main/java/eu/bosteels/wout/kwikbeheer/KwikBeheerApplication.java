package eu.bosteels.wout.kwikbeheer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@SpringBootApplication
public class KwikBeheerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(KwikBeheerApplication.class)
                .run(args);
    }

}