package com.slampvp.factory.database.queries;

import org.intellij.lang.annotations.Language;

public final class PlotQueries {
    public static final class Insert {
        @Language("SQL")
        public static final String PLOT = """
            INSERT INTO plots (plot_id, owner, start, "end", spawn)
            VALUES (?,?,?,?,?);
            """;

        @Language("SQL")
        public static final String FLAGS = """
                INSERT INTO plot_flags (plot_id, target, flags)
                VALUES (?,?,?);
                """;

        @Language("SQL")
        public static final String MEMBER = """
                INSERT INTO plot_members (plot_id, uuid, type)
                VALUES (?,?,?);
                """;

        @Language("SQL")
        public static final String WARP = """
                INSERT INTO plot_warps (plot_id, name, pos)
                VALUES (?,?,?);
                """;

        @Language("SQL")
        public static final String BANNED_PLAYER = """
                INSERT INTO plot_banned_players (plot_id, uuid)
                VALUES (?,?);
                """;
    }

    public static final class Select {
        @Language("SQL")
        public static final String FLAGS = "SELECT * FROM plot_flags WHERE plot_id = ?;";

        @Language("SQL")
        public static final String MEMBERS = "SELECT * FROM plot_members WHERE plot_id = ?;";

        @Language("SQL")
        public static final String WARPS = "SELECT * FROM plot_warps WHERE plot_id = ?;";

        @Language("SQL")
        public static final String BANNED_PLAYERS = "SELECT * FROM plot_banned_players WHERE plot_id = ?;";

        @Language("SQL")
        public static final String JOINED_BY_OWNER = """
                SELECT * FROM plots
                LEFT OUTER JOIN plot_flags flags ON plots.id = flags.plot_id
                LEFT OUTER JOIN plot_banned_players banned_players ON plots.id = banned_players.plot_id
                LEFT OUTER JOIN plot_members  members ON plots.id = members.plot_id
                LEFT OUTER JOIN plot_warps warps ON plots.id = warps.plot_id
                WHERE owner = ?
                """;
    }
}
