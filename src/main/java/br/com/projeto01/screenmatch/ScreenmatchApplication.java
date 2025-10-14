package br.com.projeto01.screenmatch;

import br.com.projeto01.screenmatch.model.Serie;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

  @Autowired GsonConvertData convertData;

  @Autowired ApiService apiService;

  @Value("${apikey}")
  private String apiKey;

  public static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    var address = "https://www.omdbapi.com/?t=breaking+bad&type=series&apikey=" + apiKey;
    var response = apiService.get(address);
    var result = convertData.fromJson(response.body(), Serie.class);
    System.out.println("result");
    System.out.println(result);
  }
}
