package az.cybernet.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("model for confirm question's status if user role is ADMIN")
public class ConfirmedQuestionRequest {

    @ApiModelProperty("difficulty degree of question")
    @Max(6)
    @Min(1)
    private int difficulty;
}
