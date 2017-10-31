package com.compiladores.sintatico;

import com.compiladores.lexico.AnalisadorLexico;
import com.compiladores.lexico.Token;
import com.compiladores.lexico.Tokens;

import java.io.IOException;

public class AnalisadorSintatico
{
    private Token lookahead;
    private AnalisadorLexico analisadorLexico;
    
    public AnalisadorSintatico(String arquivo) throws IOException
    {
        analisadorLexico = new AnalisadorLexico();
        analisadorLexico.abreArquivo(arquivo);
    }
    
    public void parser()
    {
        lookahead = analisadorLexico.getToken();
        program();
        consume(Tokens.EOF);
    }
    
    private void program()
    {
        externs();
    }
    
    private void externs()
    {
        if(lookahead.getToken() == Tokens.ID || lookahead.getToken() == Tokens.CHAR ||
           lookahead.getToken() == Tokens.FLOAT || lookahead.getToken() == Tokens.INT)
        {
            extern();
            externs();
        }
        else
        {
           lambda();
        }
    }
    
    private void extern()
    {
        if(lookahead.getToken() == Tokens.ID)
        {
            consume(Tokens.ID);
            func();
        }
        else
        {
            type();
            restoextern();
        }
    }
    
    private void restoextern()
    {
        if(lookahead.getToken() == Tokens.OPMULT)
        {
            consume(Tokens.OPMULT);
            consume(Tokens.ID);
            restovars();
        }
        else
        {
            consume(Tokens.ID);
            restoextern2();
        }
    }
    
    private void restoextern2()
    {
        if(lookahead.getToken() == Tokens.ABREPAR)
        {
            func();
        }
        else
        {
            restodclr();
            restovars();
        }
    }
    
    private void var()
    {
        type();
        dclr();
        restovars();
    }
    
    private void restovars()
    {
        if(lookahead.getToken() == Tokens.VIR)
        {
            consume(Tokens.VIR);
            dclr();
            restovars();
        }
        else
        {
            lambda();
        }
    }
    
    private void type()
    {
        if(lookahead.getToken() == Tokens.INT)
            consume(Tokens.INT);
        else if(lookahead.getToken() == Tokens.FLOAT)
            consume(Tokens.FLOAT);
        else if(lookahead.getToken() == Tokens.CHAR)
            consume(Tokens.CHAR);
    }
    
    private void dclr()
    {
        if(lookahead.getToken() == Tokens.ID)
        {
            consume(Tokens.ID);
            restodclr();
        }
        else
        {
            consume(Tokens.OPMULT);
            consume(Tokens.ID);
        }
    }
    
    private void restodclr()
    {
        if(lookahead.getToken() == Tokens.ABRECOL)
        {
            consume(Tokens.ABRECOL);
            restodclr2();
        }
        else
        {
            lambda();
        }
    }
    
    private void restodclr2()
    {
        if(lookahead.getToken() == Tokens.NUMINT)
        {
            consume(Tokens.NUMINT);
            consume(Tokens.FECHACOL);
        }
        else
        {
            consume(Tokens.FECHACOL);
        }
    }
    
    private void dcls()
    {
        if(lookahead.getToken() == Tokens.CHAR || lookahead.getToken() == Tokens.FLOAT ||
           lookahead.getToken() == Tokens.INT)
        {
            var();
            consume(Tokens.PTOVIR);
            dcls();
        }
        else
        {
            lambda();
        }
    }
    
    private void func()
    {
        fargs();
        consume(Tokens.ABRECHAVE);
        dcls();
        stmts();
        consume(Tokens.FECHACHAVE);
    }
    
    private void fargs()
    {
        consume(Tokens.ABREPAR);
        restofargs();
    }
    
    private void restofargs()
    {
        if(lookahead.getToken() == Tokens.FECHAPAR)
        {
            consume(Tokens.FECHAPAR);
        }
        else
        {
            args();
            consume(Tokens.FECHAPAR);
        }
    }
    
    private void args()
    {
        type();
        dclr();
        restoargs();
    }
    
    private void restoargs()
    {
        if(lookahead.getToken() == Tokens.VIR)
        {
            consume(Tokens.VIR);
            args();
        }
        else
        {
            lambda();
        }
    }
    
    private void stmts()
    {
        if(lookahead.getToken() == Tokens.IF || lookahead.getToken() == Tokens.WHILE ||
           lookahead.getToken() == Tokens.FOR || lookahead.getToken() == Tokens.RETURN ||
           lookahead.getToken() == Tokens.BREAK || lookahead.getToken() == Tokens.CONTINUE ||
           lookahead.getToken() == Tokens.PTOVIR || lookahead.getToken() == Tokens.OPNOTLOG ||
           lookahead.getToken() == Tokens.OPANDBIN || lookahead.getToken() == Tokens.OPMULT ||
           lookahead.getToken() == Tokens.OPSUB || lookahead.getToken() == Tokens.OPSOMA ||
           lookahead.getToken() == Tokens.OPNOTBIN || lookahead.getToken() == Tokens.OPINCR ||
           lookahead.getToken() == Tokens.OPDECR || lookahead.getToken() == Tokens.ID ||
           lookahead.getToken() == Tokens.ABREPAR || lookahead.getToken() == Tokens.NUMINT ||
           lookahead.getToken() == Tokens.NUMFLOAT || lookahead.getToken() == Tokens.STR ||
           lookahead.getToken() == Tokens.ABRECHAVE)
        {
            stmt();
            stmts();
        }
        else
        {
            lambda();
        }
    }
    
    private void stmt()
    {
        if(lookahead.getToken() == Tokens.IF)
        {
            consume(Tokens.IF);
            consume(Tokens.ABREPAR);
            expro();
            consume(Tokens.FECHAPAR);
            stmt();
            restoif();
        }
        else if(lookahead.getToken() == Tokens.WHILE)
        {
            consume(Tokens.WHILE);
            consume(Tokens.ABREPAR);
            expro();
            consume(Tokens.FECHAPAR);
            stmt();
        }
        else if(lookahead.getToken() == Tokens.FOR)
        {
            consume(Tokens.FOR);
            consume(Tokens.ABREPAR);
            expro();
            consume(Tokens.PTOVIR);
            expro();
            consume(Tokens.PTOVIR);
            expro();
            consume(Tokens.FECHAPAR);
            stmt();
        }
        else if(lookahead.getToken() == Tokens.RETURN)
        {
            consume(Tokens.RETURN);
            restoreturn();
        }
        else if(lookahead.getToken() == Tokens.BREAK)
        {
            consume(Tokens.BREAK);
            consume(Tokens.PTOVIR);
        }
        else if(lookahead.getToken() == Tokens.CONTINUE)
        {
            consume(Tokens.CONTINUE);
            consume(Tokens.PTOVIR);
        }
        else if(lookahead.getToken() == Tokens.ABRECHAVE)
        {
            block();
        }
        else if(lookahead.getToken() == Tokens.PTOVIR)
        {
            consume(Tokens.PTOVIR);
        }
        else
        {
            expr();
            consume(Tokens.PTOVIR);
        }
    }
    
    private void restoif()
    {
        if(lookahead.getToken() == Tokens.ELSE)
        {
            consume(Tokens.ELSE);
            stmt();
        }
        else
        {
            lambda();
        }
    }
    
    private void restoreturn()
    {
        if(lookahead.getToken() == Tokens.PTOVIR)
        {
            consume(Tokens.PTOVIR);
        }
        else
        {
            expr();
            consume(Tokens.PTOVIR);
        }
    }
    
    private void block()
    {
        consume(Tokens.ABRECHAVE);
        stmts();
        consume(Tokens.FECHACHAVE);
    }
    
    private void lval()
    {
        if(lookahead.getToken() == Tokens.OPMULT)
        {
            consume(Tokens.OPMULT);
            consume(Tokens.ID);
        }
        else
        {
            consume(Tokens.ID);
            restolval();
        }
    }
    
    private void restolval()
    {
        if(lookahead.getToken() == Tokens.ABRECOL)
        {
            consume(Tokens.ABRECOL);
            expr();
            consume(Tokens.FECHACOL);
        }
        else
        {
            lambda();
        }
    }
    
    private void expr()
    {
        or();
        restoexpr();
    }
    
    private void restoexpr()
    {
        if(lookahead.getToken() == Tokens.OPATRIB)
        {
            consume(Tokens.OPATRIB);
            expr();
        }
        else
        {
            lambda();
        }
    }
    
    private void or()
    {
        and();
        restoor();
    }
    
    private void restoor()
    {
        if(lookahead.getToken() == Tokens.OPORLOG)
        {
            consume(Tokens.OPORLOG);
            or();
        }
        else
        {
            lambda();
        }
    }
    
    private void and()
    {
        not();
        restoand();
    }
    
    private void restoand()
    {
        if(lookahead.getToken() == Tokens.OPANDLOG)
        {
            consume(Tokens.OPANDLOG);
            and();
        }
        else
        {
            lambda();
        }
    }
    
    private void not()
    {
        if(lookahead.getToken() == Tokens.OPNOTLOG)
        {
            consume(Tokens.OPNOTLOG);
            not();
        }
        else
        {
            cfator();
        }
    }
    
    private void cfator()
    {
        orbin();
        restocfator();
    }
    
    private void restocfator()
    {
        if(lookahead.getToken() == Tokens.OPIGUAL)
        {
            consume(Tokens.OPIGUAL);
            orbin();
        }
        else if(lookahead.getToken() == Tokens.OPDIFER)
        {
            consume(Tokens.OPDIFER);
            orbin();
        }
        else if(lookahead.getToken() == Tokens.OPMENORIGUAL)
        {
            consume(Tokens.OPMENORIGUAL);
            orbin();
        }
        else if(lookahead.getToken() == Tokens.OPMAIORIGUAL)
        {
            consume(Tokens.OPMAIORIGUAL);
            orbin();
        }
        else if(lookahead.getToken() == Tokens.OPMENOR)
        {
            consume(Tokens.OPMENOR);
            orbin();
        }
        else if(lookahead.getToken() == Tokens.OPMAIOR)
        {
            consume(Tokens.OPMAIOR);
            orbin();
        }
    }
    
    private void orbin()
    {
        xorbin();
        restoorbin();
    }
    
    private void restoorbin()
    {
        if(lookahead.getToken() == Tokens.OPORBIN)
        {
            consume(Tokens.OPORBIN);
            xorbin();
            restoorbin();
        }
        else
        {
            lambda();
        }
    }
    
    private void xorbin()
    {
        andbin();
        restoxorbin();
    }
    
    private void restoxorbin()
    {
        if(lookahead.getToken() == Tokens.OPXORBIN)
        {
            consume(Tokens.OPXORBIN);
            andbin();
            restoxorbin();
        }
        else
        {
            lambda();
        }
    }
    
    private void andbin()
    {
        rola();
        restoandbin();
    }
    
    private void restoandbin()
    {
        if(lookahead.getToken() == Tokens.OPANDBIN)
        {
            consume(Tokens.OPANDBIN);
            rola();
            restoandbin();
        }
        else
        {
            lambda();
        }
    }
    
    private void rola()
    {
        soma();
        restorola();
    }
    
    private void restorola()
    {
        if(lookahead.getToken() == Tokens.OPSHESQ)
        {
            consume(Tokens.OPSHESQ);
            soma();
            restorola();
        }
        else if(lookahead.getToken() == Tokens.OPSHDIR)
        {
            consume(Tokens.OPSHDIR);
            soma();
            restorola();
        }
        else
        {
            lambda();
        }
    }
    
    private void soma()
    {
        mult();
        restosoma();
    }
    
    private void restosoma()
    {
        if(lookahead.getToken() == Tokens.OPSOMA)
        {
            consume(Tokens.OPSOMA);
            mult();
            restosoma();
        }
        else if(lookahead.getToken() == Tokens.OPSUB)
        {
            consume(Tokens.OPSUB);
            mult();
            restosoma();
        }
        else
        {
            lambda();
        }
    }
    
    private void mult()
    {
        ender();
        restomult();
    }
    
    private void restomult()
    {
        if(lookahead.getToken() == Tokens.OPMULT)
        {
            consume(Tokens.OPMULT);
            ender();
            restomult();
        }
        else if(lookahead.getToken() == Tokens.OPDIV)
        {
            consume(Tokens.OPDIV);
            ender();
            restomult();
        }
        else if(lookahead.getToken() == Tokens.OPMOD)
        {
            consume(Tokens.OPMOD);
            ender();
            restomult();
        }
        else
        {
            lambda();
        }
    }
    
    private void ender()
    {
        if(lookahead.getToken() == Tokens.OPANDBIN)
        {
            consume(Tokens.OPANDBIN);
            lval();
        }
        else if(lookahead.getToken() == Tokens.OPMULT)
        {
            consume(Tokens.OPMULT);
            ender();
        }
        else
        {
            uno();
        }
    }
    
    private void uno()
    {
        if(lookahead.getToken() == Tokens.OPSUB)
        {
            consume(Tokens.OPSUB);
            uno();
        }
        else if(lookahead.getToken() == Tokens.OPSOMA)
        {
            consume(Tokens.OPSOMA);
            uno();
        }
        else
        {
            notbin();
        }
    }
    
    private void notbin()
    {
        if(lookahead.getToken() == Tokens.OPNOTBIN)
        {
            consume(Tokens.OPNOTBIN);
            notbin();
        }
        else
        {
            incpre();
        }
    }
    
    private void incpre()
    {
        if(lookahead.getToken() == Tokens.OPINCR)
        {
            consume(Tokens.OPINCR);
            lval();
        }
        else if(lookahead.getToken() == Tokens.OPDECR)
        {
            consume(Tokens.OPDECR);
            lval();
        }
        else
        {
            incpos();
        }
    }
    
    private void incpos()
    {
        fator();
        restoincpos();
    }
    
    private void restoincpos()
    {
        if(lookahead.getToken() == Tokens.OPDECR)
        {
            consume(Tokens.OPDECR);
        }
        else if(lookahead.getToken() == Tokens.OPINCR)
        {
            consume(Tokens.OPINCR);
        }
        else
        {
            lambda();
        }
    }
    
    private void fator()
    {
        if(lookahead.getToken() == Tokens.ID)
        {
            consume(Tokens.ID);
            restofator1();
        }
        else if(lookahead.getToken() == Tokens.ABREPAR)
        {
            consume(Tokens.ABREPAR);
            expr();
            consume(Tokens.FECHAPAR);
        }
        else if(lookahead.getToken() == Tokens.NUMINT)
        {
            consume(Tokens.NUMINT);
        }
        else if(lookahead.getToken() == Tokens.NUMFLOAT)
        {
            consume(Tokens.NUMFLOAT);
        }
        else if(lookahead.getToken() == Tokens.STR)
        {
            consume(Tokens.STR);
        }
    }
    
    private void restofator1()
    {
        if(lookahead.getToken() == Tokens.ABRECOL)
        {
            consume(Tokens.ABRECOL);
            expr();
            consume(Tokens.FECHACOL);
        }
        else if(lookahead.getToken() == Tokens.ABREPAR)
        {
            consume(Tokens.ABREPAR);
            restofator2();
        }
        else
        {
            lambda();
        }
    }
    
    private void restofator2()
    {
        if(lookahead.getToken() == Tokens.FECHAPAR)
        {
            consume(Tokens.FECHAPAR);
        }
        else
        {
            exprs();
            consume(Tokens.FECHAPAR);
        }
    }
    
    private void exprs()
    {
        expr();
        restoexprs();
    }
    
    private void restoexprs()
    {
        if(lookahead.getToken() == Tokens.VIR)
        {
            consume(Tokens.VIR);
            exprs();
        }
        else
        {
            lambda();
        }
    }
    
    private void expro()
    {
        if(lookahead.getToken() == Tokens.OPNOTLOG || lookahead.getToken() == Tokens.OPANDBIN ||
           lookahead.getToken() == Tokens.OPMULT || lookahead.getToken() == Tokens.OPSUB ||
           lookahead.getToken() == Tokens.OPSOMA || lookahead.getToken() == Tokens.OPNOTBIN ||
           lookahead.getToken() == Tokens.OPINCR || lookahead.getToken() == Tokens.OPDECR ||
           lookahead.getToken() == Tokens.ID || lookahead.getToken() == Tokens.ABREPAR ||
           lookahead.getToken() == Tokens.NUMINT || lookahead.getToken() == Tokens.NUMFLOAT ||
           lookahead.getToken() == Tokens.STR)
        {
            expr();
        }
        else
        {
            lambda();
        }
    }
    
    private void lambda()
    {
        return;
    }
    
    private void consume(Tokens token)
    {
        if(lookahead.getToken() == token)
        {
            lookahead = analisadorLexico.getToken();
        }
        else
        {
            System.out.println("Erro na linha "+lookahead.getLinha());
            System.out.println("Era esperado o token " + token.name() + " porém foi recebido "+ lookahead.getToken());
            System.exit(1);
        }
    }
}
