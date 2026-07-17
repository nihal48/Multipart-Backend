package multipartDemo.MultipartLearn.controller;

import multipartDemo.MultipartLearn.dto.UserRequest;
import multipartDemo.MultipartLearn.dto.UserResponse;
import multipartDemo.MultipartLearn.entity.User;
import multipartDemo.MultipartLearn.repository.UserRepository;
import multipartDemo.MultipartLearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createUser(@RequestPart("request") UserRequest request,
                                           @RequestPart("photo") MultipartFile photo,
                                           @RequestPart("aadhaar") MultipartFile aadhaar) {
        try {
            userService.save(request, photo, aadhaar);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id){

        User user = userRepository.findById(id).orElseThrow();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                user.getPhotoContentType()))
                .body(user.getPhoto());
    }
    @GetMapping("/{id}/aadhaar")
    public ResponseEntity<byte[]> getAadhaar(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(user.getAadhaarContentType()))
                .body(user.getAadhaarCard());
    }
}
