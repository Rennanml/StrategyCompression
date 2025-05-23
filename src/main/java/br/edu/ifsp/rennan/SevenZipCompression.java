package br.edu.ifsp.rennan;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class SevenZipCompression implements CompressionStrategy {
    @Override
    public void compress(List<String> files, String outputFile) {
        try (SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(outputFile))) {
            for (String filePath : files) {
                addTo7z(filePath, sevenZOutput); // Real compression logic function
            }
            System.out.println("Compactação 7Z concluída: " + outputFile);
        } catch (IOException e) {
            System.err.println("Erro na compactação 7Z: " + e.getMessage());
        }
    }

    private void addTo7z(String filePath, SevenZOutputFile sevenZOutput) throws IOException {
        File file = new File(filePath);
        SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(file, file.getName());
        sevenZOutput.putArchiveEntry(entry);

        if (file.isFile()) {
            byte[] content = Files.readAllBytes(file.toPath());
            sevenZOutput.write(content);
        }

        sevenZOutput.closeArchiveEntry();
    }
}