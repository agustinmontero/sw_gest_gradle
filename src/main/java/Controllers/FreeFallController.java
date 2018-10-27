package Controllers;

import Adapter.FreeFallAdapter;
import Interfaces.ControllerInterface;
import Interfaces.FreeFallModelInterface;
import Views.DJView;

import javax.swing.*;
import java.awt.*;


public class FreeFallController implements ControllerInterface{
    
    FreeFallModelInterface model;
    DJView view;

    public FreeFallController(FreeFallModelInterface model) {
        this.model = model;        
        view = new DJView(this, new FreeFallAdapter(model));
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
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
        view.removeObservers();
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

}
