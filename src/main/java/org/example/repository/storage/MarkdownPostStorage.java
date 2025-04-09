package org.example.repository.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.example.domain.Post;

public class MarkdownPostStorage implements FileStorage<Post> {
    private final String fileName;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public MarkdownPostStorage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(List<Post> posts) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {

            writer.write("id,title,created_at\n");

            for (Post post : posts) {
                writer.write(post.getId() + "," +
                        escapeComma(post.getTitle()) + "," +
                        post.getCreatedAt().format(DATE_FORMATTER) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다: " + fileName, e);
        }
    }

    @Override
    public List<Post> load() {
        List<Post> posts = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return posts;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                List<String> parts = parseCSV(line);
                try {
                    int id = Integer.parseInt(parts.get(0).trim());
                    String title = parts.get(1).trim();
                    Post post;

                    try {
                        LocalDateTime createdAt = LocalDateTime.parse(parts.get(2).trim(), DATE_FORMATTER);
                        post = new Post(id, title, createdAt);
                    } catch (DateTimeParseException e) {
                        throw new RuntimeException("유효하지 않은 날짜 포맷입니다: " + parts.get(2));
                    }

                    posts.add(post);
                } catch (NumberFormatException e) {
                    System.err.println("유효하지 않은 ID 포맷입니다: " + line);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("유효하지 않은 게시글 포맷입니다: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("게시물을 불러오는데 실패했습니다: " + fileName, e);
        }

        return posts;
    }

    private List<String> parseCSV(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }

        result.add(field.toString());
        return result;
    }

    private String escapeComma(String value) {
        if (value.contains(",")) {
            return "\"" + value + "\"";
        }
        return value;
    }
}
