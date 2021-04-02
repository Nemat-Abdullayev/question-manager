package az.cybernet.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("question response for user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionResponse {

    @ApiModelProperty("content of question")
    private String content;

    @ApiModelProperty("difficulty degree of question")
    private int difficulty;

    @ApiModelProperty("is confirmed question or not")
    private boolean confirmed;
}
