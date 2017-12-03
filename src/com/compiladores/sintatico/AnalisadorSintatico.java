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
        System.out.println("Compilação realizada com sucesso.");
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
        if(lookahead.getToken() == Tokens.ID)
        {
            consume(Tokens.ID);
            restoextern2();
        }
        else
        {
            consume(Tokens.OPMULT);
            consume(Tokens.ID);
            restovars();
    
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
            //lambda();
            consume(Tokens.PTOVIR);
        }
    }
    
    private void type()
    {
        if(lookahead.getToken() == Tokens.INT)
            consume(Tokens.INT);
        if(lookahead.getToken() == Tokens.FLOAT)
            consume(Tokens.FLOAT);
        if(lookahead.getToken() == Tokens.CHAR)
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
        if(lookahead.getToken() == Tokens.FECHACOL)
        {
            consume(Tokens.FECHACOL);
        }
        else
        {
            consume(Tokens.NUMINT);
            consume(Tokens.FECHACOL);
        }
    }
    
    private void dcls()
    {
        if(lookahead.getToken() == Tokens.CHAR || lookahead.getToken() == Tokens.FLOAT ||
           lookahead.getToken() == Tokens.INT)
        {
            var();
            //consume(Tokens.PTOVIR);
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
        if(lookahead.getToken() == Tokens.ID)
        {
            consume(Tokens.ID);
            restolval();
        }
        else
        {
            consume(Tokens.OPMULT);
            consume(Tokens.ID);
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
        boolean r1 = or();
        restoexpr(r1);
    }
    
    private void restoexpr(boolean lval)
    {
        if(lookahead.getToken() == Tokens.OPATRIB)
        {
            if(lval)
            {
                consume(Tokens.OPATRIB);
                expr();
            }
            else
            {
                error_semantico("Erro de Atribuição");
            }
        }
        else
        {
            lambda();
        }
    }
    
    private boolean or()
    {
        boolean r1 = and();
        boolean r2 = restoor();
        return r1 && r2;
    }
    
    private boolean restoor()
    {
        if(lookahead.getToken() == Tokens.OPORLOG)
        {
            consume(Tokens.OPORLOG);
            or();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean and()
    {
        boolean r1 = not();
        boolean r2 = restoand();
        return r1 && r2;
    }
    
    private boolean restoand()
    {
        if(lookahead.getToken() == Tokens.OPANDLOG)
        {
            consume(Tokens.OPANDLOG);
            and();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean not()
    {
        if(lookahead.getToken() == Tokens.OPNOTLOG)
        {
            consume(Tokens.OPNOTLOG);
            not();
            return false;
        }
        else
        {
            return cfator();
        }
    }
    
    private boolean cfator()
    {
        boolean r1 = orbin();
        boolean r2 = restocfator();
        return r1 && r2;
    }
    
    private boolean restocfator()
    {
        if(lookahead.getToken() == Tokens.OPIGUAL)
        {
            consume(Tokens.OPIGUAL);
            orbin();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPDIFER)
        {
            consume(Tokens.OPDIFER);
            orbin();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMENORIGUAL)
        {
            consume(Tokens.OPMENORIGUAL);
            orbin();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMAIORIGUAL)
        {
            consume(Tokens.OPMAIORIGUAL);
            orbin();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMENOR)
        {
            consume(Tokens.OPMENOR);
            orbin();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMAIOR)
        {
            consume(Tokens.OPMAIOR);
            orbin();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean orbin()
    {
        boolean r1 = xorbin();
        boolean r2 = restoorbin();
        return r1 && r2;
    }
    
    private boolean restoorbin()
    {
        if(lookahead.getToken() == Tokens.OPORBIN)
        {
            consume(Tokens.OPORBIN);
            xorbin();
            restoorbin();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean xorbin()
    {
        boolean r1 = andbin();
        boolean r2 = restoxorbin();
        return r1 && r2;
    }
    
    private boolean restoxorbin()
    {
        if(lookahead.getToken() == Tokens.OPXORBIN)
        {
            consume(Tokens.OPXORBIN);
            andbin();
            restoxorbin();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean andbin()
    {
        boolean r1 = rola();
        boolean r2 = restoandbin();
        return r1 && r2;
    }
    
    private boolean restoandbin()
    {
        if(lookahead.getToken() == Tokens.OPANDBIN)
        {
            consume(Tokens.OPANDBIN);
            rola();
            restoandbin();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean rola()
    {
        boolean r1 = soma();
        boolean r2 = restorola();
        return r1 && r2;
    }
    
    private boolean restorola()
    {
        if(lookahead.getToken() == Tokens.OPSHESQ)
        {
            consume(Tokens.OPSHESQ);
            soma();
            restorola();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPSHDIR)
        {
            consume(Tokens.OPSHDIR);
            soma();
            restorola();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean soma()
    {
        boolean r1 = mult();
        boolean r2 = restosoma();
        return r1 && r2;
    }
    
    private boolean restosoma()
    {
        if(lookahead.getToken() == Tokens.OPSOMA)
        {
            consume(Tokens.OPSOMA);
            mult();
            restosoma();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPSUB)
        {
            consume(Tokens.OPSUB);
            mult();
            restosoma();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean mult()
    {
        boolean r1 = ender();
        boolean r2 = restomult();
        return r1 && r2;
    }
    
    private boolean restomult()
    {
        if(lookahead.getToken() == Tokens.OPMULT)
        {
            consume(Tokens.OPMULT);
            boolean r1 = ender();
            boolean r2 = restomult();
            return r1 && r2;
        }
        else if(lookahead.getToken() == Tokens.OPDIV)
        {
            consume(Tokens.OPDIV);
            ender();
            restomult();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMOD)
        {
            consume(Tokens.OPMOD);
            ender();
            restomult();
            return false;
        }
        else
        {
            lambda();
            return true;
        }
    }
    
    private boolean ender()
    {
        if(lookahead.getToken() == Tokens.OPANDBIN)
        {
            consume(Tokens.OPANDBIN);
            lval();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPMULT)
        {
            boolean error;
            consume(Tokens.OPMULT);
            error = ender();
            if(!error)
                error_semantico("Referenciação inválida");
            return error;
        }
        else
        {
            return uno();
        }
    }
    
    private boolean uno()
    {
        if(lookahead.getToken() == Tokens.OPSUB)
        {
            consume(Tokens.OPSUB);
            uno();
            return false;
        }
        else if(lookahead.getToken() == Tokens.OPSOMA)
        {
            consume(Tokens.OPSOMA);
            uno();
            return false;
        }
        else
        {
            return notbin();
        }
    }
    
    private boolean notbin()
    {
        if(lookahead.getToken() == Tokens.OPNOTBIN)
        {
            consume(Tokens.OPNOTBIN);
            notbin();
            return false;
        }
        else
        {
            return incpre();
        }
    }
    
    private boolean incpre()
    {
        if(lookahead.getToken() == Tokens.OPINCR)
        {
            consume(Tokens.OPINCR);
            lval();
            return true;
        }
        else if(lookahead.getToken() == Tokens.OPDECR)
        {
            consume(Tokens.OPDECR);
            lval();
            return true;
        }
        else
        {
            return incpos();
        }
    }
    
    private boolean incpos()
    {
        boolean retorno = fator();
        restoincpos();
        return retorno;
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
    
    private boolean fator()
    {
        if(lookahead.getToken() == Tokens.STR)
        {
            consume(Tokens.STR);
            return false;
        }
        else if(lookahead.getToken() == Tokens.ABREPAR)
        {
            consume(Tokens.ABREPAR);
            expr();
            consume(Tokens.FECHAPAR);
            return false;
        }
        else if(lookahead.getToken() == Tokens.NUMINT)
        {
            consume(Tokens.NUMINT);
            return false;
        }
        else if(lookahead.getToken() == Tokens.NUMFLOAT)
        {
            consume(Tokens.NUMFLOAT);
            return false;
        }
        else
        {
            consume(Tokens.ID);
            return restofator1();
        }
    }
    
    private boolean restofator1()
    {
        if(lookahead.getToken() == Tokens.ABRECOL)
        {
            consume(Tokens.ABRECOL);
            expr();
            consume(Tokens.FECHACOL);
            return true;
        }
        else if(lookahead.getToken() == Tokens.ABREPAR)
        {
            consume(Tokens.ABREPAR);
            restofator2();
            return false;
        }
        else
        {
            lambda();
            return true;
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
            System.err.println("Erro na linha "+lookahead.getLinha());
            System.err.println("Era esperado o token " + token + " porém foi recebido "+ lookahead.getToken()+".");
            System.exit(1);
        }
    }
    
    private void error_semantico(String error)
    {
        System.err.println("Erro semântico na linha "+lookahead.getLinha());
        System.err.println(error);
        System.exit(1);
    }
}
