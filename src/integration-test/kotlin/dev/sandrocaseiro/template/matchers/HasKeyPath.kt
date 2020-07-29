package dev.sandrocaseiro.template.matchers

import org.hamcrest.*

class HasKeyPath<K, V, KT, VT>(
    private val keyName: String,
    private val nextKeyMatcher: Matcher<*>
): TypeSafeDiagnosingMatcher<Map<KT, VT>>() where KT: K, VT: V {
    override fun describeTo(description: Description) {
        description
            .appendText("map containing [")
            .appendValue(this.keyName)
            .appendText("->")
            .appendDescriptionOf(this.nextKeyMatcher)
            .appendText("]")
    }

    override fun matchesSafely(map: Map<KT, VT>, mismatch: Description): Boolean {
        return keyOn(map, mismatch)
            .matching(convert(nextKeyMatcher))
    }

    private fun keyOn(map: Map<out K?, V?>, mismatch: Description): Condition<Map<*, *>?> {
        val var2: Iterator<*> = map.entries.iterator()
        var entry: Map.Entry<*, *>
        do {
            if (!var2.hasNext()) {
                return Condition.notMatched()
            }
            entry = var2.next() as Map.Entry<*, *>
        } while (!Matchers.equalTo(keyName).matches(entry.key))
        return Condition.matched(entry.value as Map<*, *>?, mismatch)
    }

    private fun convert(matcher: Matcher<*>) = matcher as Matcher<Map<*, *>?>

    companion object {
        fun hasKeyAtPath(path: String): Matcher<*> {
            val keys = path.split("\\.".toRegex())
            val iterator: ListIterator<String> = keys.listIterator(keys.size)

            var ret: Any? = null
            while (iterator.hasPrevious()) {
                ret = if (ret == null) Matchers.hasKey(iterator.previous()) else HasKeyPath<Any?, Any?, Any?, Any?>(iterator.previous(), ret as Matcher<*>)
            }

            return ret as Matcher<*>
        }
    }
}
