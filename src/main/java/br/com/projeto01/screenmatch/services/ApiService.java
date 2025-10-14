package br.com.projeto01.screenmatch.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.RemoteException;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class ApiService implements IApiService {

  @Override
  public HttpResponse<String> get(String url) throws IOException {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(url))
              .GET()
              .timeout(Duration.ofMinutes(2))
              .header("Content-Type", "application/json")
              .build();

      return client.send(request, HttpResponse.BodyHandlers.ofString());

    } catch (IOException e) {
      throw new IOException(e.getMessage(), e.getCause());
    } catch (InterruptedException e) {
      throw new RemoteException(e.getMessage());
    }
  }
}
