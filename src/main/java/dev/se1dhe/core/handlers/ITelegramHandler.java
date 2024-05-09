package dev.se1dhe.core.handlers;

public interface ITelegramHandler {
	/**
	 * @return The access level required to execute this command
	 */
	default int getRequiredAccessLevel() {
		return 0;
	}
}
