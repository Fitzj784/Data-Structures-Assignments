import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    /**
     * The graph will be made using an Adjacency matrix thus the complexity of the Breadth first search will be
     *  O(V^2)
     *  Adding a relationship will be done in constant time O(1) however by simply updating the row and col index.
     */
    private int maxint;
    Vertex vertex[];
    private int[][] adjmat;
    private int vertcount;
    private Queue queue;
    ArrayList<String> flist = new ArrayList<>();
    ArrayList<String> newby = new ArrayList<>();

    public Graph(int size){

        vertex = new Vertex[size];
        adjmat = new int[size][size];
        vertcount = 0;
        initialize_Adj(adjmat, size);
        queue = new LinkedList<Integer>();
    }

    /**
     * Adding vertexes to the graph and their index numbers
     * @param name
     */
    protected void add_Vertex(String name){
        vertex[vertcount++] = new Vertex(name);
    }

    protected int[][] initialize_Adj(int[][] mat, int size){
        for(int i = 0; i < size-1; i++)
            for(int j = 0; j < size-1; j++)
                mat[i][j] = 0;

        return mat;
    }

    protected void add_Friend(String y, String x){
        int y1 = getIndex(y);
        int x1 = getIndex(x);

        adjmat[y1][x1] = 1;
        adjmat[x1][y1] = 1;
    }

    protected void remove_Friend(String y, String x){
        int y1 = getIndex(y);
        int x1 = getIndex(x);

        adjmat[y1][x1] = 0;
        adjmat[x1][y1] = 0;
    }

    protected int getIndex(String name) {
        for (int i = 0; i < vertcount; i++) {
            if (vertex[i].getName().equals(name))
                return i;
        }
        return -1;
    }

    /**
     * Used to verify that two persons are friends
     * @param y
     * @param x
     * @return
     */
    protected boolean areFriends(String y, String x){
        int y1 = getIndex(y);
        int x1 = getIndex(x);
        return (adjmat[y1][x1] == 1 && adjmat[x1][y1] == 1);
    }

    protected boolean mutual_Friends(String y, String x, String z){
        int y1 = getIndex(y);
        int x1 = getIndex(x);
        int z1 = getIndex(z);
        return adjmat[y1][x1] == 1 && adjmat[y1][z1] == 1;
    }

    /**
     * find all of the adjacent unvisited nodes... This is used in the BFS
     * @param x
     * @return
     */
    protected int getAdjUnVisited(int x){
        for(int j = 0; j < vertcount;j++){
            if (adjmat[x][j] ==1 && !vertex[j].isVisited()){
                return j;
            }
        }
        return -1;
    }

    protected int getAdjpath(String y, String x){
        int y1 = getIndex(y);
        int x1 = getIndex(x);
        for(int j = 0; j < vertcount; j++){
            if(adjmat[y1][j] == 1 && j == getIndex(x)){
                return y1;
            }
        }
        return -1;
    }

    /**
     * Upon finding an Adjacent friend who knows the person check if he is a direct friend.
     * @param s
     * @param n
     * @return
     */
    protected ArrayList<String> trace(ArrayList<String> s, String n){
        ArrayList<String> cr = new ArrayList<>();

        for(String u : s){
            if(getAdjpath(u,n) != -1 ) {
                cr.add(u);
            }
        }
        return cr;
    }

    /**
     * The breadth first search is used to try and find the shortest path to a another user via friends you already have
     * It is possible that none of your friends know the person you are trying to befriend.
     * @param y
     * @param x
     * @return
     */
    protected ArrayList BFS(String y, String x){
        flist.clear();
        int y1 = getIndex(y);

        vertex[y1].setVisited(true);
        queue.add(y1);
        flist.add(y);
        int curr2;

        while(!queue.isEmpty()){
            int curr = (int)queue.remove();

            if(vertex[getIndex(x)].isVisited()){
                queue.clear();
                for(int i = 0; i < vertcount; i++){
                    vertex[i].setVisited(false);
                }
                return flist;
            }

            while((curr2 = getAdjUnVisited(y1)) != -1){

                vertex[curr2].setVisited(true);
                if(vertex[getIndex(x)].isVisited())
                    return flist;
                if(!flist.contains(vertex[curr2].getName()))
                flist.add(vertex[curr2].getName());
                queue.add(curr2);
            }
        }

        for(int i = 0; i < vertcount; i++){
            vertex[i].setVisited(false);
        }
        return flist;
    }
}

