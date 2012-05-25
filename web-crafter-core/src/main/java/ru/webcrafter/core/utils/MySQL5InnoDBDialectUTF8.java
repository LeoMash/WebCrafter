package ru.webcrafter.core.utils;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class MySQL5InnoDBDialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + " DEFAULT CHARSET=utf8";
    }
}