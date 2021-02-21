package com.github.tscz.spring.statemachine.domain;

public class Order {
	int id;
	OrderStateMachine.OrderState state;

	public Order(int id, String state) {
		this.id = id;
		this.state = OrderStateMachine.OrderState.valueOf(state);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", state=" + getState() + "]";
	}

	public OrderStateMachine.OrderState getState() {
		return state;
	}

}