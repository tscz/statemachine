package com.github.tscz.spring.statemachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tscz.spring.statemachine.domain.OrderStateMachine;
import com.github.tscz.spring.statemachine.domain.OrderStateMachine.OrderEvent;
import com.github.tscz.spring.statemachine.persistence.OrderPersistenceAdapter;

@Component
public class OrderService {

	@Autowired
	private OrderPersistenceAdapter persist;

	public String listDbEntries() {
		return persist.listDbEntries();
	}

	public void process(int order) {
		persist.change(order, OrderStateMachine.OrderEvent.process);
	}

	public void send(int order) {
		persist.change(order, OrderStateMachine.OrderEvent.send);
	}

	public void deliver(int order) {
		persist.change(order, OrderStateMachine.OrderEvent.deliver);
	}

}
