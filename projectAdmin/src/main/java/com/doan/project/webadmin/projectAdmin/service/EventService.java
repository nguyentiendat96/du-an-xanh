package com.doan.project.webadmin.projectAdmin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.ServiceUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doan.project.webadmin.projectAdmin.entities.Event;
import com.doan.project.webadmin.projectAdmin.repository.EventRepository;
import com.doan.project.webadmin.projectAdmin.util.ServiceUtil;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

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

	@Transactional
	public Event addUpdateEvent(Event event) throws Exception {
		try {
			if (event != null && event.getId() != null) {
				ServiceUtil.checkEventExist(eventRepository, event.getId());
			}
		} catch (Exception e) {
			throw e;
		}
		event.setStatus("0");
		return eventRepository.save(event);
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
	
	@Transactional
	public void approvalEvent(Integer id) throws Exception {
		Event event = new Event();
		try {
			if (id != null) {
				event = ServiceUtil.checkEventExist(eventRepository, id).get();
			}
		} catch (Exception e) {
			throw e;
		}
		
		if("1".equals(event.getStatus())) {
			eventRepository.setStatusForEvent("0" , id);
		} else {
			eventRepository.setStatusForEvent("1" , id);
		}
	}

}
