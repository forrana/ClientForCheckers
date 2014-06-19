package CheckersClient.core;
/**
 * @author forrana aka Alexey Kuchin
 * @author forrana@gmail.com
 */
import com.badlogic.gdx.Game;
import com.checkers.network.client.NetworkClient;
import com.checkers.screens.*;

public class CheckersClient extends Game {
	public GameScreen game;
	public MainMenu	  mMenu;
    public Login      login;
    public NewGame    nGame;
    public Search     search;
    public Registration registration;
    public EndGameScreen endGame;
    public WaitingScreen waitingScreen;
    public InGameMenu inGameMenu;
    public static NetworkClient networkClient; //= new NetworkClient();
	
	public void create() {

        login = new Login(this, networkClient);
        mMenu = new MainMenu(this, networkClient);
        game = new GameScreen(this, networkClient);
        nGame = new NewGame(this, networkClient);
        waitingScreen = new WaitingScreen(networkClient,this);
        search = new Search(this, networkClient);
        registration = new Registration(this, networkClient);
        endGame = new EndGameScreen("hhh",this);
        inGameMenu = new InGameMenu(this);

        setScreen(login);
       // setScreen(search);

	}
	
}
