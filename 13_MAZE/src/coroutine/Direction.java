package coroutine;

import java.awt.Point;

/** 
 * Captures the main cardinal and intercardinal directions. 
 *
 * @author Anthony Dickson 
 */
enum Direction { 
    N(new Point(0, -1)) {
        @Override
        public String toString() {
            return "\u2191";
        }
    }, 
    S(new Point(0, 1)) {
        @Override
        public String toString() {
            return "\u2193";
        }
    }, 
    E(new Point(1, 0)) {
        @Override
        public String toString() {
            return "\u2192";
        }
    }, 
    W(new Point(-1, 0)) {
        @Override
        public String toString() {
            return "\u2190";
        }
    }, 
    NE(new Point(1, -1)) {
        @Override
        public String toString() {
            return "\u2197";
        }
    }, 
    NW(new Point(-1, -1)) {
        @Override
        public String toString() {
            return "\u2196";
        }
    }, 
    SE(new Point(1, 1)) {
        @Override
        public String toString() {
            return "\u2198";
        }
    }, 
    SW(new Point(-1, -1)) {
        @Override
        public String toString() {
            return "\u2199";
        }
    };
    
    Point asPoint;

    Direction(Point p) {
        asPoint = p;
    }
};