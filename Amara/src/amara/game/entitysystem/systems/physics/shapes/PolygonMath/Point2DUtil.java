/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
class Point2DUtil {
    public static Point2D lineSegmentIntersectionPointWithoutCorners(Point2D p1, Point2D p2, Point2D p3, Point2D p4)
        {
            assert(!p1.equals(p2));
            assert(!p3.equals(p4));
            if (p1.withinEpsilon(p3)) return null;
            if (p1.withinEpsilon(p4)) return null;
            if (p2.withinEpsilon(p3)) return null;
            if (p2.withinEpsilon(p4)) return null;

            double denom = (p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY());
            if (Util.withinEpsilon(denom)) return null;

            double nomT = (p3.getX() - p1.getX()) * (p4.getY() - p3.getY()) + (p1.getY() - p3.getY()) * (p4.getX() - p3.getX());
            double t = nomT / denom;
            double T = ((p3.getX() - p1.getX()) * (p4.getY() - p3.getY()) + (p1.getY() - p3.getY()) * (p4.getX() - p3.getX())) / ((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));
            
            //boolean test1 = !(0 < t && t < 1);
            boolean test2 = !(Util.aboveEpsilon(t) && Util.belowEpsilon(t - 1));
            
            if (test2) return null;
            assert(T == t);

            double nomU = (p1.getX() - p3.getX()) * (p2.getY() - p1.getY()) + (p3.getY() - p1.getY()) * (p2.getX() - p1.getX());
            double u = -nomU / denom;

            //test1 = !(0 < u && u < 1);
            test2 = !(Util.aboveEpsilon(u) && Util.belowEpsilon(u - 1));
            
            if (test2) return null;

            Point2D result = new Point2D(p1.getX() + t * (p2.getX() - p1.getX()), p1.getY() + t * (p2.getY() - p1.getY()));
            if (result.withinEpsilon(p1)) return null;
            if (result.withinEpsilon(p2)) return null;
            if (result.withinEpsilon(p3)) return null;
            if (result.withinEpsilon(p4)) return null;

            assert(Math.min(p1.getX(), p2.getX()) <= result.getX() + Util.Epsilon);
            assert(result.getX() <= Math.max(p1.getX(), p2.getX()) + Util.Epsilon);
            assert(Math.min(p1.getY(), p2.getY()) <= result.getY() + Util.Epsilon);
            assert(result.getY() <= Math.max(p1.getY(), p2.getY()) + Util.Epsilon);

            assert(Math.min(p3.getX(), p4.getX()) <= result.getX() + Util.Epsilon);
            assert(result.getX() <= Math.max(p3.getX(), p4.getX()) + Util.Epsilon);
            assert(Math.min(p3.getY(), p4.getY()) <= result.getY() + Util.Epsilon);
            assert(result.getY() <= Math.max(p3.getY(), p4.getY()) + Util.Epsilon);

            assert(result.between(p1, p2));
            assert(result.between(p3, p4));

            return result;
        }
    
    public static Point2D fromLineSegmentToPoint(Point2D a, Point2D b, Point2D p)
    {
        return fromLineSegmentToPoint(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
    }
    public static Point2D fromLineSegmentToPoint(double ax, double ay, double bx, double by, double px, double py)
    {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by);
        if (ay <= 0) return new Point2D(px, py);
        ax = (bx * bx + by * by);
        assert(0 < ax);
        if (ax <= ay) return new Point2D(px - bx, py - by);
        ay /= ax;
        ax = -(ay * bx - px);
        ay = -(ay * by - py);
        return new Point2D(ax, ay);
    }

    public static Point2D sum(Point2D... points)
    {
        double x = 0d;
        double y = 0d;
        for(Point2D point: points)
        {
            x += point.getX();
            y += point.getY();
        }
        return new Point2D(x, y);
    }
    public static Point2D avg(Point2D... points)
    {
        return sum(points).div(points.length);
    }
//left < 0 < right
    public static double lineSide(Point2D p, Point2D a, Point2D b)
    {
        return (b.getY() - a.getY()) * (p.getX() - a.getX()) - (b.getX() - a.getX()) * (p.getY() - a.getY());
    }

    public static double lineSegmentAxisIntersectionX(Point2D a, Point2D b, double yAxis)
    {
        return lineSegmentAxisIntersectionYHelper(a.getY(), a.getX(), b.getY(), b.getX(), yAxis);
    }
    public static double lineSegmentAxisIntersectionY(Point2D a, Point2D b, double xAxis)
    {
        return lineSegmentAxisIntersectionYHelper(a.getX(), a.getY(), b.getX(), b.getY(), xAxis);
    }
    private static double lineSegmentAxisIntersectionYHelper(double ax, double ay, double bx, double by, double xAxis)
    {
        if ((ax - xAxis) * (bx - xAxis) >= 0) return Double.NaN;
        return ay + ((xAxis - ax) / (bx - ax)) * (by - ay);
    }
    
    public static boolean chainsCenterIntersection(Point2D a, Point2D b, Point2D c, Point2D d, Point2D p)
    {
        assert !a.equals(b);
        assert !c.equals(d);
        
        assert !p.equals(a);
        assert !p.equals(b);
        assert !p.equals(c);
        assert !p.equals(d);
        
        a = a.sub(p);
        b = b.sub(p);
        c = c.sub(p);
        d = d.sub(p);
        
        double e = Math.atan2(a.getY(), a.getX());
        double f = Math.atan2(b.getY(), b.getX());
        double g = Math.atan2(c.getY(), c.getX());
        double h = Math.atan2(d.getY(), d.getX());
        
        if(f < e)
        {
            double tmp = e;
            e = f;
            f = tmp;
        }
        if(h < g)
        {
            double tmp = g;
            g = h;
            h = tmp;
        }
        
        if(e <= h) return false;
        if(g <= f) return false;
        if(f <= h && g <= e) return false;
        if(h <= e && f <= g) return false;
        
        assert false;
        
        return true;
    }
    
}