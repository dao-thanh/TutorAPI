package doancnpm.security;

import java.util.List;
import java.util.Map;

import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;

public interface IUserService {
	 List<User> all();
	 User getUserById(long id);
	void save(AddUserRequest addUser);
	void delete(long[] ids);
}
