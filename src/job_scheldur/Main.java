package job_scheldur;

// -------------------------
// JOB SCHEDULER SYSTEM (WITH MULTIPLE ALGORITHMS)
// -------------------------
import java.util.*;

class Job {
    String name;
    int duration;
    String priority; // P0 > P1 > P2
    int deadline;
    String userType; // Root > Admin > User

    public Job(String name, int duration, String priority, int deadline, String userType) {
        this.name = name;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return name;
    }
}
class JobScheduler {
    static final List<String> PRIORITY_ORDER = Arrays.asList("P0", "P1", "P2");
    static final List<String> USER_ORDER = Arrays.asList("Root", "Admin", "User");

    public static void main(String[] args) {
        List<Job> jobs = List.of(
                new Job("Job1", 4, "P1", 10, "Admin"),
                new Job("Job2", 2, "P0", 7, "User"),
                new Job("Job3", 3, "P2", 8, "Root"),
                new Job("Job4", 1, "P1", 6, "Admin"),
                new Job("Job5", 5, "P0", 12, "User")
        );

        int threads = 2;

        System.out.println("\n--- Shortest Job First ---");
        scheduleSJF(jobs, threads);

        System.out.println("\n--- First Come First Serve ---");
        scheduleFCFS(jobs, threads);

        System.out.println("\n--- Fixed Priority Scheduling ---");
        scheduleFPS(jobs, threads);

        System.out.println("\n--- Earliest Deadline First ---");
        scheduleEDF(jobs, threads);
    }

    static void scheduleSJF(List<Job> jobs, int threads) {
        List<Job> list = new ArrayList<>(jobs);
        list.sort(Comparator.comparingInt((Job j) -> j.duration)
                .thenComparingInt(j -> PRIORITY_ORDER.indexOf(j.priority)));
        distribute(list, threads);
    }

    static void scheduleFCFS(List<Job> jobs, int threads) {
        distribute(new ArrayList<>(jobs), threads);
    }

    static void scheduleFPS(List<Job> jobs, int threads) {
        List<Job> list = new ArrayList<>(jobs);
        list.sort(Comparator.comparingInt((Job j) -> PRIORITY_ORDER.indexOf(j.priority))
                .thenComparingInt(j -> USER_ORDER.indexOf(j.userType))
                .thenComparing((Job j) -> -j.duration));
        distribute(list, threads);
    }

    static void scheduleEDF(List<Job> jobs, int threads) {
        List<Job> list = new ArrayList<>(jobs);
        int currentTime = 0;
        List<Job> validJobs = new ArrayList<>();

        for (Job j : list) {
            if (currentTime + j.duration <= j.deadline) {
                validJobs.add(j);
                currentTime += j.duration;
            }
        }

        validJobs.sort(Comparator.comparingInt((Job j) -> j.deadline)
                .thenComparingInt(j -> PRIORITY_ORDER.indexOf(j.priority))
                .thenComparingInt(j -> j.duration));
        distribute(validJobs, threads);
    }

    static void distribute(List<Job> jobs, int threads) {
        List<List<Job>> schedule = new ArrayList<>();
        for (int i = 0; i < threads; i++) schedule.add(new ArrayList<>());

        for (int i = 0; i < jobs.size(); i++) {
            schedule.get(i % threads).add(jobs.get(i));
        }

        for (int i = 0; i < schedule.size(); i++) {
            System.out.println("Thread " + (i + 1) + ": " + schedule.get(i));
        }
    }
}

