package cs.eng1.piazzapanic.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.observable.Observer;
import cs.eng1.piazzapanic.stations.StationCollider;

@RunWith(GdxTestRunner.class)
public class StationColliderTests {

    public StationCollider initialiseStationCollider(){
        return new StationCollider(Mockito.mock(ChefManager.class));
    }

    @Test(expected = NullPointerException.class)
    public void testNullRegistration() {
        StationCollider stationCollider = initialiseStationCollider();
        Observer<Chef> observer = null;
        stationCollider.register(observer);
    }

}
