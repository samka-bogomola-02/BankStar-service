package bank.recommendationservice.fintech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class RecommendationDTO {

    @NotNull(message = "ID cannot be null")
    private UUID id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Text cannot be blank")
    private String text;

    public RecommendationDTO() {
    }
    public RecommendationDTO(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationDTO that = (RecommendationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text);
    }

    @Override
    public String toString() {
        return "RecommendationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
