
package studyVault;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NoteManager {

    private final Path notesDirectory = Path.of("notes");
    private final Path backupsDirectory = Path.of("backups");


    public NoteManager() throws IOException {
        Files.createDirectories(notesDirectory);
        Files.createDirectories(backupsDirectory);
    }

    private Path getNotePath(String filename) {

        if (filename == null || filename.isBlank()
                || filename.contains("/")
                || filename.contains("\\")
                || filename.equals("..")
                || filename.contains("..")) {

            throw new IllegalArgumentException("Invalid filename.");
        }

        if (!filename.matches("[\\p{L}\\p{N}_ -]+(\\.txt)?")) {
            throw new IllegalArgumentException("Use letters, numbers, spaces, _ or - only.");
        }

        String name = filename.endsWith(".txt")
                ? filename : filename + ".txt";

        return notesDirectory.resolve(name);
    }


    public void createNote(String filename, String content,
                           boolean overwrite) throws IOException {

        Path path = getNotePath(filename);

        if (Files.exists(path) && !overwrite) {
            throw new FileAlreadyExistsException(path.toString());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write(content);
        }
    }

    public List<String> listNotes() throws IOException {

        List<String> notes = new ArrayList<>();

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(notesDirectory, "*.txt")) {

            for (Path path : stream) {
                notes.add(path.getFileName().toString());
            }
        }

        return notes;
    }

    public String openNote(String filename) throws IOException {

        Path path = getNotePath(filename);

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }

        return content.toString();
    }

    public void appendNote(String filename, String content)
            throws IOException {

        Path path = getNotePath(filename);

        if (!Files.exists(path)) {
            throw new NoSuchFileException(path.toString());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {

            writer.newLine();
            writer.write(content);
        }
    }

    public void searchNotes(String keyword) throws IOException {

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be blank.");
        }

        for (String filename : listNotes()) {

            Path path = getNotePath(filename);

            try (BufferedReader reader = Files.newBufferedReader(
                    path, StandardCharsets.UTF_8)) {

                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {

                    lineNumber++;

                    if (line.toLowerCase().contains(
                            keyword.toLowerCase())) {

                        System.out.println(
                                filename + " | Line " + lineNumber
                                        + " | " + line
                        );
                    }
                }
            }
        }
    }

    public void deleteNote(String filename) throws IOException {

        Path path = getNotePath(filename);

        Files.delete(path);
    }

    public void exportNote(String filename) throws IOException {

        Path source = getNotePath(filename);

        if (!Files.exists(source)) {
            throw new NoSuchFileException(source.toString());
        }

        Path destination =
                backupsDirectory.resolve(source.getFileName());

        try (
                FileInputStream input = new FileInputStream(source.toFile());
                FileOutputStream output = new FileOutputStream(destination.toFile())
        ) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }


    public void restoreNote(String filename) throws IOException {

        Path source = backupsDirectory.resolve(
                getNotePath(filename).getFileName()
        );

        Path destination = getNotePath(filename);

        if (!Files.exists(source)) {
            throw new NoSuchFileException(source.toString());
        }

        try (
                FileInputStream input = new FileInputStream(source.toFile());
                FileOutputStream output = new FileOutputStream(
                        destination.toFile(), false)
        ) {

            if (Files.exists(destination)) {
                throw new FileAlreadyExistsException(destination.toString());
            }

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }
    }
}
