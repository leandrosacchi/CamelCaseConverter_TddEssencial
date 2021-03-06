package Pedido;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Pedido.Desconto.CalculadoraFaixaDesconto;
import Pedido.Desconto.CalculadoraDescontoPrimeiraFaixa;
import Pedido.Desconto.CalculadoraDescontoSegundaFaixa;
import Pedido.Desconto.CalculadoraDescontoTerceiraFaixa;
import Pedido.Desconto.SemDesconto;


public class PedidoTest {
	private PedidoBuilder pedido;

	@Before
	public void setup() {
		pedido = new PedidoBuilder();
	}
		
	private void assertResumoPedido(double valorTotal, double desconto) {
		ResumoPedido resumoPedido = pedido.construir().resumo();
//		assertEquals(valorTotal, resumoPedido.getValorTotal(), 0.0001);
//		assertEquals(desconto, resumoPedido.getDesconto(), 0.0001);
		
		assertEquals(new ResumoPedido(valorTotal, desconto), resumoPedido);
	}
	 
	@Test
	public void devePermitirAdicionarUmItemNoPedido() {
		pedido.comItem(3.0, 10);

	}
	
	@Test
	public void deveCalcularOValorTotalEDescontoParaPedidoVazio() {
		assertResumoPedido(0.0, 0.0);
	}

	@Test
	public void deveCalcularResumoParaUmItemSemDesconto() {
		//pedido.adicionarItem(new ItemPedido("Sabonete", 5.0, 5));
		pedido.comItem(5.0, 5);
		assertResumoPedido(25.0, 0.0);
	}
	
	@Test
	public void deveCalcularResumoParaDoisOuMaisItensSemDesconto() {
//		pedido.adicionarItem(new ItemPedido("Sabonete", 3.0, 3));
//		pedido.adicionarItem(new ItemPedido("Pasta de dente", 7.0, 3));
		pedido.comItem(3.0, 3)
			.comItem(7.0, 3);

		assertResumoPedido(30.0, 0.0);
	}
	
	@Test
	public void deveAplicarDescontoPrimeiraFaixa() {
	//	pedido.adicionarItem(new ItemPedido("Creme", 20.0, 20));
		pedido.comItem(20.0, 20);

		assertResumoPedido(400.0, 16.0);
	}
	
	@Test
	public void deveAplicarDescontoSegundaFaixa() {
//		pedido.adicionarItem(new ItemPedido("Shampoo", 15.0, 30));
//		pedido.adicionarItem(new ItemPedido("Oleo", 15.0, 30));
		pedido.comItem(15.0, 30)
		.comItem(15.0, 30);
		assertResumoPedido(900.0, 54.0);
	}
	
	@Test
	public void deveAplicarDescontoTerceiraFaixa() {
//		pedido.adicionarItem(new ItemPedido("Shampoo", 10.0, 30));
//		pedido.adicionarItem(new ItemPedido("Oleo", 15.0, 30));
//		pedido.adicionarItem(new ItemPedido("Creme", 15.0, 30));
		
		pedido.comItem(10.0, 30)
		.comItem(15.0, 30)
		.comItem(15.0, 30);
		assertResumoPedido(1200.0, 96.0);
	}
	
	@Test(expected = QuantidadeItensInvalidaException.class)
	public void naoAceitarPedidosComItensComQuantidadeNegativa() {
		pedido.comItem(0.0, -10);
	}
		
}
