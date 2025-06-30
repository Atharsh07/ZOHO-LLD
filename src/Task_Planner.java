
import java.util.*;
import java.text.SimpleDateFormat;


enum TaskStatus {
    TODO, IN_PROGRESS, DONE
}

enum TaskType {
    STORY, FEATURE, BUG
}

abstract class Task {
    protected static int idCounter = 1;
    protected int id;
    protected String title;
    protected TaskStatus status;
    protected String assignee;
    protected Date dueDate;

    public Task(String title, String assignee, Date dueDate) {
        this.id = idCounter++;
        this.title = title;
        this.status = TaskStatus.TODO;
        this.assignee = assignee;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }

    public String getAssignee() { return assignee; }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public boolean isDelayed() {
        return status != TaskStatus.DONE && new Date().after(dueDate);
    }

    public abstract TaskType getType();

    public String summary() {
        return id + " [" + getType() + "] " + title + " - " + status + " - Assigned to: " + assignee;
    }
}

class Story extends Task {
    private final List<Task> subTasks = new ArrayList<>();

    public Story(String title, String assignee, Date dueDate) {
        super(title, assignee, dueDate);
    }

    public void addSubTask(Task task) {
        subTasks.add(task);
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    @Override
    public TaskType getType() {
        return TaskType.STORY;
    }
}

class Feature extends Task {
    public Feature(String title, String assignee, Date dueDate) {
        super(title, assignee, dueDate);
    }

    @Override
    public TaskType getType() {
        return TaskType.FEATURE;
    }
}

class Bug extends Task {
    public Bug(String title, String assignee, Date dueDate) {
        super(title, assignee, dueDate);
    }

    @Override
    public TaskType getType() {
        return TaskType.BUG;
    }
}


class Sprint {
    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Sprint(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int taskId) {
        tasks.removeIf(t -> t.getId() == taskId);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void printDetails() {
        System.out.println("Sprint: " + name);
        for (Task task : tasks) {
            System.out.println(" - " + task.summary());
            if (task instanceof Story s) {
                for (Task sub : s.getSubTasks()) {
                    System.out.println("   -> SubTask: " + sub.summary());
                }
            }
        }
    }
}


class TaskManager {
    private final Map<Integer, Task> allTasks = new HashMap<>();
    private final Map<String, Sprint> sprints = new HashMap<>();

    public Task createTask(TaskType type, String title, String assignee, Date dueDate) {
        Task task = switch (type) {
            case STORY -> new Story(title, assignee, dueDate);
            case FEATURE -> new Feature(title, assignee, dueDate);
            case BUG -> new Bug(title, assignee, dueDate);
        };
        allTasks.put(task.getId(), task);
        return task;
    }

    public void addSubTaskToStory(int storyId, Task subtask) {
        Task task = allTasks.get(storyId);
        if (task instanceof Story s) {
            s.addSubTask(subtask);
            allTasks.put(subtask.getId(), subtask);
        }
    }

    public void changeStatus(int taskId, TaskStatus status) {
        Task task = allTasks.get(taskId);
        if (task != null) {
            task.setStatus(status);
        }
    }

    public void createSprint(String sprintName) {
        sprints.put(sprintName, new Sprint(sprintName));
    }

    public void addTaskToSprint(String sprintName, int taskId) {
        Task task = allTasks.get(taskId);
        Sprint sprint = sprints.get(sprintName);
        if (sprint != null && task != null) {
            sprint.addTask(task);
        }
    }

    public void removeTaskFromSprint(String sprintName, int taskId) {
        Sprint sprint = sprints.get(sprintName);
        if (sprint != null) {
            sprint.removeTask(taskId);
        }
    }

    public void printDelayedTasks() {
        System.out.println("Delayed Tasks:");
        for (Task t : allTasks.values()) {
            if (t.isDelayed()) {
                System.out.println(" - " + t.summary());
            }
        }
    }

    public void printSprintDetails(String sprintName) {
        Sprint sprint = sprints.get(sprintName);
        if (sprint != null) {
            sprint.printDetails();
        }
    }

    public void printTasksByAssignee(String assignee) {
        System.out.println("Tasks assigned to " + assignee + ":");
        for (Task task : allTasks.values()) {
            if (task.getAssignee().equals(assignee)) {
                System.out.println(" - " + task.summary());
            }
        }
    }
}





class JiraApp {
    public static void main(String[] args) throws Exception {
        TaskManager manager = new TaskManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Create Tasks
        Task s1 = manager.createTask(TaskType.STORY, "Build login", "Alice", sdf.parse("2024-06-15"));
        Task f1 = manager.createTask(TaskType.FEATURE, "OAuth support", "Bob", sdf.parse("2024-07-01"));
        Task b1 = manager.createTask(TaskType.BUG, "Login button bug", "Alice", sdf.parse("2024-06-10"));

        // Subtasks under story
        manager.addSubTaskToStory(s1.getId(), manager.createTask(TaskType.BUG, "Fix redirect issue", "Alice", sdf.parse("2024-06-12")));

        // Change status
        manager.changeStatus(f1.getId(), TaskStatus.IN_PROGRESS);

        // Sprints
        manager.createSprint("Sprint-1");
        manager.addTaskToSprint("Sprint-1", s1.getId());
        manager.addTaskToSprint("Sprint-1", f1.getId());

        // Reports
        manager.printSprintDetails("Sprint-1");
        manager.printTasksByAssignee("Alice");
        manager.printDelayedTasks(); // Depends on current date
    }
}
