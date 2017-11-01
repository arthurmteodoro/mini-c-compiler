package com.compiladores.lexico;

public enum Tokens
{
    ERRO, EOF, PTOVIR, VIR, CHAR, FLOAT, INT, ID, NUMINT, NUMFLOAT,
    ABRECHAVE, FECHACHAVE, ABREPAR, FECHAPAR, ABRECOL, FECHACOL,
    IF, ELSE, WHILE, FOR, RETURN, BREAK, CONTINUE, OPMULT, OPATRIB,
    OPORBIN, OPANDBIN, OPXORBIN, OPSHDIR, OPSHESQ, OPSOMA, OPSUB,
    OPDIV, OPMOD, OPINCR, OPDECR, STR, OPIGUAL, OPDIFER, OPMAIOR,
    OPMAIORIGUAL, OPMENORIGUAL, OPMENOR, OPORLOG, OPANDLOG, OPNOTLOG,
    OPNOTBIN;
    
    @Override
    public String toString()
    {
        switch(this)
        {
            case ERRO:
                return "Token inválido";
            case EOF:
                return "Final de Arquivo";
            case PTOVIR:
                return "';'";
            case VIR:
                return "','";
            case CHAR:
                return "identificador 'char'";
            case FLOAT:
                return "identificador 'float'";
            case INT:
                return "identificador 'int'";
            case ID:
                return "identificador";
            case NUMINT:
                return "número 'inteiro'";
            case NUMFLOAT:
                return "número 'float'";
            case ABRECHAVE:
                return "'{'";
            case FECHACHAVE:
                return "'}'";
            case ABREPAR:
                return "'('";
            case FECHAPAR:
                return "')'";
            case ABRECOL:
                return "'['";
            case FECHACOL:
                return "']'";
            case IF:
                return "comando 'if'";
            case ELSE:
                return "comando 'else'";
            case WHILE:
                return "comando 'while'";
            case FOR:
                return "comando 'for'";
            case RETURN:
                return "comando 'return'";
            case BREAK:
                return "comando 'break'";
            case CONTINUE:
                return "comando 'continue'";
            case OPMULT:
                return "operador '*'";
            case OPATRIB:
                return "operador '='";
            case OPORBIN:
                return "operador '|'";
            case OPANDBIN:
                return "operador '&'";
            case OPXORBIN:
                return "operador '^'";
            case OPSHDIR:
                return "operador '>>'";
            case OPSHESQ:
                return "operador '<<'";
            case OPSOMA:
                return "operador '+'";
            case OPSUB:
                return "operador '-'";
            case OPDIV:
                return "operador '/'";
            case OPMOD:
                return "operador '%'";
            case OPINCR:
                return "operador '++'";
            case OPDECR:
                return "operador '--'";
            case STR:
                return "string";
            case OPIGUAL:
                return "operador '=='";
            case OPDIFER:
                return "operador '!='";
            case OPMAIOR:
                return "operador '>'";
            case OPMAIORIGUAL:
                return "operador '>='";
            case OPMENORIGUAL:
                return "operador '<='";
            case OPMENOR:
                return "operador '<'";
            case OPORLOG:
                return "operador '||'";
            case OPANDLOG:
                return "operador '&&'";
            case OPNOTLOG:
                return "operador '!'";
            case OPNOTBIN:
                return "operador '~'";
            default:
                return "";
        }
    }
}
