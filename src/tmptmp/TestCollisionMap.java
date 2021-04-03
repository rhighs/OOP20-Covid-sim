public class TestCollisionMap {
    public static void main(String[] args) {
        CollisionMap<Integer> map = new CollisionMap<>();
        map.add(4);
        System.out.println(map.getTimer(4));
        map.update();
        map.update();
        System.out.println(map.getTimer(4));
        map.contains(4);
        System.out.println(map.getTimer(4));
        for (int i = 0; i <= 16; i++) {
            map.update();
        }
        System.out.println(map.getTimer(4));
        if (map.contains(4)) {
            System.out.println("map still contains 4");
        } else {
            System.out.println("no more 4");
        }
    }
}
