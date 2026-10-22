package com.transportadora.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transportadora.management.dto.ClienteDTO;
import com.transportadora.management.dto.PedidoDTO;
import com.transportadora.management.dto.mapper.ClienteMapper;
import com.transportadora.management.dto.mapper.PedidoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PrecosSabadoMapperTest {

    private final ObjectMapper json = new ObjectMapper().findAndRegisterModules();
    private final ClienteMapper clienteMapper = new ClienteMapper();
    private final PedidoMapper pedidoMapper = new PedidoMapper();

    @Test
    void preservaTodosOsPrecosNaIdaEVolta() throws Exception {
        ObjectNode payload = json.createObjectNode();
        for (int i = 0; i < campos().length; i++) {
            payload.put(campos()[i], "R$ " + (100 + i) + ",50");
        }
        for (String campo : new String[]{"valorAjudanteVuc", "valorAjudanteToco",
                "adicionalMangueira", "adicionalBombaGas"}) {
            payload.put(campo, "R$ 1,00");
        }
        ClienteDTO cliente = json.treeToValue(payload, ClienteDTO.class);
        payload.put("ajudanteToco", "NÃO");
        payload.put("bombaGas", "NÃO");
        PedidoDTO pedido = json.treeToValue(payload, PedidoDTO.class);

        assertThat(clienteMapper.toDTO(clienteMapper.toEntity(cliente))).isEqualTo(cliente);
        assertThat(pedidoMapper.toDTO(pedidoMapper.toEntity(pedido))).isEqualTo(pedido);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void normalizaCamposOmitidosOuNulos(boolean nuloExplicito) throws Exception {
        ObjectNode payload = json.createObjectNode();
        if (nuloExplicito) {
            for (String campo : campos()) {
                payload.putNull(campo);
            }
        }
        ClienteDTO cliente = clienteMapper.toDTO(
                clienteMapper.toEntity(json.treeToValue(payload, ClienteDTO.class)));
        PedidoDTO pedido = pedidoMapper.toDTO(
                pedidoMapper.toEntity(json.treeToValue(payload, PedidoDTO.class)));

        for (String campo : campos()) {
            assertThat(json.valueToTree(cliente).get(campo).asText()).isEmpty();
            assertThat(json.valueToTree(pedido).get(campo).asText()).isEmpty();
        }
    }

    private String[] campos() {
        return new String[]{"precoCx5Sabado", "precoCx10Sabado", "precoCx15Sabado",
                "precoLv5Sabado", "precoLv10Sabado", "precoLv15Sabado"};
    }
}
