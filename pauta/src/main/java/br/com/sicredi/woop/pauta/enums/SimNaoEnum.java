package br.com.sicredi.woop.pauta.enums;

public enum SimNaoEnum {
    SIM("SIM"),
    NAO("NAO");

    private String description;

    private SimNaoEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
