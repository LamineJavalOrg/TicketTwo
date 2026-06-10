package it.unipv.posw.view.home;

import java.util.List;

import it.unipv.posw.model.enums.RicercaType;
import it.unipv.posw.view.IView;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * @author rkomi-dev
 */

public class RicercaView extends HBox implements IView {
    
    private ComboBox<RicercaType> comboTipoRicerca;
    private TextField txtRicerca;
    private ContextMenu popupSuggerimenti;

    public RicercaView() {
    	
    	this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        
        this.comboTipoRicerca = new ComboBox<>();
        this.txtRicerca = new TextField();
        this.popupSuggerimenti = new ContextMenu();
        
        comboTipoRicerca.getItems().addAll(RicercaType.values());
        comboTipoRicerca.setValue(RicercaType.PER_EVENTO);
        
        txtRicerca.setPromptText("Cerca...");
        txtRicerca.setPrefWidth(300);
        
        this.getChildren().addAll(comboTipoRicerca, txtRicerca);
    }

    public List<MenuItem> rigeneraPopUpSuggerimenti(List<String> etichette) {
        popupSuggerimenti.getItems().clear();

        for (String testo : etichette) {
            MenuItem item = new MenuItem(testo);
            popupSuggerimenti.getItems().add(item);
        }

        return popupSuggerimenti.getItems(); 
    }

    public void mostraPopup() {
        if (!popupSuggerimenti.isShowing() && !popupSuggerimenti.getItems().isEmpty()) {
            popupSuggerimenti.show(txtRicerca, Side.BOTTOM, 0, 0);
        }
    }

    public void nascondiSuggerimenti() {
        popupSuggerimenti.hide();
    }

    public ComboBox<RicercaType> getComboTipoRicerca() { 
    	return comboTipoRicerca; 
    }
    
    public TextField getTxtRicerca() { 
    	return txtRicerca; 
    }
    
    public ContextMenu getPopupSuggerimenti() {
        return popupSuggerimenti;
    }

	@Override
	public Node getNodo() {
		// TODO Auto-generated method stub
		return this;
	}


}