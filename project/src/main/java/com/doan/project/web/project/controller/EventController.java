package com.doan.project.web.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.project.web.project.entities.Event;
import com.doan.project.web.project.entities.EventJoins;
import com.doan.project.web.project.entities.User;
import com.doan.project.web.project.repository.EventRepository;
import com.doan.project.web.project.repository.UserRepository;
import com.doan.project.web.project.service.EventService;

@Controller
//@CrossOrigin(origins = "*")
//@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/detailEvent/{id}")
	public String getDetailEvent(Model model, @PathVariable("id") Integer id) {
		Event event = new Event();
		try {
			event = eventService.getDetailEvent(id);
		} catch (Exception e) {

		}
		model.addAttribute("event", event);
		return "detailEvent";

		/*
		 * Optional<Event> eventOptional = eventService.getDetailEvent(id); return
		 * eventOptional.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
		 * .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		 */
	}

	@GetMapping("/getListEvents")
	public String getDetailEvent(Model model, Authentication auth) throws Exception {

		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventService.getEventsExtract(auth);
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("events", listEvents);
		model.addAttribute("eventJoin", new EventJoins());
		
		return "index";
		// return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
	}
	
	//getListEventsApproval
	@GetMapping("/getListEventsApproval")
	public String getListEventsApproval(Model model, Authentication auth) throws Exception {

		List<EventJoins> listEventJoins = new ArrayList<EventJoins>();
		try {
			listEventJoins = eventService.getEventNeedApproval(auth);
			for (EventJoins eventJoins : listEventJoins) {
				User user = userRepository.findById(eventJoins.getUser().getId()).get();
				Event event = eventRepository.findById(eventJoins.getEvent().getId()).get();
				eventJoins.setEvent(event);
				eventJoins.setUser(user);
			}
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("eventJoins", listEventJoins);
		
		return "ListApprovalEvents";
		// return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
	}
	
	@GetMapping("/create")
	public String createEvent(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("add", true);
		return "editCreateEvent";
	}
	
	@GetMapping("/listMyEvents")
	public String listMyEvents(Model model, Authentication auth) throws Exception {
		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventService.getListMyEvents(auth);
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("events", listEvents);
		return "listMyEvents";
	}
	
	/*
	 * @PostMapping("/JoinEvents/{id}") public String joinEvents(Model model, ,
	 * Authentication auth, @PathVariable("id") Integer id) throws Exception {
	 * 
	 * 
	 * 
	 * model.addAttribute("events", listEvents); return "listMyEvents"; }
	 */
	
	@GetMapping("/edit/{idEvent}")
	public String editEvent(Model model, @PathVariable("idEvent") Integer id) {
		Event event = new Event();
		try {
			event = eventService.getDetailEvent(id);
		} catch (Exception e) {

		}
		model.addAttribute("event", event);
		model.addAttribute("add", false);
		return "editCreateEvent";
	}
	
	@GetMapping("/approvalJoinEvents/{idEvent}/{idUser}")
	public String approvalEventJoins(Model model, @PathVariable("idEvent") Integer idEvent, @PathVariable("idUser") Integer idUser) throws Exception {
		try {
			eventService.approvalEventJoins(idEvent, idUser);
		} catch (Exception e) {
			throw e;
		}
		return "editCreateEvent";
	}

	@GetMapping("/cancelJoinEvents/{idEvent}")
	public String calcelEventJoins(Model model, @PathVariable("idEvent") Integer id, Authentication auth) throws Exception {
		try {
			eventService.calcelEventJoins(id, auth);
		} catch (Exception e) {
			throw e;
		}
		return "redirect:/event/getListEventsJoined";
	}
	
	@GetMapping("/getListEventsJoined")
	public String getListEventsJoined(Model model, Authentication auth) throws Exception {
		List<Event> listEvents = new ArrayList<Event>();
		try {
			listEvents = eventService.getListEventsJoined(auth);
		} catch (Exception e) {
			throw e;
		}
		model.addAttribute("events", listEvents);
		return "ListEventJoined";
	}

	@PostMapping("/addUpdateEvent")
	public String addUpdateEvent(@Validated Event event, BindingResult result, Model model, Authentication auth) throws Exception {
		// public ResponseEntity<Event> addUpdateEvent(@RequestBody Event event) throws
		// Exception {

		/*
		 * if (result.hasErrors()) { throw new Exception(); }
		 */
		try {
			eventService.addUpdateEvent(event, auth);
		} catch (Exception e) {
			throw e;
		}
		return "redirect:/event/listMyEvents";

		/*
		 * if (event.getId() == null) { return new
		 * ResponseEntity<>(eventService.addUpdateEvent(event), HttpStatus.OK); } else {
		 * Optional<Event> eventOptional = eventService.getDetailEvent(event.getId());
		 * return eventOptional.map(eventItem -> { event.setId(eventItem.getId());
		 * return new ResponseEntity<>(eventService.addUpdateEvent(event),
		 * HttpStatus.OK); }).orElseGet(() -> new
		 * ResponseEntity<>(HttpStatus.NOT_FOUND)); }
		 */
	}
	
	//joinEvents
	@PostMapping("/joinEvents/{idEvent}")
	public String joinEvents(@Validated EventJoins eventJoin, @PathVariable("idEvent") Integer idEvents, 
			BindingResult result, Model model, Authentication auth) throws Exception {
		try {
			eventService.joinEvents(eventJoin, auth, idEvents);
		} catch (Exception e) {
			throw e;
		}
		
		return "redirect:/event/getListEvents";
	}

	@GetMapping("deleteEvent/{id}")
	public String deleteEvent(@PathVariable Integer id) throws Exception {
		/*
		 * Optional<Event> eventOptional = eventService.getDetailEvent(id); return
		 * eventOptional.map(category -> { eventService.removeEvent(id); return new
		 * ResponseEntity<>(category, HttpStatus.OK); }).orElseGet(() -> new
		 * ResponseEntity<>(HttpStatus.NOT_FOUND));
		 */
		try {
			eventService.removeEvent(id);
		} catch (Exception e) {
			throw e;
		}
		return "redirect:/event/getListEvents";
	}
}
