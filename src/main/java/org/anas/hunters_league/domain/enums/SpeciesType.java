package org.anas.hunters_league.domain.enums;


public enum SpeciesType {

    SEA(9), BIG_GAME(3), BIRD(5);

    private final int value;

    SpeciesType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
