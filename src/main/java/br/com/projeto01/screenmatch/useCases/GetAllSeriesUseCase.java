package br.com.projeto01.screenmatch.useCases;

import br.com.projeto01.screenmatch.dto.GetSeriesDTO;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.utils.GsonTypeAdapter;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAllSeriesUseCase {

  private final Gson gson = new Gson().newBuilder()
      .registerTypeAdapter(LocalDate.class, new GsonTypeAdapter())
      .serializeNulls().create();
  @Autowired
  SeriesRepository seriesRepository;

  public String execute(String params) {

    List<GetSeriesDTO> outputSeries;

    if (params == null) {
      return getSeries();
    }

    String response = "";

    switch (params.toLowerCase()) {
      case "desc":
        var seriesByDesc = seriesRepository.findNewestSeries();
        response = orderedSeries(seriesByDesc);
        break;
      case "asc":
        var seriesByAsc = seriesRepository.findOldestSeries();
        response = orderedSeries(seriesByAsc);
        break;
      default:
        return getSeries();
    }

    return response;
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

  private String orderedSeries(List<Series> series) {
    Gson gson = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new GsonTypeAdapter())
        .serializeNulls().create();

    List<GetSeriesDTO> getSeriesDTOS = formatToGetSeriesDTOList(series);

    return gson.toJson(getSeriesDTOS);
  }

  public String getSeries() {
    List<Series> allSeries = seriesRepository.findAll();

    List<GetSeriesDTO> seriesDTOSList = new ArrayList<>();

    for (Series s : allSeries) {
      seriesDTOSList.add(new GetSeriesDTO(
              s.getTitle(),
              s.getPoster(),
              s.getPlot(),
              s.getGenres(),
              s.getActors(),
              s.getTotalSeason(),
              s.getRating()
          )
      );
    }

    return gson.toJson(seriesDTOSList);
  }
}
