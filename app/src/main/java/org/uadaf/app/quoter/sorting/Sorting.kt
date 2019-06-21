package org.uadaf.app.quoter.sorting

import quoter.Quote

enum class Sorting(val comparator: Comparator<Quote>, val section: (Quote) -> String) {

    Id(Comparator { q, p -> q.id.compareTo(p.id) }, { it.id.toString() }),
    Date(Comparator { q, p -> q.date.compareTo(p.date) }, { it.date.toString() }),
    Adder(Comparator { q, p -> q.adder.compareTo(p.adder, true) }, Quote::adder),
    Authors(
        Comparator { q, p -> q.authors.joinToString().compareTo(p.authors.joinToString(), true) },
        { it.authors.joinToString() });

    enum class Direction { Ascending, Descending }

}
