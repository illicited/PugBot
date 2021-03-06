
import java.util.concurrent.TimeUnit;

import core.Database;
import core.EventHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import okhttp3.OkHttpClient;

// Bot driver

public class Bot{
	
	public static void main(String[] args) {
		try{
			Database.createConnection();
			OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
					.readTimeout(60, TimeUnit.SECONDS)
					.connectTimeout(60, TimeUnit.SECONDS)
					.writeTimeout(60, TimeUnit.SECONDS);
			
			JDA jda = new JDABuilder(AccountType.BOT)
					.setToken(Tokens.TOKEN)
					.setHttpClientBuilder(httpBuilder)
					.buildBlocking();
			
			jda.setAutoReconnect(true);
			jda.addEventListener(new EventHandler(jda));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
