package br.com.projeto01.screenmatch.model;

import br.com.projeto01.screenmatch.dto.SeriesDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
public class Series {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Length(min = 1, max = 255)
  @Column(unique = true, nullable = false)
  private String title;
  private List<String> genres;
  private List<String> actors;
  @Length(min = 1, max = 255)
  private String poster;
  @Length(min = 1, max = 255)
  private String plot;
  private int totalSeason;
  private double rating;
  @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
  private List<Episode> episodes = new ArrayList<>();

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

  public void setEpisodes(List<Episode> episodes) {
    episodes.forEach(e -> e.setSeries(this));
    this.episodes = episodes;
  }

  private List<Episode> getOrderedEpisodes() {
    List<Episode> episodesOrderedBySeason = this.getEpisodes().stream()
        .sorted(Comparator.comparingInt(Episode::getSeason)).toList();

    return episodesOrderedBySeason.stream().sorted(Comparator.comparingInt(Episode::getNumber))
        .toList();
  }

  @Override
  public String toString() {

    SeriesDTO seriesDTO = new SeriesDTO(
        getTitle(),
        getGenres().toString(),
        getActors().toString(),
        getPoster(),
        getPlot(),
        getTotalSeason(),
        String.valueOf(getRating()),
        getOrderedEpisodes()
    );

    return seriesDTO.toString();
  }
}
