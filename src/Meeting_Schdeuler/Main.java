package Meeting_Schdeuler;


import java.util.*;

class Meeting {
    private int start, end;
    private Room room;

    public Meeting(int start, int end, Room room) {
        this.start = start;
        this.end = end;
        this.room = room;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

class Room {
    private String name;
    private Map<Integer, List<Meeting>> calendar;

    public Room(String name) {
        this.name = name;
        this.calendar = new HashMap<>();
    }

    public boolean book(int day, int start, int end) {
        List<Meeting> meetings = calendar.getOrDefault(day, new ArrayList<>());

        for (Meeting m : meetings) {
            if (m.getStart() < end && start < m.getEnd()) {
                return false; // Overlap
            }
        }

        meetings.add(new Meeting(start, end, this));
        calendar.put(day, meetings);
        return true;
    }

    public String getName() {
        return name;
    }
}

class Scheduler {
    private List<Room> rooms;

    public Scheduler(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String book(int day, int start, int end) {
        for (Room room : rooms) {
            if (room.book(day, start, end)) {
                return room.getName();
            }
        }
        return "No rooms available";
    }
}

class MeetingSchedulerApp {
    public static void main(String[] args) {
        Room room1 = new Room("Atlas");
        Room room2 = new Room("Nexus");
        Room room3 = new Room("HolyCow");

        List<Room> rooms = Arrays.asList(room1, room2, room3);
        Scheduler scheduler = new Scheduler(rooms);

        System.out.println(scheduler.book(15, 2, 5));  // Atlas
        System.out.println(scheduler.book(15, 5, 8));  // Atlas
        System.out.println(scheduler.book(15, 4, 8));  // Nexus
        System.out.println(scheduler.book(15, 3, 6));  // HolyCow
        System.out.println(scheduler.book(15, 7, 8));  // HolyCow
        System.out.println(scheduler.book(16, 6, 9));  // Atlas
    }
}

