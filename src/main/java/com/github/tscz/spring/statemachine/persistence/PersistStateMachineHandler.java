package com.github.tscz.spring.statemachine.persistence;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.GenericPersistStateMachineHandler;

import com.github.tscz.spring.statemachine.domain.OrderStateMachine;
import com.github.tscz.spring.statemachine.domain.OrderStateMachine.OrderEvent;
import com.github.tscz.spring.statemachine.domain.OrderStateMachine.OrderState;

public class PersistStateMachineHandler extends GenericPersistStateMachineHandler<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> {

	public PersistStateMachineHandler(StateMachine<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> stateMachine) {
		super(stateMachine);
	}

}
