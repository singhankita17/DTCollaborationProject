package com.linkin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.linkin.model.Chat;
import com.linkin.service.UsersService;

@Controller
public class SockBasedChatController {

	private final SimpMessagingTemplate messagingTemplate;
	private List<Integer> users = new ArrayList<Integer>();
	
	@Autowired
	UsersService usersService;
	
	private static Logger log = LoggerFactory.getLogger(SockBasedChatController.class);
	
	@Autowired
	public SockBasedChatController(SimpMessagingTemplate messagingTemplate) {
		
		this.messagingTemplate = messagingTemplate;
	}
	
	@SubscribeMapping("/join/{userId}")
	public List<Integer> join(@DestinationVariable("userId") int userId){
		log.info(" User Id in SockBasedChatController = "+userId);
		
		users = usersService.getOnlineUserList();
		
		
		log.info("=====Join====="+userId);
		messagingTemplate.convertAndSend("/topic/join",userId);
		
		return users;
	}
	
	@MessageMapping(value="/chat")
	public void chatReceived(Chat chat){
		if("all".equals(chat.getTo())){
			log.info("IN CHAT RECEIVED : "+chat.getMessage()+"  from "+chat.getFrom()+"  send to : "+chat.getTo());
			messagingTemplate.convertAndSend("/queue/chats",chat);
		}else{
			log.info("CHAT TO : "+chat.getTo()+" from = "+chat.getFrom()+" Message = "+chat.getMessage());
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(),chat);
			messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(),chat);
		}
	}
	
	
}
