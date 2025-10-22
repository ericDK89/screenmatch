package br.com.projeto01.screenmatch.controllers;

import br.com.projeto01.screenmatch.exceptions.SeriesNotFound;
import br.com.projeto01.screenmatch.useCases.AddSeriesUseCase;
import br.com.projeto01.screenmatch.useCases.GetAllSeriesUseCase;
import br.com.projeto01.screenmatch.useCases.GetSeriesBasedOnParams;
import br.com.projeto01.screenmatch.useCases.GetSeriesUseCase;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/series")
public class SeriesControllers {

  @Autowired
  private GetSeriesUseCase getSeriesUseCase;

  @Autowired
  private GetAllSeriesUseCase getAllSeriesUseCase;

  @Autowired
  private AddSeriesUseCase addSeriesUseCase;

  @Autowired
  private GetSeriesBasedOnParams getSeriesBasedOnParams;

  @GetMapping("/{title}")
  public ResponseEntity<String> getSeries(@PathVariable String title) {
    try {
      String series = getSeriesUseCase.execute(title);
      return ResponseEntity.ok(series);
    } catch (SeriesNotFound e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series not found");
    }
  }

  @GetMapping
  public ResponseEntity<String> getAllSeries(
      @RequestParam(required = false, value = ("orderBy")) String orderBy) {
    try {
      String allSeries = getAllSeriesUseCase.execute(orderBy);
      return ResponseEntity.ok(allSeries);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/add")
  public ResponseEntity<String> addSeries(@Valid @RequestBody String title) {
    try {
      addSeriesUseCase.execute(title);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (IOException | SeriesNotFound | NullPointerException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
