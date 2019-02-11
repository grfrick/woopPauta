package br.com.sicredi.woop.pauta.enums;

public enum SimNao {
    SIM("SIM"),
    NAO("NAO");

    private String description;

    private SimNao(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
