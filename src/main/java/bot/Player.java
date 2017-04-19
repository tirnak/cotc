import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    /**
     * Auto-generated code below aims at helping you parse
     * the standard input according to the problem statement.
     **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            List<Barrel> bs = new ArrayList<>();
            List<Ship> my = new ArrayList<>(), enemy = new ArrayList<>();
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
                }
                if ("BARREL".equals(entityType)) {
                    bs.add(new Barrel(entityId, x, y, arg1, arg2, arg3, arg4));
                }
            }
            for (int i = 0; i < myShipCount; i++) {
                Ship myI = my.get(i);
                // find closest
                Barrel closest = null; double bestDist = Double.MAX_VALUE;
                for (Barrel b : bs) {
                    double tmpDist = myI.getDist(b);
                    if (tmpDist < bestDist) {
                        closest = b;
                        bestDist = tmpDist;
                    }
                }
                boolean fire = false; Ship closestEnemy = null; double bestEDist = Double.MAX_VALUE;
                for (Ship enemyI : enemy) {
                    double tmpDist = myI.getDist(enemyI);
                    if (tmpDist < bestEDist) {
                        closestEnemy = enemyI;
                        bestEDist = tmpDist;
                    }
                }
                if (closest == null && closestEnemy == null) {
                    System.out.format("WAIT%n");
                } else if (bestEDist < 3 || closest == null) {

                    System.out.format("FIRE %d %d%n", closestEnemy.x, closestEnemy.y);
                } else {
                    System.out.format("MOVE %d %d%n", closest.x, closest.y); // Any valid action, such as "WAIT" or "MOVE x y"
                    bs.remove(closest);
                }
            }
        }
    }
}

class Entity {
    int id, x, y;
    public Entity(int id, int x, int y) {this.id = id; this.x=x; this.y=y;}
    public double getDist(Entity other) {
        return Math.sqrt(Math.pow(other.x-x,2) + Math.pow(other.y-y,2));
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
        System.err.println("ship " + arg4 + " with stock "+stock );
    }
}

class Barrel extends Entity {
    int v;
    Barrel(int id, int x, int y, int arg1, int arg2, int arg3, int arg4) {
        super(id,x,y);
        this.v = arg1;
    }
}
