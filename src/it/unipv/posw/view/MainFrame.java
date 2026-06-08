package it.unipv.posw.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MainFrame extends BorderPane {
	
    private MenuBar menuBar = new MenuBar();
    private Menu menuAccount = new Menu("\u2630"); 
    private MenuItem itemHome = new MenuItem("Home");
    private MenuItem itemLogin = new MenuItem("Login");
    private MenuItem itemLoginOrg = new MenuItem("Login organizzatore");
    private MenuItem itemRegistrati = new MenuItem("Registrati");
    private HBox topBar;
 
    private HomeView hView;
    private AutenticazioneView aView;
    private AutenticazioneOrgView aorView;
    private RegistrazioneView regView;
    private RicercaView rView;
	
    public MainFrame() {
        
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("background");
        
        hView = new HomeView();
        aView = new AutenticazioneView();
        aorView = new AutenticazioneOrgView();
        regView = new RegistrazioneView();
        
        menuAccount.getItems().addAll(itemHome, itemLogin, itemLoginOrg, itemRegistrati);
        menuBar.getMenus().add(menuAccount);
        
        Region spacerSinistra = new Region();
        Region spacerDestra = new Region();
        HBox.setHgrow(spacerSinistra, Priority.ALWAYS);
        HBox.setHgrow(spacerDestra, Priority.ALWAYS);
        
        rView = new RicercaView();
        topBar = new HBox(menuBar, spacerSinistra, rView, spacerDestra);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));
        topBar.getStyleClass().add("topbar");
        
        this.setTop(topBar);
		
    }
    
    public void mostraSchermata(IView s) {
    	this.setCenter(s.getNodo());
    }
    
    public void setVisibilitaBarraRicerca(boolean visibile) {
        if (rView != null) {
            rView.setVisible(visibile);
            rView.setManaged(visibile); 
        }
    }
 
    public MenuItem getItemHome() { 
    	return itemHome;
    }
    
    public MenuItem getItemLogin() { 
    	return itemLogin; 
    }
    
    public MenuItem getItemRegistrati() { 
    	return itemRegistrati; 
    }
    
    public HomeView gethView() { 
    	return hView; 
    }
    
    public AutenticazioneView getaView() { 
    	return aView; 
    }
    
    public AutenticazioneOrgView getAorView() {
		return aorView;
	}
    
	public RegistrazioneView getRegView() { 
    	return regView; 
    }
    
	
    public MenuItem getItemLoginOrg() {
		return itemLoginOrg;
	}

	public RicercaView getrView() {
    	return rView;
    	
    }
    
    
}