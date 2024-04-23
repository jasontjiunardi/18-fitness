package com.fitness.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.UserRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepo userRepository;

    @Test
    public void testCreateUser() {
        // User user = new User("1", );
        // User user = new User();
        // user.setEmail("
}
}
