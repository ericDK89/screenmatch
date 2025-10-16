package br.com.projeto01.screenmatch.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record Season(
    @SerializedName("Season") Integer season,
    @SerializedName("Episodes") List<EpisodeDTO> episodeDTOS,
    @SerializedName("Response") boolean response) {}
