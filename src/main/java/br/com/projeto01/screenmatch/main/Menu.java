package br.com.projeto01.screenmatch.main;

import br.com.projeto01.screenmatch.dto.SeasonDTO;
import br.com.projeto01.screenmatch.dto.SeriesDTO;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import br.com.projeto01.screenmatch.utils.FormatInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
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
  @Autowired
  SeriesRepository seriesRepository;
  @Value("${apikey}")
  private String apiKey;

  public void execute() throws IOException, InterruptedException {

    int option = -1;

    while (option != 0) {
      String menu = """
          1 - search series
          2 - search episode
          3 - show all listed series
          
          0 - exit
          """;

      System.out.println(menu);

      option = sc.nextInt();
      sc.nextLine();

      switch (option) {
        case 1:
          showSeries();
          break;
        case 2:
          getEpisodesPerSeries();
          break;
        case 3:
          showListedSeries();
        case 0:
          System.out.println("Exit");
          break;
        default:
          System.err.println("Insert a valid option");
          System.out.println(menu);
      }
    }
  }

  private void showSeries() throws IOException, InterruptedException {
    SeriesDTO seriesDTO = getSeriesData();

    Series series = new Series(seriesDTO);
    seriesRepository.save(series);
    System.out.println(series);
  }

  private SeriesDTO getSeriesData() throws IOException {
    try {
      System.out.println("Type the series name for search");
      String seriesDTO = sc.nextLine();
      var response = apiService.get(URI + formatInput.execute(seriesDTO) + "&apikey=" + apiKey);
      return convertData.fromJson(response.body(), SeriesDTO.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void getEpisodesPerSeries() throws IOException {
    SeriesDTO seriesDTO = getSeriesData();
    List<SeasonDTO> seasonDTOS = new ArrayList<>();

    for (int i = 1; i <= seriesDTO.totalSeason(); i++) {
      var response = apiService.get(
          URI + formatInput.execute(seriesDTO.title()) + "&season=" + i + "&apikey=" + apiKey);
      SeasonDTO seasonDTO = convertData.fromJson(response.body(), SeasonDTO.class);
      seasonDTOS.add(seasonDTO);
    }

    seasonDTOS.forEach(System.out::println);
  }

  private void showListedSeries() {
    List<Series> series = seriesRepository.findAll();
    series.stream().sorted(Comparator.comparing(Series::getTitle)).forEach(System.out::println);
  }
}
