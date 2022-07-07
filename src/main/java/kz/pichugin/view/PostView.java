package kz.pichugin.view;

import kz.pichugin.controller.PostController;
import kz.pichugin.controller.WriterController;
import kz.pichugin.model.Post;
import kz.pichugin.model.PostStatus;
import kz.pichugin.model.Writer;
import kz.pichugin.util.CheckCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PostView implements View {
    Scanner scanner = new Scanner(System.in);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    boolean isAppRunning = true;
    private final PostController postController = new PostController();
    private final WriterController writerController = new WriterController();

    @Override
    public void run() {
        while (isAppRunning) {
            try {
                int commandNumber;
                menu();
                commandNumber = scanner.nextInt();
                switch (commandNumber) {
                    case 1 -> savePostView();
                    case 2 -> updatePostView();
                    case 3 -> getPostByIdView();
                    case 4 -> deletePostByIdView();
                    case 5 -> getAllPostView();
                    case 6 -> isAppRunning = false;
                    default -> System.out.println(CheckCommand.ERR_COMMAND);
                }
            } catch (InputMismatchException e) {
                System.out.println(CheckCommand.ERR_INPUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void savePostView() throws IOException {
        System.out.print("Input content: ");
        String content = reader.readLine();
        System.out.print("Input writer ID: ");
        Long writerId = scanner.nextLong();
        Writer writer = writerController.getById(writerId);
        if (writer == null) {
            return;
        }
        PostStatus postStatus = getPostStatus();
        Post post = new Post(content, writer);
        post.setPostStatus(postStatus);
        postController.save(post);
    }

    public void updatePostView() throws IOException {
        System.out.print("Input post ID:");
        Long id = scanner.nextLong();
        System.out.print("Input new content: ");
        String content = reader.readLine();

        PostStatus postStatus = getPostStatus();
        Post post = new Post(id, content);
        post.setPostStatus(postStatus);
        postController.update(post);
    }

    private PostStatus getPostStatus() {
        int index;
        do {
            System.out.println("Select post status: ");
            System.out.println("1: ACTIVE");
            System.out.println("2: UNDER_REVIEW");
            index = scanner.nextInt();
        } while (index < 1 || index > 2);
        return PostStatus.values()[index-1];
    }

    public void getPostByIdView() {
        System.out.print("Input post ID:");
        Long id = scanner.nextLong();
        Post post = postController.getById(id);
        System.out.println(post);
    }

    public void deletePostByIdView() {
        System.out.print("Input post ID: ");
        Long id = scanner.nextLong();
        postController.deleteById(id);
        System.out.println("Deleted!");
    }

    public void getAllPostView() {
        List<Post> postList = postController.getAll();
        postList.forEach(System.out::println);
    }

    private void menu() {
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.println("Menu for Writers:");
        System.out.println("1. Save post");
        System.out.println("2. Update post");
        System.out.println("3. Get post by ID");
        System.out.println("4. Delete post by ID");
        System.out.println("5. Get all posts");
        System.out.println("6. Return");
        System.out.println(CheckCommand.MENU_SPLITTER);
        System.out.print("Enter command (1-6): ");
    }
}
