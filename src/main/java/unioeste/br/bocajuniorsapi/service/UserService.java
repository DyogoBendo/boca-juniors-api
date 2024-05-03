package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.domain.User;
import unioeste.br.bocajuniorsapi.dto.UserLoggedDTO;
import unioeste.br.bocajuniorsapi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public boolean checkUserExists(String username){
        return userRepository.findById(username).isPresent();
    }

    public String generatePassword(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Set the length of the random string
        int length = 5;

        // Create an instance of Random class
        Random random = new Random();

        // Create a StringBuilder to store the random string
        StringBuilder sb = new StringBuilder(length);

        // Generate the random string by choosing characters randomly from the set
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public User findByUsername(String username){
        return userRepository.findById(username).orElse(null);
    }

    public boolean passwordMatches(User user, String password){
        if(user == null) return false;
        return user.getPassword().equals(password);
    }

    public UserLoggedDTO convert(User user, boolean exists){
        String password = exists ? null : user.getPassword();
        return new UserLoggedDTO(user.getUsername(), password);
    }

    public User convert(String username, String password){
        return new User(username, password);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<String> findUsernamesBySubmission(List<Submission> submissionList){
        Set<String> usernameSet = new HashSet<>();

        for (Submission submission : submissionList){
            usernameSet.add(submission.getUsername());
        }
        return usernameSet.stream().toList();
    }
}
