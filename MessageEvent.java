package org.powerbot.script;

import java.util.EventListener;

import org.powerbot.bot.AbstractEvent;
import org.powerbot.bot.rt6.client.MessageEntry;

/**
 * A message event that is dispatched when a new message is dispatched in the game.
 */
public class MessageEvent extends AbstractEvent {
	private static final long serialVersionUID = 4178447203851407187L;
	public static final int MESSAGE_EVENT = 0x20;
	private final int id;
	private final String source, message;

	public MessageEvent(final MessageEntry entry) {
		this(entry.getType(), entry.getSender(), entry.getMessage());
	}

	public MessageEvent(final int id, final String source, final String message) {
		super(MESSAGE_EVENT);
		this.id = id;
		this.source = source;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void call(final EventListener eventListener) {
		((MessageListener) eventListener).messaged(this);
	}

	/**
	 * @return the id of this message.
	 */
	public int type() {
		return id;
	}


	/**
	 * @return the name of the sender of this message
	 */
	public String source() {
		return source;
	}

	/**
	 * @return the contents of this message
	 */
	public String text() {
		return message;
	}

	@Override
	public String toString() {
		return String.format("(%d) [%s]: %s%n", id, source, message);
	}
}