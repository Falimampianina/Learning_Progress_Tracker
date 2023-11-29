package tracker;


import java.util.*;

public class Tracker {
    private final String begin = "Learning Progress Tracker";

    private final Scanner scanner = new Scanner(System.in);

    private Map<String, Student> students = new LinkedHashMap<>();

    public String getBegin() {
        return begin;
    }

    private int[] maxPoints = {600 , 400 , 480 , 550};

    private int[] submissions = {0 , 0 , 0 , 0};

    public void waitForInput(){
        String input = scanner.nextLine();
        while (!Objects.equals(input, "exit")){
            if(Objects.equals(input,null) || (input.matches("\\s*"))){
                System.out.println("No input");
            }else if(Objects.equals(input,"add students")){
                addStudents();
            }else if(Objects.equals(input , "back")){
                System.out.println("Enter 'exit' to exit the program");
            }else if(Objects.equals(input , "list")){
                printStudentsId();
            }else if(Objects.equals(input , "add points")){
                addPoints();
            }else if (Objects.equals(input , "find")){
                find();
            }else if (Objects.equals(input , "statistics")){
                statistics();
            }else if (Objects.equals(input , "notify")){
                notifySuccess();
            }
            else{
                System.out.println("Error: unknown command!");
            }
            input=scanner.nextLine();
        }
        System.out.println("Bye!");
        System.exit(0);
    }

    public void addStudents() {
        System.out.println("Enter student credentials or 'back' to return: ");
        String input = scanner.nextLine().trim();
        int count = 0;
        while(!Objects.equals(input , "back")){
            String[] credentials = input.split(" ");
            if(credentials.length < 3){
                System.out.println("Incorrect credentials.");
            }else{
                if(emailChecking(credentials[credentials.length-1])){
                    boolean correct = true;
                    for(int i=0; i<= credentials.length-2; i++){
                        if (!nameChecking(credentials[i])){
                            if (i == 0){
                                System.out.println("Incorrect first name.");
                            }else {
                                System.out.println("Incorrect last name.");
                            }
                            correct = false;
                            break;
                        }
                    }
                    if (correct){
                        if(students.containsKey(credentials[credentials.length-1])){
                            System.out.println("This email is already taken");
                        }else{
                            count++;
                            String firstname = credentials[0];
                            StringBuilder lastname = new StringBuilder();
                            for (int j = 1; j <= credentials.length-2;j++){
                                lastname.append(credentials[j]).append(" ");
                            }
                            Student student = new Student();
                            student.setFirstname(firstname);
                            student.setLastname(String.valueOf(lastname));
                            // use the email as key and student as value
                            students.put(credentials[credentials.length-1], student);
                            System.out.println("The student has been added.");
                        }
                    }
                }else{
                    System.out.println("Incorrect email.");
                }
            }
            input = scanner.nextLine().trim();
        }
        System.out.println("Total " + count + " students have been added.");
        waitForInput();
    }

    // Check if the name contains only english alphabet
    public boolean nameChecking(String name){
        if(name.length() >= 2){
            return name.matches("([a-zA-Z]+[-']?[a-zA-Z]+)([-']?[a-zA-Z])*");
        }else{
            return false;
        }
    }

    // check if the provided email is correct
    public boolean emailChecking(String email){
        return email.matches("[a-zA-Z\\d]*\\.?[a-zA-Z\\d]*@[a-zA-Z\\d]*\\.[a-z\\d]+");
    }

    public void printStudentsId(){
        long[] ids = students.values().stream().mapToLong(Student::getID).toArray();
        if(ids.length==0){
            System.out.println("No students found.");
        }else{
            System.out.println("Students:" );
            for (long l : ids){
                System.out.println(l);
            }
        }
    }

    // update the points for a specific student
    public void addPoints(){
        System.out.println("Enter an id and points or 'back' to return.");
        String input = scanner.nextLine();
        while(!Objects.equals(input, "back")){
            int[] points = new int[4];
            String key="";
            String id = "";
            if(Objects.equals(input, "back")){
                waitForInput();
            }else{
                String[] inputs = input.trim().split(" ");
                if (inputs.length != 5){
                    //System.out.println("hier0!");
                    System.out.println("Incorrect points format.");
                }else{
                    try{
                        id = inputs[0];
                        for (Map.Entry<String, Student> entry : students.entrySet()){
                            if (String.valueOf(entry.getValue().getID()).equals(id)){
                                key = entry.getKey();
                            }
                        }
                        if (Objects.equals(key, "")){
                            String output = "No student is found for id="+ id + ".";
                            System.out.println(output);
                        }else{
                            for (int i = 0; i < 4; i++){
                                points[i] = Integer.parseInt(inputs[i+1]);
                            }
                            // counting the number of submissions for each course every pointsUpdate and put it in an array
                            for(int j=0; j < submissions.length;j++){
                                if (points[j] > 0){
                                    submissions[j]+=1;
                                }
                            }
                            Student s = students.get(key);
                            s.updatePoints(points);
                            students.replace(key , s);
                        }
                    } catch (Exception e){
                        System.out.println("Incorrect points format.");
                    }
                }
            }
            input = scanner.nextLine();
        }
    }

    // find a student by its Id and print his points
    public void find(){
        System.out.println("Enter an id or 'back' to return.");
        String input = scanner.nextLine();
        String id="";
        String key="";
        while(!Objects.equals(input , "back")){
            id = input.trim();
            for (Map.Entry<String, Student> entry : students.entrySet()){
                if (String.valueOf(entry.getValue().getID()).equals(id)){
                    key = entry.getKey();
                }
            }
            if (Objects.equals(key, "")){
                String output = "No student is found for id="+ id + ".";
                System.out.println(output);
            }else{
                Student s = students.get(key);
                System.out.println(s);
            }
            input = scanner.nextLine();
        }
        waitForInput();
    }

    // Mapping the numbers of enrolled students pro course
    public Map<String, Integer> enrolledStudents(){
        Map<String, Integer> enrolledStudents = new HashMap<>();
        enrolledStudents.put("Java", (int) students.values().stream().filter(s -> s.getJavapoints() > 0).count());
        enrolledStudents.put("DSA" , (int) students.values().stream().filter(s -> s.getDsapoints() > 0).count());
        enrolledStudents.put("Databases" , (int) students.values().stream().filter(s -> s.getDatabasespoints() > 0).count());
        enrolledStudents.put("Spring" , (int) students.values().stream().filter(s -> s.getSpringpoints() > 0).count());
        return enrolledStudents;
    }

    public Set<String> mostPopularCourses(){
        Set<String> mostPopular = new HashSet<>();
        List<Integer> numbers = new ArrayList<Integer>(enrolledStudents().values());
        Collections.sort(numbers);
        for (String s : enrolledStudents().keySet()){
            if (Objects.equals(enrolledStudents().get(s), numbers.get(numbers.size() - 1))){
                mostPopular.add(s);
            }
        }
        return mostPopular;
    }

    public Set<String> leastPopularCourses(){
        Set<String> leastPopular = new HashSet<>();
        List<Integer> numbers = new ArrayList<Integer>(enrolledStudents().values());
        Collections.sort(numbers);
        for (String s : enrolledStudents().keySet()){
            if(!mostPopularCourses().contains(s) && Objects.equals(enrolledStudents().get(s), numbers.get(0))){
                leastPopular.add(s);
            }
        }
        return leastPopular;
    }

    // Mapping the number of submissions for every course
    public Map<String, Integer> submissionsMapping(){
        Map<String, Integer> submissionsNumber = new HashMap<>();
        submissionsNumber.put("Java", submissions[0]);
        submissionsNumber.put("DSA", submissions[1]);
        submissionsNumber.put("Databases", submissions[2]);
        submissionsNumber.put("Spring", submissions[3]);
        return submissionsNumber;
    }

    // Listing the courses with the highest submissions
    public Set<String> highestActiveCourses(){
        Set<String> highestActive = new HashSet<>();
        List<Integer> numbers = new ArrayList<Integer>(submissionsMapping().values());
        Collections.sort(numbers);
        for (String s : submissionsMapping().keySet()){
            if(Objects.equals(submissionsMapping().get(s) , numbers.get(numbers.size() - 1))){
                highestActive.add(s);
            }
        }
        return highestActive;
    }

    // Listing the courses with the lowest submissions
    public Set<String> lowestActiveCourses(){
        Set<String> lowestActive = new HashSet<>();
        List<Integer> numbers = new ArrayList<Integer> (submissionsMapping().values());
        Collections.sort(numbers);
        for (String s : submissionsMapping().keySet()){
            if(!highestActiveCourses().contains(s) && Objects.equals(submissionsMapping().get(s), numbers.get(0))){
                lowestActive.add(s);
            }
        }
        return lowestActive;
    }

    // Mapping the average points for every course
    public Map<String, Double> averagePointProCourse(){
        Map<String, Double> averagePoints = new HashMap<>();
        averagePoints.put("Java", students.values().stream().mapToDouble(Student::getJavapoints).average().orElse(Double.NaN));
        averagePoints.put("DSA", students.values().stream().mapToDouble(Student::getDsapoints).average().orElse(Double.NaN));
        averagePoints.put("Databases", students.values().stream().mapToDouble(Student::getDatabasespoints).average().orElse(Double.NaN));
        averagePoints.put("Spring", students.values().stream().mapToDouble(Student::getSpringpoints).average().orElse(Double.NaN));
        return averagePoints;
    }

    // Listing the courses with the highest average points
    public Set<String> easiestCourses(){
        Set<String> easiestCourses = new HashSet<>();
        List<Double> numbers = new ArrayList<Double>(averagePointProCourse().values());
        Collections.sort(numbers);
        for ( String s : averagePointProCourse().keySet()){
            if (Objects.equals(averagePointProCourse().get(s), numbers.get(numbers.size() - 1))){
                easiestCourses.add(s);
            }
        }
        return easiestCourses;
    }

    // Listing the courses with the lowest average points
    public Set<String> hardestCourses(){
        Set<String> hardestCourses = new HashSet<>();
        List<Double> numbers = new ArrayList<Double>(averagePointProCourse().values());
        Collections.sort(numbers);
        for(String s : averagePointProCourse().keySet()){
            if (Objects.equals(averagePointProCourse().get(s), numbers.get(0)) && !easiestCourses().contains(s)){
                hardestCourses.add(s);
            }
        }
        return hardestCourses;
    }

    public void statistics(){
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        String noAvailableData = "n/a";
        String mostPopular = "Most popular: ";
        String leastPopular = "Least popular: ";
        String highestActivity = "Highest activity: ";
        String lowestActivity = "Lowest activity: ";
        String easiestCourse = "Easiest course: ";
        String hardestCourse = "Hardest course: ";
        if (students.isEmpty()){
            System.out.println(mostPopular + noAvailableData);
            System.out.println(leastPopular + noAvailableData);
            System.out.println(highestActivity + noAvailableData);
            System.out.println(lowestActivity + noAvailableData);
            System.out.println(easiestCourse + noAvailableData);
            System.out.println(hardestCourse + noAvailableData);
        }else{
            int i = 0;
            if (!mostPopularCourses().isEmpty()){
                for (String s : mostPopularCourses()){
                    i++;
                    mostPopular+=s;
                    if (i < mostPopularCourses().size()){
                        mostPopular+=", ";
                    }
                }
            }else{
                mostPopular+=noAvailableData;
            }
            i = 0;
            if (!leastPopularCourses().isEmpty()){
                for (String s : leastPopularCourses()){
                    i++;
                    leastPopular+=s;
                    if (i < leastPopularCourses().size()){
                        leastPopular+=", ";
                    }
                }
            }else{
                leastPopular+=noAvailableData;
            }
            i = 0;
            if(!highestActiveCourses().isEmpty()){
                for (String s : highestActiveCourses()){
                    i++;
                    highestActivity+=s;
                    if (i < highestActiveCourses().size()){
                        highestActivity+=", ";
                    }
                }
            }else{
                highestActivity+=noAvailableData;
            }
            i = 0;
            if(!lowestActiveCourses().isEmpty()){
                for (String s : lowestActiveCourses()){
                    i++;
                    lowestActivity+=s;
                    if (i < lowestActiveCourses().size()){
                        lowestActivity+=", ";
                    }
                }
            }else{
                lowestActivity+=noAvailableData;
            }
            i = 0;
            if(!easiestCourses().isEmpty()){
                for (String s : easiestCourses()){
                    i++;
                    easiestCourse+=s;
                    if (i < easiestCourses().size()){
                        easiestCourse+=", ";
                    }
                }
            }else{
                easiestCourse+=noAvailableData;
            }
            i = 0;
            if (!hardestCourses().isEmpty()){
                for (String s : hardestCourses()){
                    i++;
                    hardestCourse+=s;
                    if (i < hardestCourses().size()){
                        hardestCourse+=", ";
                    }
                }
            }else{
                hardestCourse+=noAvailableData;
            }
            System.out.println(mostPopular);
            System.out.println(leastPopular);
            System.out.println(highestActivity);
            System.out.println(lowestActivity);
            System.out.println(easiestCourse);
            System.out.println(hardestCourse);
        }
        String input = scanner.nextLine().trim().toLowerCase();
        while(!Objects.equals(input , "back")){
            //
            if(Objects.equals(input , "java")){
                javaStatistics();
            }else if(Objects.equals(input , "dsa")){
                dsaStatistics();
            }else if(Objects.equals(input , "databases")){
                databasesStatistics();
            }else if(Objects.equals(input , "spring")){
                springStatistics();
            }else{
                System.out.println("Unknown course.");
            }
            input = scanner.nextLine().trim().toLowerCase();
        }
        waitForInput();
    }

    public void javaStatistics(){
        System.out.println("Java");
        System.out.println("id\t\t\t\t\tpoints\t\t\tcompleted");
        ArrayList<Student> javaStudents = new ArrayList<>(students.values());
        Comparator<Student> byId = Comparator.comparingLong(Student::getID);
        javaStudents.sort(byId);
        Collections.reverse(javaStudents);
        Comparator<Student> byJavaPoints = Comparator.comparingInt(Student::getJavapoints);
        javaStudents.sort(byJavaPoints);
        Collections.reverse(javaStudents);
        if(!javaStudents.isEmpty()){
            for(Student s : javaStudents){
                if (s.getJavapoints()!=0){
                    String output = s.getID()+"\t\t\t"+s.getJavapoints()+"\t\t\t%.1f%%";
                    System.out.printf((output) + "%n",(double) s.getJavapoints() / 6);
                }
            }
        }
    }

    public void dsaStatistics(){
        System.out.println("DSA");
        System.out.println("id\t\t\t\t\tpoints\t\t\tcompleted");
        ArrayList<Student> dsaStudents = new ArrayList<>(students.values());
        Comparator<Student> byId = Comparator.comparingLong(Student::getID);
        dsaStudents.sort(byId);
        Collections.reverse(dsaStudents);
        Comparator<Student> byDsaPoints = Comparator.comparingInt(Student::getDsapoints);
        dsaStudents.sort(byDsaPoints);
        Collections.reverse(dsaStudents);
        if (!dsaStudents.isEmpty()){
            for(Student s : dsaStudents){
                if(s.getDsapoints()!=0){
                    String output = s.getID()+"\t\t\t"+s.getDsapoints()+"\t\t\t%.1f%%";
                    System.out.printf((output) + "%n",(double) s.getDsapoints() / 4);
                }
            }
        }
    }

    public void databasesStatistics(){
        System.out.println("Databases");
        System.out.println("id\t\t\t\t\tpoints\t\t\tcompleted");
        ArrayList<Student> databasesStudents = new ArrayList<>(students.values());
        Comparator<Student> byId = Comparator.comparingLong(Student::getID);
        databasesStudents.sort(byId);
        Collections.reverse(databasesStudents);
        Comparator<Student> byDatabasesPoints = Comparator.comparingInt(Student::getDatabasespoints);
        databasesStudents.sort(byDatabasesPoints);
        Collections.reverse(databasesStudents);
        if (!databasesStudents.isEmpty()){
            for(Student s : databasesStudents){
                if (s.getDatabasespoints() != 0){
                    String output = s.getID()+"\t\t\t"+s.getDatabasespoints()+"\t\t\t%.1f%%";
                    System.out.printf((output) + "%n",(double) s.getDatabasespoints() / 4.8);
                }
            }
        }
    }

    public void springStatistics(){
        System.out.println("Spring");
        System.out.println("id\t\t\t\t\tpoints\t\t\tcompleted");
        ArrayList<Student> springStudents = new ArrayList<>(students.values());
        Comparator<Student> byId = Comparator.comparingLong(Student::getID);
        springStudents.sort(byId);
        Collections.reverse(springStudents);
        Comparator<Student> bySpringPoints = Comparator.comparingInt(Student::getSpringpoints);
        springStudents.sort(bySpringPoints);
        Collections.reverse(springStudents);
        if (!springStudents.isEmpty()){
            for(Student s : springStudents){
                if (s.getSpringpoints()!=0){
                    String output = s.getID()+"\t\t\t"+s.getSpringpoints()+"\t\t\t%.1f%%";
                    System.out.printf((output) + "%n",(double) s.getSpringpoints() / 5.5);
                }
            }
        }
    }

    public void notifySuccess(){
        Set<Student> notifiedStudents = new HashSet<>();
        for (Student s : students.values()){
            // notify the student if he reached the max points for DSA Course
            if (!s.isNotifiedDsa()){
                String email = "";
                if (s.getDsapoints() == maxPoints[1]){
                    for (String str : students.keySet()){
                        if(students.get(str) == s){
                            email = str;
                        }
                    }
                    System.out.println("To: "+ email);
                    System.out.println("Re: Your Learning Progress");
                    System.out.println("Hello, " + s.getFirstname()+" "+s.getLastname()+"! You have accomplished our DSA course!");
                    s.setNotifiedDsa(true);
                    notifiedStudents.add(s);
                }
            }
            // notify the student if he reached the max points for Java Course
            if (!s.isNotifiedJava()){
                String email = "";
                if (s.getJavapoints() == maxPoints[0]){
                    for (String str : students.keySet()){
                        if(students.get(str) == s){
                            email = str;
                        }
                    }
                    System.out.println("To: "+ email);
                    System.out.println("Re: Your Learning Progress");
                    System.out.println("Hello, " + s.getFirstname()+" "+s.getLastname()+"! You have accomplished our Java course!");
                    s.setNotifiedJava(true);
                    notifiedStudents.add(s);
                }
            }
            // notify the student if he reached the max points for Databases Course
            if (!s.isNotifiedDatabases()){
                String email = "";
                if (s.getDatabasespoints() == maxPoints[2]){
                    for (String str : students.keySet()){
                        if(students.get(str) == s){
                            email = str;
                        }
                    }
                    System.out.println("To: "+ email);
                    System.out.println("Re: Your Learning Progress");
                    System.out.println("Hello, " + s.getFirstname()+" "+s.getLastname()+"! You have accomplished our Databases course!");
                    s.setNotifiedDatabases(true);
                    notifiedStudents.add(s);
                }
            }
            // notify the student if he reached the max points for Spring Course
            if (!s.isNotifiedSpring()){
                String email = "";
                if (s.getSpringpoints() == maxPoints[3]){
                    for (String str : students.keySet()){
                        if(students.get(str) == s){
                            email = str;
                        }
                    }
                    System.out.println("To: "+ email);
                    System.out.println("Re: Your Learning Progress");
                    System.out.println("Hello, " + s.getFirstname()+" "+s.getLastname()+"! You have accomplished our Spring course!");
                    s.setNotifiedSpring(true);
                    notifiedStudents.add(s);
                }
            }
        }
        System.out.println("Total "+notifiedStudents.size()+" students have been notified.");
    }

}
