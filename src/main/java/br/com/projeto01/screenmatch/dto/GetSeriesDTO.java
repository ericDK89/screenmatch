package br.com.projeto01.screenmatch.dto;

import java.util.List;

public record GetSeriesDTO(
    String title,
    String poster,
    String plot,
    List<String> genres,
    List<String> actors,
    Integer totalSeasons,
    Double rating
) {

}
