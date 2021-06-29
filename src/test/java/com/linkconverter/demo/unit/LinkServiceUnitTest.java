package com.linkconverter.demo.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.linkconverter.demo.entity.Link;
import com.linkconverter.demo.repository.LinkRepository;
import com.linkconverter.demo.service.LinkService;
import com.linkconverter.demo.service.LinkServiceImpl;
import com.linkconverter.demo.util.EncoderUtils;
import com.linkconverter.demo.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LinkServiceImpl.class})
public class LinkServiceUnitTest {

	@Autowired
	private LinkService linkService;

	@MockBean
	private LinkRepository linkRepository;

	@Test
	public void testOnlyPathWebToDeep() {
		String request = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865";
		String response = "ty://?Page=Product&ContentId=1925865";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testOnlyPathDeepToWeb() {
		String request = "ty://?Page=Product&ContentId=1925865";
		String response = "https://www.trendyol.com/brand/name-p-1925865";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithBoutiqueWebtoDeep() {
		String request = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892";
		String response = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithBoutiqueDeepToWeb() {
		String request = "ty://?Page=Product&ContentId=1925865&CampaignId=439892";
		String response = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithMerchantWebToDeep() {
		String request = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=105064";
		String response = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithMerchantDeepToWeb() {
		String request = "ty://?Page=Product&ContentId=1925865&MerchantId=105064";
		String response = "https://www.trendyol.com/brand/name-p-1925865?merchantId=105064";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithBoutiqueAndMerchantWebToDeep() {
		String request = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064";
		String response = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testWithBoutiqueAndMerchantDeepToWeb() {
		String request = "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064";
		String response = "https://www.trendyol.com/brand/name-p-1925865?boutiqueId=439892&merchantId=105064";
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testSearchWebToDeep() {
		String queryString = "elbise";
		String queryStringEncoded = EncoderUtils.encode(queryString);
		String request = "https://www.trendyol.com/tum--urunler?q=" + queryString;
		String requestEncoded = "https://www.trendyol.com/tum--urunler?q=" + queryStringEncoded;
		String response = "ty://?Page=Search&Query=" + queryStringEncoded;
		Link link = new Link(requestEncoded, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testSearchDeepToWeb() {
		String queryString = "elbise";
		String queryStringEncoded = EncoderUtils.encode(queryString);
		String request = "ty://?Page=Search&Query=" + queryString;
		String requestEncoded = "ty://?Page=Search&Query=" + queryStringEncoded;
		String response = "https://www.trendyol.com/tum--urunler?q=" + queryStringEncoded;
		Link link = new Link(requestEncoded, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testSearchWithTurkishCharWebToDeep() {
		String queryString = "elbişe";
		String queryStringEncoded = EncoderUtils.encode(queryString);
		String request = "https://www.trendyol.com/tum--urunler?q=" + queryString;
		String requestEncoded = "https://www.trendyol.com/tum--urunler?q=" + queryStringEncoded;
		String response = "ty://?Page=Search&Query=" + queryStringEncoded;
		Link link = new Link(requestEncoded, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testSearchWithTurkishCharDeepToWeb() {
		String queryString = "elbişe";
		String queryStringEncoded = EncoderUtils.encode(queryString);
		String request = "ty://?Page=Search&Query=" + queryString;
		String requestEncoded = "ty://?Page=Search&Query=" + queryStringEncoded;
		String response = "https://www.trendyol.com/tum--urunler?q=" + queryStringEncoded;
		Link link = new Link(requestEncoded, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testHesabimFavorilerWebToDeep() {
		String request = "https://www.trendyol.com/Hesabim/Favoriler";
		String response = UrlUtil.DEEP_LINK_HOME;
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testHesabimFavorilerDeepToWeb() {
		String request = "ty://?Page=Favorites";
		String response = UrlUtil.WEB_URL_HOME;
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testHesabimSiparislerimWebToDeep() {
		String request = "https://www.trendyol.com/Hesabim/#/Siparislerim";
		String response = UrlUtil.DEEP_LINK_HOME;
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToDeepLink(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testHesabimSiparislerimDeepToWeb() {
		String request = "ty://?Page=Orders";
		String response = UrlUtil.WEB_URL_HOME;
		Link link = new Link(request, response);
		Mockito.when(linkRepository.save(link)).thenReturn(link);

		String actual = linkService.convertToWebUrl(request);
		assertThat(actual).isEqualTo(response);
	}

	@Test
	public void testInvalidUrlWebToDeep() {
		String request = "asdasd";
		assertThrows(AssertionError.class, () -> linkService.convertToDeepLink(request));
	}

	@Test
	public void testInvalidUrlDeepToWeb() {
		String request = "asdasd";
		assertThrows(AssertionError.class, () -> linkService.convertToWebUrl(request));
	}
}
