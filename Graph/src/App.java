import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class App {
    static ArrayList<String> info = new ArrayList<>();
    static ArrayList<String> arg = new ArrayList<>();
    static ArrayList<String> bfs = new ArrayList<>();
    static int num = 0;


    public static void main(String[] args) {
        try {
            readIN(args[0], info);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            readIN(args[1], arg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Graph friendship = new Graph(Integer.parseInt(info.get(0)));
        fillGraph(info, friendship);
        answer_Query(arg, friendship);
    }

    /**
     * Reading in the information from the text files.
     * @param s
     * @param info
     * @throws FileNotFoundException
     */
    private static void readIN(String s, ArrayList<String> info) throws FileNotFoundException {
        Scanner kb = new Scanner(new File(s));
        while (kb.hasNextLine()) {
            info.add(kb.nextLine());
        }
        kb.close();
    }

    /**
     * Adding the information to the graph so we can later perform operations.
     * @param info
     * @param g
     */
    private static void fillGraph(ArrayList<String> info, Graph g) {

        ArrayList<String> dups = new ArrayList<>();
        String[] lines;
        String[] fr;
        info.remove(0);

        for (String x : info) {
            lines = x.split(" ");
            if (!dups.contains(lines[0]) && !dups.contains(lines[1])) {
                dups.add(lines[0]);
                dups.add(lines[1]);
            } else if (dups.contains(lines[0]) && !dups.contains(lines[1])) {
                dups.add(lines[1]);
            } else if (dups.contains(lines[1]) && !dups.contains(lines[0])) {
                dups.add(lines[0]);
            }
        }
        for (String q : dups) {
            g.add_Vertex(q);
        }

        for (String z : info) {
            fr = z.split(" ");
            g.add_Friend(fr[0], fr[1]);
        }
    }
//Methos is used to answer the second text file requests...
    public static void answer_Query(ArrayList<String> query, Graph friends) {
        String[] text;
        for (int i = 0; i < query.size(); i++) {
            text = query.get(i).split(" ");

            switch (text[0]) { //switch statement to answer specific queries.
                case "AddFriendship":
                    System.out.println(text[0] + " " + text[1] + " " + text[2]);
                    friends.add_Friend(text[1], text[2]);
                    break;

                case "RemoveFriendship":
                    System.out.println(text[0] + " " + text[1] + " " + text[2]);
                    if(friends.areFriends(text[1], text[2])) {
                        friends.remove_Friend(text[1], text[2]);
                    }else {
                        System.out.print("NOFriend Error - you guys aren't friends");
                    }
                    break;

                case "WantToBefriend":
                    System.out.println(text[0] + " " + text[1] + " " + text[2]);
                    if (friends.areFriends(text[1], text[2])) {
                        System.out.println("AlreadyFriend Error");
                    } else {
                        Hashtable<String, ArrayList<String>> fr = new Hashtable<>();
                        bfs.addAll(friends.BFS(text[1], text[2]));
                        ArrayList<String> dr = new ArrayList<>();
                        ArrayList<String> cr = new ArrayList<>();

                        for(String s : bfs){
                            cr.addAll(friends.BFS(s,text[2]));
                            if(cr.get(0).equals(text[1]))
                                cr.clear();
                            fr.put(s,friends.trace(cr,text[2]));
                            cr.clear();
                        }

                        int paths = 0;
                        int mutual = 0;
                        int set = 0;

                        for (String j : fr.keySet()) {
                            if(fr.get(j).size() > 0)
                                mutual++;
                            paths += fr.get(j).size();
                        }

                        System.out.println("You have " + mutual + " mutual friends in " +
                                paths + " paths - try asking them for instuctions: ");

                        for(String r : fr.keySet()){
                            if(fr.get(r).size() == 1) {
                                if(r.equals(fr.get(r).get(0))) {
                                    System.out.println(r + " - path is " + text[1] + " " + fr.get(r).get(0) + " " + text[2]);
                                }else{
                                    System.out.println(r + " - path is " + text[1] +" "+ r + " "+ fr.get(r).get(0) + " " + text[2]);
                                }
                            }else if (fr.get(r).size() > 1){
                                int w = 0;
                                for(String q : fr.get(r)){
                                    if(!r.equals(fr.get(r).get(0)))
                                        System.out.println(r + " - path is " + text[1]+ " "+ r+ " " + fr.get(r).get(w) + " " + text[2]);
                                    w++;
                                }
                            }
                        }
                        break;
                    }
            }
        }
    }
}


