package CheckersClient.html;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
//import com.badlogic.gdx.Application.ApplicationType.
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class CheckersClientHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new CheckersClient();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
