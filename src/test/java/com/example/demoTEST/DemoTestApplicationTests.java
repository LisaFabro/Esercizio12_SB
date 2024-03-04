package com.example.demoTEST;

import com.example.demoTEST.Entity.Users;
import com.example.demoTEST.Repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DemoTestApplicationTests {

    @Autowired
	private UsersController usersController;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createUser() throws Exception{
		Users users = new Users(1L, "Gianni", "Rossi", "qqqqqq");
		mockMvc.perform(post("/users/new").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(users))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").value("Gianni"))
				.andExpect(jsonPath("$.surname").value("Rossi"))
				.andExpect(jsonPath("$.mail").value("qqqqqq"));
	}
	@Test
	public void getUserById() throws Exception{
		Users users = new Users(1L, "Gianni", "Rossi", "qqqqqq");
		Users userSaved = usersRepository.save(users);
		mockMvc.perform(get("/users/{id}", users.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(users.getId()));
	}
	@Test
	public void updateUser() throws Exception{
		Users users = new Users(1L, "Gianni", "Rossi", "qqqqqq");
		Users userSaved = usersRepository.save(users);

		Users users2 = new Users(1L, "Giovanni", "Bianchi", "qqqqqq");

		mockMvc.perform(put("/users/update/{id}", users.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(users2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(users.getId()))
				.andExpect(jsonPath("$.name").value("Giovanni"))
				.andExpect(jsonPath("$.surname").value("Bianchi"))
				.andExpect(jsonPath("$.mail").value("qqqqqq"));

	}
	@Test
	public  void deleteUserById() throws Exception{
		Users users = new Users(1L, "Gianni", "Rossi", "qqqqqq");
		users = usersRepository.save(users);
		mockMvc.perform(delete("/users/delete/{id}", users.getId())).andExpect(status().isOk());

		mockMvc.perform(get("/users/{id}", users.getId())).andExpect(status().isInternalServerError());
	}
}
