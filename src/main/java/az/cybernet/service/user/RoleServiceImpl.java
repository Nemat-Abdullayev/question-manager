package az.cybernet.service.user;

import az.cybernet.model.entity.user.UserRole;
import az.cybernet.model.enums.RoleName;
import az.cybernet.repository.userRole.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public UserRole findOne(RoleName roleName) {
        try {
            return roleRepository.findByRoleName(roleName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
