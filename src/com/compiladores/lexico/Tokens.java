package com.compiladores.lexico;

public enum Tokens
{
    ERRO, EOF, PTOVIR, VIR, CHAR, FLOAT, INT, ID, NUMINT, NUMFLOAT,
    ABRECHAVE, FECHACHAVE, ABREPAR, FECHAPAR, ABRECOL, FECHACOL,
    IF, ELSE, WHILE, FOR, RETURN, BREAK, CONTINUE, OPMULT, OPATRIB,
    OPORBIN, OPANDBIN, OPXORBIN, OPSHDIR, OPSHESQ, OPSOMA, OPSUB,
    OPDIV, OPMOD, OPINCR, OPDECR, STR, OPIGUAL, OPDIFER, OPMAIOR,
    OPMAIORIGUAL, OPMENORIGUAL, OPMENOR, OPORLOG, OPANDLOG, OPNOTLOG,
    OPNOTBIN
}