package core.commands;

import core.entities.Server;
import core.exceptions.BadArgumentsException;
import core.util.Utils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class CmdUnban extends Command {

	public CmdUnban(Server server) {
		super(server);
	}

	@Override
	public Message execCommand(Member caller, String[] args) {
		String pName = "All";

		if (args.length < 1) {
			throw new BadArgumentsException();
		}

		if (args.length == 0) {
			server.unbanAll();
		} else if (args.length == 1) {
			Member m = server.getMember(args[0]);
			pName = m.getEffectiveName();

			server.unbanUser(m.getUser().getIdLong());
		}

		this.response = Utils.createMessage(String.format("`%s has been unbanned`", pName));

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
		return "Unban";
	}

	@Override
	public String getDescription() {
		return "Unbans a user";
	}

	@Override
	public String getHelp() {
		return getBaseCommand() + " <username>";
	}
}
