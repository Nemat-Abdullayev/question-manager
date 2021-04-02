package az.cybernet.model.entity.user;

import az.cybernet.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CBRN_USER")
public class User extends BaseEntity {

    @Column(name = "USERNAME", length = 20, unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne
    private UserRole userRole;

}
