package br.com.projeto01.screenmatch.useCases;

import br.com.projeto01.screenmatch.dto.GetSeriesDTO;
import br.com.projeto01.screenmatch.exceptions.SeriesNotFound;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSeriesUseCase {

  @Autowired
  SeriesRepository seriesRepository;

  @Autowired
  GsonConvertData gsonConvertData;

  public String execute(String title) {
    Series series = seriesRepository.findByTitleContainingIgnoreCase(title).orElseThrow(
        SeriesNotFound::new);

    GetSeriesDTO seriesDTO = new GetSeriesDTO(
        series.getTitle(),
        series.getPoster(),
        series.getPlot(),
        series.getGenres(),
        series.getActors(),
        series.getTotalSeason(),
        series.getRating()
    );

    return gsonConvertData.toJson(seriesDTO);
  }
}
