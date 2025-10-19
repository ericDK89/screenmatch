package br.com.projeto01.screenmatch.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record SeasonDTO(
    @SerializedName("Season") Integer season,
    @SerializedName("Episodes") List<EpisodeDTO> episodeDTOS,
    @SerializedName("Response") boolean response) {

}
