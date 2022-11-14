package com.github.yeetmanlord.zeta_core.sql.connection.batch;

import com.github.yeetmanlord.zeta_core.sql.connection.SQLHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLBatchStatement {

    protected List<List<Object>> batches;

    protected String statement;

    public SQLBatchStatement(String statement) {
        this.statement = statement;
        this.batches = new ArrayList<>();
    }

    public SQLBatchStatement(AsyncSQLBatchStatement statement) {
        this.statement = statement.statement;
        this.batches = statement.batches;
    }

    public void addBatch(Object... args) {
        this.batches.add(Arrays.asList(args));
    }

    public void execute(final SQLHandler handler) {
        try {
            PreparedStatement statement = handler.getClient().prepareStatement(this.statement);
            for (List<Object> args : batches) {
                for (int x = 0; x < args.size(); x++) {
                    statement.setObject(x + 1, args.get(x));
                }
                statement.addBatch();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


}
