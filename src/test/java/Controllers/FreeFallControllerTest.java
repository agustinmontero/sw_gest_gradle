package Controllers;

import Models.FreeFallModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 *
 * @author Agustin
 */
public class FreeFallControllerTest {
    
    private FreeFallModel model;
    private FreeFallController fFallContInstance;
    private final int HIGH;
    private final double MASS;
    private final double GRAV;
    
    public FreeFallControllerTest() {
        model = new FreeFallModel();
        fFallContInstance = new FreeFallController(model);
        this.HIGH = 500;
        this.MASS = 2.3;
        GRAV = this.fFallContInstance.model.getGravity();
    }

    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testStart() {
        fFallContInstance.start();
    }

    @Test
    public void testStop() {
        fFallContInstance.stop();
    }

    @Test
    public void testSetBPM(){
        int bpm = 20;
        fFallContInstance.view.bpmTextField.setText(String.valueOf(bpm));
        fFallContInstance.view.setBPMButton.doClick();
        fFallContInstance.view.increaseBPMButton.doClick();
        fFallContInstance.view.decreaseBPMButton.doClick();
        assertEquals(bpm, model.getAltitude());
    }

    @Test(timeout = 25000)
    public void testDJView() {
        fFallContInstance.model.setMass(MASS);
        fFallContInstance.view.bpmTextField.setText(String.valueOf(HIGH));
        fFallContInstance.view.setBPMButton.doClick();
        fFallContInstance.view.startMenuItem.doClick();
        try {
            Thread.sleep(17000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FreeFallController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int totalEnergy = this.fFallContInstance.model.getTotalEnergy();
        int kinetic = this.fFallContInstance.model.getKineticEn();
        int t = (int) (MASS*GRAV*HIGH);
        assertEquals(t, totalEnergy);
        assertEquals(t , kinetic);
        //assertEquals(fFallContInstance.view.bpmOutputLabel.getText(), "offline");
    }

    @Test(timeout = 25000)
    public void testDJViewStop() {
        fFallContInstance.model.setMass(MASS);
        fFallContInstance.view.bpmTextField.setText(String.valueOf(HIGH));
        fFallContInstance.view.setBPMButton.doClick();
        fFallContInstance.view.startMenuItem.doClick();
        try {
            Thread.sleep(2000);
            fFallContInstance.view.stopMenuItem.doClick();
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FreeFallController.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotEquals(fFallContInstance.model.getAltitude() , 0);
        assertNotEquals(fFallContInstance.view.bpmOutputLabel.getText(), "offline");
    }
    
    @Test
    public void testSetBPM2(){
        int bpm = -20;
        fFallContInstance.view.bpmTextField.setText(String.valueOf(bpm));
        fFallContInstance.view.setBPMButton.doClick();
        assertNotEquals(bpm, model.getAltitude());
    }
}
