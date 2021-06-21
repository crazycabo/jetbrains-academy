class Move {
    public static void moveRobot(Robot robot, int toX, int toY) {

        // Move to X
        int currentX = robot.getX();
        int stepsX = 0;

        if (currentX < toX) {
            stepsX = Math.abs(toX) - currentX;

            while (!robot.getDirection().equals(Direction.RIGHT)) {
                robot.turnRight();
            }
        }

        if (currentX > toX) {
            stepsX = Math.abs(toX) + currentX;

            while (!robot.getDirection().equals(Direction.LEFT)) {
                robot.turnLeft();
            }
        }

        for (int i = stepsX; i > 0; i--) {
            robot.stepForward();
        }

        // Move to Y
        int currentY = robot.getY();
        int stepsY = 0;

        if (currentY < toY) {
            stepsY = Math.abs(toY) - currentY;

            while (!robot.getDirection().equals(Direction.UP)) {
                robot.turnRight();
            }
        }

        if (currentY > toY) {
            stepsY = Math.abs(toY) + currentY;

            while (!robot.getDirection().equals(Direction.DOWN)) {
                robot.turnLeft();
            }
        }

        for (int i = stepsY; i > 0; i--) {
            robot.stepForward();
        }
    }
}

//Don't change code below

enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnLeft() {
        switch (this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException();
        }
    }

    public Direction turnRight() {
        switch (this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException();
        }
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public void stepForward() {
        x += direction.dx();
        y += direction.dy();
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}