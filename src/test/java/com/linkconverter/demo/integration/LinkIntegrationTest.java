package com.linkconverter.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkconverter.demo.LinkConverterApplication;
import com.linkconverter.demo.entity.Link;
import com.linkconverter.demo.model.DeepLinkRequest;
import com.linkconverter.demo.model.WebUrlRequest;
import com.linkconverter.demo.repository.LinkRepository;
import com.linkconverter.demo.util.EncoderUtils;
import com.linkconverter.demo.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class LinkIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testOnlyPathWebToDeep() throws Exception {
		DeepLinkRequest request = new DeepLinkRequest();
		request.setWebUrl("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865");
		String expected = "ty://?Page=Product&ContentId=1925865";

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
		String expected = "https://www.trendyol.com/brand/name-p-1925865";

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
		String expected = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892";

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
		String expected = "https://www.trendyol.com/brand/name-p-1925865?merchantId=105064";

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
		String expected = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064";

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
		String queryString = "elbişe";
		String encodedQueryString = EncoderUtils.encode(queryString);
		String path = "https://www.trendyol.com/tum--urunler?q=";
		request.setWebUrl(path + queryString);
		String expected = "ty://?Page=Search&Query=" + encodedQueryString;

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
		String queryString = "elbişe";
		String encodedQueryString = EncoderUtils.encode(queryString);
		String path = "ty://?Page=Search&Query=";
		request.setDeepLink(path + queryString);
		String expected = "https://www.trendyol.com/tum--urunler?q=" + encodedQueryString;

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

		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testHesabimFavorilerDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Favorites");
		String expected = UrlUtil.WEB_URL_HOME;

		MvcResult mvcResult = mvc.perform(get("/api/weburl")
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

		MvcResult mvcResult = mvc.perform(get("/api/deeplink")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isOk())
				.andReturn();
		String actual = mvcResult.getResponse().getContentAsString();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testHesabimSiparislerimDeepToWeb() throws Exception {
		WebUrlRequest request = new WebUrlRequest();
		request.setDeepLink("ty://?Page=Orders");
		String expected = UrlUtil.WEB_URL_HOME;

		MvcResult mvcResult = mvc.perform(get("/api/weburl")
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
