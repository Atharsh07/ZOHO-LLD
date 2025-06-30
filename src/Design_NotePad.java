import java.util.*;

class Clipboard {
    private List<String> buffer = new ArrayList<>();

    public void copy(List<String> text) {
        buffer.clear();
        buffer.addAll(text);
    }

    public List<String> getContent() {
        return new ArrayList<>(buffer);
    }
}

class TextEditor {
    private final List<String> lines = new ArrayList<>();
    private final Clipboard clipboard = new Clipboard();
    private final UndoManager undoManager = new UndoManager();

    public void display() {
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }
    }

    public void display(int n, int m) {
        for (int i = n - 1; i < m && i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }
    }

    public void insert(int n, String text) {
        undoManager.execute(new InsertCommand(this, n, text));
    }

    public void delete(int n) {
        undoManager.execute(new DeleteCommand(this, n, n));
    }

    public void delete(int n, int m) {
        undoManager.execute(new DeleteCommand(this, n, m));
    }

    public void copy(int n, int m) {
        clipboard.copy(lines.subList(n - 1, Math.min(m, lines.size())));
    }

    public void paste(int n) {
        undoManager.execute(new PasteCommand(this, n, clipboard.getContent()));
    }

    public void undo() {
        undoManager.undo();
    }

    public void redo() {
        undoManager.redo();
    }

    // Package-private access for Command classes
    void insertLines(int index, List<String> content) {
        lines.addAll(index, content);
    }

    List<String> removeLines(int from, int to) {
        List<String> removed = new ArrayList<>();
        for (int i = from; i <= to && from < lines.size(); i++) {
            removed.add(lines.remove(from));
        }
        return removed;
    }
}

interface Command {
    void execute();
    void undo();
}


class InsertCommand implements Command {
    private final TextEditor editor;
    private final int line;
    private final String text;

    public InsertCommand(TextEditor editor, int line, String text) {
        this.editor = editor;
        this.line = line - 1;
        this.text = text;
    }

    @Override
    public void execute() {
        editor.insertLines(line, List.of(text));
    }

    @Override
    public void undo() {
        editor.removeLines(line, line);
    }
}


class DeleteCommand implements Command {
    private final TextEditor editor;
    private final int from, to;
    private List<String> deletedLines;

    public DeleteCommand(TextEditor editor, int from, int to) {
        this.editor = editor;
        this.from = from - 1;
        this.to = to - 1;
    }

    @Override
    public void execute() {
        deletedLines = editor.removeLines(from, to);
    }

    @Override
    public void undo() {
        editor.insertLines(from, deletedLines);
    }
}


class PasteCommand implements Command {
    private final TextEditor editor;
    private final int line;
    private final List<String> content;

    public PasteCommand(TextEditor editor, int line, List<String> content) {
        this.editor = editor;
        this.line = line - 1;
        this.content = content;
    }

    @Override
    public void execute() {
        editor.insertLines(line, content);
    }

    @Override
    public void undo() {
        editor.removeLines(line, line + content.size() - 1);
    }
}


class UndoManager {
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}


class NotepadApp {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        editor.insert(1, "Hello World!");
        editor.insert(2, "This is line two.");
        editor.insert(3, "This is line three.");
        editor.display();

        System.out.println("\nAfter deleting line 2:");
        editor.delete(2);
        editor.display();

        System.out.println("\nUndo deletion:");
        editor.undo();
        editor.display();

        System.out.println("\nCopy line 2 to 3 and paste at line 1:");
        editor.copy(2, 3);
        editor.paste(1);
        editor.display();

        System.out.println("\nUndo paste:");
        editor.undo();
        editor.display();
    }
}

