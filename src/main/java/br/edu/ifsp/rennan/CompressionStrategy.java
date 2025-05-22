package br.edu.ifsp.rennan;

import java.util.List;

public interface CompressionStrategy {
    void compress(List<String> files, String outputFile);
}