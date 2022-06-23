package com.payment.utils;

import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureVerificationUtil {
	private static final Logger logger = LoggerFactory.getLogger(SignatureVerificationUtil.class);

	/**
	 * This class defines common routines for generating authentication signatures
	 * for Razorpay Webhook requests.
	 */
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	/**
	 * Computes RFC 2104-compliant HMAC signature. * @param data The data to be
	 * signed.
	 * 
	 * @param key The signing key.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException when signature generation fails
	 */
	public static boolean calculateRFC2104HMAC(String data, String secret, String signature)
			throws java.security.SignatureException {
		boolean isVerified = false;
		logger.debug("Start of method calculateRFC2104HMAC");
		try {
			// get an hmac_sha256 key from the raw secret bytes
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);

			// get an hmac_sha256 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			String result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

			isVerified = signature.equals(result);

			logger.debug("Result of method calculateRFC2104HMAC" + result);
		} catch (Exception e) {
			logger.debug("Error while verifing signature in calculateRFC2104HMAC method");
			isVerified = false;
			throw new SignatureException("Failed to generate HMAC signature verification failed: " + e.getMessage());
		}
		logger.debug("End of method calculateRFC2104HMAC");
		return isVerified;
	}
}
