package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.model.LightTrail;
import be.kuleuven.swop.objectron.model.Square;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.*;

/**
 * @author : Nik Torfs
 *         Date: 28/02/13
 *         Time: 20:56
 */
public class TestLightTrail {
    private LightTrail trail;

    @Before
    public void setUp(){
        trail = new LightTrail();
    }

    @Test
    public void test_expand_retract(){
        Square s1 = new Square();
        Square s2 = new Square();
        Square s3 = new Square();
        Square s4 = new Square();

        trail.expand(s1);
        assertTrue(s1.isObstructed());
        trail.expand(s2);
        assertTrue(s2.isObstructed());
        trail.expand(s3);
        assertTrue(s3.isObstructed());
        trail.expand(s4);
        assertTrue(s4.isObstructed());
        assertFalse(s1.isObstructed());

        trail.reduce();
        trail.reduce();
        trail.reduce();
        assertFalse(s2.isObstructed());
        assertFalse(s3.isObstructed());
        assertFalse(s4.isObstructed());
    }

}
