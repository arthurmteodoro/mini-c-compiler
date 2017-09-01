package com.compiladores.lexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico
{
    private BufferedReader arquivo;
    private String arquivoNome;
    private String linha;
    private int numeroLinha;
    private int posiçãoLinha;

    AnalisadorLexico()
    {
        this.arquivo = null;
        this.arquivoNome = "";
        this.linha = "";
        this.numeroLinha = -1;
        this.posiçãoLinha = -1;
    }

    AnalisadorLexico(String nomeArquivo) throws FileNotFoundException
    {
        this.arquivo = new BufferedReader(new FileReader(nomeArquivo));
        this.arquivoNome = nomeArquivo;
        this.linha = "";
        this.numeroLinha = -1;
        this.posiçãoLinha = -1;
    }

    public void abreArquivo(String nomeArquivo) throws FileNotFoundException
    {
        this.arquivo = new BufferedReader(new FileReader(nomeArquivo));
        this.arquivoNome = nomeArquivo;
        this.linha = "";
        this.numeroLinha = -1;
        this.posiçãoLinha = -1;
    }

    public void fechaArquivo() throws IOException
    {
        this.arquivo.close();
        this.linha = "";
        this.numeroLinha = -1;
        this.posiçãoLinha = -1;
    }

    public Token getToken()
    {
        return new Token(null, null, 0);
    }
}
