package com.tommy.gimnasiofitness.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio")
public class ClaseEntrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Indique el nombre de la clase")
    @Size(max = 100, message = "El nombre debe tener maximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "Indique el precio de la clase")
    @DecimalMin(value = "0.01", message = "El precio debe ser positivo")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "Seleccione una categoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private AreaEntrenamiento categoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public AreaEntrenamiento getCategoria() {
        return categoria;
    }

    public void setCategoria(AreaEntrenamiento categoria) {
        this.categoria = categoria;
    }
}
