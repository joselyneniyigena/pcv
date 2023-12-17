package rw.ac.auca.pcv.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String province;
    private String district;
    private String sector;
    private String cell;
    private String village;
}
