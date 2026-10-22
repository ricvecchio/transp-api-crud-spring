package com.transportadora.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transportadora.management.dto.PedidoDTO;
import com.transportadora.management.dto.mapper.PedidoMapper;
import com.transportadora.management.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoOpcoesMapperTest {

    private final ObjectMapper json = new ObjectMapper().findAndRegisterModules();
    private final PedidoMapper mapper = new PedidoMapper();

    @ParameterizedTest
    @CsvSource({"SIM,SIM", "SIM,NÃO", "NÃO,SIM", "NÃO,NÃO"})
    void preservaOpcoesIndependentesNaIdaEVolta(String entregaSabado, String exibirPreco) throws Exception {
        ObjectNode payload = json.createObjectNode();
        payload.put("entregaSabado", entregaSabado);
        payload.put("exibirPreco", exibirPreco);
        payload.put("ajudante", "SIM");
        payload.put("adicional", "NÃO");
        PedidoDTO dto = json.treeToValue(payload, PedidoDTO.class);

        assertThat(mapper.toDTO(mapper.toEntity(dto)))
                .extracting(PedidoDTO::entregaSabado, PedidoDTO::exibirPreco,
                        PedidoDTO::ajudante, PedidoDTO::adicional)
                .containsExactly(entregaSabado, exibirPreco, "SIM", "NÃO");
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void normalizaOpcoesOmitidasOuNulas(boolean nuloExplicito) throws Exception {
        ObjectNode payload = json.createObjectNode();
        if (nuloExplicito) {
            payload.putNull("entregaSabado");
            payload.putNull("exibirPreco");
        }
        PedidoDTO dto = json.treeToValue(payload, PedidoDTO.class);
        assertThat(dto.entregaSabado()).isEqualTo("NÃO");
        assertThat(dto.exibirPreco()).isEqualTo("NÃO");
        Pedido pedido = mapper.toEntity(dto);
        assertThat(pedido.getEntregaSabado()).isEqualTo("NÃO");
        assertThat(pedido.getExibirPreco()).isEqualTo("NÃO");
        assertThat(mapper.toDTO(pedido))
                .extracting(PedidoDTO::entregaSabado, PedidoDTO::exibirPreco)
                .containsExactly("NÃO", "NÃO");
    }

    @Test
    void retornaNaoParaEntidadeLegadaComCamposNulos() {
        Pedido pedido = new Pedido();
        ReflectionTestUtils.setField(pedido, "entregaSabado", null);
        ReflectionTestUtils.setField(pedido, "exibirPreco", null);

        assertThat(mapper.toDTO(pedido).entregaSabado()).isEqualTo("NÃO");
        assertThat(mapper.toDTO(pedido).exibirPreco()).isEqualTo("NÃO");
        pedido.setEntregaSabado(null);
        pedido.setExibirPreco(null);
        assertThat(pedido.getEntregaSabado()).isEqualTo("NÃO");
        assertThat(pedido.getExibirPreco()).isEqualTo("NÃO");
    }
}
