package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.obstruction.LightTrail;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author : Nik Torfs
 *         Date: 28/02/13
 *         Time: 20:56
 */
public class TestLightTrail {
    private LightTrail trail;

    @Before
    public void setUp() {
        trail = new LightTrail();
    }

    @Test
    public void test_expand_retract() {
        Square s1 = new Square(new Position(0, 0));
        Square s2 = new Square(new Position(1, 1));
        Square s3 = new Square(new Position(1, 2));
        Square s4 = new Square(new Position(1, 4));

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
