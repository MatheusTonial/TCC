package utfpr.edu.br.tcc;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
//import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.ISpringTemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.*;

import java.util.Locale;


@SpringBootApplication
//@ComponentScan({"utfpr.edu.br.tcc.repository"})
public class TccApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

//	private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
//		SpringTemplateEngine engine = new SpringTemplateEngine();
//		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
//		engine.addDialect(new Java8TimeDialect());
//		engine.setTemplateResolver(templateResolver);
//		engine.setTemplateEngineMessageSource(messageSource());
//		return engine;
//	}
	@Bean
	public org.thymeleaf.spring3.ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		org.thymeleaf.spring3.SpringTemplateEngine engine = new org.thymeleaf.spring3.SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new Java8TimeDialect());
		engine.addDialect(new SpringSecurityDialect());
		engine.setTemplateResolver(templateResolver);
		engine.setTemplateEngineMessageSource(messageSource());
		return engine;
	}

	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}


}
