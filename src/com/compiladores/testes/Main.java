package com.compiladores.testes;

import com.compiladores.lexico.AnalisadorLexico;
import com.compiladores.lexico.Token;
import com.compiladores.lexico.Tokens;

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
            while(token.getToken() != Tokens.EOF)
            {
                System.out.println(token.getToken() + " " + token.getLexema());
                token = analisador.getToken();
            }
            System.out.println(token.getToken() + " " + token.getLexema());

        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
