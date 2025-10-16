package br.com.projeto01.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.Data;

@Data
public class Episode {
  private String title;
  private Integer season;
  private Integer number;
  private Double rating;
  private LocalDate releasedAt;

  public void setReleasedAt(Object releasedAt) {
    try {
      this.releasedAt = LocalDate.parse(releasedAt.toString());
    } catch (DateTimeParseException e) {
      this.releasedAt = null;
    }
  }

  public void setRating(Object rating) {
    try {
      this.rating = Double.parseDouble(rating.toString());
    } catch (NumberFormatException e) {
      this.rating = 0.0;
    }
  }
}
