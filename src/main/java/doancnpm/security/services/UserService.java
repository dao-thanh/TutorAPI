package doancnpm.security.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.converter.UserConverter;
import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.repository.UserRepository;
import doancnpm.security.IUserService;
@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	
	@Override
	public void save(AddUserRequest addUser) {
		User user = new User();
		if(addUser.getId() != null) {
			User oldUser = userRepository.findOne(addUser.getId());
			user = userConverter.toUser(addUser, oldUser);
		}else
		{
			user = userConverter.toUser(addUser);
		}
		
		user = userRepository.save(user);
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

}
