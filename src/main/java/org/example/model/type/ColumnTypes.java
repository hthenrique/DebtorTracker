package org.example.model.type;

public enum ColumnTypes {

    NAME("name", "nome"),
    DOC_NUMBER("doc_number", "cpf"),
    EMAIL("email", "email");

    private final String columnName;
    private final String columnNick;

    ColumnTypes(String columnName, String columnNick) {
        this.columnName = columnName;
        this.columnNick = columnNick;
    }

    public String getColumnName() {
        return columnName;
    }
    public String getColumnNick() {
        return columnNick;
    }

    public static ColumnTypes fromColumnNick(String columnNick) {
        for (ColumnTypes type : ColumnTypes.values()) {
            if (type.getColumnNick().equalsIgnoreCase(columnNick)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Apelido de coluna inválido: " + columnNick);
    }
}
