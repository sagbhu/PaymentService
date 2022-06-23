package com.payment.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.constant.PaymentServiceConstant;
import com.payment.request.OrderProductsRequest;
import com.payment.request.RazoerpayOrderRequest;

public final class APIUtils {
	private static final Logger logger = LoggerFactory.getLogger(APIUtils.class);

	public static String doPost(RazoerpayOrderRequest rozerpayOrderRequest) {
		logger.debug("doPost method start in Payment Service APIUtils class");
		String orderResponseJson = "";
		String orderRequestJson = "";
		try {
			orderRequestJson = new ObjectMapper().writeValueAsString(rozerpayOrderRequest);
			HttpPost post = new HttpPost(PaymentServiceConstant.RAZORPAY_ORDER_URL);
			CredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials("rzp_test_jqB2DBpQmJlFYI", "LaXCI7lzvenXJA9LpyHKCh36"));
			StringEntity entity = new StringEntity(orderRequestJson, ContentType.APPLICATION_JSON);
			post.setEntity(entity);

			try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)
					.build(); CloseableHttpResponse response = httpClient.execute(post)) {
				orderResponseJson = EntityUtils.toString(response.getEntity());
			}

		} catch (Exception e) {
			logger.debug("Error while sending post request to razorpay order post api:" + e.getMessage());
		}

		logger.debug("doPost method end in Payment Service APIUtils class");
		return orderResponseJson;
	}
	
	public static String doPost(OrderProductsRequest orderProductsRequest) throws IOException {
		logger.debug("doPost method start in APIUtils class");
		String ordersJson = "";
		ObjectMapper mapper = new ObjectMapper();
		HttpPost post = new HttpPost(PaymentServiceConstant.ORDER_PRODUCTS_URL);
		post.addHeader("content-type", "application/json");
		try {
			post.setEntity(new StringEntity(mapper.writeValueAsString(orderProductsRequest)));
		} catch (JsonProcessingException e1) {
			logger.debug("error when converting object to json" + e1.getMessage());
		} catch (UnsupportedEncodingException e1) {

			logger.debug("error when converting object to json" + e1.getMessage());
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			ordersJson = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			logger.debug("error when converting object to json" + e.getMessage());
		}
		logger.debug("doPost method end in APIUtils class");
		return ordersJson;
	}
}
