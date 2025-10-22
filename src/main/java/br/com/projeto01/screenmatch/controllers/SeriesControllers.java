package br.com.projeto01.screenmatch.controllers;

import br.com.projeto01.screenmatch.dto.SeriesDTO;
import br.com.projeto01.screenmatch.exceptions.SeriesNotFound;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/series")
public class SeriesControllers {

  @Autowired
  private SeriesRepository seriesRepository;

  @Autowired
  private GsonConvertData gsonConvertData;

  @GetMapping("/{title}")
  public ResponseEntity<Object> getSeries(@PathVariable String title) {
    try {
      Series series = seriesRepository.findByTitleContainingIgnoreCase(title).orElseThrow(
          SeriesNotFound::new);

      SeriesDTO seriesOutput = new SeriesDTO(
          series.getTitle(),
          series.getGenres().toString(),
          series.getActors().toString(),
          series.getPoster(),
          series.getPlot(),
          series.getTotalSeason(),
          String.valueOf(series.getRating()),
          series.getEpisodes()
      );

      return ResponseEntity.ok(seriesOutput);
    } catch (SeriesNotFound e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series not found");
    }
  }

}
