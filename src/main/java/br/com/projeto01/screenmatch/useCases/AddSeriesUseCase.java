package br.com.projeto01.screenmatch.useCases;

import br.com.projeto01.screenmatch.dto.AddSeriesDTO;
import br.com.projeto01.screenmatch.dto.SeriesDTO;
import br.com.projeto01.screenmatch.exceptions.SeriesNotFound;
import br.com.projeto01.screenmatch.model.Series;
import br.com.projeto01.screenmatch.repositories.SeriesRepository;
import br.com.projeto01.screenmatch.services.ApiService;
import br.com.projeto01.screenmatch.services.GsonConvertData;
import br.com.projeto01.screenmatch.utils.FormatInput;
import java.io.IOException;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddSeriesUseCase {

  @Autowired
  SeriesRepository seriesRepository;
  @Autowired
  ApiService apiService;
  @Autowired
  GsonConvertData gsonConvertData;
  @Value("${apikey}")
  private String apiKey;
  @Autowired
  private FormatInput formatInput;

  public void execute(String title) throws IOException {
    AddSeriesDTO addSeriesDTO = gsonConvertData.fromJson(title, AddSeriesDTO.class);

    if (addSeriesDTO.title() == null ||
        addSeriesDTO.title().isEmpty()
        || addSeriesDTO.title().isBlank()) {
      throw new NullPointerException("Series name is empty");
    }

    String URI = "https://www.omdbapi.com/?t=";
    HttpResponse<String> response = apiService.get(
        URI + formatInput.execute(addSeriesDTO.title()) + "&apikey=" + apiKey);

    if (response.body().contains("Movie not found!")) {
      throw new SeriesNotFound();
    }

    SeriesDTO seriesDTO = gsonConvertData.fromJson(response.body(), SeriesDTO.class);

    seriesRepository.save(new Series(seriesDTO)
    );
  }

}
