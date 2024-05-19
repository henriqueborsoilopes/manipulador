package br.unipar.manipulador.modelo.util;

import br.unipar.manipulador.modelo.servico.excecao.ArquivoExcecao;
import java.io.File;
import java.io.IOException;

public class ArquivoUtil {
        
    public static File gerarArquivoCSV(File pasta, boolean substituir) {
        pasta = new File(pasta, "dados.csv");
        if (!pasta.exists()) {
            try {
                pasta.createNewFile();
                return pasta;
            } catch (IOException e) {
                throw new ArquivoExcecao("Erro ao criar o arquivo: " + e.getMessage(), false);
            }
        } else if (substituir) {
            return pasta;
        }
        throw new ArquivoExcecao("Arquivo já existe. Deseja substituir?", true);
    }
}
