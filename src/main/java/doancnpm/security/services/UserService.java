package doancnpm.security.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import doancnpm.converter.UserConverter;
import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.request.UserRequest;
import doancnpm.repository.UserRepository;
import doancnpm.security.IUserService;
@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void save(String username, UserRequest userRequest) {
		
		User user = userRepository.findOneByusername(username);
	
		user.setUsername(userRequest.getUsername());
		user.setPassword(encoder.encode(userRequest.getPassword()));
		user.setEmail(userRequest.getEmail());
		user.setPhonenumber(userRequest.getPhonenumber());
		user.setName(userRequest.getName());
		user.setAge(userRequest.getAge());
		user.setGender(userRequest.getGender());
		
		userRepository.save(user);
	}


	@Override
	public List<User> all() {
		List<User> users = userRepository.findAll();
		return users;
	}


	@Override
	public User getUserById(long id) {
		User userData = userRepository.findOne(id);
		return userData;
	}
	
	@Override
	public void delete(long[] ids) {
		for(long item:ids) {
			userRepository.delete(item);
		}
	}
	
}
