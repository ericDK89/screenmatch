package br.com.projeto01.screenmatch.useCases;

import br.com.projeto01.screenmatch.dto.GetSeriesDTO;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import br.com.projeto01.screenmatch.utils.GsonTypeAdapter;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSeriesBasedOnParams {

  @Autowired
  SeriesRepository seriesRepository;

  @Autowired
  GsonConvertData gsonConvertData;

  public String execute(String params) {

    List<GetSeriesDTO> outputSeries;

    switch (params.toLowerCase()) {
      case "desc":
        var seriesByDesc = seriesRepository.findNewestSeries();
        outputSeries = formatToGetSeriesDTOList(seriesByDesc);
        break;
      default:
        var allSeries = seriesRepository.findAll();
        outputSeries = formatToGetSeriesDTOList(allSeries);
    }

    Gson gson = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new GsonTypeAdapter())
        .serializeNulls().create();

    var t = outputSeries.stream()
        .map(GetSeriesDTO::title)
        .toList();

    return gson.toJson(t);
  }

  private List<GetSeriesDTO> formatToGetSeriesDTOList(List<Series> series) {
    return series.stream()
        .map(s -> new GetSeriesDTO(
            s.getTitle(),
            s.getPoster(),
            s.getPlot(),
            s.getGenres(),
            s.getActors(),
            s.getTotalSeason(),
            s.getRating()
        )).toList();
  }
}
