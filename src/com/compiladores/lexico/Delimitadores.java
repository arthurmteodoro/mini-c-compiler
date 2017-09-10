package com.compiladores.lexico;

public class Delimitadores
{
    public static boolean isDelimiter(char ch)
    {
        if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '&' || ch == '|' ||
           ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '{' || ch == '}' ||
           ch == ';' || ch == ',' || ch == '~' || ch == '^' || ch == ' ' || ch == '=' ||
           ch == '\n')
            return true;
        return false;
    }
}
