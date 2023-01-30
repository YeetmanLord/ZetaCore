package com.github.yeetmanlord.zeta_core.sql.connection;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SQLBatchStatement {

    protected List<List<Object>> batches;

    protected String statement;

    public SQLBatchStatement(String statement) {
        this.statement = statement;
        this.batches = new ArrayList<>();
    }

    public void addBatch(Object... args) {
        this.batches.add(Arrays.asList(args));
    }

    public void execute(final SQLHandler handler, boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(ZetaCore.getInstance(), () -> this.execute(handler));
        } else {
            this.execute(handler);
        }
    }


    private void execute(final SQLHandler handler) {
        if (handler != null && handler.getClient().isConnected()) {
            try (Connection conn = handler.getClient().getSource().getConnection(); PreparedStatement statement = conn.prepareStatement(this.statement)) {

                for (List<Object> objects : new ArrayList<>(this.batches)) {
                    List<Object> batch = new ArrayList<>(objects);
                    Iterator<Object> argIter = batch.iterator();
                    int i = 1;
                    while (argIter.hasNext()) {
                        Object arg = argIter.next();
                        statement.setObject(i, arg);
                        i++;
                    }
                    statement.addBatch();
                }
                statement.executeBatch();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
