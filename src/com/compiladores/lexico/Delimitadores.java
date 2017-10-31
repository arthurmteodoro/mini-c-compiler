package com.compiladores.lexico;

public class Delimitadores
{
    public static boolean isDelimiter(char ch)
    {
        if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '&' || ch == '|' ||
           ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}' ||
           ch == ';' || ch == ',' || ch == '~' || ch == '^' || ch == ' ' || ch == '=' ||
           ch == '<' || ch == '>' || ch == '!' || ch == '\n' || ch == '\t' || ch == '"')
            return true;
        return false;
    }
}
