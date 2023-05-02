package cs.eng1.piazzapanic.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class StationCollider {

    public StationCollider initialiseStationCollider(){
        return new StationCollider();
    }

    @Test
    public void test(){
        StationCollider stationCollider = initialiseStationCollider();
        assertTrue(true);
    }

}
