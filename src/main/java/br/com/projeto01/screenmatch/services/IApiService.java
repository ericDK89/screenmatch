package br.com.projeto01.screenmatch.services;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface IApiService {
  HttpResponse<String> get(String url) throws IOException;
}
