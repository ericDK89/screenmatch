package br.com.projeto01.screenmatch.exceptions;

public class SeriesNotFound extends RuntimeException {

  public SeriesNotFound() {
    super("Series not found.");
  }
}
