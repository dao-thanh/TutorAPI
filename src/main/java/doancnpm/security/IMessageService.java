package doancnpm.security;

import doancnpm.payload.request.MessageRequest;

public interface IMessageService {
	void save(MessageRequest messageRequest);
}
