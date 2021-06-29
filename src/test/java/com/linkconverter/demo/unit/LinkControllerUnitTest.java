package com.linkconverter.demo.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkconverter.demo.controller.LinkController;
import com.linkconverter.demo.model.DeepLinkRequest;
import com.linkconverter.demo.model.WebUrlRequest;
import com.linkconverter.demo.service.LinkService;
import com.linkconverter.demo.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LinkController.class)
public class LinkControllerUnitTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LinkService linkService;

	@Test
	public void testOnlyPathWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865");
		String expected = "ty://?Page=Product&ContentId=1925865";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testOnlyPathDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Product&ContentId=1925865");
		String expected = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithBoutiqueWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892");
		String expected = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithBoutiqueDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Product&ContentId=1925865&CampaignId=439892");
		String expected = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithMerchantWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=105064");
		String expected = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithMerchantDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Product&ContentId=1925865&MerchantId=105064");
		String expected = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=105064";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithBoutiqueAndMerchantWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064");
		String expected = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testWithBoutiqueAndMerchantDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064");
		String expected = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testSearchWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/tum--urunler?q=elbise");
		String expected = "ty://?Page=Search&Query=elbise";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testSearchDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Search&Query=elbise");
		String expected = "https://www.trendyol.com/tum--urunler?q=elbise";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testSearchWithTurkishCharWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/tum--urunler?q=elbişe");
		String expected = "ty://?Page=Search&Query=elbi%C5%9Fe";

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testSearchWithTurkishCharDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Search&Query=elbi%C5%9Fe");
		String expected = "https://www.trendyol.com/tum--urunler?q=elbişe";

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testHesabimFavorilerWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/Hesabim/Favoriler");
		String expected = UrlUtil.DEEP_LINK_HOME;

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testHesabimSiparislerimWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/Hesabim/#/Siparislerim");
		String expected = UrlUtil.DEEP_LINK_HOME;

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testInvalidUrlWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("unidentified://url");
		String expected = UrlUtil.DEEP_LINK_HOME;

		Mockito.when(linkService.convertToDeepLink(request.getWebUrl())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testInvalidUrlDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("unidentified://url");
		String expected = UrlUtil.WEB_URL_HOME;

		Mockito.when(linkService.convertToWebUrl(request.getDeepLink())).thenReturn(expected);
		MvcResult mvcResult = mvc.perform(get("/api/weburl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	private String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
