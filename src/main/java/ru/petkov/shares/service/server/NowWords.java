package ru.petkov.shares.service.server;

public enum NowWords {

    STOP("stop"),
    LIST("list"),
    ADD("add");

    public final String word;

    NowWords(String word) {
        this.word = word;
    }
}
