package com.github.yeetmanlord.zeta_core.sql.connection.batch;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.ZetaPlugin;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;
import org.bukkit.Bukkit;

public class AsyncSQLBatchStatement extends SQLBatchStatement {
    public AsyncSQLBatchStatement(String statement) {
        super(statement);
    }

    public AsyncSQLBatchStatement(SQLBatchStatement statement) {
        super(statement.statement);
        this.batches = statement.batches;
    }

    @Override
    public void execute(final SQLHandler handler) {
        Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.INSTANCE, () -> {
            super.execute(handler);
        });
    }
}
