package Views;

import Interfaces.BeatModelInterface;
import Interfaces.ControllerInterface;
import Interfaces.ViewInterface;
import Observers.BPMObserver;
import Observers.BeatObserver;

import javax.swing.*;
import java.awt.*;

public class DJView implements BeatObserver, BPMObserver, ViewInterface {
    private BeatModelInterface model;
    private ControllerInterface controller;

    private BeatBar beatBar;
    public JLabel bpmOutputLabel;
    public JTextField bpmTextField;
    public JMenuItem startMenuItem;
    public JMenuItem stopMenuItem;
    public JButton setBPMButton;
    public JButton increaseBPMButton;
    public JButton decreaseBPMButton;


    public DJView(ControllerInterface controller, BeatModelInterface model) {
        this.controller = controller;
        this.model = model;
    }

    public void removeObservers() {
        this.model.removeObserver((BeatObserver) this);
        this.model.removeObserver((BPMObserver) this);
    }

    public void registerObservers(){
        this.model.registerObserver((BeatObserver) this);
        this.model.registerObserver((BPMObserver) this);
    }

    public void createView() {
        // Create all Swing components here
        JPanel viewPanel = new JPanel(new GridLayout(1, 2));
        JFrame viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
        beatBar = new BeatBar();
        beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(3, 1));
        bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);

        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
    }


    @Override
    public void createControls() {
        // Create all Swing components here
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        JPanel controlPanel = new JPanel(new GridLayout(1, 2));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("DJ Control");
        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener(event -> controller.start());
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem);
        stopMenuItem.addActionListener(event -> controller.stop());
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(event -> System.exit(0));

        menu.add(exit);
        menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        JLabel bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        setBPMButton = new JButton("Set");
        setBPMButton.setSize(new Dimension(10, 40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        setBPMButton.addActionListener(this::setBPMButtonActionPerformed);
        increaseBPMButton.addActionListener(this::increaseBPMButtonActionPerformed);
        decreaseBPMButton.addActionListener(this::decreaseBPMButtonActionPerformed);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        buttonPanel.add(decreaseBPMButton);
        buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        controlPanel.add(insideControlPanel);

        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

    @Override
    public void enableStopMenuItem() {
        stopMenuItem.setEnabled(true);
    }

    @Override
    public void disableStopMenuItem() {
        stopMenuItem.setEnabled(false);
    }

    @Override
    public void enableStartMenuItem() {
        startMenuItem.setEnabled(true);
    }

    @Override
    public void disableStartMenuItem() {
        startMenuItem.setEnabled(false);
    }

    private void setBPMButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int bpm = Integer.parseInt(bpmTextField.getText());
        controller.setBPM(bpm);
    }

    private void increaseBPMButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.increaseBPM();
    }

    private void decreaseBPMButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.decreaseBPM();
    }

    public void updateBPM() {

        int bpm = model.getBPM();
        if (bpm == 0) {
            bpmOutputLabel.setText("offline");
        } else {
            bpmOutputLabel.setText("Current BPM: " + model.getBPM());
        }
    }

    public void updateBeat() {
        beatBar.setValue(100);
    }
}
