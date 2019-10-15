package org.fasttrackit.bookreview.service;

import org.fasttrackit.bookreview.domain.User;
import org.fasttrackit.bookreview.exception.ResourceNotFoundException;
import org.fasttrackit.bookreview.persistance.UserRepository;
import org.fasttrackit.bookreview.transfer.book.user.SaveUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(SaveUserRequest request){
        LOGGER.info("Creating user: {}", request);

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setName(request.getUsername());
        user.setPassword(request.getPassword());
        user.setBookTokens(10.0);

        return userRepository.save(user);
    }

    public User getUser(long id) {
        LOGGER.info("retrieving {}", id);
        return  userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to retrieve User ", "id", id));
    }

    public User updateUser(long id, SaveUserRequest request){
        LOGGER.info("updating user {}: {}", id, request);
        User user = getUser(id);
        BeanUtils.copyProperties(request, user);
        return userRepository.save(user);
    }

    public void deleteUser(long id){
        LOGGER.info("deleting book {}", id);
        userRepository.deleteById(id);
    }


}
