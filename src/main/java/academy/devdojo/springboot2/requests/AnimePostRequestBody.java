package academy.devdojo.springboot2.requests;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePostRequestBody {
    @NotEmpty
    @NotNull
    private String name;
}
