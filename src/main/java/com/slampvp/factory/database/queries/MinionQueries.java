package com.slampvp.factory.database.queries;

import org.intellij.lang.annotations.Language;

public final class MinionQueries {
    public static final class Insert {
        @Language("PostgreSQL")
        public static final String MINION = """
                INSERT INTO minions (minion_id, owner, time_active, amount_generated, position, chest_position)
                VALUES (?,?,?,?,?,?);
                """;
    }

    public static final class Select {
        @Language("PostgreSQL")
        public static final String BY_OWNER = "SELECT * FROM minions WHERE owner = ?";
    }

    public static final class Delete {
        @Language("PostgreSQL")
        public static final String BY_ID = "DELETE FROM plots WHERE id = ?";
    }
}
