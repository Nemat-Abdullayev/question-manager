package az.cybernet.repository.userRole;

import az.cybernet.model.entity.user.UserRole;
import az.cybernet.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRoleName(RoleName roleName);
}
