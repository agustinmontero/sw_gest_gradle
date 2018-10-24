package Views;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class FreeFallViewTest {
    
    private FreeFallView freeFallViewInstance;
    private final int HIGH;
    private final double MASS;
    private final double GRAV;

    public FreeFallViewTest() {
        this.HIGH = 950;
        this.MASS = 2.3;
        freeFallViewInstance = new FreeFallView();
        GRAV = this.freeFallViewInstance.model.getGravity();
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        this.freeFallViewInstance.freeFallAdapter.off();
    }

    @Test(timeout = 25000)
    public void testMain() {
        FreeFallView.main(null);
        //this.freeFallViewInstance.freeFallAdapter.on();
        freeFallViewInstance.jTextFieldHigh.setText(String.valueOf(HIGH));
        freeFallViewInstance.jButtonSetHigh.doClick();
        freeFallViewInstance.jTextFieldMass.setText(String.valueOf(MASS));
        freeFallViewInstance.jButtonMass.doClick();
        this.freeFallViewInstance.jButtonStart.doClick();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FreeFallViewTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int totalEnergy = this.freeFallViewInstance.model.getTotalEnergy();
        int kinetic = this.freeFallViewInstance.model.getKineticEn();
        int t = (int) (MASS*GRAV*HIGH);
        assertEquals(t, totalEnergy);
        assertEquals(t , kinetic);
    }
    
    @Test(expected = NumberFormatException.class)
    public void badHighTest(){
        int badHigh = -10;
        this.freeFallViewInstance.freeFallAdapter.setBPM(badHigh);
        /*this.freeFallViewInstance.jTextFieldHigh.setText(String.valueOf(badHigh));
        this.freeFallViewInstance.jButtonSetHigh.doClick();*/
    }
    
    @Test(expected = NumberFormatException.class)
    public void badMassTest(){
        double badMass = -2.5;
        this.freeFallViewInstance.freeFallAdapter.setMass(badMass);
    }
}
