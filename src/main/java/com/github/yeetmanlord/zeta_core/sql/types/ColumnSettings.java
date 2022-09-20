package com.github.yeetmanlord.zeta_core.sql.types;

/**
 * This is to add special settings to columns. Whether that be making that
 * column nonNull or unsigned.
 *
 * @author YeetManLord
 */
public class ColumnSettings {

    /**
     * Each different option. If you do not know what this does. Look at SQL
     * documentations not here. Or just use Stack Overflow!
     */
    private boolean nonNull, unique, unsigned, index;

    /**
     * Default value with nothing special
     */
    public static final ColumnSettings DEFAULT = new SettingsBuilder().build();

    /**
     * This should be used for all primary keys. This is because primary keys must be nonNull. If you want NULL as a possible value please specify "unique" instead.
     */
    public static final ColumnSettings PRIMARY_KEY = new SettingsBuilder().setNonNull(true).build();

    public ColumnSettings(boolean nonNull, boolean unique, boolean unsigned) {

        this(nonNull, unique, unsigned, false);

    }

    public ColumnSettings(boolean nonNull, boolean unique, boolean unsigned, boolean index) {

        this.nonNull = nonNull;
        this.unique = unique;
        this.unsigned = unsigned;
        this.index = index;

        if (unique) {
            this.index = true;
        }

    }

    public boolean isNonNull() {

        return nonNull;

    }

    public void setNonNull(boolean nonNull) {

        this.nonNull = nonNull;

    }

    public boolean isUnique() {

        return unique;

    }

    public void setUnique(boolean unique) {

        this.unique = unique;

    }

    public boolean isUnsigned() {

        return unsigned;

    }

    public void setUnsigned(boolean unsigned) {

        this.unsigned = unsigned;

    }

    public boolean hasIndex() {

        return index;

    }

    public void setIndex(boolean index) {

        this.index = index;

    }

    /**
     * Simple Settings builder to make one liner settings easier.
     *
     * @author YeetManLord
     */
    public static class SettingsBuilder {

        private boolean nonNull = false;

        private boolean unique = false;

        private boolean unsigned = false;

        private boolean index = false;

        public SettingsBuilder() {

        }

        public SettingsBuilder setNonNull(boolean nonNull) {

            this.nonNull = nonNull;
            return this;

        }

        public SettingsBuilder setUnique(boolean unique) {

            this.unique = unique;

            if (unique) {
                this.index = true;
            }

            return this;

        }

        public SettingsBuilder setUnsigned(boolean unsigned) {

            this.unsigned = unsigned;
            return this;

        }

        public SettingsBuilder setIndex(boolean index) {

            this.index = index;
            return this;

        }

        /**
         * @return Finalizes builder and returns an instance of ColumnSettings with the
         * preferred settings.
         */
        public ColumnSettings build() {

            return new ColumnSettings(nonNull, unique, unsigned, index);

        }

    }

}
