package org.fugalang.kgrammar

import org.fugalang.core.parser.ParserRule

fun andRule(name: String): NormalRule {
    return NormalRule(ParserRule.and_rule(name))
}

fun orRule(name: String): NormalRule {
    return NormalRule(ParserRule.or_rule(name))
}

fun leftRecRule(name: String): LeftRecRule {
    return LeftRecRule(ParserRule.leftrec_rule(name))
}

sealed class GrammerRule(val rule: ParserRule) {
}

class NormalRule(rule: ParserRule) : GrammerRule(rule)
class LeftRecRule(rule: ParserRule) : GrammerRule(rule)