
package com.br.INFUSE.controler;

import com.br.INFUSE.model.Pedido;
import com.br.INFUSE.repositer.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class PedidoControllerTests {

    public MockMvc mockMvc;

    @MockBean
    private PedidoRepository pedidoRepository;

    @Test
    public void testCreatePedidoSuccessfully() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(12345);
        pedido.setNomeProduto("Produto Teste");
        pedido.setValorUnitario(new BigDecimal("100.00"));
        pedido.setQuantidade(10);
        pedido.setCodigoCliente(1);
        pedido.setValorTotal(new BigDecimal("900.00"));

        when(pedidoRepository.findByNumeroPedido(12345)).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"controlNumber\":\"12345\",\"productName\":\"Produto Teste\",\"unitValue\":100.00,\"quantity\":10,\"clientCode\":1}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatePedidoWithDuplicateControlNumber() throws Exception {
        when(pedidoRepository.findByNumeroPedido(12345)).thenReturn(Arrays.asList(new Pedido()));

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"controlNumber\":\"12345\",\"productName\":\"Produto Teste\",\"unitValue\":100.00,\"quantity\":5,\"clientCode\":1}]"))
                .andExpect(status().isBadRequest());
    }
}
