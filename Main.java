import daj.*;

public class Main extends Application {

    private static final int DIM = 600; // width and height of the window


    public static void main(String[] args) {
        new Main().run();
    }


    public Main() {
        super("Singhal_Heuristic_ME", DIM, DIM);
    }

    @Override
    public void resetStatistics() {

    }


    public void construct() {
        // number of nodes
        int NODES = 5;
        Node[] node = new Node[NODES];
        int radius = (DIM - 200) / 2;
        double delta = 2 * Math.PI / NODES;
        double rad;

        // create the nodes, place them in a cycle
        rad = 0;
        for (int i = 0; i < NODES; i++) {
            node[i] = node(// use one of them
                    //new LogicalTimeME(i, NODES),
                    new NewSinghal(i, NODES),
                    (Integer.valueOf(i)).toString(),
                    (int)(DIM / 2 + radius * Math.cos(rad)),
                    (int)(DIM / 2 + radius * Math.sin(rad)));
            rad += delta;
        }
        // create channels between nodes
        for (int i = 0; i < NODES; i++) {
            for (int j = 0; j < NODES; j++) {
                link(node[i], node[j]);
            }
        }
    }


    public String getText() {
        return "Singhal Heuristic Algorithm";
    }

}
