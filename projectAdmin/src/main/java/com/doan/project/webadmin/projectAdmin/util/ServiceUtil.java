package com.doan.project.webadmin.projectAdmin.util;

import java.util.Optional;

import com.doan.project.webadmin.projectAdmin.entities.Event;
import com.doan.project.webadmin.projectAdmin.repository.EventRepository;

public class ServiceUtil {

	public static Optional<Event> checkEventExist(EventRepository eventRepository,
			Integer id ) throws Exception {
		try {
			Optional<Event> eventOptional = eventRepository.findById(id);
			if (!eventOptional.isPresent()) {
				throw new Exception();
			}
			return eventOptional;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
}
