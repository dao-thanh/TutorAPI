package doancnpm.security;

import java.util.List;

import doancnpm.models.Message;

import doancnpm.payload.request.MessageRequest;

public interface IMessageService {
	List<Message> all();
	Message getMessageById(long id);
	void save(MessageRequest messageRequest);
}
