package com.github.tscz.spring.statemachine.domain;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public class OrderStateMachine {

	public static enum OrderState {
		PLACED, PROCESSING, SENT, DELIVERED;
	}

	public static enum OrderEvent {
		process, send, deliver;
	}

	@Configuration
	@EnableStateMachine
	static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent> {

		@Override
		public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {

			states//
					.withStates()//
					.states(EnumSet.allOf(OrderState.class))//
					.initial(OrderState.PLACED);
		}

		@Override
		public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {

			transitions//
					.withExternal()//
					.source(OrderState.PLACED).target(OrderState.PROCESSING)//
					.event(OrderEvent.process)//

					.and()//

					.withExternal()//
					.source(OrderState.PROCESSING).target(OrderState.SENT)//
					.event(OrderEvent.send)//

					.and()//

					.withExternal()//
					.source(OrderState.SENT).target(OrderState.DELIVERED)//
					.event(OrderEvent.deliver);
		}

	}

}
