package br.edu.ifsp.rennan;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

public class TarGzCompression implements CompressionStrategy {
    @Override
    public void compress(List<String> files, String outputFile) {
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             GzipCompressorOutputStream gzos = new GzipCompressorOutputStream(fos);
             TarArchiveOutputStream taos = new TarArchiveOutputStream(gzos)) {

            taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);

            for (String filePath : files) {
                addToTarGz(taos, new File(filePath), ""); // Real compression logic function
            }

            System.out.println("Compactação TAR.GZ concluída: " + outputFile);
        } catch (IOException e) {
            System.err.println("Erro na compactação TAR.GZ: " + e.getMessage());
        }
    }

    private void addToTarGz(TarArchiveOutputStream taos, File file, String parent) throws IOException {
        String entryName = parent + file.getName();
        TarArchiveEntry entry = new TarArchiveEntry(file, entryName);
        taos.putArchiveEntry(entry);

        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                IOUtils.copy(fis, taos);
            }
        }
        taos.closeArchiveEntry();

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToTarGz(taos, child, entryName + "/");
                }
            }
        }
    }
}