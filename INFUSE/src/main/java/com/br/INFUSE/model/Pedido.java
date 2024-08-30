package com.br.INFUSE.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Setter
@Getter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer numeroPedido;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private int codigoCliente;

    @Column(nullable = false)
    private BigDecimal valorTotal;

}

