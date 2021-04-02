package az.cybernet.service.user;

import az.cybernet.model.entity.user.User;
import az.cybernet.repository.user.UserRepository;
import az.cybernet.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final String jwtHeader;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository,
                           @Lazy PasswordEncoder passwordEncoder,
                           RoleService roleService,
                           @Value("${jwt.header}") String jwtHeader,
                           JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtHeader = jwtHeader;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getUserDataFromHttpServletRequest(HttpServletRequest request) {
        final String requestHeader = request.getHeader(this.jwtHeader);
        if (Objects.nonNull(requestHeader)
                && requestHeader.startsWith("Bearer ")
                && !requestHeader.contains("null")) {
            String authorizationToken = requestHeader.substring(7);
            return jwtTokenUtil.getUserNameFromToken(authorizationToken);
        } else {
            return null;
        }
    }
}
