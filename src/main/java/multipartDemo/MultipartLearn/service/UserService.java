package multipartDemo.MultipartLearn.service;

import multipartDemo.MultipartLearn.dto.UserRequest;
import multipartDemo.MultipartLearn.dto.UserResponse;
import multipartDemo.MultipartLearn.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    void save(UserRequest request, MultipartFile photo, MultipartFile aadhaar) throws IOException;

    Optional<User> getByPhone(String phone);

    UserResponse getUser(Long id);
}
