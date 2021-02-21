package com.github.tscz.spring.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.tscz.spring.statemachine.application.OrderService;

@Controller
public class IndexController {

	@Autowired
	private OrderService commands;

	@GetMapping("/")
	public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	@GetMapping("/todo")
	public String todo(@RequestParam(name = "order", required = false, defaultValue = "1") int order, Model model) {
		System.out.println("TODO");

		commands.process(order);

		model.addAttribute("orders", commands.listDbEntries());
		return "index";
	}

}
