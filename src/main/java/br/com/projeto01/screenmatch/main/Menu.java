package br.com.projeto01.screenmatch.main;

import br.com.projeto01.screenmatch.model.Season;
import br.com.projeto01.screenmatch.model.Serie;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import br.com.projeto01.screenmatch.utils.FormatInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.imageio.IIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Menu {

  private final Scanner sc = new Scanner(System.in);
  private final String URI = "https://www.omdbapi.com/?t=";

  @Autowired
  GsonConvertData convertData;

  @Autowired
  ApiService apiService;

  @Autowired
  FormatInput formatInput;

  @Value("${apikey}")
  private String apiKey;

  public void execute() throws IOException {

    String menu = """
        1 - search series
        2 - search episode
        
        0 - exit
        """;

    System.out.println(menu);
    int option = -1;

    while (true) {
      try {
        option = sc.nextInt();
        sc.nextLine();

        if (option != 1 && option != 2 & option != 0) {
          System.out.println("Insert a valid option");
          System.out.println(menu);
          sc.nextLine();
        }

        break;
      } catch (InputMismatchException e) {
        System.err.println("Invalid input");
        sc.nextLine();
      }
    }

    switch (option) {
      case 1:
        showSeries();
        break;
      case 2:
        getEpisodesPerSeries();
        break;
      case 0:
        System.out.println("Exit");
    }
  }

  private void showSeries() throws IOException {
    Serie serie = getSeriesData();
    System.out.println(serie);
  }

  private Serie getSeriesData() throws IIOException {
    try {
      System.out.println("Type the series name for search");
      String series = sc.nextLine();
      var response = apiService.get(URI + formatInput.execute(series) + "&apikey=" + apiKey);
      return convertData.fromJson(response.body(), Serie.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void getEpisodesPerSeries() throws IOException {
    Serie series = getSeriesData();
    List<Season> seasons = new ArrayList<>();

    for (int i = 1; i <= series.totalSeason(); i++) {
      var response = apiService.get(
          URI + formatInput.execute(series.title()) + "&season=" + i + "&apikey=" + apiKey);
      Season season = convertData.fromJson(response.body(), Season.class);
      seasons.add(season);
    }

    seasons.forEach(System.out::println);
  }
}
