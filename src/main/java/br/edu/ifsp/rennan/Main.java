package br.edu.ifsp.rennan;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> arquivos = Arrays.asList(
                getResourcePath("arquivos_para_compactar/emoji-joinha.jpeg"),
                getResourcePath("arquivos_para_compactar/logo-ifpng.png")
        );

        FileCompressor compressor = new FileCompressor();

        // Compactar como ZIP
        compressor.setStrategy(new ZipCompression());
        compressor.compressFiles(arquivos, "meus_arquivos.zip");

        // Compactar como 7Z
        compressor.setStrategy(new SevenZipCompression());
        compressor.compressFiles(arquivos, "meus_arquivos.7z");

        // Compactar como TAR.GZ
        compressor.setStrategy(new TarGzCompression());
        compressor.compressFiles(arquivos, "meus_arquivos.tar.gz");
    }

    private static String getResourcePath(String resourceName) {
        // Obtém o caminho absoluto do arquivo no classpath
        return Main.class.getClassLoader().getResource(resourceName).getPath();
    }
}