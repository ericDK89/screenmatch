package br.com.projeto01.screenmatch.dto;

import com.google.gson.annotations.SerializedName;

public record SeriesDTO(
    @SerializedName("Title") String title,
    @SerializedName("Genre") String genre,
    @SerializedName("Actors") String actors,
    @SerializedName("Poster") String poster,
    @SerializedName("Plot") String plot,
    @SerializedName("totalSeasons") int totalSeason,
    @SerializedName("imdbRating") String rating) {

}
