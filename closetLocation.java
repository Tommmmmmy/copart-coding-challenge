import java.io.File;
import java.util.*;

public class closetLocation {
  
  /** use an array to store locations 
      a map to look up the city
   **/
  public Point[] locations;
  HashMap<Point, String> map;
  
  /** create a point class **/
  class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
  /** instantiate the class **/
  public closetLocation(File file) {
    this.locations = new Point[1000];
    this.map = new HashMap<>();
    int i = 0;
    
    try {
      File f = new File("zip_codes_states.csv");
      Scanner sc = new Scanner(f);
      
      /** skip the first line */
      sc.nextLine();
      while(sc.hasNextLine()) {
        String[] s = sc.nextLine().split(",");
        Point p = new Point(Integer.valueOf(s[1]), Integer.valueOf(s[2]));
        locations[i] = p;
        map.put(p, s[3]);
        i++;
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * use priorityqueue to store points, the order is the distance to the target city
   * then traverse the array to find k nearest cities
   * the time complexity is Nlog(N)
   * @param k
   * @param target
   * @return
   */
  public List<Point> findKClosest(int k, Point target) {
    PriorityQueue<Point> pq = new PriorityQueue<>(1000, new Comparator<Point>() {
        @Override
        public int compare(Point a, Point b) {
            return ((b.x - target.x) * (b.x - target.x) + (b.y - target.y) * (b.y - target.y)) - ((a.x - target.x) * (a.x - target.x) + (a.y - target.y) * (a.y - target.y));
        }
    });
     
    for (int i = 0; i < locations.length; i++) {
        if (i < k)
            pq.offer(locations[i]);
        else {
            Point temp = pq.peek();
            if ((locations[i].x - target.x) * (locations[i].x - target.x) + (locations[i].y - target.y) * (locations[i].y - target.y) - (temp.x - target.x) * (temp.x - target.x) + (temp.y - target.y) * (temp.y - target.y) < 0) {
                pq.poll();
                pq.offer(locations[i]);
            }
        }
    }
     
    List<Point> res = new ArrayList<>();
    while (!pq.isEmpty())
        res.add(pq.poll());
     
    return res;
  }
  
  public static void main(String[] args) {
    
    try {
      File f = new File("zip_codes_states.csv");
      closetLocation cl = new closetLocation(f);
      List<Point> result = new ArrayList<>();
      Map<Point, String> map = cl.map;
      Scanner sc = new Scanner(System.in);
      int x = sc.nextInt();
      int y = sc.nextInt();
      int k = sc.nextInt();
      closetLocation.Point target = cl.new Point(x, y);
      result = cl.findKClosest(k, target);
      for(Point p : result) {
        System.out.println(map.get(p));
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}

