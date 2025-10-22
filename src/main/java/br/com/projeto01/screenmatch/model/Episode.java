package br.com.projeto01.screenmatch.model;

import br.com.projeto01.screenmatch.dto.EpisodeDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Data
public class Episode {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Length(min = 1, max = 255)
  private String title;
  @ManyToOne
  @JoinColumn(name = "series_id", nullable = false)
  private Series series;
  private Integer season;
  private Integer number;
  private Double rating;
  private LocalDate releasedAt;

  public Episode(Integer season, EpisodeDTO episodeDTO) {
    this.title = episodeDTO.title();
    this.season = season;
    this.rating = formatRating(episodeDTO.rating());
    this.number = episodeDTO.episode();
    this.releasedAt = formatReleasedAt(episodeDTO.releasedAt());
  }

  private LocalDate formatReleasedAt(String releasedAtStr) throws DateTimeParseException {
    try {
      return LocalDate.parse(releasedAtStr);
    } catch (DateTimeParseException e) {
      return null;
    }
  }

  private Double formatRating(String ratingStr) throws NumberFormatException {
    try {
      return Double.parseDouble(ratingStr);
    } catch (NumberFormatException | NullPointerException e) {
      return 0.0;
    }
  }

  public String getReleasedAt() {
    if (this.releasedAt == null) {
      return "";
    }
    return this.releasedAt.toString();
  }

  @Override
  public String toString() {
    EpisodeDTO episodeDTO = new EpisodeDTO(
        getTitle(),
        getNumber(),
        String.valueOf(getRating()),
        getReleasedAt()
    );

    return episodeDTO.toString();
  }
}
