package com.br.INFUSE.repositer;

import com.br.INFUSE.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByNumeroPedido(Integer numeroPedido);
    List<Pedido> findByDataCadastro(LocalDateTime dataCadastro);
}
