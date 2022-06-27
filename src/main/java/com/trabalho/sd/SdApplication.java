package com.trabalho.sd;

import com.trabalho.sd.view.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdApplication.class, args);

		Menu menu = new Menu();
		menu.menuInicial();
	}

}
