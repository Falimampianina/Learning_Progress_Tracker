package tracker;

import java.util.stream.IntStream;

public class Student {
    private final long ID;

    private String firstname;

    private String lastname;

    private int javapoints = 0;

    private int dsapoints = 0;

    private int springpoints = 0;

    private int databasespoints = 0;

    private boolean notifiedJava = false;

    private boolean notifiedDsa = false;

    private boolean notifiedDatabases = false;

    private boolean notifiedSpring = false;

    public Student() {
        this.ID = System.currentTimeMillis();
    }

    public long getID() {
        return ID;
    }

    public int getJavapoints() {
        return javapoints;
    }

    public int getDsapoints() {
        return dsapoints;
    }

    public int getDatabasespoints() {
        return databasespoints;
    }

    public int getSpringpoints() {
        return springpoints;
    }



    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isNotifiedJava() {
        return notifiedJava;
    }

    public void setNotifiedJava(boolean notifiedJava) {
        this.notifiedJava = notifiedJava;
    }

    public boolean isNotifiedDsa() {
        return notifiedDsa;
    }

    public void setNotifiedDsa(boolean notifiedDsa) {
        this.notifiedDsa = notifiedDsa;
    }

    public boolean isNotifiedDatabases() {
        return notifiedDatabases;
    }

    public void setNotifiedDatabases(boolean notifiedDatabases) {
        this.notifiedDatabases = notifiedDatabases;
    }

    public boolean isNotifiedSpring() {
        return notifiedSpring;
    }

    public void setNotifiedSpring(boolean notifiedSpring) {
        this.notifiedSpring = notifiedSpring;
    }

    public void updatePoints(int[] points){
        if(points.length != 4){
            System.out.println("Incorrect points format.");
        }else if(IntStream.of(points).anyMatch(x -> x < 0)){
            System.out.println("Incorrect points format.");
        }else{
            this.javapoints+=points[0];
            this.dsapoints+=points[1];
            this.databasespoints+=points[2];
            this.springpoints+=points[3];
            System.out.println("Points updated.");
        }
    }

    public String toString(){
        return getID() + " points: Java=" + getJavapoints() + "; DSA=" + getDsapoints() + "; Databases=" + getDatabasespoints() + "; Spring=" + getSpringpoints();
    }

}
