package br.com.projeto01.screenmatch.model;

import br.com.projeto01.screenmatch.dto.SeriesDTO;
import java.util.List;
import java.util.OptionalDouble;
import lombok.Data;

@Data
public class Series {

  private String title;
  private List<String> genres;
  private List<String> actors;
  private String poster;
  private String plot;
  private int totalSeason;
  private double rating;

  public Series(SeriesDTO seriesDTO) {
    this.title = seriesDTO.title();
    this.genres = formatList(seriesDTO.genre());
    this.rating = OptionalDouble.of(Double.parseDouble(seriesDTO.rating())).orElse(0.0);
    this.actors = formatList(seriesDTO.actors());
    this.plot = seriesDTO.plot();
    this.totalSeason = seriesDTO.totalSeason();
    this.poster = seriesDTO.poster();
  }

  private List<String> formatList(String str) {
    List<String> list = List.of(str.split(","));
    return list.stream().map(String::trim).toList();
  }
}
