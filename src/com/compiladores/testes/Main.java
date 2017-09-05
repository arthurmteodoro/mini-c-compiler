package com.compiladores.testes;

import com.compiladores.lexico.AnalisadorLexico;
import com.compiladores.lexico.Token;

public class Main
{
    public static void main(String args[])
    {
        AnalisadorLexico analisador = new AnalisadorLexico();
        try
        {
            analisador.abreArquivo("teste.txt");

            Token token;

            token = analisador.getToken();
            token = analisador.getToken();
            token = analisador.getToken();
            token = analisador.getToken();
            token = analisador.getToken();

        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
