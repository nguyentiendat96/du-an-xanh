package com.doan.project.web.project.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.ServiceUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doan.project.web.project.entities.Event;
import com.doan.project.web.project.entities.EventJoins;
import com.doan.project.web.project.entities.User;
import com.doan.project.web.project.repository.EventJoinsRepository;
import com.doan.project.web.project.repository.EventRepository;
import com.doan.project.web.project.repository.UserRepository;
import com.doan.project.web.project.util.ServiceUtil;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventJoinsRepository eventJoinsRepository;

	public Event getDetailEvent(Integer id) throws Exception {

		try {
			Optional<Event> optionalEvent = eventRepository.findById(id);
			if (!optionalEvent.isPresent()) {
				throw new Exception();
			}
			return optionalEvent.get();
		} catch (Exception e) {
			throw e;
		}

		/* return eventRepository.findById(id); */
	}
	
	@Transactional
	public void approvalEventJoins(Integer idEvent, Integer idUser) throws Exception {
		try {
			eventJoinsRepository.approvalEventJoins(idUser, idEvent, "1");
		} catch (Exception e) {
			throw e;
		}
		/* return eventRepository.findById(id); */
	}
	
	@Transactional
	public void calcelEventJoins(Integer id, Authentication auth) throws Exception {

		// get ID user login
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		
		// Delete event joined
		eventJoinsRepository.deleteEventJoins(userIdLogin, id);

		/* return eventRepository.findById(id); */
	}

	public List<Event> getEvents() {

		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		return listEvents;

		// return eventRepository.findAll();
	}
	
	public List<Event> getEventsExtract(Authentication auth) throws Exception {
		// get ID user login
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventRepository.getListEventsExtract(userIdLogin);
		} catch (Exception e) {
			throw e;
		}
		return listEvents;

		// return eventRepository.findAll();
	}
	
	//getEventNeedApproval
	public List<EventJoins> getEventNeedApproval(Authentication auth) throws Exception {
		// get ID user login
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		List<EventJoins> listEventJoins = new ArrayList<EventJoins>();
		try {
			listEventJoins = eventJoinsRepository.getEventNeedApproval(userIdLogin);
		} catch (Exception e) {
			throw e;
		}
		return listEventJoins;

		// return eventRepository.findAll();
	}
	
	public List<Event> getListMyEvents(Authentication auth) throws Exception {
		// get ID user login
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventRepository.getListMyEvents(userIdLogin);
		} catch (Exception e) {
			throw e;
		}
		return listEvents;
		// return eventRepository.findAll();
	}
	//getListEventsJoined
	public List<Event> getListEventsJoined(Authentication auth) throws Exception {
		// get ID user login
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventRepository.getListEventsJoined(userIdLogin);
		} catch (Exception e) {
			throw e;
		}
		return listEvents;
		// return eventRepository.findAll();
	}
	
	@Transactional
	public Event addUpdateEvent(Event event, Authentication auth) throws Exception {
		try {
			if (event != null && event.getId() != null) {
				ServiceUtil.checkEventExist(eventRepository, event.getId());
			}
		} catch (Exception e) {
			throw e;
		}
		event.setStatus("0");
		
		// get user create/update events
		Integer userIdLogin = ServiceUtil.getUserIdLogin(auth, userRepository);
		event.setIdUser(userIdLogin);
		
		/*
		 * DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS"); LocalDateTime
		 * ldt2 = LocalDateTime.parse(event.getTimeStart().toString(), formatter);
		 */
		
		//action save event
		return eventRepository.save(event);
	}
	
	@Transactional
	public void joinEvents(EventJoins eventJoin, Authentication auth, Integer idEvents) throws Exception {
		if(eventJoin == null) {
			throw new Exception();
		}
		eventJoin.setStatus("0");
		
		// get user create/update events
		User user = ServiceUtil.getUserLogin(auth, userRepository);
		eventJoin.setUser(user);
		
		// get event need join
		try {
			Optional<Event> optionalEvent = eventRepository.findById(idEvents);
			if (!optionalEvent.isPresent()) {
				throw new Exception();
			}
			eventJoin.setEvent(optionalEvent.get());
		} catch (Exception e) {
			throw e;
		}
		eventJoinsRepository.save(eventJoin);
	}
	
	

	@Transactional
	public void removeEvent(Integer id) throws Exception {
		try {
			if (id != null) {
				ServiceUtil.checkEventExist(eventRepository, id);
			}
		} catch (Exception e) {
			throw e;
		}
		eventRepository.deleteById(id);
	}

}
