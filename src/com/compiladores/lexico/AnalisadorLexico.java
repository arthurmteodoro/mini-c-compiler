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
        this.linha = arquivo.readLine().concat("\n");
        this.numeroLinha = 1;
        this.posicaoLinha = 0;
    }

    public void abreArquivo(String nomeArquivo) throws IOException
    {
        this.arquivo = new BufferedReader(new FileReader(nomeArquivo));
        this.arquivoNome = nomeArquivo;
        this.linha = arquivo.readLine().concat("\n");
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

    private char getChar()
    {
        if(linha == null)
            return 0;

        if(posicaoLinha == linha.length())
        {
            try
            {
                linha = arquivo.readLine();
                if(linha == null)//caso leu o EOF
                {
                    numeroLinha++;
                    return 0;
                }
                linha = linha.concat("\n");
                numeroLinha++;
                posicaoLinha = 0;
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

    private void getNewLine()
    {
        try
        {
            linha = arquivo.readLine().concat("\n");
            posicaoLinha = 0;
            numeroLinha++;
        } catch(IOException e)
        {
            e.printStackTrace();
        }
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
                            token = new Token(Tokens.FECHAPAR, ")", numeroLinha);
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
                                posicaoLinha--;
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
                                posicaoLinha--;
                                if(verificaProximoChar('='))
                                    token = new Token(Tokens.OPMAIORIGUAL, ">=", numeroLinha);
                                else
                                {
                                    posicaoLinha--;
                                    token = new Token(Tokens.OPMAIOR, ">", numeroLinha);
                                }
                            }
                            break;
                        case '<':
                            if(verificaProximoChar('<'))
                                token = new Token(Tokens.OPSHESQ, "<<", numeroLinha);
                            else
                            {
                                posicaoLinha--;
                                if(verificaProximoChar('='))
                                    token = new Token(Tokens.OPMENORIGUAL, "<=", numeroLinha);
                                else
                                {
                                    posicaoLinha--;
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
                            if(verificaProximoChar('/'))
                            {
                                getNewLine();
                                continue;
                            }
                            else
                            {
                                posicaoLinha--;
                                token = new Token(Tokens.OPDIV, "/", posicaoLinha);
                            }
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
                            if(ch == ' ' || ch == '\n' || ch == '\t')
                                continue;
                            else if(ch == 0)
                                token = new Token(Tokens.EOF, "", numeroLinha);
                            else if(ch == '"')
                            {
                                estado = EstadosAutomato.STRING;
                                lexema = String.valueOf(ch);
                            }
                            else if(Character.isLetter(ch))
                            {
                                estado = EstadosAutomato.IDENTIFICADOR;
                                lexema = String.valueOf(ch);
                            }
                            else if(Character.isDigit(ch))
                            {
                                estado = EstadosAutomato.NUM_INTEIRO;
                                lexema = String.valueOf(ch);
                            }
                            else
                            {
                                estado = EstadosAutomato.ERRO;
                                lexema = String.valueOf(ch);
                            }
                            break;
                    }
                break;

                case NUM_INTEIRO:
                    ch = getChar();
                    if(Delimitadores.isDelimiter(ch))
                    {
                        token = new Token(Tokens.NUMINT, lexema, numeroLinha);
                        posicaoLinha--;
                    }
                    else
                    {
                        if(ch == '.')
                        {
                            estado = EstadosAutomato.NUM_REAL_DECIMAL;
                            lexema += String.valueOf('.');
                            continue;
                        }
                        else if(ch == 'e')
                        {
                            estado = EstadosAutomato.NUM_REAL_EXP;
                            lexema += String.valueOf('e');
                            continue;
                        }
                        else if(Character.isDigit(ch))
                            lexema += String.valueOf(ch);
                        else
                        {
                            estado = EstadosAutomato.ERRO;
                            lexema += String.valueOf(ch);
                            continue;
                        }
                    }
                    break;
                    
                case NUM_REAL_DECIMAL:
                    ch = getChar();
                    if(Delimitadores.isDelimiter(ch))
                    {
                        token = new Token(Tokens.NUMFLOAT, lexema, numeroLinha);
                        posicaoLinha--;
                    }
                    else
                    {
                        if(ch == 'e')
                        {
                            estado = EstadosAutomato.NUM_REAL_EXP;
                            lexema += String.valueOf('e');
                            continue;
                        }
                        else if(Character.isDigit(ch))
                            lexema += String.valueOf(ch);
                        else
                        {
                            estado = EstadosAutomato.ERRO;
                            lexema += String.valueOf(ch);
                            continue;
                        }
                    }
                    break;
                    
                case NUM_REAL_EXP:
                    ch = getChar();
                    if(Delimitadores.isDelimiter(ch) && lexema.charAt(lexema.length()-1) != 'e')
                    {
                        if(Character.isDigit(lexema.charAt(lexema.length()-1)))
                            token = new Token(Tokens.NUMFLOAT, lexema, numeroLinha);
                        else
                            token = new Token(Tokens.ERRO, lexema, numeroLinha);
                        
                        posicaoLinha--;
                    }
                    else
                    {
                        if(ch == '+' || ch == '-')
                        {
                            if(lexema.charAt(lexema.length()-1) == 'e')
                            {
                                lexema += String.valueOf(ch);
                            }
                            else
                            {
                                lexema += String.valueOf(ch);
                                estado = EstadosAutomato.ERRO;
                                continue;
                            }
                        }
                        else if(Character.isDigit(ch))
                            lexema += String.valueOf(ch);
                        else
                        {
                            lexema += String.valueOf(ch);
                            estado = EstadosAutomato.ERRO;
                            continue;
                        }
                    }
                    break;

                case IDENTIFICADOR:
                    ch = getChar();
                    if(Delimitadores.isDelimiter(ch))
                    {
                        switch(lexema)
                        {
                            case "char":
                                token = new Token(Tokens.CHAR, lexema, numeroLinha);
                                break;
                            case "float":
                                token = new Token(Tokens.FLOAT, lexema, numeroLinha);
                                break;
                            case "int":
                                token = new Token(Tokens.INT, lexema, numeroLinha);
                                break;
                            case "if":
                                token = new Token(Tokens.IF, lexema, numeroLinha);
                                break;
                            case "else":
                                token = new Token(Tokens.ELSE, lexema, numeroLinha);
                                break;
                            case "while":
                                token = new Token(Tokens.WHILE, lexema, numeroLinha);
                                break;
                            case "for":
                                token = new Token(Tokens.FOR, lexema, numeroLinha);
                                break;
                            case "return":
                                token = new Token(Tokens.RETURN, lexema, numeroLinha);
                                break;
                            case "break":
                                token = new Token(Tokens.BREAK, lexema, numeroLinha);
                                break;
                            case "continue":
                                token = new Token(Tokens.CONTINUE, lexema, numeroLinha);
                                break;
                            default:
                                token = new Token(Tokens.ID, lexema, numeroLinha);
                                break;
                        }
                        posicaoLinha--;
                    }
                    else
                    {
                        if(Character.isLetterOrDigit(ch) || ch == '_')
                            lexema += String.valueOf(ch);
                        else
                        {
                            estado = EstadosAutomato.ERRO;
                            lexema += String.valueOf(ch);
                            continue;
                        }
                    }
                    break;
                case STRING:
                        ch = getChar();
                        if(ch == '"' && lexema.charAt(lexema.length()-1) != '\\')
                        {
                            lexema += String.valueOf(ch);
                            token = new Token(Tokens.STR, lexema, numeroLinha);
                        }
                        else if(ch == '\n')
                        {
                            if(lexema.charAt(lexema.length()-1) != '"')
                                token = new Token(Tokens.ERRO, lexema, numeroLinha);
                        }
                        else
                            lexema += String.valueOf(ch);
                    break;

                case ERRO:
                    ch = getChar();
                    if(Delimitadores.isDelimiter(ch))
                    {
                        token = new Token(Tokens.ERRO, lexema, numeroLinha);
                        posicaoLinha--;
                    }
                    else
                        lexema += String.valueOf(ch);
                    break;
            }
        }
        return token;
    }
}
