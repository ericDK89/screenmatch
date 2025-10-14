package br.com.projeto01.screenmatch.model;

import com.google.gson.annotations.SerializedName;

public record Serie(
    @SerializedName("Title") String title,
    @SerializedName("totalSeasons") int totalSeason,
    @SerializedName("imdbRating") String rating) {}
