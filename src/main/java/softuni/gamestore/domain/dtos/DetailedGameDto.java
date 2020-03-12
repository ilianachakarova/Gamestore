package softuni.gamestore.domain.dtos;

import javax.validation.constraints.NotNull;

public class DetailedGameDto {
    private String title;

    public DetailedGameDto() {
    }

    public DetailedGameDto(String title) {
        this.title = title;
    }
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
