package CheckersClient.java;

import CheckersClient.core.CheckersClient;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class CheckersClientDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = false;
        config.width = 500;
        config.height = 500;
        //config.resizable = false;
		new LwjglApplication(new CheckersClient(), config);
	}
}
