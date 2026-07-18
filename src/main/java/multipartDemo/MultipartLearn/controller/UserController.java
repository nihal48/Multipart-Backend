package multipartDemo.MultipartLearn.controller;

import multipartDemo.MultipartLearn.dto.UserRequest;
import multipartDemo.MultipartLearn.dto.UserResponse;
import multipartDemo.MultipartLearn.entity.User;
import multipartDemo.MultipartLearn.repository.UserRepository;
import multipartDemo.MultipartLearn.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createUser(
            @RequestPart("request") UserRequest request,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("aadhaar") MultipartFile aadhaar) throws IOException {

        userService.save(request, photo, aadhaar);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User created successfully");

        return ResponseEntity.ok(response);
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
