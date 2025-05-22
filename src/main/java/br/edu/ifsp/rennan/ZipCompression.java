package br.edu.ifsp.rennan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompression implements CompressionStrategy {
    @Override
    public void compress(List<String> files, String outputFile) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile))) {
            for (String filePath : files) {
                addToZip(filePath, zos);
            }
            System.out.println("Compactação ZIP concluída: " + outputFile);
        } catch (IOException e) {
            System.err.println("Erro na compactação ZIP: " + e.getMessage());
        }
    }

    private void addToZip(String filePath, ZipOutputStream zos) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        fis.close();
        zos.closeEntry();
    }
}