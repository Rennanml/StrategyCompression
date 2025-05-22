package br.edu.ifsp.rennan;

import java.util.List;

public class FileCompressor {
    private CompressionStrategy strategy;

    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void compressFiles(List<String> files, String outputFile) {
        if (strategy == null) {
            throw new IllegalStateException("Estratégia de compressão não definida");
        }
        strategy.compress(files, outputFile);
    }
}