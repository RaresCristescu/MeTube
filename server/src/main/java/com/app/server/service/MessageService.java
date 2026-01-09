package com.app.server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.data.dto.MessageDto;
import com.app.data.entity.Message;
import com.app.data.repo.MessageRepo;

@Service
public class MessageService {

	private final MessageRepo repo;

	public MessageService(MessageRepo repo) {
		this.repo = repo;
	}
	
	public MessageDto getMessage(final UUID id) {
		Message m = repo.findById(id).orElseThrow(NoSuchElementException::new);
		MessageDto md = new MessageDto(m.getId(),m.getDescription());
		return md;
	}
	
	public List<MessageDto> getAllMessage() {
		List<Message> mList = repo.findAll();
		List<MessageDto> md = mList.stream()
					.map(m -> new MessageDto(m.getId(),m.getDescription()))
					.collect(Collectors.toList());
		return md;
	}
	
	public MessageDto createMessage(final MessageDto dto) {
		Message m = new Message();
		m.setDescription(dto.getDescription());		
		m =repo.save(m);
		return new MessageDto(m.getId(),m.getDescription());
	}
	
	public MessageDto updateMessage(final MessageDto dto) {
		Message m = repo.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
		m.setDescription(dto.getDescription());
		m = repo.save(m);
		return new MessageDto(m.getId(),m.getDescription());
	}
	
	public void deleteMessage(final UUID id) {
		repo.deleteById(id);
	}

	

}
