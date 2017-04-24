package bot;

import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class TemplateBot {

    public static void main(String args[]) throws IllegalAccessException, InstantiationException {
        TemplateBot bot = new TemplateBot();
        bot.run();
    }

    public void run() throws InstantiationException, IllegalAccessException {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            List<Barrel> bs = new ArrayList<>();
            List<Ship> my = new ArrayList<>(), enemy = new ArrayList<>();
            List<Mine> ms = new ArrayList<>();
            List<Cannonball> cs = new ArrayList<>();
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                if ("SHIP".equals(entityType)) {
                    if (arg4 == 1) my.add(new Ship(entityId, x, y, arg1, arg2, arg3, arg4));
                    else enemy.add(new Ship(entityId, x, y, arg1, arg2, arg3, arg4));
                } else if ("BARREL".equals(entityType)) {
                    bs.add(new Barrel(entityId, x, y, arg1, arg2, arg3, arg4));
                } else if ("MINE".equals(entityType)) {

                }
            }
            final Class<?> thisClass = MethodHandles.lookup().lookupClass();
            for (int i = 0; i < myShipCount; i++) {
                Ship myI = my.get(i);
                try {
                    this.printForShip(myI, bs, my, enemy, ms, cs);
                } catch (Exception e) {System.out.format("WAIT%n");}
            }
        }
    }

    public void printForShip(Ship myI, List<Barrel> bs, List<Ship> my, List<Ship> enemy, List<Mine> ms, List<Cannonball> cs) {
        Barrel closest = myI.getClosest(bs);
        Ship closestEnemy = myI.getClosest(enemy);
        if (closest == null && closestEnemy == null) {
            System.out.format("WAIT%n");
        } else if (myI.getDist(closestEnemy) < 3 || closest == null) {
            System.out.format("FIRE %d %d%n", r(closestEnemy.x), r(closestEnemy.y));
        } else {
            System.out.format("MOVE %d %d%n", r(closest.x), r(closest.y)); // Any valid action, such as "WAIT" or "MOVE x y"
            bs.remove(closest);
        }
    }

    static long r(double d) { return Math.round(d); }

    static long x(double d) { return Math.abs(Math.round(d)) % 21; }
    static long y(double d) { return Math.abs(Math.round(d)) % 23; }

    static boolean equals(Object object1, Object object2) {
        if (object1 == null && object2 == null) return true;
        if (object1 == null || object2 == null) return false;
        return object1.equals(object2);
    }

    static boolean equals(double f1, double f2) {
        return f1 - f2 > -0.001 && f1 - f2 < 0.001;
    }

    static double getDist(Entity e1, Entity e2) {
        if (e1 == null || e2 == null) return Double.MAX_VALUE;
        return e1.getDist(e2);
    }
}

class Entity {
    double id, x, y;
    public Entity(int id, int x, int y) {this.id = id; this.x=x; this.y=y;}
    public double getDist(Entity other) {
        return Math.sqrt(Math.pow(other.x-x,2) + Math.pow(other.y-y,2));
    }
    public <T extends Entity> T getClosest(List<T> entities) {
        T closest = null; double bestDist = Double.MAX_VALUE;
        for (T entity : entities) {
            double tmpDist = this.getDist(entity);
            if (tmpDist < bestDist) {
                closest = entity;
                bestDist = tmpDist;
            }
        }
        return closest;
    }
}
class Ship extends Entity {
    int or, speed, stock, user;
    Ship(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
        super(id,x,y);
        this.or = arg1;
        this.speed = arg2;
        this.stock = arg3;
        this.user = arg4;
//        System.err.println("ship " + arg4 + " with stock "+stock );
    }
}

class Barrel extends Entity {
    int v;
    Barrel(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
        super(id,x,y);
        this.v = arg1;
    }
}

class Mine extends Entity {
    Mine(int id, int x, int y) { super(id, x, y); }
}

class Cannonball extends Entity {
    int targetId;
    int turnsLeft;
    Cannonball(int id, int x, int y, int targetEntityId, int tLeft) {
        super(id, x, y);
        targetId = targetEntityId; turnsLeft = tLeft;
    }
}
