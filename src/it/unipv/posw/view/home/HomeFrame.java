package it.unipv.posw.view.home;

import it.unipv.posw.view.IView;
import it.unipv.posw.view.acquisto.AcquistoFrame;
import it.unipv.posw.view.admin.PannelloAdminFrame;
import it.unipv.posw.view.ricerca.RicercaFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class HomeFrame extends BorderPane {
	

    private MenuBar menuBar;
    private Menu menuAccount;
    private MenuItem itemHome;
    private MenuItem itemLogin;
    private MenuItem itemLoginOrg;
    private MenuItem itemRegistrati;
    private MenuItem itemPanAdm;
    private HBox topBar;
 
    private HomeView hView;
    private AutenticazioneView aView;
    private AutenticazioneOrgView aorView;
    private RegistrazioneView regView;
    private RicercaFrame ricercaF;

  
    public HomeFrame() {
        
        this.getStylesheets().add(getClass().getResource("/css/home-css.css").toExternalForm());
        this.getStyleClass().add("background");
        
        hView = new HomeView();
        aView = new AutenticazioneView();
        aorView = new AutenticazioneOrgView();
        regView = new RegistrazioneView();
        ricercaF = new RicercaFrame();
        
        menuBar = new MenuBar();
        menuAccount = new Menu("\u2630"); 
        itemHome = new MenuItem("Home");
        itemRegistrati = new MenuItem("Registrati");
        itemLogin = new MenuItem("Login");
        itemLoginOrg = new MenuItem("Login organizzatore");
        itemPanAdm = new MenuItem("Area Admin");
        
        menuAccount.getItems().addAll(itemHome, itemRegistrati, itemLogin, itemLoginOrg, itemPanAdm);
        menuBar.getMenus().add(menuAccount);
        
        Region spacerSinistra = new Region();
        Region spacerDestra = new Region();
        HBox.setHgrow(spacerSinistra, Priority.ALWAYS);
        HBox.setHgrow(spacerDestra, Priority.ALWAYS);
        
       
        topBar = new HBox(menuBar, spacerSinistra, ricercaF.getRicercaView(), spacerDestra);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));
        topBar.getStyleClass().add("topbar");
        
        this.setTop(topBar);
		
    }
    
    public void mostraSchermata(IView s) {
    	this.setCenter(s.getNodo());
    }
    
    public void setVisibilitaBarraRicerca(boolean visibile) {
        if (ricercaF.getRicercaView() != null) {
        	ricercaF.getRicercaView().setVisible(visibile);
        	ricercaF.getRicercaView().setManaged(visibile); 
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
    
	
    public RicercaFrame getRicercaF() {
		return ricercaF;
	}

	public MenuItem getItemLoginOrg() {
		return itemLoginOrg;
	}
	
	public MenuItem getItemPanAdm() {
		return itemPanAdm;
	}

	public PannelloAdminFrame creaPannelloAdminFrame() {
		return new PannelloAdminFrame();
	}

	public AcquistoFrame creAcquistoFrame() {
		return new AcquistoFrame();
	}
}