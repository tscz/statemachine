package com.github.tscz.spring.statemachine.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;

import com.github.tscz.spring.statemachine.domain.OrderStateMachine;

@Configuration
class PersistHandlerConfig {

	@Autowired
	private StateMachine<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> stateMachine;

	@Bean
	public OrderPersistenceAdapter persist() {
		return new OrderPersistenceAdapter(persistStateMachineHandler());
	}

	@Bean
	public PersistStateMachineHandler persistStateMachineHandler() {
		return new PersistStateMachineHandler(stateMachine);
	}

}