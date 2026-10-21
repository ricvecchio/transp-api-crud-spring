package com.transportadora.management;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transportadora.management.controller.ClienteController;
import com.transportadora.management.controller.PedidoController;
import com.transportadora.management.dto.mapper.ClienteMapper;
import com.transportadora.management.dto.mapper.PedidoMapper;
import com.transportadora.management.service.ClienteService;
import com.transportadora.management.service.PedidoService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest(properties = "spring.jpa.show-sql=false")
@ActiveProfiles("test")
@Import({ClienteService.class, PedidoService.class, ClienteMapper.class, PedidoMapper.class})
class PrecosSabadoIntegrationTest {

    private static final String[] CAMPOS = {"precoCx5Sabado", "precoCx10Sabado", "precoCx15Sabado",
            "precoLv5Sabado", "precoLv10Sabado", "precoLv15Sabado"};

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private EntityManager entityManager;

    private final ObjectMapper json = new ObjectMapper().findAndRegisterModules();
    private MockMvc mvc;

    @BeforeEach
    void configurarEndpoints() {
        mvc = MockMvcBuilders.standaloneSetup(
                new ClienteController(clienteService), new PedidoController(pedidoService)).build();
    }

    @Test
    void criaAtualizaConsultaEExportaPrecosPersistidos() throws Exception {
        ObjectNode clientePayload = clientePayload();
        JsonNode clienteCriado = enviar(post("/api/clientes"), clientePayload, 201);
        long clienteId = clienteCriado.get("idCliente").asLong();
        recarregar();
        JsonNode cliente = consultar("/api/clientes/" + clienteId);
        conferirPrecos(cliente, clientePayload);

        ObjectNode pedidoPayload = pedidoPayload(cliente);
        JsonNode pedidoCriado = enviar(post("/api/pedidos"), pedidoPayload, 201);
        long pedidoId = pedidoCriado.get("idPedido").asLong();
        recarregar();
        conferirPrecos(consultar("/api/pedidos/" + pedidoId), clientePayload);

        for (int i = 0; i < CAMPOS.length; i++) {
            clientePayload.put(CAMPOS[i], (400 + i) + ",90");
        }
        conferirPrecos(enviar(put("/api/clientes/" + clienteId), clientePayload, 200), clientePayload);
        recarregar();
        conferirPrecos(consultar("/api/clientes/" + clienteId), clientePayload);
        conferirPrecos(consultar("/api/clientes/backup").get(0), clientePayload);
        conferirPrecos(consultar("/api/clientes").get("clientes").get(0), clientePayload);

        // O pedido guarda o snapshot enviado, sem acompanhar futuras alteracoes do cliente.
        conferirPrecos(consultar("/api/pedidos/" + pedidoId), pedidoPayload);
        for (String campo : CAMPOS) {
            pedidoPayload.set(campo, clientePayload.get(campo));
        }
        conferirPrecos(enviar(put("/api/pedidos/" + pedidoId), pedidoPayload, 200), pedidoPayload);
        recarregar();
        conferirPrecos(consultar("/api/pedidos/" + pedidoId), pedidoPayload);
        conferirPrecos(consultar("/api/pedidos?statusFiltro=Aberto").get("pedidos").get(0), pedidoPayload);
        conferirPrecos(consultar("/api/pedidos/ultimos?idCliente=" + clienteId).get(0), pedidoPayload);
        assertThat(consultar("/api/clientes/" + clienteId).get("precoCx5").asText()).isEqualTo("90,00");
        assertThat(consultar("/api/pedidos/" + pedidoId).get("precoCx5").asText()).isEqualTo("90,00");
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void aceitaPayloadAntigoOuNulosNoPostENoPut(boolean nuloExplicito) throws Exception {
        ObjectNode cliente = clientePayload();
        for (String campo : CAMPOS) {
            if (nuloExplicito) {
                cliente.putNull(campo);
            } else {
                cliente.remove(campo);
            }
        }
        JsonNode criado = enviar(post("/api/clientes"), cliente, 201);
        long clienteId = criado.get("idCliente").asLong();
        conferirVazios(criado);
        conferirVazios(enviar(put("/api/clientes/" + clienteId), cliente, 200));

        ObjectNode pedido = pedidoPayload(criado);
        for (String campo : CAMPOS) {
            if (nuloExplicito) {
                pedido.putNull(campo);
            } else {
                pedido.remove(campo);
            }
        }
        JsonNode pedidoCriado = enviar(post("/api/pedidos"), pedido, 201);
        long pedidoId = pedidoCriado.get("idPedido").asLong();
        conferirVazios(pedidoCriado);
        conferirVazios(enviar(put("/api/pedidos/" + pedidoId), pedido, 200));
        recarregar();
        conferirVazios(consultar("/api/clientes/" + clienteId));
        conferirVazios(consultar("/api/pedidos/" + pedidoId));
    }

    private ObjectNode clientePayload() throws Exception {
        try (var input = new ClassPathResource("cliente-precos-sabado.json").getInputStream()) {
            return (ObjectNode) json.readTree(input);
        }
    }

    private ObjectNode pedidoPayload(JsonNode cliente) {
        ObjectNode pedido = ((ObjectNode) cliente).deepCopy();
        pedido.put("idCliente", cliente.get("idCliente").asLong());
        pedido.remove("dataAtualizacaoCliente");
        pedido.put("dataAtualizacaoPedido", "2026-10-21T09:00:00");
        pedido.put("volume", "5");
        pedido.put("ajudante", "");
        pedido.put("adicional", "");
        pedido.put("precoFinal", "100,00");
        pedido.put("observacao2", "");
        pedido.put("status", "Aberto");
        pedido.put("imagemPedido", "");
        return pedido;
    }

    private JsonNode enviar(MockHttpServletRequestBuilder request, JsonNode payload, int status) throws Exception {
        return json.readTree(mvc.perform(request.contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(payload)))
                .andExpect(status().is(status)).andReturn().getResponse().getContentAsString());
    }

    private JsonNode consultar(String url) throws Exception {
        return json.readTree(mvc.perform(get(url)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

    private void conferirPrecos(JsonNode resultado, JsonNode esperado) {
        for (String campo : CAMPOS) {
            assertThat(resultado.get(campo)).as(campo).isEqualTo(esperado.get(campo));
        }
    }

    private void conferirVazios(JsonNode resultado) {
        for (String campo : CAMPOS) {
            assertThat(resultado.get(campo).isTextual()).as(campo).isTrue();
            assertThat(resultado.get(campo).asText()).as(campo).isEmpty();
        }
    }

    private void recarregar() {
        entityManager.flush();
        entityManager.clear();
    }
}
