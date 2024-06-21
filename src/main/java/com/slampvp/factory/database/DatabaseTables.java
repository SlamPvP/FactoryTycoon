package com.slampvp.factory.database;

final class DatabaseTables {
    static final String TABLES_QUERY = """
            DO
            $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'plot_id') THEN
                        CREATE TYPE PLOT_ID AS
                        (
                            x INT,
                            z INT
                        );
                    END IF;
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'vec') THEN
                        CREATE TYPE VEC AS
                        (
                            x FLOAT,
                            y FLOAT,
                            z FLOAT
                        );
                    END IF;
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'pos') THEN
                        CREATE TYPE POS AS
                        (
                            x     FLOAT,
                            y     FLOAT,
                            z     FLOAT,
                            yaw   FLOAT,
                            pitch FLOAT
                        );
                    END IF;
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'plot_target') THEN
                        CREATE TYPE PLOT_TARGET AS ENUM ('TRUSTED', 'MEMBER', 'PUBLIC');
                    END IF;
                    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'plot_member') THEN
                        CREATE TYPE PLOT_MEMBER AS ENUM ('TRUSTED', 'MEMBER');
                    END IF;
                END
            $$;
            DO
            $$
                BEGIN
                    CREATE TABLE IF NOT EXISTS plots
                    (
                        id      BIGINT GENERATED ALWAYS AS IDENTITY,
                        plot_id PLOT_ID     NOT NULL,
                        owner   VARCHAR(36) NOT NULL,
                        start   VEC         NOT NULL,
                        "end"   VEC         NOT NULL,
                        spawn   POS         NOT NULL,
                        PRIMARY KEY (id)
                    );
                    CREATE INDEX IF NOT EXISTS index_plots_on_owner ON plots (owner);
                    CREATE TABLE IF NOT EXISTS plot_banned_players
                    (
                        plot_id BIGINT      NOT NULL,
                        uuid    VARCHAR(36) NOT NULL,
                        PRIMARY KEY (plot_id, uuid),
                        CONSTRAINT fk_plot FOREIGN KEY (plot_id) REFERENCES plots (id) ON DELETE CASCADE
                    );
                    CREATE UNIQUE INDEX IF NOT EXISTS index_plot_banned_players_on_both ON plot_banned_players (plot_id, uuid);
                    CREATE INDEX IF NOT EXISTS index_plot_banned_players_on_plot_id ON plot_banned_players (plot_id);
                    CREATE TABLE IF NOT EXISTS plot_warps
                    (
                        plot_id BIGINT NOT NULL,
                        name    TEXT   NOT NULL,
                        pos     POS    NOT NULL,
                        PRIMARY KEY (plot_id, name, pos),
                        CONSTRAINT fk_plot FOREIGN KEY (plot_id) REFERENCES plots (id) ON DELETE CASCADE
                    );
                    CREATE UNIQUE INDEX IF NOT EXISTS index_plot_warps_on_all ON plot_warps (plot_id, name, pos);
                    CREATE INDEX IF NOT EXISTS index_plot_warps_on_plot_id ON plot_warps (plot_id);
                    CREATE TABLE IF NOT EXISTS plot_members
                    (
                        plot_id BIGINT      NOT NULL,
                        uuid    VARCHAR(36) NOT NULL,
                        type    PLOT_MEMBER NOT NULL,
                        PRIMARY KEY (plot_id, uuid, type),
                        CONSTRAINT fk_plot FOREIGN KEY (plot_id) REFERENCES plots (id) ON DELETE CASCADE
                    );
                    CREATE UNIQUE INDEX IF NOT EXISTS index_plot_members_on_all ON plot_members (plot_id, uuid, type);
                    CREATE INDEX IF NOT EXISTS index_plot_members_on_plot_id ON plot_members (plot_id);
                    CREATE TABLE IF NOT EXISTS plot_flags
                    (
                        plot_id BIGINT      NOT NULL,
                        target  PLOT_TARGET NOT NULL,
                        flags   INT         NOT NULL,
                        PRIMARY KEY (plot_id, target, flags),
                        CONSTRAINT fk_plot FOREIGN KEY (plot_id) REFERENCES plots (id) ON DELETE CASCADE
                    );
                    CREATE UNIQUE INDEX IF NOT EXISTS index_plot_flags_on_all ON plot_flags (plot_id, target, flags);
                    CREATE INDEX IF NOT EXISTS index_plot_flags_on_plot_id ON plot_flags (plot_id);
                END
            $$;
            """;
}
