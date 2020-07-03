package org.fugalang.kgrammar

import org.fugalang.core.parser.ElementType
import org.fugalang.core.parser.ParseTree

@Suppress("NOTHING_TO_INLINE")
class Parser(@JvmField val t: ParseTree) {
    inline operator fun NormalRule.invoke(func: () -> Boolean): Boolean {
        val m: Boolean? = t.enter(this.rule)
        if (m != null) {
            return m
        }
        val r = func()
        t.exit(r)
        return r
    }

    inline operator fun LeftRecRule.invoke(func: () -> Boolean): Boolean {
        val m: Boolean? = t.enter(this.rule)
        if (m != null) {
            return m
        }
        var p = t.position()
        var s = false
        while (true) {
            t.cache(s)
            val r = func()
            s = r || s
            val e = t.position()
            if (e <= p) break
            p = e
        }
        t.restore(p)
        t.exit(s)
        return s
    }

    inline operator fun Boolean.plus(b: ElementType): Boolean {
        return this && t.consume(b)
    }

    inline operator fun Boolean.div(b: ElementType): Boolean {
        return this || t.consume(b)
    }

    inline operator fun Boolean.plus(b: String): Boolean {
        return this && t.consume(b)
    }

    inline operator fun Boolean.div(b: String): Boolean {
        return this || t.consume(b)
    }

    inline operator fun Boolean.plus(b: Boolean): Boolean {
        return this && b
    }

    inline operator fun Boolean.div(b: Boolean): Boolean {
        return this || b
    }


    inline fun delim(s: String, b: () -> Boolean): Boolean {
        t.enterLoop()
        val r = b()
        if (r) while (true) {
            val p = t.position()
            if (t.skip(s) && b()) continue
            t.reset(p)
            break
        }
        t.exitLoop()
        return r
    }

    inline fun opt(func: () -> Boolean): Boolean {
        func()
        return true
    }
}