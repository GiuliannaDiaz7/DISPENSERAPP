package com.devicedispenzer.dispenzermockup;

import java.util.ArrayList;
import java.util.List;

public class HistorialData {

    private static HistorialData instance;
    private List<String> registros;

    private HistorialData() {
        registros = new ArrayList<>();
    }

    public static synchronized HistorialData getInstance() {
        if (instance == null) {
            instance = new HistorialData();
        }
        return instance;
    }

    public void addRegistro(String r) {
        registros.add(r);
    }

    public List<String> getRegistros() {
        return registros;
    }
}
