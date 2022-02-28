package dev.yudin;

import dev.yudin.config.AppConfig;
import dev.yudin.dialogues.Dialogue;
import dev.yudin.dialogues.InitialAppDialogue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class WellAppApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

		try (Scanner scan = new Scanner(System.in)) {
			try {
				Dialogue initialAppDialogue = applicationContext.getBean(InitialAppDialogue.class);

				initialAppDialogue.start(scan);
			} catch (IllegalArgumentException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}
}
