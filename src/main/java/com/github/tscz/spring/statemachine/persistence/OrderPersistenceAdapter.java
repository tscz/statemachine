package com.github.tscz.spring.statemachine.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.AbstractPersistStateMachineHandler.GenericPersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import com.github.tscz.spring.statemachine.domain.Order;
import com.github.tscz.spring.statemachine.domain.OrderStateMachine;

public class OrderPersistenceAdapter {

	private final PersistStateMachineHandler handler;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final GenericPersistStateChangeListener<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> listener = new LocalPersistStateChangeListener();

	public OrderPersistenceAdapter(PersistStateMachineHandler handler) {
		this.handler = handler;
		this.handler.addPersistStateChangeListener(listener);
	}

	public String listDbEntries() {
		List<Order> orders = jdbcTemplate.query("select id, state from orders", new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Order(rs.getInt("id"), rs.getString("state"));
			}
		});
		StringBuilder buf = new StringBuilder();
		for (Order order : orders) {
			buf.append(order);
			buf.append("\n");
		}
		return buf.toString();
	}

	public void change(int order, OrderStateMachine.OrderEvent event) {
		Order o = jdbcTemplate.queryForObject("select id, state from orders where id = ?", new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Order(rs.getInt("id"), rs.getString("state"));
			}
		}, new Object[] { order });
		handler.handleEventWithStateReactively(MessageBuilder.withPayload(event).setHeader("order", order).build(),
				o.getState()).block();
	}

	private class LocalPersistStateChangeListener
			implements GenericPersistStateChangeListener<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> {

		@Override
		public void onPersist(State<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> state,
				Message<OrderStateMachine.OrderEvent> message,
				Transition<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> transition,
				StateMachine<OrderStateMachine.OrderState, OrderStateMachine.OrderEvent> stateMachine) {

			if (message != null && message.getHeaders().containsKey("order")) {
				Integer order = message.getHeaders().get("order", Integer.class);
				jdbcTemplate.update("update orders set state = ? where id = ?", state.getId().toString(), order);
			}
		}
	}

}
