package me.profelements.dynatech.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.thebusybiscuit.slimefun5.libraries.keys.NamespacedKey;

public class LiquidRegistry {
    private static final ArrayList<Liquid> LIQUIDS = new ArrayList<>();

    private static LiquidRegistry INSTANCE;

    private LiquidRegistry() {
        INSTANCE = this;
    }

    public static LiquidRegistry getInstance() {
        return INSTANCE;
    }

    public static LiquidRegistry init() {
        return new LiquidRegistry();
    }

    public LiquidRegistry addLiquid(Liquid liquid) {
        LIQUIDS.add(liquid);
        return this;
    }

    public List<Liquid> getLiquids() {
        return LIQUIDS;
    }

    public final Liquid getByKey(NamespacedKey key) {
        return getLiquids().stream().filter(r -> r.getKey().equals(key)).collect(Collectors.toList()).get(0);
    }
}
