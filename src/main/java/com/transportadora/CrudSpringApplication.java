package com.transportadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

//	@Bean
//	CommandLineRunner initDatagase(ClienteRepository clienteRepository){
//		return args -> {
//			clienteRepository.deleteAll();
//
//			for (int i = 0; i < 20; i++) {
//				Cliente c = new Cliente();
//				c.setNome("Ricardo Inclusão Cliente " + i);
//				c.setCpfcnpj("324.149.231-98");
//				c.setTelefone("(11) 5028-3066");
//				c.setCelular("(11) 98828-0548");
//				c.setEmail("ricardo@ricardo.com");
//				c.setCep("04053-070");
//				c.setLogradouro("Rua Quinze de Setembro");
//				c.setNumero("50");
//				c.setComplemento("Apto 64");
//				c.setBairro("Vila Saúde");
//				c.setCidade("São Paulo");
//				c.setEstado("SP");
//
//				Pedido p1 = new Pedido();
//				p1.setNomePedido("Ricardo P1 Backend inclusão pedido");
//				p1.setRazaoSocial("Condomínio Turquesa");
//				p1.setCpfcnpjPedido("33.041.260/0652-90");
//				p1.setTipoPgto("Á Vista");
//				p1.setCepPedido("09250-760");
//				p1.setLogradouroPedido("Rua das Conchas");
//				p1.setNumeroPedido("244");
//				p1.setComplementoPedido("Casa");
//				p1.setBairroPedido("Vila Clarice");
//				p1.setCidadePedido("Santo André");
//				p1.setEstadoPedido("SP");
//				p1.setSfobras("622323");
//				p1.setCno("2114561");
//				p1.setIe("21321");
//				p1.setMangueira("30 metros");
//				p1.setVolume("lav-5m³");
//				p1.setPrecoCx5("R$ 150,00");
//				p1.setPrecoCx10("R$ 130,00");
//				p1.setPrecoCx15("R$ 160,00");
//				p1.setPrecoLv5("R$ 100,00");
//				p1.setPrecoLv10("R$ 180,00");
//				p1.setPrecoLv15("R$ 160,00");
//				p1.setAjudanteHora("R$ 110,00");
//				p1.setObservacao("R$ 190,00");
//				p1.setStatus(Status.EMITIDO);
//				p1.setCliente(c);
//				c.getPedidos().add(p1);
//
//				Pedido p2 = new Pedido();
//				p2.setNomePedido("Ricardo P2 Backend inclusão pedido");
//				p2.setRazaoSocial("Condomínio Turquesa");
//				p2.setCpfcnpjPedido("33.041.260/0652-90");
//				p2.setTipoPgto("Á Vista");
//				p2.setCepPedido("09250-760");
//				p2.setLogradouroPedido("Rua das Conchas");
//				p2.setNumeroPedido("244");
//				p2.setComplementoPedido("Casa");
//				p2.setBairroPedido("Vila Clarice");
//				p2.setCidadePedido("Santo André");
//				p2.setEstadoPedido("SP");
//				p2.setSfobras("622323");
//				p2.setCno("2114561");
//				p2.setIe("21321");
//				p2.setMangueira("30 metros");
//				p2.setVolume("lav-5m³");
//				p2.setPrecoCx5("R$ 150,00");
//				p2.setPrecoCx10("R$ 130,00");
//				p2.setPrecoCx15("R$ 160,00");
//				p2.setPrecoLv5("R$ 100,00");
//				p2.setPrecoLv10("R$ 180,00");
//				p2.setPrecoLv15("R$ 160,00");
//				p2.setAjudanteHora("R$ 110,00");
//				p2.setObservacao("R$ 190,00");
//				p2.setStatus(Status.EMITIDO);
//				p2.setCliente(c);
//				c.getPedidos().add(p2);
//
//				clienteRepository.save(c);
//			}
//		};
//	}
}
