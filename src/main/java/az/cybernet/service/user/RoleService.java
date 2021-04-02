package az.cybernet.service.user;

import az.cybernet.model.entity.user.UserRole;
import az.cybernet.model.enums.RoleName;

public interface RoleService {
    UserRole findOne(RoleName roleName);
}
