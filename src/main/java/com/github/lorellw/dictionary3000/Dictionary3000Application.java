package com.github.lorellw.dictionary3000;

import com.github.lorellw.dictionary3000.entities.Word;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class Dictionary3000Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Dictionary3000Application.class, args);
	}

}
