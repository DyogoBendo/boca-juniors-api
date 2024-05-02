package unioeste.br.bocajuniorsapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.User;
import unioeste.br.bocajuniorsapi.dto.UserLoggedDTO;
import unioeste.br.bocajuniorsapi.dto.UserLoginFormDTO;
import unioeste.br.bocajuniorsapi.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoggedDTO> login(@RequestBody UserLoginFormDTO form){
        String username = form.getUsername();
        boolean userExists = userService.checkUserExists(username);
        if (userExists){
            User user = userService.findByUsername(username);

            boolean passwordMatches = userService.passwordMatches(user, form.getPassword());

            if (passwordMatches){
                UserLoggedDTO userLoggedDTO= userService.convert(user, true);

                return ResponseEntity.ok(userLoggedDTO);
            }
            return ResponseEntity.badRequest().build();
        }

        String generatedPassword = userService.generatePassword();
        User user = userService.convert(username, generatedPassword);

        userService.save(user);

        UserLoggedDTO userLoggedDTO = userService.convert(user, false);
        return ResponseEntity.ok(userLoggedDTO);
    }
}
