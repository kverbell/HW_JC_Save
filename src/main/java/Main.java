import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(50, 3,25, 7.8);
        saveGame("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save1.dat", gameProgress1);

        GameProgress gameProgress2 = new GameProgress(100, 1,12, 1.5);
        saveGame("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save2.dat", gameProgress2);

        GameProgress gameProgress3 = new GameProgress(98, 5, 50, 16.1);
        saveGame("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save3.dat", gameProgress3);

        ArrayList<String> filesToZip = new ArrayList<>();
        filesToZip.add("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save1.dat");
        filesToZip.add("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save2.dat");
        filesToZip.add("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\save3.dat");
        zipFiles("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\zip.zip", filesToZip);
        deleteNonArchivedFiles("E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames\\zip.zip", "E:\\Users\\Anna\\IdeaProjects\\HW_JC_Setup\\Games\\savegames");
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
            System.out.println("Игра сохранена в файл: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, ArrayList<String> filesToZip) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            for (String filePath : filesToZip) {
                File fileToZip = new File(filePath);
                if (fileToZip.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, length);
                    }
                    fileInputStream.close();
                }
            }
            System.out.println("Файлы упакованы в архив: " + zipFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNonArchivedFiles(String archivePath, String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!file.getAbsolutePath().equals(archivePath)) {
                    file.delete();
                }
            }
            System.out.println("Файлы вне архива успешно удалены.");
        }
    }
}
