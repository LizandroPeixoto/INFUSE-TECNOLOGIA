package com.br.INFUSE.controler;

import com.br.INFUSE.model.Pedido;
import com.br.INFUSE.repositer.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControler {

    private final PedidoRepository pedidoRepository;

    public PedidoControler(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<List<Pedido>> createPedido(@RequestBody List<Pedido> pedidoList) {
        if (pedidoList.size() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        for (Pedido pedido : pedidoList) {

            if (pedidoRepository.findByNumeroPedido(pedido.getNumeroPedido()).size() > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            if (pedido.getDataCadastro() == null) {
                pedido.setDataCadastro(LocalDateTime.now());
            }

            if (pedido.getQuantidade() == 0) {
                pedido.setQuantidade(1);
            }

            BigDecimal totalValue = pedido.getValorUnitario().multiply(BigDecimal.valueOf(pedido.getQuantidade()));

            if (pedido.getQuantidade() > 5) {
                totalValue = totalValue.multiply(BigDecimal.valueOf(0.95));
            }
            if (pedido.getQuantidade() >= 10) {
                totalValue = totalValue.multiply(BigDecimal.valueOf(0.90));
            }

            pedido.setValorTotal(totalValue);
            pedidoRepository.save(pedido);
        }

        return ResponseEntity.ok(pedidoList);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getPedidos(
            @RequestParam(required = false) Integer numeroPedido,
            @RequestParam(required = false) LocalDateTime dataCadastro) {

        List<Pedido> pedidos;

        if (numeroPedido != null) {
            pedidos = pedidoRepository.findByNumeroPedido(numeroPedido);
        } else if (dataCadastro != null) {
            pedidos = pedidoRepository.findByDataCadastro(dataCadastro);
        } else {
            pedidos = pedidoRepository.findAll();
        }

        return ResponseEntity.ok(pedidos);
    }
}
