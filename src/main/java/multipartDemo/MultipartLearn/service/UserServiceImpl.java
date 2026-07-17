package multipartDemo.MultipartLearn.service;

import multipartDemo.MultipartLearn.dto.UserRequest;
import multipartDemo.MultipartLearn.dto.UserResponse;
import multipartDemo.MultipartLearn.entity.User;
import multipartDemo.MultipartLearn.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserRequest request, MultipartFile photo, MultipartFile aadhaar) throws IOException {
        User user = new User();
        // Set user properties from the request

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPhoto(photo.getBytes());
        user.setPhotoContentType(photo.getContentType());
        user.setAadhaarCard(aadhaar.getBytes());
        user.setAadhaarContentType(aadhaar.getContentType());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public UserResponse getUser(Long id) {
        return userRepository.findById(id).map(this::convertToResponse).orElse(null);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        return response;
    }
}
