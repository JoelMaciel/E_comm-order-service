package com.joel.order_service.infrastructure.adapters.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEntity {

    @Column(name = "shipping_address_street", nullable = false)
    private String street;

    @Column(name = "shipping_address_number", nullable = false)
    private String number;

    @Column(name = "shipping_address_complement")
    private String complement;

    @Column(name = "shipping_address_district", nullable = false)
    private String district;

    @Column(name = "shipping_address_city", nullable = false)
    private String city;

    @Column(name = "shipping_address_state", nullable = false)
    private String state;

    @Column(name = "shipping_address_zip_code", nullable = false)
    private String zipCode;

    @Column(name = "shipping_address_country", nullable = false)
    private String country;

    @Column(name = "shipping_address_reference_point")
    private String referencePoint;
}
