package br.com.projeto01.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.OptionalDouble;
import lombok.Data;

@Data
public class Episode {

  private String title;
  private Integer season;
  private Integer number;
  private Double rating;
  private LocalDate releasedAt;

  public void setReleasedAt(String releasedAt) {
    try {
      this.releasedAt = LocalDate.parse(releasedAt);
    } catch (DateTimeParseException e) {
      this.releasedAt = null;
    }
  }

  public void setRating(String rating) {
    this.rating = OptionalDouble.of(Double.parseDouble(rating)).orElse(0.0);
  }
}
