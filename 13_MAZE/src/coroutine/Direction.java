package coroutine;

import java.awt.Point;

/** 
 * Captures the main cardinal and intercardinal directions. 
 *
 * @author Anthony Dickson 
 */
enum Direction { 
    N(new Point(0, 1)) {}, 
    S(new Point(0, -1)) {}, 
    E(new Point(1, 0)) {}, 
    W(new Point(-1, 0)) {}, 
    NE(new Point(1, 1)) {}, 
    NW(new Point(-1, 1)) {}, 
    SE(new Point(1, -1)) {}, 
    SW(new Point(-1, 1)) {};
    
    Point asPoint;

    Direction(Point p) {
        asPoint = p;
    }

};