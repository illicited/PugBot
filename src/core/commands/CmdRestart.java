package core.commands;

import core.entities.Server;
import core.entities.ServerManager;
import core.util.Utils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class CmdRestart extends Command {

	public CmdRestart(Server server) {
		super(server);
	}

	@Override
	public Message execCommand(Member caller, String[] args) {
		server.getQueueManager().finishAllGames();
		ServerManager.removeServer(server.getId());
		ServerManager.addNewServer(server.getGuild());

		this.response = Utils.createMessage("`Server instance restarted`");

		return response;
	}

	@Override
	public boolean isAdminRequired() {
		return true;
	}

	@Override
	public boolean isGlobalCommand() {
		return true;
	}

	@Override
	public String getName() {
		return "Restart";
	}

	@Override
	public String getDescription() {
		return "Restarts this bot's instance";
	}

	@Override
	public String getHelp() {
		return getBaseCommand();
	}

}
