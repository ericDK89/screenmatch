package br.com.projeto01.screenmatch.main;

import br.com.projeto01.screenmatch.model.Episode;
import br.com.projeto01.screenmatch.model.Season;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.File;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
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
  private final Scanner scanner = new Scanner(System.in);

  @Autowired GsonConvertData convertData;

  @Autowired ApiService apiService;

  @Autowired File file;

  @Value("${apikey}")
  private String apiKey;

  public void execute() {
    //    var episodeURI =
    //        "https://www.omdbapi.com/?t=breaking+bad&season=1&episode=2&type=series&apikey=" +
    // apiKey;

    try {
      //      System.out.println("Type serie's name: ");
      //      var serieName = scanner.nextLine().trim().replace(" ", "+");
      String URL = "https://www.omdbapi.com/";

      List<Season> seasons = new ArrayList<>();

      Season season;
      var seasonCount = 1;
      HttpResponse<String> response;

      while (true) {
        var seasonURI =
            URL + "?t=" + "euphoria" + "&season=" + seasonCount + "&type=series&apikey=" + apiKey;
        response = apiService.get(seasonURI);

        season = convertData.fromJson(response.body(), Season.class);

        if (!season.response()) break;

        seasons.add(season);
        seasonCount++;
      }

      var jsonSeasons = convertData.toJson(seasons);
      file.write("seasons.json", jsonSeasons);

      List<Episode> episodes =
          seasons.stream()
              .flatMap(
                  s ->
                      s.episodeDTOS().stream()
                          .map(
                              dto -> {
                                Episode episode = new Episode();
                                episode.setTitle(dto.title());
                                episode.setSeason(s.season());
                                episode.setNumber(dto.episode());
                                episode.setRating(dto.rating());
                                episode.setReleasedAt(dto.releasedAt());
                                return episode;
                              }))
              .toList();

      List<Episode> topFive =
          episodes.stream()
              .sorted(Comparator.comparingDouble(Episode::getRating).reversed())
              .limit(5)
              .toList();

      int year;

      while (true) {
        try {
          System.out.println("Year: ");
          year = scanner.nextInt();
          break;
        } catch (InputMismatchException e) {
          System.err.println(e.getMessage());
        }
      }

      scanner.nextLine();

      LocalDate selectedYear = LocalDate.of(year, 1, 1);

      List<Episode> episodesFromYear =
          episodes.stream()
              .filter(e -> e.getReleasedAt() != null && e.getReleasedAt().isAfter(selectedYear))
              .toList();

      episodesFromYear.forEach(System.out::println);

    } catch (InputMismatchException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
