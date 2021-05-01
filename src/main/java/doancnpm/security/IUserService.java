package doancnpm.security;

import java.util.List;
import java.util.Map;

import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.request.UserRequest;

public interface IUserService {
	 List<User> all();
	 User getUserById(long id);
	void save(String username, UserRequest userRequest);
	void delete(long[] ids);
}
