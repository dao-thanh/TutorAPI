package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Message;

import doancnpm.payload.request.MessageRequest;


@Component
public class MessageConverter {
	public Message toMessage(MessageRequest messageRequest)
	{
		Message message = new Message();
		message.setContent(messageRequest.getContent());
		return message;
	}
}
