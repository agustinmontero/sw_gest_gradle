package Controllers;

import Models.FreeFallModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Agustin
 */
public class FreeFallControllerTest {
    
    private FreeFallModel model;
    private FreeFallController fFallContInstance;
    
    public FreeFallControllerTest() {
        model = new FreeFallModel();
        fFallContInstance = new FreeFallController(model);
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
        fFallContInstance.setBPM(bpm);
        assertEquals(bpm, model.getAltitude());
    }
    
    @Test
    public void testSetBPM2(){
        int bpm = -20;
        fFallContInstance.setBPM(bpm);
        assertNotSame(bpm, model.getAltitude());
    }
}
