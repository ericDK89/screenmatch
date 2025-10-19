package br.com.projeto01.screenmatch.dto;

import com.google.gson.annotations.SerializedName;

public record EpisodeDTO(
    @SerializedName("Title") String title,
    @SerializedName("Episode") Integer episode,
    @SerializedName("imdbRating") String rating,
    @SerializedName("Released") String releasedAt) {

}
