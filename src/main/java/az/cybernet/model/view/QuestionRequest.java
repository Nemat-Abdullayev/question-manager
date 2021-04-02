package az.cybernet.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("request model for question")
public class QuestionRequest {

    @ApiModelProperty("content for question")
    @NotBlank
    private String content;
    @Max(6)
    @Min(1)
    @ApiModelProperty("max 6 min 1")
    private int difficulty;

}
