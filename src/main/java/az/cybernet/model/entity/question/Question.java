package az.cybernet.model.entity.question;


import az.cybernet.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CBRNT_QUESTION")
public class Question extends BaseEntity {

    @Column(name = "CONTENT", length = 4000)
    private String content;

    @Column(name = "CONFIRMED")
    private boolean confirmed=false;

    @Column(name = "DIFFICULTY")
    private int difficulty;

}
