package Controllers;

import Interfaces.ControllerInterface;
import Views.DJView;
import Views.DJViewAppSelector;
import Adapter.FreeFallAdapter;
import Interfaces.FreeFallModelInterface;
import Interfaces.ViewInterface;

import javax.swing.*;
import java.awt.*;


public class FreeFallController implements ControllerInterface{
    
    private FreeFallModelInterface model;
    private ViewInterface view;

    public FreeFallController(FreeFallModelInterface model) {
        this.model = model;        
        view = new DJView(this, new FreeFallAdapter(model));
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }

    /**
     * Constructor utilizado en main de DJViewAppSelector
     * @param model
     * @param viewName Nombre de la vista
     */
    public FreeFallController(FreeFallModelInterface model, String viewName) {
        if ("DJViewAppSelector".equals(viewName)) {
            this.model = model;
            this.initalizeDJVAppSelector();
        }
    }

    public FreeFallController(FreeFallModelInterface model, ViewInterface view) {
        this.model = model;
        this.view = view;
        this.updateControls();
    }
    
    
    @Override
    public void start() {
        model.on();
        view.disableStartMenuItem();
        view.enableStopMenuItem();
    }

    @Override
    public void stop() {
        model.off();
        view.enableStartMenuItem();
        view.disableStopMenuItem();
    }

    @Override
    public void increaseBPM() {
        
    }

    @Override
    public void decreaseBPM() {
        
    }

    @Override
    public void setBPM(int bpm) {
        try {
            model.setAltitude(bpm);
        } catch (Exception e) {
            final int closeDelay = 5000;
            final JLabel label = new JLabel();
            label.setText("Invalid Data! Try again..");
            new Timer(closeDelay, e1 -> {
                ((Timer) e1.getSource()).stop();
                Window win = SwingUtilities.getWindowAncestor(label);
                win.dispose();
            }){{setInitialDelay(closeDelay);}}.start();
            JOptionPane.showMessageDialog(null, label);
        }
    }
    
    private void initalizeDJVAppSelector(){        
        this.view = new DJViewAppSelector(this, new FreeFallAdapter(model));
        this.view.createView();
        this.view.createControls();
        this.view.disableStopMenuItem();
        this.view.enableStartMenuItem();
        
    }
    
    private void updateControls(){
        this.view.disableStopMenuItem();
        this.view.enableStartMenuItem();
        this.model.initialize();
    }
    
}
