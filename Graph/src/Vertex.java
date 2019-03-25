/**
 * A vertex class that stores a person and their information
 * The class with be used by the Graph class.
 */
public class Vertex {
    private String name;
    private boolean visited;


    public Vertex(String name){
        this.name = name;
        this.visited = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString(){
        return this.getName();
    }
}
