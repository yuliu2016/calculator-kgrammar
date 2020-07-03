package org.fugalang.kgrammar

import org.fugalang.core.token.TokenType

/*
# Calculator Grammar

sum:
    | sum '+' term
    | sum '-' term
    | term
term:
    | term '*' factor
    | term '/' factor
    | term '%' factor
    | factor
factor:
    | '+' factor
    | '-' factor
    | '~' factor
    | power
power:
    | atom '**' factor
    | atom
atom:
    | '(' sum ')'
    | NAME '(' [parameters] ')'
    | NAME
    | NUMBER
parameters:
    | ','.sum+ [',']
 */

val sum = leftRecRule("sum")
val sum1 = andRule("sum1")
val sum2 = andRule("sum2")

val term = leftRecRule("term")
val term1 = andRule("term1")
val term2 = andRule("term2")
val term3 = andRule("term3")

val factor = orRule("factor")
val factor1 = orRule("factor1")
val factor2 = orRule("factor2")
val factor3 = orRule("factor3")

val power = orRule("power")
val power1 = andRule("power1")

val atom = orRule("atom")
val atom1 = orRule("atom1")
val atom2 = orRule("atom2")

val parameters = andRule("parameters")

fun Parser.sum(): Boolean = sum {
    false /
            sum1 { sum() + "+" + term() } /
            sum2 { sum() + "-" + term() } /
            term()
}

fun Parser.term(): Boolean = term {
    false /
            term1 { term() + "*" + factor() } /
            term2 { term() + "/" + factor() } /
            term3 { term() + "%" + factor() } /
            factor()
}

fun Parser.factor(): Boolean = factor {
    false /
            factor1 { true + "+" + factor() } /
            factor2 { true + "-" + factor() } /
            factor3 { true + "~" + factor() } /
            power()
}

fun Parser.power(): Boolean = power {
    false /
            power1 { atom() + "**" + factor() } /
            atom()
}

fun Parser.atom(): Boolean = atom {
    false /
            atom1 { true + "(" + sum() + ")" } /
            atom2 { true + TokenType.NAME + "(" + parameters() + ")" } /
            TokenType.NAME /
            TokenType.NUMBER
}

fun Parser.parameters(): Boolean = parameters {
    true + delim(",") { sum() } + opt { true + "," }
}