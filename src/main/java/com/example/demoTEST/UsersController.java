package com.example.demoTEST;

import com.example.demoTEST.Entity.Users;
import com.example.demoTEST.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;
    @PostMapping("/new")
    public Users newUser(@RequestBody Users users){
        return usersRepository.save(users);
    }
    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id){
        return usersRepository.getReferenceById(id);
    }
    @PutMapping("/update/{id}")
    public  Users updateUser(@PathVariable Long id, @RequestBody Users users){
        Users userUpdated = usersRepository.findById(id).orElse(null);
        if(userUpdated != null){
            userUpdated.setId(users.getId());
            userUpdated.setName(users.getName());
            userUpdated.setSurname(users.getSurname());
            userUpdated.setMail(users.getMail());
        }
        return usersRepository.save(userUpdated);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        usersRepository.deleteById(id);
    }
}
