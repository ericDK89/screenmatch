package br.com.projeto01.screenmatch.main;

import br.com.projeto01.screenmatch.dto.SeasonDTO;
import br.com.projeto01.screenmatch.dto.SeriesDTO;
import br.com.projeto01.screenmatch.exceptions.SeriesNotFound;
import br.com.projeto01.screenmatch.model.Episode;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.EpisodeRepository;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import br.com.projeto01.screenmatch.utils.FormatInput;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
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
  @Autowired
  EpisodeRepository episodeRepository;
  @Value("${apikey}")
  private String apiKey;

  private List<Series> series = new ArrayList<>();

  public void execute() throws IOException, InputMismatchException {

    int option = -1;

    while (option != 0) {
      String menu = """
          1 - search series
          2 - search episode
          3 - show all listed series
          
          0 - exit
          """;

      System.out.println(menu);

      try {
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
      } catch (InputMismatchException e) {
        System.err.println("Type a valid option");
        System.out.println(menu);
        sc.nextLine();
        option = -1;
      }
    }
  }

  private void showSeries() throws IOException {
    SeriesDTO seriesDTO = getSeriesData();
    Series series = new Series(seriesDTO);
    seriesRepository.save(series);
    System.out.println(series);
  }

  private SeriesDTO getSeriesData() throws IOException {
    try {
      System.out.println("Type the series name for search: ");
      String seriesInput = sc.nextLine();
      HttpResponse<String> response = apiService.get(
          URI + formatInput.execute(seriesInput) + "&apikey=" + apiKey);
      return convertData.fromJson(response.body(), SeriesDTO.class);

    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  private void getEpisodesPerSeries() throws IOException {
    showListedSeries();
    System.out.println("Choose a series.");
    String seriesInput = sc.nextLine();
    List<SeasonDTO> seasonsDTOList = new ArrayList<>();

    Series outputSeries = series.stream()
        .filter(s -> s.getTitle().equalsIgnoreCase(seriesInput))
        .findFirst().orElseThrow(SeriesNotFound::new);

    for (int i = 1; i <= outputSeries.getTotalSeason(); i++) {
      var response = apiService.get(
          URI + formatInput.execute(outputSeries.getTitle()) + "&season=" + i + "&apikey="
              + apiKey);
      SeasonDTO seasonDTO = convertData.fromJson(response.body(), SeasonDTO.class);
      seasonsDTOList.add(seasonDTO);
    }

    List<Episode> episodes = seasonsDTOList.stream()
        .flatMap(s -> s.episodeDTOS()
            .stream()
            .map(e -> new Episode(s.season(), e))).toList();

    outputSeries.setEpisodes(episodes);
    seriesRepository.save(outputSeries);
  }

  private void showListedSeries() {
    series = seriesRepository.findAllWithEpisodes();
    series.stream()
        .sorted(Comparator.comparing(Series::getTitle))
        .forEach(System.out::println);
  }
}
