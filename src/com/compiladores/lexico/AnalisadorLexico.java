package com.compiladores.lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AnalisadorLexico
{
    private BufferedReader arquivo;
    private String arquivoNome;
    private String linha;
    private int numeroLinha;
    private int posicaoLinha;

    public AnalisadorLexico()
    {
        this.arquivo = null;
        this.arquivoNome = "";
        this.linha = "";
        this.numeroLinha = 0;
        this.posicaoLinha = 0;
    }

    public AnalisadorLexico(String nomeArquivo) throws IOException
    {
        this.arquivo = new BufferedReader(new FileReader(nomeArquivo));
        this.arquivoNome = nomeArquivo;
        this.linha = arquivo.readLine();
        this.numeroLinha = 1;
        this.posicaoLinha = 0;
    }

    public void abreArquivo(String nomeArquivo) throws IOException
    {
        this.arquivo = new BufferedReader(new FileReader(nomeArquivo));
        this.arquivoNome = nomeArquivo;
        this.linha = arquivo.readLine();
        this.numeroLinha = 1;
        this.posicaoLinha = 0;
    }

    public void fechaArquivo() throws IOException
    {
        this.arquivo.close();
        this.linha = "";
        this.numeroLinha = 0;
        this.posicaoLinha = 0;
    }

    public char getChar()
    {
        if(linha == null)
            return 0;

        if(linha.equals("") || posicaoLinha == linha.length())
        {
            try
            {
                linha = arquivo.readLine();
                posicaoLinha = 0;
                numeroLinha++;
                return '\n';
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        char ch = linha.charAt(posicaoLinha);
        posicaoLinha++;
        return ch;
    }

    private boolean verificaProximoChar(char ch)
    {
        char nextChar = getChar();
        return ch == nextChar;
    }

    public Token getToken()
    {
        Token token = null;
        EstadosAutomato estado = EstadosAutomato.NAO_ALFANUMERICO;
        String lexema = "";
        char ch;

        while(token == null)
        {
            switch(estado)
            {
                case NAO_ALFANUMERICO:
                    ch = getChar();
                    switch(ch)
                    {
                        case ';':
                            token =  new Token(Tokens.PTOVIR, ";", numeroLinha);
                            break;
                        case ',':
                            token = new Token(Tokens.VIR, ",", numeroLinha);
                            break;
                        case '{':
                            token = new Token(Tokens.ABRECHAVE, "{", numeroLinha);
                            break;
                        case '}':
                            token = new Token(Tokens.FECHACHAVE, "}", numeroLinha);
                            break;
                        case '(':
                            token = new Token(Tokens.ABREPAR, "(", numeroLinha);
                            break;
                        case ')':
                            token = new Token(Tokens.FECHAPAR, "(", numeroLinha);
                            break;
                        case '[':
                            token = new Token(Tokens.ABRECOL, "[", numeroLinha);
                            break;
                        case ']':
                            token = new Token(Tokens.FECHACOL, "]", numeroLinha);
                            break;
                        case '*':
                            token = new Token(Tokens.OPMULT, "*", numeroLinha);
                            break;
                        case '=':
                            if(verificaProximoChar('='))
                                token = new Token(Tokens.OPIGUAL, "==", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPATRIB, "=", numeroLinha);
                            }
                            break;
                        case '|':
                            if(verificaProximoChar('|'))
                                token = new Token(Tokens.OPORLOG, "||", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPORBIN, "|", numeroLinha);
                            }
                            break;
                        case '&':
                            if(verificaProximoChar('&'))
                                token = new Token(Tokens.OPANDLOG, "&&", numeroLinha);
                            else
                            {
                                numeroLinha--;
                                token = new Token(Tokens.OPANDBIN, "&", numeroLinha);
                            }
                            break;
                        case '^':
                            token = new Token(Tokens.OPXORBIN, "^", numeroLinha);
                            break;
                        case '>':
                            if(verificaProximoChar('>'))
                                token = new Token(Tokens.OPSHDIR, ">>", numeroLinha);
                            else
                            {
                                numeroLinha--;
                                if(verificaProximoChar('='))
                                    token = new Token(Tokens.OPMAIORIGUAL, ">=", numeroLinha);
                                else
                                {
                                    numeroLinha--;
                                    token = new Token(Tokens.OPMAIOR, ">", numeroLinha);
                                }
                            }
                            break;
                        case '<':
                            if(verificaProximoChar('<'))
                                token = new Token(Tokens.OPSHESQ, "<<", numeroLinha);
                            else
                            {
                                numeroLinha--;
                                if(verificaProximoChar('='))
                                    token = new Token(Tokens.OPMENORIGUAL, "<=", numeroLinha);
                                else
                                {
                                    numeroLinha--;
                                    token = new Token(Tokens.OPMENOR, "<", numeroLinha);
                                }
                            }
                            break;
                        case '+':
                            if(verificaProximoChar('+'))
                                token = new Token(Tokens.OPINCR, "++", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPSOMA, "+", numeroLinha);
                            }
                            break;
                        case '-':
                            if(verificaProximoChar('-'))
                                token = new Token(Tokens.OPDECR, "--", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPSUB, "-", numeroLinha);
                            }
                            break;
                        case '/':
                            token = new Token(Tokens.OPDIV, "/", posicaoLinha);
                            break;
                        case '%':
                            token = new Token(Tokens.OPMOD, "%", posicaoLinha);
                            break;
                        case '!':
                            if(verificaProximoChar('='))
                                token = new Token(Tokens.OPDIFER, "!=", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPNOTLOG, "!", numeroLinha);
                            }
                            break;
                        case '~':
                            token = new Token(Tokens.OPNOTBIN, "~", numeroLinha);
                            break;
                        default:
                            if(ch == '"')
                            {
                                estado = EstadosAutomato.STRING;
                                lexema = String.valueOf(ch);
                            }
                            else if(ch == Character.getNumericValue('/'))
                            {
                                estado = EstadosAutomato.COMENTARIO;
                                lexema = String.valueOf(ch);
                            }
                            else if(Character.isAlphabetic(ch))
                            {
                                estado = EstadosAutomato.IDENTIFICADOR;
                                lexema = String.valueOf(ch);
                            }
                            else if(Character.isDigit(ch))
                            {
                                estado = EstadosAutomato.NUM_INTEIRO;
                                lexema = String.valueOf(ch);
                            }
                            break;
                    }
                break;
            }
        }
        return token;
    }
}
