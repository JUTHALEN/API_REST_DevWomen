package com.proyecto.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.proyecto.user.Role;
import com.proyecto.user.User;
import com.proyecto.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

/**
 * Test de la capa repositorio
 */

@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE) 
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user0;

    @BeforeEach
    void setUp() {
        user0 = User.builder()
        .firstName("Test User 0")
        .lastName("Agulló")
        .password("54321")
        .email("USER0@gmail.com")
        .role(Role.USER)
        .build();
    }

    /**
     * Testea el método añadir un nuevo usuario
     *  */
    @Test 
    @DisplayName("Test para agregar un user")
    public void testAddUser() {

        //given

        User user1 = User.builder()
        .firstName("Test User 1")
        .lastName("Test Last Name 1")
        .password("12345")
        .email("user1@gmail.com")
        .role(Role.USER)
        .build();

        //when

        User userAdded = userRepository.save(user1);

        //then

        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);

    }

    @DisplayName("Test para listar usuario")
    @Test
    public void testFindAllUsers() {


        //given

        User user1 = User.builder()
        .firstName("Test User 1")
        .lastName("Test Last Name 1")
        .password("12345")
        .email("user1@gmail.com")
        .role(Role.USER)
        .build();

        userRepository.save(user0);
        userRepository.save(user1);

        //when

        List<User> usuarios = userRepository.findAll();

        //then

        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);


    }

    @DisplayName("Test para recuperar un user por ID")
    @Test
    public void testFindUserById() {
        //given
        userRepository.save(user0);
        //when
        User user = userRepository.findById(user0.getId()).get();
        //then
        assertThat(user.getId()).isNotEqualTo(0L);
    }

    @DisplayName("Test para actualizar un user")
    @Test
    public void testUpdateUser() {

          //given

          userRepository.save(user0);

          //when

          User userGuardado = userRepository.findByEmail(user0.getEmail()).get();
          userGuardado.setFirstName("Juan");
          userGuardado.setLastName("Rodriguez");
          userGuardado.setEmail("haha@gmail.com");

          User userUpdated = userRepository.save(userGuardado);

          //then

          assertThat(userUpdated.getEmail()).isEqualTo("haha@gmail.com");
          assertThat(userUpdated.getFirstName()).isEqualTo("Juan");
    }

    @DisplayName("Test para borrar un user")
    @Test
    public void testDeleteUser() {

        //given

        userRepository.save(user0);


        //when

        userRepository.delete(user0);

        Optional<User> optionalUser = userRepository.findByEmail(user0.getEmail());
        //then

        assertThat(optionalUser).isEmpty();
    }

}
