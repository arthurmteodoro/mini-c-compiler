package com.compiladores.lexico;

public enum EstadosAutomato
{
    NAO_ALFANUMERICO, NUM_INTEIRO, NUM_REAL_DECIMAL, NUM_REAL_EXP,
    IDENTIFICADOR, STRING, ERRO
}