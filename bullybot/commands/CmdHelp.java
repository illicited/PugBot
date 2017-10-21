package bullybot.commands;

import java.util.ArrayList;

import bullybot.classfiles.Commands;
import bullybot.classfiles.Info;
import bullybot.classfiles.QueueManager;
import bullybot.classfiles.ServerManager;
import bullybot.classfiles.util.Functions;
import net.dv8tion.jda.core.entities.Member;

public class CmdHelp extends Command{
	
	private Commands cmds;
	
	public CmdHelp(){
		this.helpMsg = Info.HELP_HELP;
		this.description = Info.HELP_DESC;
		this.name = "help";
		this.dm = true;
	}
	@Override
	public void execCommand(QueueManager qm, Member member, ArrayList<String> args) {
		try{
			cmds = ServerManager.getServer(member.getGuild().getId()).cmds;
			if(args.size() == 0){
				this.response = Functions.createMessage(helpBuilder(member));
			}else{
				if(cmds.validateCommand(args.get(0))){
					this.response = Functions.createMessage(cmds.getCommandObj(args.get(0)).help());
				}
			}
		}catch(Exception ex){
			this.response = Functions.createMessage("Error!", ex.getMessage(), false);
		}
	}
	
	private String helpBuilder(Member member){
		String s = "Commands:\n\n";
		Command cmdObj;
		for(String cmd : cmds.getCmds()){
			cmdObj = cmds.getCommandObj(cmd);
			s += String.format("!%s - %s. Usage: %s%n", cmdObj.getName(), cmdObj.getDescription(), cmdObj.help()); 
		}
		
		if(Functions.isAdmin(member)){
			s += "\nAdmin commands:\n\n";
			for(String cmd : cmds.getAdminCmds()){
				cmdObj = cmds.getCommandObj(cmd);
				s += String.format("!%s - %s. Usage: %s%n", cmdObj.getName(), cmdObj.getDescription(), cmdObj.help()); 
			}
		}
		
		s = String.format("```%s```", s);
		return s;
	}
}
