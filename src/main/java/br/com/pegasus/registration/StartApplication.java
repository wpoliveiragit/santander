package br.com.pegasus.registration;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {

  public static void main(String[] args) {
    configureConsole();
    SpringApplication.run(StartApplication.class, args);
  }

  /**
   * Configura a saída padrão do console para utilizar UTF-8,
   * garantindo a correta exibição de caracteres especiais.
   */
  private static void configureConsole() {
    System.setOut(new PrintStream(
        new FileOutputStream(FileDescriptor.out),
        true,
        StandardCharsets.UTF_8));
  }

}
